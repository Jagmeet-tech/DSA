import java.util.LinkedList;
import java.util.List;

public class l003 {
    
    //1. First transition point:- https://www.geeksforgeeks.org/problems/find-transition-point-1587115620/0
    public int transitionPoint(int arr[]) {
        int lo = 0,hi = arr.length - 1;
        int idx = -1;
        while(lo <= hi){
            int mid = (lo + hi) / 2;
            if(arr[mid] == 1){
                idx = mid;
                hi = mid - 1;   // try to find smallest/first index of 1
            }else{
                lo = mid + 1;   // if 0 found means , 1 should be on right side (since sorted given)
            }
        }
        return idx;
    }

    //2. First Bad version:- https://leetcode.com/problems/first-bad-version/description/
    public int firstBadVersion(int n) {
        int lo = 1,hi = n;
        int idx = -1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;   //This way because of constraint:- lo = 1,hi = 2^31 - 1 then (lo + hi) will exceed integer limit.
            if(isBadVersion(mid)){  // isBadVersion is API fn.
                idx = mid;
                hi = mid - 1;   //first bad version
            }else{
                lo = mid + 1;   // [lo,mid] will not have any bad version , so discarding that range.
            }
        }
        return idx;
    }

    //3. Guess Number Higher or Lower :- https://leetcode.com/problems/guess-number-higher-or-lower/description/
    public int guessNumber(int n) {
        int lo = 1;
        int hi = n;
        int idx = -1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            int apiRes = guess(mid);    //guess is API fn.
            if(apiRes == -1){
                hi = mid - 1;
            }else if(apiRes == 1){
                lo = mid + 1;
            }else{ // 0
                idx = mid;
                break;
            }
        }
        return idx;
    }

    //4. Find K Closet Elements (Using Binary Search):- https://leetcode.com/problems/find-k-closest-elements/
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int lo = 0,hi = arr.length -1;
        int idx = 0; //closest element to target idx.
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            idx = Math.abs(x - arr[mid]) < Math.abs(x - arr[idx]) ? mid : idx;

            if(arr[mid] == x){
                idx = mid;
                break;
            }else if(arr[mid] > x){
                hi = mid - 1;
            }else{
                lo = mid + 1;
            }
        }

        LinkedList<Integer> res = new LinkedList<>();
        lo = idx - 1;
        hi = idx;
        while(res.size() < k && lo >= 0 && hi < arr.length){
            if(Math.abs(arr[lo] - x) <= Math.abs(arr[hi] - x)){         // <= isliye because if distance equal then prefer lo over hi as mentioned in question.
                res.addFirst(arr[lo]);
                lo--;
            }else{
                res.addLast(arr[hi]);
                hi++;
            }
        }

        while(res.size() < k && lo >=0){
            res.addFirst(arr[lo]);
            lo--;
        }

        while(res.size() < k && hi < arr.length){
            res.addLast(arr[hi]);
            hi++;
        }

        return res;
    }

    //5. Search in Rotated Sorted Array (Distinct value) :- https://leetcode.com/problems/search-in-rotated-sorted-array/description/
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        int idx = -1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            if(nums[mid] == target){    //equal condition checked here only,so no need to write equal in below "else" conditions.
                idx = mid;
                break;
            }else if(nums[mid] < nums[hi]){     //right side is sorted.
                if(target > nums[mid] && target <= nums[hi]){
                    lo = mid + 1;
                }else{
                    hi = mid - 1;
                }
            }else{  //left side is sorted.
                if(target >= nums[lo] && target < nums[mid]){
                    hi = mid - 1;
                }else{
                    lo = mid + 1;
                }
            }
        }
        return idx;
    }

    //6.Find Minimum in Rotated Sorted Array :- https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
    public int findMin(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;

        while(lo < hi){     // converges to lo.
            int mid = (lo + hi) / 2; 
            if(nums[mid] < nums[hi])  //Since it converges to lo , so mid should be compared with hi pointer as lo could be equal to mid 
                hi = mid;
            else    
                lo = mid + 1;    
        }

        return nums[lo];
    }

    //7. Find Kth Rotation :- https://www.geeksforgeeks.org/problems/rotation4723/1
    int findKRotation(int nums[], int n) {
       int lo = 0;
       int hi = nums.length - 1;
       int idx = 0;

       while(lo <= hi){
           int mid = (lo + hi) / 2;

           if(nums[mid] < nums[hi]){   //min left side exist
               hi = mid;
           }else{      //min right side exist
               lo = mid + 1;
           }
           idx = mid;
       }

       return idx;  // returned index of min element as it indicates kth rotation.
   }
}
