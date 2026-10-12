class Solution {
    int dp[];
    public int helper(int n){
        if(n <= 2){
            return n;
        }
        if(dp[n] != -1){
            return dp[n];
        }
            return dp[n] = helper(n-1)+helper(n-2);
    }
    public int climbStairs(int n) {
        dp = new int[n+1];
        Arrays.fill(dp,-1);
        return helper(n);
    }
}
