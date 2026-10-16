# Triangle — Minimum Path Sum

- **Problem:** LeetCode 120 — Triangle
- **Topic:** Dynamic Programming
- **Pattern:** Grid/triangle DP, top-down or bottom-up state transitions
- **Goal:** Find the minimum sum from the top element to the bottom row. From position `(i, j)`, the next step can go to `(i + 1, j)` (down) or `(i + 1, j + 1)` (diagonally down-right).

## 1. Intuition

At each cell, there are only two possible next positions. Instead of trying to manually list every path, calculate the minimum cost from the current cell to the bottom:

`minPath(i, j) = triangle[i][j] + min(minPath(i + 1, j), minPath(i + 1, j + 1))`

The current value must be added once, then we choose the cheaper of the two possible continuations.

## 2. How to recognize this pattern

- The question asks for a **minimum cost/path**.
- Each state has a small number of allowed next moves.
- The same cell can be reached through different paths, so subproblems overlap.
- A state can be described using the current row and column: `(i, j)`.

## 3. DP state and transition

**State:** `dp[i][j]` = minimum path sum starting at cell `(i, j)` and ending anywhere in the last row.

**Transition:**

```java
int down = triangle.get(i).get(j) + dp[i + 1][j];
int diagonal = triangle.get(i).get(j) + dp[i + 1][j + 1];
dp[i][j] = Math.min(down, diagonal);
```

**Base case:** At the last row, there are no further choices, so the minimum path sum is the cell's own value.

`dp[n - 1][j] = triangle.get(n - 1).get(j)`

## 4. Approaches

### A. Recursion

Start at `(0, 0)`. Recursively calculate the result for the down and diagonal children, add the current cell, and return the smaller sum.

- **Time:** Exponential in the height in the worst-case upper bound, because the same states are recalculated.
- **Auxiliary space:** `O(n)` recursion stack, where `n` is the number of rows.

### B. Memoization (top-down DP)

Use the same recursion, but store each computed state in `dp[i][j]`. If the state has already been calculated, return the cached value.

- **Time:** `O(n²)`, since the triangle contains `n(n + 1) / 2` cells and each is solved once.
- **Auxiliary space:** `O(n²)` for the cache plus `O(n)` recursion stack.

### C. Tabulation (bottom-up DP)

Initialize the DP table with the values in the last row. Then process rows from `n - 2` up to row `0`. Each cell uses the already-computed values directly below it.

- **Time:** `O(n²)`.
- **Auxiliary space:** `O(n²)` for the DP table.

### D. Space optimization

Only the row immediately below the current row is needed. Store it in `prev`, calculate the current row in `curr`, and then assign `prev = curr`.

- **Time:** `O(n²)`.
- **Auxiliary space:** `O(n)` for the row arrays.

## 5. Dry run

Example:

```text
      2
     3 4
    6 5 7
   4 1 8 3
```

Work upward from the bottom row:

1. Last row: `[4, 1, 8, 3]`
2. Row `[6, 5, 7]` becomes `[min(6+4, 6+1), min(5+1, 5+8), min(7+8, 7+3)] = [7, 6, 10]`
3. Row `[3, 4]` becomes `[min(3+7, 3+6), min(4+6, 4+10)] = [9, 10]`
4. Top becomes `2 + min(9, 10) = 11`

**Answer: `11`**, along the path `2 → 3 → 5 → 1`.

## 6. Common mistakes to avoid

- Use `j` and `j + 1` for the two children; the diagonal child is not `(i + 1, j)`.
- Add the **current cell's value** to each child's result.
- In tabulation, initialize every value in the last row before moving upward.
- The valid columns in row `i` are only `0` through `i`.
- In memoization, initialize uncomputed entries to a sentinel such as `-1` only when that sentinel cannot be a valid answer. If negative path sums make this ambiguous, use a separate visited array or another safe sentinel strategy.

## 7. Quick comparison

| Approach | Time | Auxiliary space |
|---|---:|---:|
| Recursion | Exponential upper bound | `O(n)` stack |
| Memoization | `O(n²)` | `O(n²)` cache + stack |
| Tabulation | `O(n²)` | `O(n²)` |
| Space optimization | `O(n²)` | `O(n)` |

Here, `n` is the number of rows.

## 8. My revision checklist

- [ ] Explain why each state needs only two child states.
- [ ] Write the recurrence without looking.
- [ ] Re-implement memoization and tabulation from memory.
- [ ] Explain why space optimization needs only the next row.
- [ ] Revisit this problem after 3 days and 7 days.

## 9. Key takeaway

For minimum-path problems, define the meaning of a state first. Here, `dp[i][j]` means the minimum sum **from this cell to the bottom**, so bottom-up DP naturally starts at the last row and works toward the top.
