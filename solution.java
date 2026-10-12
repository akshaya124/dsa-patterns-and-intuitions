import java.util.Arrays;

public class Solution {
    // 1) Plain recursion: exponential time.
    public static int fibRecursive(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n <= 1) return n;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    // 2) Memoization: O(n) time, O(n) cache and call stack.
    public static int fibMemo(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return fibMemoHelper(n, memo);
    }

    private static int fibMemoHelper(int n, int[] memo) {
        if (n <= 1) return n;
        if (memo[n] != -1) return memo[n];
        return memo[n] = fibMemoHelper(n - 1, memo) + fibMemoHelper(n - 2, memo);
    }

    // 3) Tabulation: O(n) time and O(n) space.
    public static int fibTabulation(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) dp[i] = dp[i - 1] + dp[i - 2];
        return dp[n];
    }

    // 4) Space optimized: O(n) time and O(1) auxiliary space.
    public static int fibSpaceOptimized(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (n <= 1) return n;
        int prev2 = 0, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        return prev1;
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println(fibRecursive(n));
        System.out.println(fibMemo(n));
        System.out.println(fibTabulation(n));
        System.out.println(fibSpaceOptimized(n));
    }
}
