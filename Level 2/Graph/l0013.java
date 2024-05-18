import java.util.*;

public class l0013{

    static class Edge{
        int v,w;
        Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }

    //490:- https://leetcode.com/problems/the-maze/
    public boolean hasPath(int [][]maze, int []start, int []dest){
        int dir[][] = {{0,1},{0,-1},{1,0},{-1,0}};
        LinkedList<Integer> que = new LinkedList<>();
        int n = maze.length , m = maze[0].length;
        boolean [][]vis = new boolean[n][m];

        int sr = start[0] , sc = start[1];
        int er = dest[0] , ec = dest[1];

        que.addLast(sr * m + sc);
        vis[sr][sc] = true;

        while(que.size() != 0){      // all level iterate
            int size = que.size();
            while(size-- > 0){  //particular level explore
                int idx = que.removeFirst();
                int i = idx / m, j = idx % m ;

                for(int d[] : dir){
                    int r = i;
                    int c = j;

                    while(r >= 0 && c >=0 && r < n && c < m && maze[r][c] == 0){    //one direction multiple jumps
                        r += d[0];
                        c += d[1];
                    }

                    //one extra subtracted 
                    r -= d[0];
                    c -= d[1];

                    if(vis[r][c]){
                        continue;
                    }

                    vis[r][c] = true;
                    que.addLast(r * m + c);

                    //destination reached
                    if(r == er && c == ec)  
                        return true;

                }
            }
        }
        return false;
    }

    //505:- https://leetcode.com/problems/the-maze-ii/description/
    public int shortestDistance(int [][]maze, int []start, int []dest){
        int n = maze.length, m = maze[0].length;
        int sr = start[0], sc  = start[1];
        int er = dest[0], ec = dest[1];

        int [][]dir = {{0,1},{0,-1},{1,0},{-1,0}};
        
        //storing each cell mein kitne distance se paunche from src.(also helps in visit check)
        int [][]dist = new int[n][m];  

        for(int d[]: dist){
            Arrays.fill(d,(int)1e9);
        }

        //local class
        class pair implements Comparable<pair>{
            int r ,c ,dist;

            pair(int r,int c, int dist){
                this.r = r;
                this.c = c;
                this.dist = dist;
            }

            public int compareTo(pair o){
                return this.dist - o.dist;  //min pq
            }
        }

        PriorityQueue<pair> pq = new PriorityQueue<>();
        pq.add(new pair(sr, sc, 0));
        dist[sr][sc] = 0;


        while(pq.size() != 0){
            int size = pq.size();

            while(size-- > 0){
                pair p = pq.remove();
                
                for(int d[] : dir){
                    int r = p.r, c = p.c, l = p.dist;

                    while(r >= 0 && c >=0 && r < n && c < m && maze[r][c] == 0){
                        r += d[0];
                        c += d[1];
                        l++;
                    }

                    r -= d[0];
                    c -= d[1];
                    l--;

                    if(l >= dist[r][c]){   //visited check handled by dist array as it doesnot  allow to put elements in pq if large distance encounters.
                        continue;
                    }

                    pq.add(new pair(r,c,l));
                    dist[r][c] = l;
                }
            }
        }        
        return dist[er][ec] != (int)1e9 ? dist[er][ec] : -1;
    }


    // Algorithm:- KosaRaju (To find Strongly connected components in cyclic graph) 
    public void kosaRaju_Algo(ArrayList<Edge>[] graph, int n){
        boolean vis[] = new boolean[n];

        // topological order
        ArrayList<Integer> order = new ArrayList<>();
        for(int i =0;i<n;i++){
            if(!vis[i]){
                dfs_topo(i,graph,vis,order);
            }
        }


        // compliment the graph 
        ArrayList<Edge> []ngraph = new ArrayList[n];    // Assume like this syntax:- int []g = new int[n];
        for(int i = 0;i<n;i++){
            ngraph[i] = new ArrayList<>();
        }

        //reverse the edges to make compliment of graph.
        for(int i = 0;i<n;i++){
            ArrayList<Edge> ar = graph[i];
            for(Edge e: ar){
                ngraph[e.v].add(new Edge(i,e.w));
            }
        }

        // Strongly connected components
        for(int i = 0;i<n;i++)
            vis[i] = false;

        ArrayList<Integer> components = new ArrayList<>();
        for(int i = order.size() - 1;i >=0;i--){
            int vtx = order.get(i);
            if(!vis[vtx]){
                dfs_Scc_Components(vtx,ngraph,vis,components);
                System.out.println(components);
                components.clear();    
            }
        }    
    }

    public void dfs_topo(int src,ArrayList<Edge> []graph,boolean []vis,ArrayList<Integer> order){
        vis[src] = true;
        for(Edge e : graph[src]){
            if(!vis[e.v])
                dfs_topo(e.v, graph, vis, order);
        }

        //postorder mein node add krte hai topological order mein
        order.add(src);
    }

    public void dfs_Scc_Components(int src,ArrayList<Edge> []ngraph,boolean []vis,ArrayList<Integer> components){
        vis[src] = true;
        components.add(src);

        for(Edge e : ngraph[src]){
            if(!vis[e.v])
                dfs_Scc_Components(e.v, ngraph, vis, components);
        }
    }

    //Mother vtx:- https://www.geeksforgeeks.org/problems/mother-vertex/1
    public int findMotherVertex(int V, ArrayList<ArrayList<Integer>>adj){
        boolean []vis = new boolean[V];
        ArrayList<Integer> order = new ArrayList<>();

        //As we starting our dfs call with 0 vtx., so mother vtx will always be minium vtx.
        for(int i = 0;i<V;i++){
            if(!vis[i]){
                dfs(i,adj,vis,order);
            }
        }

        int motherVtx = order.get(order.size() - 1);
        for(int i = 0;i<V;i++)
            vis[i] = false;

        dfs(motherVtx,adj,vis,order);    
        for(int i = 0;i<V;i++){
            if(!vis[i])     //top of stack element in order arraylist is not mother vtx .(As we can't reach to other vertexs in above dfs call.) 
                return -1;
        }

        return motherVtx;
    }

    public void dfs(int src,ArrayList<ArrayList<Integer>>adj,boolean []vis,ArrayList<Integer> order){
        vis[src] = true;
        for(Integer v : adj.get(src)){
            if(!vis[v])
                dfs(v, adj, vis, order);
        }

        order.add(src);
    }
}