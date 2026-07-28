class Solution {
    public String smallestPalindrome(String s) {

        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
        }

        StringBuilder left = new StringBuilder();
        char middle = 0;

        for (int i = 0; i < 26; i++) {

           
            for (int j = 0; j < freq[i] / 2; j++) {
                left.append((char) (i + 'a'));
            }

           
            if (freq[i] % 2 == 1) {
                middle = (char) (i + 'a');
            }
        }

    
        String right = new StringBuilder(left).reverse().toString();

       
        if (middle != 0) {
            return left.toString() + middle + right;
        }

        return left.toString() + right;
    }
}