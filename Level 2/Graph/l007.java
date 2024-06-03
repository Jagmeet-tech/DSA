import java.util.*;

public class l007{
    static class Edge{
        int v,w;
        Edge(int v,int w){
            this.v = v;
            this.w = w;
        }
    }

    public static void addEdge(int u,int v,int w,ArrayList<Edge> []graph){
        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }


    //Kruskal Algorithm
    static int parent[];
    static int size[];


    public static int findParent(int u){
        if(parent[u] == u)
            return u;
        return parent[u] = findParent(parent[u]);    
    }

    public static void union(int p1,int p2){
        if(size[p1] <= size[p2]){
            parent[p1]= p2;
            size[p2] += size[p1];
        }else{
            parent[p2] = p1;
            size[p1] += size[p2];
        }
    }

    public static void unionFind(int [][]edges,ArrayList<Edge> []graph){
        int n = graph.length;
        parent = new int[n];        
        size = new int[n];
        
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
                addEdge(u,v,w,graph);
            }
        }
    }

    //[{u,v,w},{u,v,w},....]
    //T : O(V+ Elog(V))
    public static ArrayList<Edge>[] kruskalAlgo(int [][]edges,int n){
        // Based on weight (increasing order)
        Arrays.sort(edges,(a,b)->{
            return a[2] - b[2];
        });

        ArrayList<Edge>[] graph = new ArrayList[n];

        for(int i = 0;i<n;i++){
            graph[i] = new ArrayList<>();
        }

        unionFind(edges,graph);

        //MST (Minimum Spanning tree)
        return graph;   
    }

    //1168:- https://leetcode.com/problems/optimize-water-distribution-in-a-village/
    public int minCostToSupplyWater(int n,int []wells,int [][]pipes){
        ArrayList<int[]> allPipes = new ArrayList<>();
        for(int []p : pipes)
            allPipes.add(p);

        for(int i = 0;i<wells.length;i++){
            allPipes.add(new int[]{0,i+1,wells[i]});
        }    

        Collections.sort(allPipes,(a,b)->{
            return a[2] - b[2];
        });

        parent = new int[n+1];
        for(int i = 0;i<=n;i++)
            parent[i] = i;

        int cost = 0; 
        for(int e[] : allPipes){
            int u = e[0],v = e[1],w = e[2];
            int p1 = findParent(u);
            int p2 = findParent(v);

            if(p1 != p2){
                parent[p1] = p2;
                cost += w;
            }
        }  
        
        return cost;
    }

    //https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/practice-problems/algorithm/mr-president/
    public int mrPresident(int [][]edges,int n,int k){
        parent = new int[n + 1];
        for(int i =0;i<=n;i++)
            parent[i] = i;

        Arrays.sort(edges,(a,b)->{
            return a[2] - b[2];
        });
        
        ArrayList<Integer> roads = new ArrayList<>();
        int components = n,mCost = 0;   //mCost = maintaince cost
        for(int e[] : edges){
            int u = e[0],v = e[1],w = e[2];
            int p1 = findParent(u);
            int p2 = findParent(v);

            //removed cycle edges
            if(p1 != p2){
                parent[p1] = p2;
                components--;
                mCost += w;
                roads.add(w);
            }
        }  

        if(components > 1)
            return -1;

        int superRoads = 0;
        //max wt. roads ko super road mein convert.
        for(int i = roads.size() -1;i >=0;i--){
            if(mCost <= k)
                break;
            mCost = mCost - roads.get(i) + 1; 
            superRoads++;   
        }

        return mCost <= k ? superRoads : -1;
    }
}