import java.util.*;

public class l001{
    
    class Student implements Comparable<Student>{
        int phy;
        int chem;
        int math;

        Student(int phy,int chem,int math){
            this.phy = phy;
            this.chem = chem;
            this.math = math;   
        }

        public int compareTo(Student other){
            if(this.phy - other.phy == 0){  //decision depends on chem
                if(this.chem - other.chem == 0){    //decision depends on math
                    return this.math - other.math;
                }else{
                    return other.chem - this.chem;
                }
            }else{
                return this.phy - other.phy;
            }
        }
    }

    //1. Marks of PCM :- https://www.geeksforgeeks.org/problems/marks-of-pcm2529/0
    public void customSort (int phy[], int chem[], int math[], int N){
        Student pair[] = new Student[N];
        for(int i = 0;i<N;i++){
            pair[i] = new Student(phy[i],chem[i],math[i]);
        }

        Arrays.sort(pair);
        for(int i = 0;i<N;i++){
            phy[i] = pair[i].phy;
            chem[i] = pair[i].chem;
            math[i] = pair[i].math;
        }
    }   
    
    //2. Union of two sorted arrays (duplicate) :- https://www.geeksforgeeks.org/problems/union-of-two-sorted-arrays-1587115621/1
    public static ArrayList<Integer> findUnion(int a[], int b[]) {
        ArrayList<Integer> res = new ArrayList<>();
        int i = 0,j = 0;
        while(i < a.length && j < b.length){
            if(a[i] < b[j]){
                if(res.size() == 0 || res.get(res.size() - 1) != a[i])
                    res.add(a[i]);
                i++;    
            }else if(a[i] > b[j]){
                if(res.size() == 0 || res.get(res.size() - 1) != b[j])
                    res.add(b[j]);
                j++;    
            }else{  //equal
                if(res.size() == 0 || res.get(res.size() - 1) != a[i])
                    res.add(a[i]);
                i++;
                j++;    
            }
        }


        while(i < a.length){
            if(res.size() == 0 || res.get(res.size() - 1) != a[i])
                    res.add(a[i]);
            i++;
        }

        while(j < b.length){
            if(res.size() == 0 || res.get(res.size() - 1) != b[j])
                    res.add(b[j]);
            j++;
        }

        return res;
    }

    //3. Search in 2d Matrix:- https://leetcode.com/problems/search-a-2d-matrix/description/
    public int findIthRow(int [][]matrix,int target){
        int lo = 0,hi = matrix.length - 1;
        int idx = -1;
        while(lo <= hi){
            int mid = (lo + hi) / 2;
            if(target >= matrix[mid][0] && target <= matrix[mid][matrix[0].length - 1]){
                idx = mid;
                break;
            }else if(matrix[mid][0] < target){
                lo = mid + 1;
            }else{
                hi = mid - 1;
            }
        }
        return idx;
    }

    public int findInIthRow(int [][]matrix,int target,int ridx){
        int lo = 0,hi = matrix[0].length - 1;
        int idx = -1;
        while(lo <= hi){
            int mid = (lo + hi) / 2;
            if(target > matrix[ridx][mid]){
                lo = mid + 1;
            }else if(target < matrix[ridx][mid]){
                hi = mid - 1;
            }else{
                idx = mid;
                break;
            }
        }
        return idx;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int ridx = findIthRow(matrix,target);
        if(ridx == -1)
            return false;
        int cidx = findInIthRow(matrix,target,ridx);
        return cidx != -1;    
    }

    //4. Search a 2D Matrix II :- https://leetcode.com/problems/search-a-2d-matrix-ii/description/
    public boolean searchMatrixII(int[][] matrix, int target) {
        int r = 0,c = matrix[0].length - 1;
        while(r < matrix.length && c >= 0){
            if(matrix[r][c] > target){  //current col. discarded.
                c--;
            }else if(matrix[r][c] < target){    //current mrow discarded.
                r++;
            }else{  //equal
                return true;
            }
        }
        return false;
    }

    //5. Find Pivot index :- https://leetcode.com/problems/find-pivot-index/description/
    public int pivotIndex(int[] nums) {
        int totalSum = 0;
        int idx = -1;
        for(int num : nums){
            totalSum += num;
        }

        int lsum = 0;
        for(int i = 0;i<nums.length;i++){
            int rsum = totalSum - lsum - nums[i];
            if(lsum == rsum){
                idx = i;
                break;
            }
            lsum += nums[i];
        }
        return idx;
    }

    //6. Find K Closest Elements (Using Priority Queue) :- https://leetcode.com/problems/find-k-closest-elements/description/
    class Pair implements Comparable<Pair>{
        int val;
        int diff;

        Pair(int val,int diff){
            this.val = val;
            this.diff = diff;
        }

        public int compareTo(Pair o){
            if(this.diff == o.diff)     // same difference , then check on value
                return o.val - this.val; 
            else
                return o.diff - this.diff;  //maximum difference vale remove first from pq.
        }
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        PriorityQueue<Pair> pq = new PriorityQueue<>(); 
        for(int i = 0;i<arr.length;i++){
            if(i < k){
                pq.add(new Pair(arr[i],Math.abs(x - arr[i])));
            }else{
                if(pq.peek().diff > Math.abs(x-arr[i])){
                    Pair rpair = pq.remove();
                    pq.add(new Pair(arr[i],Math.abs(x - arr[i])));
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        while(pq.size() != 0){
            res.add(pq.remove().val);
        }

        Collections.sort(res);
        return res;
    }

}