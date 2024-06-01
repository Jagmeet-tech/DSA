import java.util.*;

public class l005{

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

    //329:- https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
    public int longestIncreasingPath(int[][] matrix) {
        LinkedList<Integer> que = new LinkedList<>();
        int n = matrix.length,m = matrix[0].length;
        int [][]indegree = new int[n][m];
        int[][] dir = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

        //indegree array
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++){
                for(int d=0;d<dir.length;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r>=0 && c>=0 && r<n && c<m && matrix[i][j] > matrix[r][c])
                        indegree[i][j]++;
                }

                if(indegree[i][j] == 0)
                    que.addLast(i*m + j);
            }
        
        //BFS    
        int level = 0;    
        while(que.size()!=0){
            int size = que.size();
            while(size-- >0){
                int rvtx = que.removeFirst();
                int i = rvtx/m, j = rvtx % m;

                for(int d=0;d<dir.length;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r>=0 && c>=0 && r<n && c<m && matrix[i][j] < matrix[r][c] && --indegree[r][c] == 0)
                        que.addLast(r*m +c);    
                }

            }
            level++;
        }

        return level;    
    }

    //Cycle detection in topological order using DFS.
    //0 = unvisited , 1 = current Path ka hissa hai , 2 = backtrack
    public static boolean dfs_topo_iscycle(ArrayList<Edge>[] graph,int src,int []vis,ArrayList<Integer> order){
        vis[src] = 1;

        boolean cycle = false;
        for(Edge e :graph[src]){
            if(vis[e.v] == 0)
                cycle = cycle || dfs_topo_iscycle(graph,e.v,vis,order);
            else if(vis[e.v] == 1)
                return true;    //cycle exist as current path ka hissa hai.    
        }

        vis[src] = 2;
        order.add(src);
        return cycle;
    }

    public static ArrayList<Integer> dfs_topo_cycle(ArrayList<Edge>[] graph){
        int n = graph.length;
        int []vis = new int[n];
        ArrayList<Integer> order = new ArrayList<>();
        boolean isCycle = false;

        for(int i =0 ;i<n;i++){
            if(vis[i] == 0){
                isCycle = isCycle || dfs_topo_iscycle(graph,i,vis,order);
            }
        }

        if(isCycle){
            order.clear();
        }

        return order;
    }

    //do course schedule and course schedule 2 using above code as reference.


    //Disjoint set Algorithm...(DSU)
    static int []parent;
    static int []size;

    public static int findParent(int u){
        if(parent[u] == u)
            return u;

        return parent[u] = findParent(parent[u]);   //path compression
    }

    public static void union(int p1,int p2){
        if(size[p1] > size[p2]){
            size[p1] += size[p2];
            parent[p2] = p1;
        }else{
            size[p2] += size[p1];
            parent[p1] = p2;
        }
    }

    //[{u,v,w},{u,v,w},...]
    public static void unionFindAlgo(int [][]edges){
        int n = edges.length;
        parent = new int[n];
        size = new int[n];
        boolean isCycle = false;

        //No need to create graph
        ArrayList<Edge>[] graph = new ArrayList[n];
        for(int i = 0;i < n;i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 0;i<n;i++){
            parent[i] = i;
            size[i] = 1;
        }

        for(int e[] : edges){
            int u = e[0],v= e[1],w = e[2];
            int p1 = findParent(u);
            int p2 = findParent(v);

            if(p1 != p2){  
                union(p1,p2);
                addEdge(graph, u, v, w);
            }else{
                //same parent/leader means cycle
                isCycle = true;
            }
        }
    }
}