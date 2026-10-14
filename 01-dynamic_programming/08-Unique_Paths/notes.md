# Unique Paths — Dynamic Programming

## Problem
Given an `m x n` grid, start at the top-left cell `(0, 0)` and reach the bottom-right cell `(m - 1, n - 1)`. You may move only **right** or **down**. Return the number of distinct paths.

## Intuition and DP pattern
At any cell `(i, j)`, the last move must have come either from above `(i - 1, j)` or from the left `(i, j - 1)`. Therefore:

```
ways(i, j) = ways(i - 1, j) + ways(i, j - 1)
```

- **Base case:** the starting cell `(0, 0)` has one way to be reached.
- **Out of bounds:** a negative row or column contributes zero paths.
- **Pattern recognition:** when a grid problem asks for the number of ways to reach a cell using a small set of moves, try defining the state as the number of ways to reach that cell and combine the valid predecessor states.

## 1. Recursion

Your recursive solution starts at the destination and works backward:
- `helper(i - 1, j)` counts paths arriving from above.
- `helper(i, j - 1)` counts paths arriving from the left.
- Add both results.

This directly follows the recurrence, but the same states are recalculated many times.

**Dry run — 2 × 3 grid**

```
Start at (0,0), destination (1,2)

ways(0,0) = 1
ways(0,1) = 1
ways(0,2) = 1
ways(1,0) = 1
ways(1,1) = ways(0,1) + ways(1,0) = 2
ways(1,2) = ways(0,2) + ways(1,1) = 3
```

- Time: exponential, commonly bounded by `O(2^(m+n))`
- Auxiliary space: `O(m+n)` recursion stack

## 2. Memoization (top-down DP)

Your code stores each computed state in `dp[i][j]`. Initialize the table to `-1`; if a state is already computed, return it instead of exploring it again.

- States: `m × n`
- Time: `O(mn)`
- Space: `O(mn)` for the cache plus `O(m+n)` recursion stack

**Important:** `Arrays.fill` requires `import java.util.Arrays;` if your submission does not already include it.

## 3. Tabulation (bottom-up DP)

Your code sets `dp[0][0] = 1`, then visits cells row by row. For each non-start cell, initialize `up` and `left` to zero, add the values of valid predecessors, and store `dp[i][j] = up + left`.

- Time: `O(mn)`
- Space: `O(mn)`

## 4. Space optimization

Your implementation uses:
- `prev[j]`: number of paths to column `j` in the previous row.
- `temp[j - 1]`: number of paths to the cell immediately left in the current row.

For each row, build `temp`, then assign `prev = temp`. This removes the full 2D table.

- Time: `O(mn)`
- Space: `O(n)` for the two 1D arrays

A further in-place variant can update one array from left to right, because the old value at `dp[j]` represents **up**, while the newly updated `dp[j - 1]` represents **left**.

## Complexity summary

| Approach | Time | Auxiliary space |
|---|---:|---:|
| Recursion | Exponential, `O(2^(m+n))` upper bound | `O(m+n)` stack |
| Memoization | `O(mn)` | `O(mn)` cache + stack |
| Tabulation | `O(mn)` | `O(mn)` |
| Space optimization | `O(mn)` | `O(n)` |

## Edge cases and reminders
- A `1 × 1` grid has exactly one path.
- A single row or a single column has exactly one path.
- The recurrence assumes both moves are allowed and there are no obstacles.
- The usual LeetCode constraints make `int` sufficient for this problem.

## Key takeaway
**Define the state → identify valid previous states → write the recurrence → choose recursion, memoization, tabulation, or space optimization.** Unique Paths is a classic 2D-grid DP example.
