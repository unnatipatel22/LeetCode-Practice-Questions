import java.util.*;

class Solution {

    // Segment tree for Range Maximum Query over precalculated trade gains
    static class SegmentTree {
        int n;
        int[] tree;

        public SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * Math.max(1, n)];
            if (n > 0) build(arr, 0, 0, n - 1);
        }

        private void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
                return;
            }
            int mid = start + (end - start) / 2;
            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }

        public int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l || l > r) return 0;
            if (l <= start && end <= r) return tree[node];
            int mid = start + (end - start) / 2;
            return Math.max(
                query(2 * node + 1, start, mid, l, r),
                query(2 * node + 2, mid + 1, end, l, r)
            );
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') totalOnes++;
        }

        // 1. Group contiguous blocks of '0's and '1's
        // segment: [type (0 or 1), length, start_idx, end_idx]
        List<int[]> segments = new ArrayList<>();
        for (int i = 0; i < n; ) {
            int j = i;
            while (j < n && s.charAt(j) == s.charAt(i)) j++;
            segments.add(new int[]{s.charAt(i) - '0', j - i, i, j - 1});
            i = j;
        }

        int numSegs = segments.size();
        int[] segIndex = new int[n];
        for (int k = 0; k < numSegs; k++) {
            for (int idx = segments.get(k)[2]; idx <= segments.get(k)[3]; idx++) {
                segIndex[idx] = k;
            }
        }

        // 2. Precompute gain for '1'-segments strictly sandwiched between two full '0'-segments
        int[] gain = new int[numSegs];
        for (int i = 1; i < numSegs - 1; i++) {
            if (segments.get(i)[0] == 1) {
                gain[i] = segments.get(i - 1)[1] + segments.get(i + 1)[1];
            }
        }

        SegmentTree st = new SegmentTree(gain);
        List<Integer> result = new ArrayList<>(queries.length);

        for (int[] q : queries) {
            int l = q[0], r = q[1];
            int segL = segIndex[l];
            int segR = segIndex[r];

            int maxGain = 0;

            // --- A. Query fully contained inner '1' segments in O(log N) ---
            // For a '1' segment i to have both its left zero-segment (i-1) and right zero-segment (i+1)
            // FULLY inside [l, r], i must satisfy: i-1 > segL and i+1 < segR
            int innerStart = segL + 2;
            int innerEnd = segR - 2;
            if (innerStart <= innerEnd) {
                maxGain = Math.max(maxGain, st.query(0, 0, numSegs - 1, innerStart, innerEnd));
            }

            // --- B. Check boundary '1' segments in O(1) ---
            // 1. Check '1' segment at segL + 1 (left boundary zero-segment is truncated at l)
            int cand1 = segL + 1;
            if (cand1 <= segR && segments.get(cand1)[0] == 1 && segments.get(cand1)[3] <= r) {
                if (segments.get(segL)[0] == 0) {
                    int leftLen = segments.get(segL)[3] - l + 1;
                    int rightLen = (cand1 + 1 == segR && segments.get(segR)[0] == 0)
                            ? (r - segments.get(segR)[2] + 1)
                            : (cand1 + 1 <= segR ? segments.get(cand1 + 1)[1] : 0);
                    if (cand1 + 1 <= segR && segments.get(cand1 + 1)[0] == 0) {
                        maxGain = Math.max(maxGain, leftLen + rightLen);
                    }
                }
            }

            // 2. Check '1' segment at segR - 1 (right boundary zero-segment is truncated at r)
            int cand2 = segR - 1;
            if (cand2 >= segL && cand2 != cand1 && segments.get(cand2)[0] == 1 && segments.get(cand2)[2] >= l) {
                if (segments.get(segR)[0] == 0) {
                    int rightLen = r - segments.get(segR)[2] + 1;
                    int leftLen = (cand2 - 1 == segL && segments.get(segL)[0] == 0)
                            ? (segments.get(segL)[3] - l + 1)
                            : (cand2 - 1 >= segL ? segments.get(cand2 - 1)[1] : 0);
                    if (cand2 - 1 >= segL && segments.get(cand2 - 1)[0] == 0) {
                        maxGain = Math.max(maxGain, leftLen + rightLen);
                    }
                }
            }

            result.add(totalOnes + maxGain);
        }

        return result;
    }
}