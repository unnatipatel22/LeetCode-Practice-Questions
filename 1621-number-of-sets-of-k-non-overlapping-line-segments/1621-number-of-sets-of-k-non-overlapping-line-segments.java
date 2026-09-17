class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1000000007;

        long[][] dp = new long[n + k][2 * k + 1];

        // dp[i][j] = C(i, j)
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = 1;

            for (int j = 1; j <= 2 * k && j <= i; j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            }
        }

        return (int) dp[n + k - 1][2 * k];
    }
}