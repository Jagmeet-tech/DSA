import java.util.*;

public class l002{

    //Max chunks to make array sorted:- https://leetcode.com/problems/max-chunks-to-make-sorted/
    //T:O(n) , S:O(1)
    public int maxChunksToSorted(int[] arr) {
        int chunks = 0;
        int maxNo = (int)-1e9;
        for(int i = 0;i<arr.length;i++){
            maxNo = Math.max(maxNo, arr[i]);

            if(maxNo == i)
                chunks++;
        }
        
        return chunks;
    }

    //Max chunks to make array sorted II:- https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
    //T:O(n) , S:O(n)
    public int maxChunksToSortedII(int[] arr) {
        int chunks = 0,n = arr.length;
        int min[] = new int[n];
        min[n-1] = arr[n-1];

        for(int i = n-2;i>=0;i--){
            min[i] = Math.min(arr[i],min[i+1]);
        }

        int maxNo = (int)-1e9;
        for(int i = 0;i<n-1;i++){
            maxNo = Math.max(maxNo, arr[i]);

            if(maxNo <= min[i+1])
                chunks++;
        }

        return chunks + 1;
    }

    //No of subarrays with bounded maximum:- https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/
    //T:O(n), S:O(1)
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int si = -1, ei = -1;
        int count = 0;

        for(int i = 0;i<nums.length;i++){
            if(nums[i] >= left && nums[i] <= right){
                ei = i;
            }else if(nums[i] > right){
                ei = si = i;
            }else{
                //lesser than left (do nothing)
            }
            count += (ei - si);
        }

        return count;
    }

    //Long pressed name:- https://leetcode.com/problems/long-pressed-name/description/
    public boolean isLongPressedName(String name, String typed) {
        int i = 0;  //name
        int j = 0;  //typed 

        while(j < typed.length()){
            if(i < name.length() && name.charAt(i) == typed.charAt(j)){
                i++;
                j++;
            }else if( j > 0 && typed.charAt(j-1) == typed.charAt(j)){
                j++;
            }else{
                return false;
            }
        }

        return i == name.length();
    }

    //Largest number at least twice of others:- https://leetcode.com/problems/largest-number-at-least-twice-of-others/
    public int dominantIndex(int[] nums) {
        int idx1 = -1; //max ele
        int idx2 = -1; //second max ele

        for(int i = 0;i<nums.length;i++){
            int val = nums[i];
            if(idx1 == -1 || val >= nums[idx1]){
                idx2 = idx1;
                idx1 = i;
            }else if (idx2 == -1 || val > nums[idx2]){
                idx2 = i;
            }
        }


        if(nums[idx1] >= 2 * nums[idx2])
            return idx1;
            
        return -1;    
    }


}