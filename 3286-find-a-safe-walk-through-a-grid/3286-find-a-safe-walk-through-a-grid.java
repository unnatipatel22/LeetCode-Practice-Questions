class Solution {
    public boolean dfs(List<List<Integer>> grid, int i, int j, int health, int[] delrow, int[] delcol, int[][] currhealth, int n, int m){
        
        if (grid.get(i).get(j) == 1) health = health - 1;
        if (health <= 0) return false;
        if (i == n-1 && j == m-1) return true;
        if (health <= currhealth[i][j]) return false;
        currhealth[i][j] = health;
        for(int k = 0; k < 4; k++){
            int nrow = i + delrow[k];
            int ncol = j + delcol[k];
            if (nrow >= 0 && nrow < n && ncol >= 0 && ncol < m){
                if (dfs(grid, nrow, ncol, health, delrow, delcol, currhealth, n, m)) return true;
            }
        }
        return false;
    }
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int n = grid.size();
        int m = grid.get(0).size();
        int[][] currhealth = new int[n][m];
        int[] delrow = {0 , 0, +1, -1};
        int[] delcol = {+1, -1, 0, 0};
        return dfs(grid, 0, 0, health, delrow, delcol, currhealth, n, m);
        
    }
}