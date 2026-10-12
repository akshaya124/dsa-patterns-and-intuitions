Climbing Stairs
Platform: LeetCode / common DSA practice problem (confirm the exact platform link before publishing)
Topic: Dynamic Programming
Pattern: 1D DP; counting ways; Fibonacci-like transition.
Status: Completed study topic; add your submission link if desired.
1. Problem in my own words
Count distinct ways to reach step n when each move can climb either 1 or 2 steps.
2. Intuition
If the final move is 1 step, the previous position was n-1. If the final move is 2 steps, the previous position was n-2. These cases do not overlap, so add their counts.
3. How to recognize the pattern
1D DP; counting ways; Fibonacci-like transition.
Ask:
Can the answer be expressed using smaller states?
Are the same states recalculated in a recursive solution?
What information must a state store so the remaining work can be decided?
4. DP state
`dp[i]` is the number of ways to reach step i.
5. Recurrence / transition
`dp[i] = dp[i-1] + dp[i-2]`.
6. Base cases
Common convention: `dp[0]=1` (one way to stay at the ground), `dp[1]=1`. Thus n=2 gives 2.
7. Dry run
For n=4: dp[0..4] = 1, 1, 2, 3, 5; answer = 5.
8. Approaches and complexity
Naive recursion: O(2^n) time, O(n) stack.
Memoization: O(n) time, O(n) cache and stack.
Tabulation: O(n) time, O(n) space.
Two previous counts suffice: O(n) time, O(1) space.
9. Edge cases to test
n=0 convention; n=1; ensure the problem's allowed steps match the recurrence.
10. My attempt, mistakes, and revision
My first observation: Fill in your own words.
Where I got stuck: Fill in honestly; e.g., base case, index, transition.
Bug or wrong assumption: Add after reviewing your original submission.
What I learned: Write the clue that will help you recognize a similar problem.
[ ] Re-solve without looking
[ ] Explain the recurrence aloud
[ ] Revise after 3 days
[ ] Revise after 7 days
11. Key takeaway
Think about the last move. Partition all valid paths by the last step and sum the number of ways to reach the prior states.
