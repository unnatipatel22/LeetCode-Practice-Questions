    class Solution {
    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {

        int n = s.length();

        long[] pow10 = new long[n + 1];
        long[] prefixX = new long[n + 1];
        long[] prefixSum = new long[n + 1];
        int[] cnt = new int[n + 1];

        // powers of 10
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        // prefix arrays
        for (int i = 0; i < n; i++) {

            prefixX[i + 1] = prefixX[i];
            prefixSum[i + 1] = prefixSum[i];
            cnt[i + 1] = cnt[i];

            char ch = s.charAt(i);

            if (ch != '0') {
                int digit = ch - '0';

                cnt[i + 1]++;

                prefixSum[i + 1] += digit;

                prefixX[i + 1] =
                        (prefixX[i + 1] * 10 + digit) % MOD;
            }
        }

        int m = queries.length;
        int[] ans = new int[m];

        for (int i = 0; i < m; i++) {

            int l = queries[i][0];
            int r = queries[i][1];

            long digitSum = prefixSum[r + 1] - prefixSum[l];

            int len = cnt[r + 1] - cnt[l];

            long value =
                    (prefixX[r + 1]
                    - (prefixX[l] * pow10[len]) % MOD
                    + MOD) % MOD;

            ans[i] = (int)((value * digitSum) % MOD);
        }

        return ans;
    }
}