class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        String t = "1" + s + "1";
        int m = t.length();

        int initialOnes = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') initialOnes++;
        }

        int ans = initialOnes;

        int i = 0;
        while (i < m) {
            if (t.charAt(i) == '0') {
                i++;
                continue;
            }

            int oneStart = i;
            while (i < m && t.charAt(i) == '1') i++;
            int oneEnd = i - 1;

            if (oneStart > 0 && oneEnd < m - 1 &&
                t.charAt(oneStart - 1) == '0' &&
                t.charAt(oneEnd + 1) == '0') {

                int left = 0;
                int j = oneStart - 1;
                while (j >= 0 && t.charAt(j) == '0') {
                    left++;
                    j--;
                }

                int right = 0;
                j = oneEnd + 1;
                while (j < m && t.charAt(j) == '0') {
                    right++;
                    j++;
                }

                ans = Math.max(ans, initialOnes + left + right);
            }
        }

        return ans;
    }
}