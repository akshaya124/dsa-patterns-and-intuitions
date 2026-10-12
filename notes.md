# Fibonacci Number

- **Platform:** LeetCode / common DSA practice problem (confirm the exact platform link before publishing)
- **Topic:** Dynamic Programming
- **Pattern:** 1D DP; overlapping subproblems; optimal substructure.
- **Status:** Completed study topic; add your submission link if desired.

## 1. Problem in my own words

Return F(n), where F(0)=0, F(1)=1, and F(n)=F(n-1)+F(n-2).

## 2. Intuition

To compute a value at index n, only the two previous Fibonacci values are needed. The direct recursive definition mirrors the mathematical definition, but repeatedly solves the same subproblems.

## 3. How to recognize the pattern

1D DP; overlapping subproblems; optimal substructure.

Ask:
- Can the answer be expressed using smaller states?
- Are the same states recalculated in a recursive solution?
- What information must a state store so the remaining work can be decided?

## 4. DP state

`dp[i]` stores the i-th Fibonacci number.

## 5. Recurrence / transition

`F(n) = F(n-1) + F(n-2)` for n >= 2.

## 6. Base cases

`F(0)=0`, `F(1)=1`.

## 7. Dry run

For n=5: F(0..5) = 0, 1, 1, 2, 3, 5. Each value after the first two is the sum of the previous two.

## 8. Approaches and complexity

Plain recursion: O(2^n) time in the usual upper-bound analysis; O(n) recursion stack.

Memoization: O(n) time, O(n) cache plus O(n) call stack.

Tabulation: O(n) time, O(n) array space.

Two variables: O(n) time, O(1) auxiliary space.

## 9. Edge cases to test

n=0, n=1; negative input should be rejected if the API requires non-negative n; integer overflow for large n.

## 10. My attempt, mistakes, and revision

- **My first observation:** _Fill in your own words._
- **Where I got stuck:** _Fill in honestly; e.g., base case, index, transition._
- **Bug or wrong assumption:** _Add after reviewing your original submission._
- **What I learned:** _Write the clue that will help you recognize a similar problem._
- [ ] Re-solve without looking
- [ ] Explain the recurrence aloud
- [ ] Revise after 3 days
- [ ] Revise after 7 days

## 11. Key takeaway

Memoization avoids repeated work; tabulation removes recursion; space optimization works because only two prior states are needed.
