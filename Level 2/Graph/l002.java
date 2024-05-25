import java.util.*;

public class l002 {

    static class Edge{
        int v = 0,w=0;
        
        Edge(int u,int w){
            this.w = w;
            this.v = v;
        }
    }
    
    //https://www.geeksforgeeks.org/problems/number-of-distinct-islands/1
    public static void dfs_Distinct_Islands(int [][]grid,int i,int j,int [][]dir,String []dirs,StringBuilder sb){
        grid[i][j] = 0;

        for(int d =0;d<dir.length;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if(r>=0 && c>=0 && r < grid.length && c < grid[0].length && grid[r][c] == 1){
                sb.append(dirs[d]);
                dfs_Distinct_Islands(grid, r, c, dir, dirs, sb);
                sb.append("b");
            }
        }
    }

    int countDistinctIslands(int[][] grid) {
        int n = grid.length,m = grid[0].length;
        HashSet<String> hs = new HashSet<>();
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        String dirs[] = {"R","L","D","U"};

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j] == 1){
                    StringBuilder sb = new StringBuilder();
                    dfs_Distinct_Islands(grid,i,j,dir,dirs,sb);
                    hs.add(sb.toString());
                }
            }
        }

        return hs.size();
    }

    //https://leetcode.com/problems/count-sub-islands/
    public static boolean dfs_CountSub_Islands(int [][]grid1,int [][]grid2,int i,int j,int [][]dir){
        grid2[i][j] = 0;

        boolean res = true;
        for(int d = 0;d<dir.length;d++){
            int r = i + dir[d][0];  
            int c = j + dir[d][1];
            
            if(r >= 0 && c>=0 && r < grid2.length && c < grid2[0].length && grid2[r][c] == 1){
                res = dfs_CountSub_Islands(grid1, grid2, r, c, dir) && res; 
            }
        }

        return res && grid1[i][j] == 1;
    }
    
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int n = grid2.length,m = grid2[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};

        int count = 0;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid2[i][j] == 1){
                    boolean isSubIsland = dfs_CountSub_Islands(grid1,grid2,i,j,dir);
                    if(isSubIsland)
                        count++;
                }
            }
        }
        
        return count;
    }


    //BFS With Cycle Space:O(E)
    public static void BFS_WithCycle(ArrayList<Edge>[] graph,int src,boolean []vis){
        LinkedList<Integer> que = new LinkedList<>();
        int level = 0;
        boolean isCycle = false;

        que.add(src);
        while(que.size() != 0){ //traverse to all levels
            int size = que.size();

            System.out.println("Min no of Edges: " + level + "-> ");
            while(size-- > 0){  // traverse to particular level
                int rvtx = que.removeFirst();

                if(vis[rvtx]){
                    isCycle = true;
                    continue;
                }

                vis[rvtx] = true;
                System.out.print(rvtx + ", ");
                for(Edge e : graph[rvtx]){
                    if(!vis[e.v])
                        que.addLast(e.v);
                }
            }
            System.out.println();
            level++;
        }
    }

    //BFS Without Cycle Space complexity:O(V)
    public static void BFS_WithoutCycle(ArrayList<Edge>[] graph,int src,boolean []vis){
        LinkedList<Integer> que = new LinkedList<>();
        int level = 0;

        que.add(src);
        vis[src] = true;
        while(que.size() != 0){ //traverse to all levels
            int size = que.size();

            System.out.println("Min no of Edges: " + level + "-> ");
            while(size-- > 0){  // traverse to particular level
                int rvtx = que.removeFirst();

                System.out.print(rvtx + ", ");
                for(Edge e : graph[rvtx]){
                    if(!vis[e.v]){
                        vis[e.v] = true;
                        que.addLast(e.v);
                    }
                }
            }
            System.out.println();
            level++;
        }
    }  
    
    
    //https://leetcode.com/problems/rotting-oranges/
    //(Use bFS without cycle)
    public int orangesRotting(int[][] grid) {
        int n = grid.length,m = grid[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        LinkedList<Integer> que = new LinkedList<>();
        int freshOrange = 0,time = 0;

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j] == 2){
                    int idx = i * m + j;
                    que.addLast(idx);
                    grid[i][j] = 2;
                }
                else if(grid[i][j] == 1)
                    freshOrange++;
            }
        }

        if(freshOrange == 0)
            return 0;


        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int rvtx = que.removeFirst();
                int i = rvtx / m;
                int j = rvtx % m;
                
                for(int d = 0;d<dir.length;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r >=0 && c >= 0 && r<n && c < m && grid[r][c] == 1){
                        grid[r][c] = 2;
                        que.addLast(r * m + c);
                        freshOrange--;

                        if(freshOrange == 0)
                            return time + 1;
                    }
                }
            }
            time++;
        }

        if(freshOrange != 0)
            return -1;

        return time;    
    }
}
