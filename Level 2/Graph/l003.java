import java.util.*;

public class l003{

    //https://leetcode.com/problems/shortest-path-in-binary-matrix/
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length,m = grid[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,1},{1,-1},{-1,-1}};
        LinkedList<Integer> que = new LinkedList<>();
        int ans = 0;

        if(grid[0][0] == 1 || grid[n-1][m-1] == 1)
            return -1;

        que.addLast(0);
        grid[0][0] = 1;

        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int idx = que.removeFirst();
                int i = idx / m;
                int j = idx % m;

                if(i == n-1 && j == m-1)
                    return ans + 1;     //in terms of cells not edges

                for(int d = 0;d<dir.length;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r >= 0 && c >= 0 && r < n && c < m && grid[r][c] == 0){
                        grid[r][c] = 1;
                        que.addLast(r * m + c);

                    }
                }
            }

            ans++;
        }
        return -1;
    }


    // https://leetcode.com/problems/01-matrix/
    public int[][] updateMatrix(int[][] mat) {
        int n  = mat.length,m = mat[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        boolean vis[][] = new boolean[n][m];
        LinkedList<Integer> que = new LinkedList<>();
        int level = 0;

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(mat[i][j] == 0){
                    que.addLast(i * m + j);
                    vis[i][j] = true;
                }
            }
        }

        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int idx = que.removeFirst();
                int i = idx / m;
                int j = idx % m;

                mat[i][j] = level;

                for(int d = 0;d<dir.length;d++){
                    int r = i + dir[d][0];  
                    int c = j + dir[d][1];
                    
                    if(r >= 0 && c>=0 && r < n && c < m && !vis[r][c]){
                        vis[r][c] = true;
                        que.addLast(r * m + c);
                    }
                }
            }

            level++;
        }

        return mat;
    }

    // Without boolean visited[] space (same above question)
    public int[][] updateMatrix(int[][] mat) {
        int n  = mat.length,m = mat[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        LinkedList<Integer> que = new LinkedList<>();
        int level = 0;

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(mat[i][j] == 0){
                    que.addLast(i * m + j);
                }
            }
        }

        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int idx = que.removeFirst();
                int i = idx / m;
                int j = idx % m;


                mat[i][j] = -(level);

                for(int d = 0;d<dir.length;d++){
                    int r = i + dir[d][0];  
                    int c = j + dir[d][1];
                    
                    if(r >= 0 && c>=0 && r < n && c < m && mat[r][c] > 0){
                        mat[r][c] = -mat[r][c];
                        que.addLast(r * m + c);
                    }
                }
            }

            level++;
        }

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(mat[i][j] < 0)
                    mat[i][j] = Math.abs(mat[i][j]);
            }
        }

        return mat;
    }

    //https://leetcode.com/problems/is-graph-bipartite/
    //(use bfs with cycle algo, as cycle thing is compulsory)
    public boolean isBipartite(int[][] graph) {

        //-1 : no color , 0 : red , 1 : white 
        int n = graph.length,m = graph[0].length;
        int []vis = new int[n];
        Arrays.fill(vis,-1);
        boolean res = true;
        for(int i = 0;i<n;i++){
            if(vis[i] == -1)
                res = res && helperBipartite(graph,i,vis);
        }
        
        return res;
    }

    public static boolean helperBipartite(int[][] graph,int src,int []vis){
        LinkedList<Integer> que = new LinkedList<>();
        que.addLast(src);
        int color = 0;
        boolean isCycle = false;
        boolean isBipartite = true;

        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int rvtx = que.removeFirst();
                
                if(vis[rvtx] != -1){
                    isCycle = true;
                    if(color != vis[rvtx]){
                        isBipartite = false;
                        break;
                    }
                    continue;
                }

                vis[rvtx] = color;

                for(Integer v : graph[rvtx]){
                    if(vis[v] == -1)
                        que.addLast(v);
                }

            }

            color = (color + 1) % 2;
            if(!isBipartite)
                break;
        }

        if(isCycle){
            if(isBipartite)
                System.out.println("Graph is bipartite, it means has even length cycle.");
            else
                System.out.println("Graph is non-bipartite, it means has odd length cycle.");    
        }else{
            System.out.println("Graph is non-bipartite");
        }

        return isBipartite;

    }

    //https://leetcode.com/problems/possible-bipartition/description/
    public boolean possibleBipartition(int n, int[][] dislikes) {
        ArrayList<Integer>[] graph = constructGraph(dislikes,n);
        int vis[] = new int[n + 1];
        Arrays.fill(vis,-1);    //-1 :- unassigned , 0 = red , 1 = green
        boolean res = true;
        for(int i = 0;i<=n;i++){
            if(vis[i] == -1){
                res = res && helper(graph,i,vis);
            }
        }
        return res;
    
    }

    public boolean helper(ArrayList<Integer>[] graph,int src,int []vis){
        int n = graph.length;
        LinkedList<Integer> que = new LinkedList<>();
        int color = 0;
        que.addLast(src);
        vis[src] = color;

        boolean isbipartite = true, isCycle = false;
        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int rvtx = que.removeFirst();
                if(vis[rvtx] != -1){
                    isCycle = true;
                    if(vis[rvtx] != color){
                        isbipartite = false;
                        return isbipartite;
                    }

                }else{
                    vis[rvtx] = color;
                }

                for(int vtx : graph[rvtx]){
                    if(vis[vtx] == -1)
                        que.addLast(vtx);
                }
            }

            color = (color + 1) % 2;
        }
        return true;
    }

    public ArrayList<Integer>[] constructGraph(int [][]dislikes,int n){
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for(int i = 0;i<=n;i++){
            graph[i] = new ArrayList<>();
        }

        for(int []d : dislikes){
            int u = d[0];
            int v = d[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        return graph;
    }


    //https://leetcode.com/problems/walls-and-gates/description/
    public static void wallsGates(int [][]rooms){
        LinkedList<Integer> que = new LinkedList<>();
        int n = rooms.length,m = rooms[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};

        for(int i =0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(rooms[i][j] == 0)
                    que.addLast(i * m + j);
            }
        }

        while(que.size() != 0){
            int size = que.size();
            while(size-- > 0){
                int idx = que.removeFirst();
                int i = idx / m;
                int j = idx % m;

                for(int d = 0;d<dir.length;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r >=0 && c >= 0 && r < n && c < m && rooms[r][c] == 214748347){
                        rooms[r][c] = rooms[i][j] + 1;  //either use parent grid value + 1 or use level variable   
                        que.addLast(r * m + c);
                    }
                }
            }
        }
    }
}