class Solution {

    public String smallestPalindrome(String s, int k) {

        int n=s.length();

        int[] c = new int[26];

        String middle =(n%2==0) ? "" :
                String.valueOf(s.charAt(n>>1));
        n>>= 1;
        for(int i=0; i<n;i++) {
            c[s.charAt(i)-'a']++;
        }
        long total=countWays(c, n);

        if(k>total) {
            return "";
        }

        String left =solve(n, c, k);

        return left+middle+new StringBuilder(left).reverse().toString();
    }


    public String solve(int n,int[] c,long k) {

        StringBuilder ans = new StringBuilder();

        for(int pos=0; pos<n;pos++) {
            for(int i=0; i<26;i++) {
                if(c[i]==0)
                    continue;
                c[i]--;
                long count=countWays(c,n-pos-1);
                if(k>count) {
                    k-=count;
                    c[i]++;
                }else{
                    ans.append((char)(i + 'a'));
                    break;
                }
            }
        }
        return ans.toString();
    }


    public long countWays(int[] c, int len) {

    long ans=1;
    int remaining=len;
    for(int i=0; i<26;i++) {
        if(c[i]==0)
            continue;
        ans=multiplyCap(ans, combination(remaining, c[i]));
        remaining-=c[i];
        if(ans>=1000000000L)
            return 1000000000L;
    }
    return ans;
}
   public long factorial(int n) {
    long ans=1;
    for(int i=2;i<=n;i++) {
        if(ans>1000000000L/i) {
            return 1000000000L;
        }
        ans*=i;
    }

    return ans;
}
public long multiplyCap(long a, long b) {

    if(a>=1000000000L || b>=1000000000L)
        return 1000000000L;

    if(a>1000000000L/b)
        return 1000000000L;

    return a*b;
}
public long combination(int n, int r) {
    r=Math.min(r, n - r);
    long ans=1;
    for(int i=1; i<=r;i++) {
        ans=ans*(n-r+i)/i;
        if(ans>=1000000000L)
            return 1000000000L;
    }
    return ans;
}
}