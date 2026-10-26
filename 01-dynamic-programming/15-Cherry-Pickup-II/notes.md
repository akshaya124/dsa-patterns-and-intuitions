# Cherry Pickup II — 3D Dynamic Programming

- **Problem:** LeetCode 1463 — Cherry Pickup II
- **Topic:** Dynamic Programming
- **Pattern:** Grid DP with two agents / 3D DP
- **Goal:** Two robots start at the top row, one at column `0` and the other at column `m - 1`. Both move down one row at a time and may shift left, stay in the same column, or shift right. Maximize the total cherries collected.

## 1. Intuition

Think of the robots as moving together, row by row. Since both robots are always on the same row, the state needs only three coordinates:

- `i`: current row
- `j1`: Robot 1's column
- `j2`: Robot 2's column

At each row, try every combination of their three possible moves. Add the cherries collected on the current row and choose the combination that leads to the maximum total from the rows below.

**Important:** If both robots land on the same cell, count that cell only once.

## 2. How to recognize this pattern

- There are **two agents** moving through the same grid.
- Their positions both affect the answer.
- Each agent has a small set of possible moves.
- The objective is to maximize a score collected along the paths.
- A state with only `(i, j)` is not enough, because we must remember both robots' columns.

These clues suggest a 3D DP state: `dp[i][j1][j2]`.

## 3. DP state and recurrence

**State:** `dp[i][j1][j2]` is the maximum number of cherries collectable from row `i` through the last row when Robot 1 is at column `j1` and Robot 2 is at column `j2`.

### Current-row cherries

```java
int cherries;
if (j1 == j2) {
    cherries = grid[i][j1];
} else {
    cherries = grid[i][j1] + grid[i][j2];
}
```

### Transition

Each robot can move by an offset of `-1`, `0`, or `1`. Try all nine pairs of offsets:

```java
for (int d1 = -1; d1 <= 1; d1++) {
    for (int d2 = -1; d2 <= 1; d2++) {
        int nj1 = j1 + d1;
        int nj2 = j2 + d2;

        if (nj1 < 0 || nj1 >= m || nj2 < 0 || nj2 >= m) {
            continue;
        }

        int candidate = cherries + dp[i + 1][nj1][nj2];
        best = Math.max(best, candidate);
    }
}
```

Mathematically:

`dp[i][j1][j2] = cherries(i, j1, j2) + max(dp[i + 1][j1 + d1][j2 + d2])`

The maximum is taken over all valid pairs `d1, d2 ∈ {-1, 0, 1}`.

### Base case

On the last row, no more moves are possible:

- If `j1 == j2`, collect `grid[n - 1][j1]`.
- Otherwise, collect `grid[n - 1][j1] + grid[n - 1][j2]`.

The initial state is `dp[0][0][m - 1]`.

## 4. Three approaches

### A. Recursion

Start at `(0, 0, m - 1)`. For each state, calculate the cherries on the current row and recursively try all nine combinations of moves for the next row. Return the maximum valid result.

- **Time:** Exponential in the number of rows in the worst case, because the same states are recomputed many times.
- **Auxiliary space:** `O(n)` for the recursion stack.

### B. Memoization (top-down DP)

Use the same recursive logic, but cache the answer for each state `(i, j1, j2)`. If the state has already been computed, return the cached value.

There are at most `n × m × m` states, and each state checks nine move combinations.

- **Time:** `O(n × m²)`.
- **Space:** `O(n × m²)` for the memo table plus `O(n)` recursion stack.

**Implementation note:** Use a separate visited array or a safe sentinel for uncomputed states. Do not confuse a valid DP result with an uncomputed value.

### C. Tabulation (bottom-up DP)

Build the 3D DP table from the last row upward:

1. Initialize `dp[n - 1][j1][j2]` for every pair of columns.
2. Iterate rows from `n - 2` down to `0`.
3. For every pair `(j1, j2)`, try all nine move combinations.
4. Ignore moves that leave the grid.
5. Store the best result in `dp[i][j1][j2]`.
6. Return `dp[0][0][m - 1]`.

- **Time:** `O(n × m² × 9) = O(n × m²)`.
- **Space:** `O(n × m²)` for the DP table.

### Optional optimization: reduce space

The transition for row `i` uses only row `i + 1`. Therefore, the full 3D table can be replaced by two `m × m` tables: `front` (the next row) and `curr` (the current row). After processing a row, set `front = curr`.

- **Time:** `O(n × m²)`.
- **Space:** `O(m²)`.

## 5. Dry run

Example:

```text
grid = [
  [3, 1, 1],
  [2, 5, 1],
  [1, 5, 5],
  [2, 1, 1]
]
```

The robots start at `(0, 0)` and `(0, 2)`.

One optimal route is:

- Row 0: columns `0` and `2` → cherries `3 + 1 = 4`
- Row 1: columns `1` and `1` → cherries `5` (shared cell counted once)
- Row 2: columns `1` and `2` → cherries `5 + 5 = 10`
- Row 3: columns `0` and `1` → cherries `2 + 1 = 3`

Total for this route: `4 + 5 + 10 + 3 = 22`.

The DP explores every valid pair of robot moves and returns the maximum among all possible routes.

## 6. Common mistakes to avoid

- **Counting a shared cell twice:** When `j1 == j2`, add the cell value only once.
- **Wrong boundary check:** Column `0` is valid. Use `nj1 >= 0` and `nj2 >= 0`, not `> 0`.
- **Rewarding invalid moves:** Never add a large positive number for an invalid transition in a maximization problem. Skip the move with `continue`.
- **Forgetting to save the state:** After finding `best`, assign `dp[i][j1][j2] = best`.
- **Returning the wrong state:** The robots start at columns `0` and `m - 1`, so return `dp[0][0][m - 1]`.
- **Wrong loop direction in tabulation:** Since the transition uses row `i + 1`, calculate the last row first and work upward.
- **Incorrect complexity:** There are `n × m²` states and nine transitions per state, so time is `O(n × m²)`.

## 7. Complexity comparison

| Approach | Time | Auxiliary space |
|---|---:|---:|
| Recursion | Exponential | `O(n)` stack |
| Memoization | `O(n × m²)` | `O(n × m²)` cache + `O(n)` stack |
| Tabulation | `O(n × m²)` | `O(n × m²)` |
| Space optimization | `O(n × m²)` | `O(m²)` |

Here, `n` is the number of rows and `m` is the number of columns.

## 8. Revision checklist

- [ ] Explain why the state needs both robot columns.
- [ ] Write the base case for the last row.
- [ ] Explain why there are nine move combinations.
- [ ] Handle the case when both robots share one cell.
- [ ] Write the recurrence without looking at the notes.
- [ ] Explain why tabulation works from bottom to top.
- [ ] Explain how two 2D arrays reduce the space.
- [ ] Revisit the problem after 3 days and 7 days.

## 9. Key takeaway

When two agents move through a grid and both positions affect the result, track both positions in the DP state. Here, `dp[i][j1][j2]` captures the row and both robot columns. Enumerating all valid move pairs lets us choose the maximum cherries without counting a shared cell twice.
