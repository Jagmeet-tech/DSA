import java.util.*;

public class l004{

    //Directed Graph
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
    }

    // https://www.geeksforgeeks.org/problems/topological-sort/1
    static void dfs_topo(ArrayList<ArrayList<Integer>> adj,int src,boolean []vis,ArrayList<Integer> topoOrder){
        vis[src] = true;
        for(Integer v : adj.get(src)){
            if(!vis[v])
                dfs_topo(adj,v,vis,topoOrder);
        }
        topoOrder.add(src);
    }

    static int[] topoSort(int V, ArrayList<ArrayList<Integer>> adj) {
        boolean vis[] = new boolean[V];
        ArrayList<Integer> topoOrder = new ArrayList<>();
        
        for(int i = 0;i<V;i++){
            if(!vis[i])
                dfs_topo(adj,i,vis,topoOrder);
        }

        int []res = new int[topoOrder.size()];
        for(int i = 0;i<topoOrder.size();i++){
            res[i] = topoOrder.get(V-1-i);
        }

        return res;
    }

    //O(E+V) ~ O(N)
    public static ArrayList<Integer> kahansAlgo(ArrayList<Edge>[] graph){
        int n = graph.length;
        int indegree[] = new int[n];
        LinkedList<Integer> que = new LinkedList<>();
        ArrayList<Integer> ans = new ArrayList<>();     //storing order

        //Prepared indegree array.
        for(int i = 0;i<n;i++){
            for(Edge e : graph[i])
                indegree[e.v]++;
        }

        for(int i = 0;i<n;i++){
            if(indegree[i] == 0)
                que.addLast(i);
        }

        //BFS
        while(que.size() != 0){
            int rvtx = que.removeFirst();
            ans.add(rvtx);

            for(Edge e : graph[rvtx]){
                if(--indegree[e.v] == 0){
                    que.addLast(e.v);
                }
            }
        }

        if(ans.size() != n)
            ans.clear();

        return ans;
    }

    //207:- https://leetcode.com/problems/course-schedule/
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int n = numCourses;

        //graph construct
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0;i<n;i++){
            graph[i] = new ArrayList<>();
        }

        for(int []e : prerequisites){
            int u = e[0],v = e[1];
            graph[u].add(v);
        }

        //Kahan's Algo
        boolean canFinish = true; 
        int indegree[] = new int[n];
        LinkedList<Integer> que = new LinkedList<>();
        ArrayList<Integer> order = new ArrayList<>();   //order of processing/work

        for(int i = 0;i<n;i++){
            for(Integer v : graph[i])
                indegree[v]++;
        }

        for(int i = 0;i<n;i++){
            if(indegree[i] == 0)
                que.addLast(i);
        }

        while(que.size() != 0){
            int rvtx = que.removeFirst();
            order.add(rvtx);
            for(Integer v : graph[rvtx]){
                if(--indegree[v] == 0)
                    que.addLast(v);
            }
        }

        if(order.size() != n)
            canFinish = false;

        return canFinish;    
    }


    //210:- https://leetcode.com/problems/course-schedule-ii/ 
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int n = numCourses;

        //graph construct
        ArrayList<Integer>[] graph = new ArrayList[n];
        for(int i = 0;i<n;i++){
            graph[i] = new ArrayList<>();
        }

        for(int []e : prerequisites){
            int u = e[0],v = e[1];
            graph[u].add(v);
        }

        //Kahan's Algo
        int indegree[] = new int[n];
        LinkedList<Integer> que = new LinkedList<>();
        int[] order = new int[n];   //order of processing/work
        int idx = n-1;

        for(int i = 0;i<n;i++){
            for(Integer v : graph[i])
                indegree[v]++;
        }

        for(int i = 0;i<n;i++){
            if(indegree[i] == 0)
                que.addLast(i);
        }

        while(que.size() != 0){
            int rvtx = que.removeFirst();
            order[idx--] = rvtx;

            for(Integer v : graph[rvtx]){
                if(--indegree[v] == 0)
                    que.addLast(v);
            }
        }

        if(idx != -1)
            order = new int[0];

        return order;        
    }

    public static void main(String[] args) {
        constructGraph();
    }
}