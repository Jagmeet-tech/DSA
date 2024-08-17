// Searching and Sorting (Binary Search Group)
import java.util.*;;

public class l0014{

    //Kth smallest Element in sorted matrix:- https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/description/
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int m = matrix[0].length;
        int reqdKth = k;    //required kth smallest no.

        int lo = matrix[0][0];  //lowest allowed no.
        int hi = matrix[n-1][m-1];      //highest allowed no.

        while(lo < hi){
            int mid = (lo + (hi - lo) / 2);    //allowed smallest no.

            //count
            int actualKth = 0;      //actual kth smallest no.
            int i = 0 , j = m - 1;
            while(i < n && j >= 0){
                if(matrix[i][j] > mid){
                    j--;
                }else{
                    actualKth += (j + 1);   //smallest no. ka count jinki individual value less than equal mid value se.
                    i++;
                }
            }

            //binary search
            if(actualKth < reqdKth){
                lo = mid + 1;   //allowed smallest no. ki range badhao taaki kth smallest mil paye.
            }else{
                hi = mid;   //better kth smallest no. dhundne ka try kro.
            }
        }

        return lo;
    }

    //kth smallest no in mutliplication table:- https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/description/
    public int findKthNumber(int m, int n, int k) {
        int lo =  1; //smallest multipication table value.
        int hi = m * n;  //highest mutlplication table value.
 
        while(lo < hi){
            int mid = (lo + hi) / 2; //allocated smallest no.
 
             int count = 0;
             //count of smallest no. less than mid.
             int j = n;
             for(int i = 1;i<=m;i++){
                 while(j >= 1 && i * j > mid)
                     j--;

                 count += j; 
             }
 
             if(count < k){  //allocated smallest no. ki range badhao tabhi kth smallest mil payega.
                 lo = mid + 1;
             }else{  //aur smallest no. find kro range kam krke.
                 hi = mid;
             }
        }
 
        return lo;
    }

    //Kth smallest pair distance:- https://leetcode.com/problems/find-k-th-smallest-pair-distance/description/
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);  //taaki min and max values find kr paye for calculating lo and hi.

        int lo = 0;
        for(int i=0;i<nums.length - 1;i++){
            lo = Math.min(lo,nums[i + 1] - nums[i]);
        }

        int hi = nums[nums.length - 1] - nums[0];
        while(lo < hi){
            int mid = (lo + hi) / 2;    //allocated absolute difference

            //count of pairs jinka absolute difference mid se kam hai.
            int count = 0;
            for(int i=0;i < nums.length - 1;i++){
                int j = i + 1;
                while(j < nums.length && nums[j] - nums[i] <= mid){
                    j++;
                }
                count += j - i - 1; 
            }

            if(count < k){  // allocated diff kam reh gya usko badhao.
                lo = mid + 1;
            }else{  // zyada badh gya allocated diff,usko kam kro.
                hi = mid;
            }
        }

        return lo;
    }

    //kth smallest prime fraction:- https://leetcode.com/problems/k-th-smallest-prime-fraction/description/
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        Arrays.sort(arr);

        int n = arr.length, reqd = k;
        double lo = 0.0;     //lowest fraction value.
        double hi = 1.0;     //highest fraction value.

        while(lo <= hi){
            double mid = (lo + hi) / 2;    //allocated smallest fraction.

            //count of smaller fraction elements less than mid.
            int actualNos = 0;
            int j = 0;
            int p = 0,q = 1;            //result fraction numerator and denominator.    
            for(int i = 0;i<n;i++){
                while(j < n && arr[i] * 1.0 / arr[j] > mid)
                    j++;

                actualNos += arr.length - j;
                
                if(j < n && arr[i] * 1.0 / arr[j] > p * 1.0 / q){   //smallest fraction closest to mid.
                    p = arr[i];
                    q = arr[j];
                }
            }
        
            //binary search
            if(actualNos < reqd)
                lo = mid;       //estimated fraction / decimal chota reh gya usko badhao.
            else if(actualNos > reqd)
                hi = mid;       // estimated decimal bdi leli, usko chota kro.
            else{
                return new int[]{p,q};  //kth smallest fraction yhi / (closest) hai .
            }          
        }
        return new int[2];
    }

    //Find Minimum in rotated sorted array:- https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
    public int findMin(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;

        while(lo < hi){
            int mid = (lo + hi) / 2; 
            if(nums[mid] < nums[hi])
                hi = mid;
            else    
                lo = mid + 1;    
        }

        return nums[lo];
    }


    //Search in rotated sorted array:- https://leetcode.com/problems/search-in-rotated-sorted-array/description/
    public int search(int[] arr, int target) {
        int lo = 0;
        int hi = arr.length - 1;
        int idx = -1;

        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;

            if(arr[mid] == target){
                idx = mid;
                break;
            }else if(arr[mid] < arr[hi]){   // right sorted region
                if(arr[mid] < target && target <= arr[hi]){     //within range
                    lo = mid + 1;
                }else{      //opposite
                    hi = mid - 1;           
                }   
            }else{  //left sorted region
                if(arr[lo] <= target && target < arr[mid]){     //within range
                    hi = mid - 1;
                }else{      //opposite
                    lo = mid + 1;           
                }  
            }

        }
        return idx;
    }

    //Similar code (above question)
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length -1;

        while(lo < hi){ //finding smallest element idx in rotated sorted array. (2 region binary search)
            int mid = (lo + hi) / 2;
            if(nums[hi] > nums[mid]){
                hi = mid;
            }else{
                lo = mid + 1;
            }
        }

        int pivotIdx = lo;
        int res = binarySearch(nums,0,pivotIdx - 1,target);    //range1 mein find kro target.
        if(res != -1)
            return res;
        res = binarySearch(nums,pivotIdx,nums.length - 1,target);   //range2 find kro target
        return res;
    }


    public int binarySearch(int nums[],int lo,int hi,int target){   //3 region binary search
        while(lo <= hi){
            int mid = (lo + hi) / 2;
            if(nums[mid] < target){
                lo = mid + 1;
            }else if(nums[mid] > target){
                hi = mid - 1;
            }else{
                return mid;
            }
        }

        return -1;
    }

}