import java.util.*;
public class l0018 {
    
    // Largest Number :- https://leetcode.com/problems/largest-number/description/
    public String largestNumber(int[] nums) {
        String []str = new String[nums.length];
        for(int i = 0;i<nums.length;i++){
            str[i] = nums[i] + "";
        }

        //criteria defined.
        Arrays.sort(str,(a,b) -> {
            String case1 = a + b;
            String case2 = b + a;

            return case2.compareTo(case1);  //max no. return (to get largest number at front :- decreasing order)
        });

        if(str[0].equals("0")){
            return "0";
        }

        //no. mein convert
        StringBuilder sb = new StringBuilder();
        for(String s : str){
            sb.append(s);
        }
        
        return sb.toString();
    }

    // Range Sum Query Mutable (Segement Tree) :- https://leetcode.com/problems/range-sum-query-mutable/description/
    class Node{
        int ss = 0; //segment start index
        int se = 0; //segment end index
        int val = 0;

        Node left,right = null;

        Node(int ss,int se){
            this.ss = ss;
            this.se = se;
        }
    }

    Node root;      //global root Node 

    public NumArray(int[] nums) {
        root = constructSegmentTree(nums,0,nums.length - 1);
    }

    public Node constructSegmentTree(int nums[],int si,int ei){
        if(si == ei){
            Node node = new Node(si,ei);
            node.left = node.right = null;
            node.val = nums[si];
            return node;
        }
        
        Node node = new Node(si,ei);
        int mid = (si + ei) / 2;
        node.left = constructSegmentTree(nums, si, mid);
        node.right = constructSegmentTree(nums, mid + 1, ei);
        node.val = node.left.val + node.right.val;

        return node;
    }
    
    void update(Node node,int idx,int val){
        if(node.ss == node.se){     //leaf node
            node.val = val;
            return;
        }
        
        int mid = (node.ss + node.se) / 2;
        if(idx <= mid){     //left side
            update(node.left,idx, val);
        }else{      //right side
            update(node.right,idx, val);
        }

        //postorder re-evaluate the values of effected nodes
        node.val = node.left.val + node.right.val;
    }

    public void update(int index, int val) {
        update(root,index, val);
    }
    
    int query(Node node,int qs,int qe){         //qs = query start index, qe = query end idx
        if(node == null || qs > node.se || qe < node.ss){       //no overlap / intersection
            return 0;
        }else if(node.ss >= qs && node.se <= qe){   //completely consumed
            return node.val;
        }else{  //some intersection / overlap
            int lval = query(node.left,qs,qe);
            int rval = query(node.right,qs,qe);
            return lval + rval;
        }
    }

    public int sumRange(int left, int right) {
        return query(root,left,right);
    }


    //Search pattern (Z-Algorithm) :- https://www.geeksforgeeks.org/problems/search-pattern-z-algorithm--141631/1
    ArrayList<Integer> search(String pat, String S){
        String str = pat + "$" + S;
        int z[] = new int[str.length()];        //Z array:- Storing largest substring length at z[i] which is also a prefix.
        z[0] = 0;

        //substring window establish pointers
        int l = 0;
        int r = 0;
        for(int i = 1;i<str.length();i++){
            if(i > r){      //new window need to develop
                l = r = i; 
                
                while(r < str.length() && str.charAt(r) == str.charAt(r - l)){      // (r - l) representing prefix window pointer
                    r++;
                }

                r--;   // unmatch char se ekk vapis aane ke liye
                z[i] = r - l + 1;       // r - l + 1 is saved -> as we need length
            }else{      // i pointer is within window. (so need to scan match character from ith character onwards)
                int k = i - l;  //prefix gap
                if(z[k] == r - i + 1){   // checking whether we need to extend previous window or not -> thorugh z[k] value.
                    l = i;      //extend current window  
                    while(r < str.length() && str.charAt(r) == str.charAt(r - l)){
                        r++;
                    }

                    r--;
                    z[i] = r - l + 1;
                }else{  
                    z[i] = z[k];        //same z value would work
                }
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0;i<z.length;i++){
            int val = z[i];
            if(val == pat.length())
                res.add(i - pat.length());      // to get actual text starting indexes as i is not actual indexes. ->(includes pat + "$" + S)
        }

        return res;
    }

}
