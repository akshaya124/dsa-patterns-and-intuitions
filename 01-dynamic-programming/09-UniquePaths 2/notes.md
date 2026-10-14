# Unique Paths II — Dynamic Programming with Obstacles

## Problem
Given an `m × n` grid where `1` represents an obstacle and `0` represents an open cell, count paths from the top-left to bottom-right. You may move only **right** or **down**, and cannot enter an obstacle.

## Intuition / pattern
This is the same grid DP as **Unique Paths**, with one extra rule: an obstacle cell has zero ways to reach it.

For a free cell `(i, j)`:
```
dp[i][j] = ways from above + ways from left
```
For an obstacle:
```
dp[i][j] = 0
```

**Base case:** if the starting cell is blocked, the answer is `0`; otherwise `dp[0][0] = 1`.

## 1. Recursion (your implementation)
Your helper starts at the destination and moves backward:
- Return `0` if the current cell is blocked.
- Return `1` at `(0, 0)` if it is open.
- Recursively count paths from above and from the left, then add them.

**Small example**
```
Grid:       Ways:
0 0 0       1 1 1
0 1 0       1 0 1
0 0 0       1 1 2
```
The center obstacle blocks paths through it, leaving two valid routes.

- Time: exponential, with an `O(2^(m+n))` upper bound
- Stack space: `O(m+n)`

**Code review note:** the recursion checks `obstacles[i][j]` before checking whether `i` or `j` is negative. A call that reaches a negative index can therefore throw an `ArrayIndexOutOfBoundsException`. Check bounds first, then inspect the obstacle. Also, checking only the current cell is sufficient; you do not need to pre-check the previous cell before making the recursive call.

## 2. Memoization (your implementation)
The `dp` array is initialized to `-1`. Once a cell's answer is calculated, save it so repeated calls reuse the result.

Your memoized helper checks the obstacle only when `i >= 0 && j >= 0`, then handles the start and out-of-bounds states. This ordering avoids indexing a negative position.

- Time: `O(mn)`
- Space: `O(mn)` cache + `O(m+n)` recursion stack
- Java reminder: import `java.util.Arrays` for `Arrays.fill` if needed.

## 3. Tabulation (your implementation)
Your code fills the grid row by row:
1. If the start is blocked, return `0`.
2. Set `dp[0][0] = 1`.
3. Skip the start cell during iteration.
4. If a cell is blocked, explicitly set its DP value to `0`.
5. Otherwise, add the ways from above and left when those cells exist.

- Time: `O(mn)`
- Space: `O(mn)`

## Space optimization (extension)
You have not added this version in the folder yet. It can be done with a single 1D array:
- Before updating column `j`, `dp[j]` holds the value from **above** (previous row).
- `dp[j - 1]` already holds the value from the **left** (current row).
- Set `dp[j] = 0` for an obstacle; otherwise add the left value when `j > 0`.
- Initialize `dp[0]` to `0` if the start is blocked, otherwise `1`.

This uses `O(n)` auxiliary space (where `n` is the number of columns), while time remains `O(mn)`.

## Complexity summary

| Approach | Time | Auxiliary space |
|---|---:|---:|
| Recursion | Exponential, `O(2^(m+n))` upper bound | `O(m+n)` stack |
| Memoization | `O(mn)` | `O(mn)` cache + stack |
| Tabulation | `O(mn)` | `O(mn)` |
| 1D space optimization | `O(mn)` | `O(n)` |

## Edge cases to test
- Start blocked → `0`.
- Destination blocked → `0`.
- Single open cell → `1`.
- Single row or column with an obstacle → `0` if the obstacle blocks the only route.
- Obstacles in the first row/column block every cell beyond them in that direction.

## Key takeaway
**Obstacle first: zero ways. Otherwise, add ways from valid predecessors.** Start from Unique Paths, then introduce the blocked-cell rule without changing the core DP recurrence.
