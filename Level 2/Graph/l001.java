import java.util.*;

public class l001{
    static class Edge{
        int v = 0,w = 0;
        
        Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }


    //O(E)
    public static void display(ArrayList<Edge>[] graph){
        int N = graph.length;
        for(int i = 0;i<N;i++){
            System.out.print(i + " -> ");
            for(Edge e : graph[i]){
                System.out.print("(" + e.v + ", " + e.w + ") ");
            }
            System.out.println();
        }
    }

    public static void addEdge(ArrayList<Edge>[] graph,int u,int v,int w){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    //O(E)
    public static int findEdge(ArrayList<Edge>[] graph,int u,int v){
        int N = graph.length;
        ArrayList<Edge> list = graph[u];
        for(int i = 0;i<N;i++){
            Edge e = list.get(i);
            if(e.v == v)
                return i;
        }

        return -1;
    }

    //O(E)
    public static void removeEdge(ArrayList<Edge>[] graph,int u, int v){
        //bidirectional graph
    
        int idx = findEdge(graph,u,v);
        graph[u].remove(idx);

        idx = findEdge(graph,v,u);
        graph[v].remove(idx);
    }

    //has Path
    //O(E) :- where E is the total no. of edges in that component.
    public static boolean dfs_findPath(ArrayList<Edge>[] graph,int src,int dest,boolean vis[]){
        if(src == dest)
            return true;

        vis[src] = true;
        boolean res = false;
        for(Edge e : graph[src]){
            if(!vis[e.v]){
                //ekk child ne true de diya,no further calls needed then.
                res = res || dfs_findPath(graph, e.v, dest, vis);
            }
        }

        return res;
    }

    public static int printAllPath(ArrayList<Edge>[] graph,int src,int dest,int wsf,String psf,boolean vis[]){
        if(src == dest){
            System.out.println(psf + dest + "@ " + wsf);
            return 1;
        }
        
        vis[src] = true;
        int count = 0;
        for(Edge e : graph[src]){
            if(!vis[e.v]){
                count += printAllPath(graph,e.v,dest,wsf + e.w,psf + src,vis);
            }
        }

        vis[src] = false;
        return count;
    }


    //GCC
    //O(E + V)
    public static void dfs_Gcc(ArrayList<Edge>[] graph,int src,boolean []vis){
        vis[src] = true;
        for(Edge e : graph[src]){
            if(!vis[e.v])
                dfs_Gcc(graph, e.v, vis);
        }
    }

    public static int getConnectedComponents(ArrayList<Edge>[] graph){
        int N = graph.length;
        boolean []vis = new boolean[N];
        int components = 0;

        for(int i = 0;i<N;i++){
            if(!vis[i]){
                dfs_Gcc(graph,i,vis);
                components++;
            }
        }

        return components;
    }


    public static void constructGraph(){
        int N = 7;      //no. of vertices
        ArrayList<Edge>[] graph = new ArrayList[N];
        for(int i = 0;i < N;i++){
            graph[i] = new ArrayList<>();
        }

        addEdge(graph,0,1,10);
        addEdge(graph,1,2,10);
        addEdge(graph,2,3,40);
        addEdge(graph,3,0,10);
        addEdge(graph,3,4,2);
        addEdge(graph,4,5,2);
        addEdge(graph,5,6,3);
        addEdge(graph,6,4,8);

        display(graph);
        boolean vis[] = new boolean[N];
        // System.out.println("Has Path : ->" + dfs_findPath(graph,0,6,vis));
        System.out.println("Print All Path : ->" + printAllPath(graph,0,6,0,"",vis));
    }


    //200:- https://leetcode.com/problems/number-of-islands/
    public static void dfs_No_Of_Islands(char [][]grid,int i,int j,int [][]dir){
        grid[i][j] = '0';
        for(int d = 0;d<dir.length;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if(r >= 0 && c >= 0 && r < grid.length && c < grid[0].length && grid[r][c] == '1'){
                dfs_No_Of_Islands(grid, r, c, dir);
            }
        }
    }
    
    public int numIslands(char[][] grid) {
        int islands = 0;
        int n = grid.length,m = grid[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j] == '1'){
                    dfs_No_Of_Islands(grid,i,j,dir);
                    islands++;
                }
            }
        }
        
        return islands;
    }

    //695 :- https://leetcode.com/problems/max-area-of-island/ 
    public static int dfs_Max_Area_Island(int [][]grid,int i,int j,int dir[][]){
        grid[i][j] = 0;

        int count = 0;
        for(int d = 0;d<dir.length;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if(r >= 0 && c >=0 && r < grid.length && c < grid[0].length && grid[r][c] == 1){
                count += dfs_Max_Area_Island(grid, r, c, dir);
            }
        }

        return count + 1;
    }

    public int maxAreaOfIsland(int[][] grid) {
        int maxArea = 0;
        int n = grid.length,m = grid[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j] == 1){
                    maxArea = Math.max(maxArea,dfs_Max_Area_Island(grid,i,j,dir));
                }
            }
        }

        return maxArea;
    }

    //463 :- https://leetcode.com/problems/island-perimeter/
    public int islandPerimeter(int[][] grid) {
        int n = grid.length,m = grid[0].length;
        int dir[][] = {{0,1},{1,0}};
        int count1s = 0, commonEdges = 0;

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j] == 1){
                    count1s++;
                    for(int d = 0;d<dir.length;d++){
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];

                        if(r >=0 && c >=0 && r < n && c < m && grid[r][c] == 1){
                            commonEdges++;
                        }
                    }
                }
            }
        }

        return (4 * count1s - (commonEdges * 2)); 
    }


    //130 :- https://leetcode.com/problems/surrounded-regions/
    public static void dfs_SurroundedRegion(char [][]board,int i,int j,int [][]dir){
        board[i][j] = '$';

        for(int d = 0;d<dir.length;d++){
            int r = i + dir[d][0];
            int c = j + dir[d][1];

            if(r >=0 && c >= 0 && r < board.length && c < board[0].length && board[r][c] == 'O'){
                dfs_SurroundedRegion(board, r, c, dir);
            }
        }
    }

    public void solve(char[][] board) {
        int n = board.length, m = board[0].length;
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if((i == 0 || j == 0 || i == n-1 || j == m-1) && board[i][j] == 'O')
                    dfs_SurroundedRegion(board,i,j,dir);
            }
        }

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(board[i][j] == '$')
                    board[i][j] = 'O';
                else
                    board[i][j] = 'X';    

            }
        }
    }

    //Journey to Moon :- https://www.hackerrank.com/challenges/journey-to-the-moon/problem
    public static int dfs_Moon(ArrayList<Integer>[] graph,int i,boolean []vis){
        vis[i] = true;

        int size = 0;
        for(Integer v : graph[i]){
            if(!vis[v]){
                size += dfs_Moon(graph, v, vis);
            }
        }

        return size + 1;
    }

    public static int journeyToMoon(int n, List<List<Integer>> astronaut) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0;i<n;i++){
            graph[i] = new ArrayList<>();
        }

        for(List<Integer> l : astronaut){
            int u = l.get(0), v= l.get(1);
            graph[u].add(v);
            graph[v].add(u);
        }

        boolean []vis = new boolean[n];
        ArrayList<Integer> componentSize = new ArrayList<>();
        for(int i = 0;i<n;i++){
            if(!vis[i]){
                int size = dfs_Moon(graph,i,vis);
                componentSize.add(size);
            }
        }

        int ans = 0,ssf = 0;    //ssf = sum so far
        for(int i = componentSize.size() - 1;i>=0;i--){
            int no = componentSize.get(i);
            ans += no * ssf;
            ssf += no;
        }

        return ans;
    }

    public static void main(String[] args) {
        constructGraph();
        
    }
}