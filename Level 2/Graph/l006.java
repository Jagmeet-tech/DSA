import java.util.*;

public class l006{

    static int []parent;
    static int []size;

    public static int findParent(int u){
        if(parent[u] == u)
            return u;

        return parent[u] = findParent(parent[u]);    
    }

    //695:- https://leetcode.com/problems/max-area-of-island/description/
    public int maxAreaOfIsland(int[][] grid) {  
        int n = grid.length,m = grid[0].length;
        parent = new int[n*m];
        size = new int[n*m];
        int dir[][] = {{0,1},{1,0}};

        for(int i = 0;i<n*m;i++){
            parent[i] = i;
            size[i] = 1;
        }

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(grid[i][j] == 1){
                    int p1 = findParent(i * m + j);     //global parent
                    for(int d = 0;d<dir.length;d++){
                        int r = i + dir[d][0];
                        int c = j + dir[d][1];

                        if(r>= 0 && c >= 0 && r < n && c < m && grid[r][c] == 1){
                            int p2 = findParent(r * m + c);
                            if(p1 != p2){
                                parent[p2] = p1;
                                size[p1] += size[p2];
                            }
                        }
                    }
                }
            }
        }

        int maxArea = 0;
        for(int i = 0;i<n*m;i++){
            //global leader ka maxArea(size) compare krenge
            if(parent[i] == i && size[i] > maxArea && grid[i/m][i%m] == 1)  
                maxArea = size[i]; 
        }

        return maxArea;
    }

    //1061:- https://leetcode.com/problems/lexicographically-smallest-equivalent-string/
    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        int n = 26;
        parent = new int[n];

        for(int i = 0;i<n;i++){
            parent[i] = i;
        }

        for(int i = 0;i<s1.length();i++){
            char ch1 = s1.charAt(i);
            char ch2 = s2.charAt(i);

            int p1 = findParent(ch1 - 'a');
            int p2 = findParent(ch2 - 'a');

            if(p1 != p2){
                if(p1 < p2)
                    parent[p2] = p1;
                else
                    parent[p1] = p2;    
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<baseStr.length();i++){
            char ch = baseStr.charAt(i);
            int par = findParent(ch - 'a');

            sb.append((char) (par + 'a'));
        }

        return sb.toString();
    }

    //990:- https://leetcode.com/problems/satisfiability-of-equality-equations/
    public boolean equationsPossible(String[] equations) {
        int n = 26;
        parent = new int[n];
        boolean res = true; 

        for(int i = 0;i<n;i++)
            parent[i] = i;

        for(String str : equations){
            char ch1 = str.charAt(0);
            char ch2 = str.charAt(3);
            char symbol = str.charAt(1);


            int p1 = findParent(ch1 - 'a');
            int p2 = findParent(ch2 - 'a');

            if(p1 != p2 && symbol == '='){
                parent[p2] = p1;
            }
        }
        
        for(String str : equations){
            char ch1 = str.charAt(0);
            char ch2 = str.charAt(3);
            char symbol = str.charAt(1);


            int p1 = findParent(ch1 - 'a');
            int p2 = findParent(ch2 - 'a');

            if(p1 == p2 && symbol == '!')
                res = false;
        }
        
        return res;
    }

    //839:- https://leetcode.com/problems/similar-string-groups/
    public static boolean isSimilar(String s1,String s2){
        int count = 0;
        if(s1.length() != s2.length())
            return false;

        for(int i = 0;i<s1.length();i++){
            if(s1.charAt(i) != s2.charAt(i) && ++count > 2)
                return false;
        }

        return true;
    }

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        parent = new int[n];

        int groups = strs.length;
        for(int i = 0;i<n;i++){
            parent[i] = i;
        }

        for(int i =0;i<strs.length-1;i++){
            String s1 = strs[i];

            int p1 = findParent(i);
            for(int j = i+1;j<strs.length;j++){
                String s2 = strs[j];
                int p2 = findParent(j);

                if(p1 != p2 && isSimilar(s1,s2)){
                    parent[p2] = p1;
                    groups--;
                }
            }
        }

        return groups;
    }

    //305:- https://leetcode.com/problems/number-of-islands-ii/
    public List<Integer> numIslands2(int n,int m,int [][]positions){
        List<Integer> ans = new ArrayList<>();
        parent = new int[n*m];
        Arrays.fill(parent,-1);     //indicating no land is present currently so no parent is there.

        int [][]dir = {{0,1},{0,-1},{1,0},{-1,0}};
        int count = 0;
        for(int []p : positions){
            int i = p[0], j = p[1];

            //no land is there
            if(parent[i * m + j] == -1){
                count++;
                parent[i * m + j] = i * m + j;

                int p1 = findParent(i * m + j);
                for(int d = 0;d<dir.length;d++){
                    int r = i + dir[d][0];
                    int c = j + dir[d][1];

                    if(r >= 0 && c >= 0 && r < n && c < m && parent[r * m + c] != -1){
                        int p2 = findParent(r * m + c);
                        if(p1 != p2){
                            count--;
                            parent[p2] = p1;
                        }
                    }
                }
            }

            ans.add(count);
        }

        return ans;
    }

    //684:- https://leetcode.com/problems/redundant-connection/description/
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length , m = edges[0].length;
        int []ans = new int[2];
        parent = new int[n+1];    
  
        for(int i=0;i<n;i++)
            parent[i] = i;
  
        for(int []e : edges){
            int i = e[0], j = e[1];
            int p1 = findParent(i);
            int p2 = findParent(j);
            if(p1 == p2 ){  //cycle
              ans[0] = i;
              ans[1] = j;
            }
            else{
                parent[p2] = p1;
            }
        }
        return ans;
      }
}