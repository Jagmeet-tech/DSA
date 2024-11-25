import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class l006 {
    
    //1. Count zeroes in sorted matrix :- https://www.geeksforgeeks.org/problems/count-zeros-in-a-sorted-matrix/1
    int countZeros(int mat[][], int N){
        int count = 0;
        int i = 0,j = mat[0].length - 1;

        while(i < mat.length && j >= 0){
            if(mat[i][j] == 1){
                j--;
            }else{
                count += j + 1;   //length = (index + 1)
                i++;
            }
        }

        return count;
    }

    //2. Counting elements in two arrays:- https://www.geeksforgeeks.org/problems/counting-elements-in-two-arrays/1
    //T : o(nlog(m)) , S:O(1)
    public static int findCeilIdx(int b[],int ele){
        int lo = 0;
        int hi = b.length - 1;
        int idx = -1;

        while(lo <= hi){
            int mid = (lo + hi) / 2;
            
            if(b[mid] > ele){  //just bda chahiye
                idx = mid;  //potential ans 
                hi = mid - 1;   //better dhundo
            }else{  
                lo = mid + 1;
            }
        }

        return idx;
    }

    public static int findFloorIdx(int arr[],int ele){
        int lo = 0;
        int hi = arr.length-1;
        int idx = -1;

        while(lo <= hi){
            int mid = (lo + hi) / 2;

            if(arr[mid] < ele){ //just chota chahiye
                idx = mid;  //potential ans
                lo = mid + 1;   //better dhundo 
            }else{
                hi = mid - 1;
            }
        }
        return idx;
    }

    public static ArrayList<Integer> countEleLessThanOrEqual(int a[], int b[]) {
        Arrays.sort(b);
        ArrayList<Integer> res = new ArrayList<>();

        for(int i = 0;i<a.length;i++){
            int idx = findCeilIdx(b,a[i]);
            idx = idx == -1 ? b.length : idx;
            res.add(idx);
        }

        return res;
    }

    //Optimised Approach T: O(n) , S:O(n)
    public static ArrayList<Integer> countEleLessThanOrEqualOptimised(int a[], int b[]) {
        int maxEle = findMaxElement(a,b);
        int fmap[] = new int[maxEle + 1];
        
        //frequency mapped
        for(int bElem : b){
            fmap[bElem]++;
        }
        
        //prefix sum
        int pSum = 0;
        for(int i = 0;i<fmap.length;i++){
            pSum += fmap[i];
            fmap[i] = pSum;
        }
        
        ArrayList<Integer> res = new ArrayList<>();
        for(int val : a){
            res.add(fmap[val]);
        }
        
        return res;
    }
    
    public static int findMaxElement(int a[],int b[]){
        int maxElem = -(int)1e9;
        for(int val : a){
            maxElem = Math.max(maxElem,val);
        }
        
        for(int val : b){
            maxElem = Math.max(maxElem,val);
        }
        
        return maxElem;
    }


    //3. Counts Zeros Xor Pairs:- https://www.geeksforgeeks.org/problems/counts-zeros-xor-pairs0349/0
    public static long calculate (int arr[], int n) {
        HashMap<Long,Long> fmap = new HashMap<>();
        for(long val : arr){
            if(fmap.containsKey(val)){
                fmap.put(val,fmap.get(val) + 1);
            }else{
                fmap.put(val, 1L);
            }
        }

        long countPairs = 0;
        for(long key : fmap.keySet()){
            if(fmap.get(key) > 1){
                long freq = fmap.get(key);
                countPairs += (freq * (freq - 1)) / 2;
            }
        }

        return countPairs;
    }

    //4. Facing the sun :-https://www.geeksforgeeks.org/problems/facing-the-sun2126/0
    public int countBuildings(int[] height) {
        int maxHt = 0;
        int count = 0;
        for(int ht : height){
            if(ht > maxHt){
                count++;
                maxHt = ht;
            }
        }
        return count;
    }

    //5. Distint absolute array elements :- https://www.geeksforgeeks.org/problems/distinct-absolute-array-elements4529/0
    public int distinctCount(int[] arr) {
        int count = 0;
        int left = 0;
        int right = arr.length - 1;
        int prev = -(int)1e9;  // left ka previous value dekhne ke liye
        int next = (int)1e9;   // right ka next value dekhne ke liye

        while(left <= right){
            int lval = Math.abs(arr[left]);
            int rval = Math.abs(arr[right]);

            if(lval == rval){
                if(lval != prev && rval != next)    // distinct check
                    count++;
                
                // pointers adjust
                prev = lval;
                left++;
                next = rval;
                right--;    
            }else if(lval > rval){
                if(lval != prev)    // distinct check
                    count++;
                prev = lval;
                left++;    
            }else{  // lval < rval
                if(rval != next)    // distinct check
                    count++;
                next = rval;
                right--;    
            }
        }

        return count;
    }
}
