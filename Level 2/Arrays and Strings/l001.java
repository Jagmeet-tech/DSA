import java.util.*;

public class l001{

    //Container with most water:- https://leetcode.com/problems/container-with-most-water/
    //T:O(n), S:O(1)
    public int maxArea(int[] height) {
        int maxArea = 0;
        int l = 0, r = height.length - 1;
        
        while(l < r){
            int area = Math.min(height[l],height[r]) * (r - l);
            maxArea = Math.max(area,maxArea);

            if(height[l] < height[r])
                l++;
            else
                r--;    
        }

        return maxArea;
    }

    //Squares of sorted array:- https://leetcode.com/problems/squares-of-a-sorted-array/description/
    //T:O(n), S:O(1)
    public int[] sortedSquares(int[] nums) {
        int res[] = new int[nums.length];
        int l = 0, r = nums.length - 1, k = nums.length - 1;
        
        while(l <= r){
           int sqr1 = Math.abs(nums[l] * nums[l]);
           int sqr2 = Math.abs(nums[r] * nums[r]);

            if(sqr1 < sqr2){
                res[k] = sqr2;
                k--;
                r--;
            }else{
                res[k] = sqr1;
                k--;
                l++;
            }
        }

        return res;
    }

    //Next Greater Element III:- https://leetcode.com/problems/next-greater-element-iii/description/
    //T:O(n), S:O(1)
    public int nextGreaterElement(int n) {
        char arr[] = (n + "").toCharArray();
        int i = arr.length - 1;

        //find dipIdx (decrease in dip)
        while(i > 0){
            if(arr[i-1] < arr[i]){
                break;
            }
            i--;
        }
        if(i == 0)
            return -1;

        int dipIdx = i - 1;
        int j = arr.length-1;
        
        //Just bda digit finded
        while(j > dipIdx){
            if(arr[j] > arr[dipIdx])
                break;
            j--;    
        }

        swap(arr,dipIdx,j);
        reverse(arr,dipIdx + 1,arr.length - 1);

        String s = new String(arr);
        long res = Long.parseLong(s);
        return res > Integer.MAX_VALUE ? -1 : (int)res;
    }

    public static void swap(char arr[],int i,int j){
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void reverse(char arr[],int i,int j){
        while(i<j){
            swap(arr,i,j);
            i++;
            j--;
        }
    }

    // Majority Element :- https://leetcode.com/problems/majority-element/description/
    //T:O(n) , S:O(1)
    public int majorityElement(int[] nums) {
        int count = 0;
        int me = 0;
        for(int i = 0;i<nums.length;i++){
            if(count == 0)
                me = nums[i];

            if(nums[i] == me)
                count++;
            else
                count--;
        }    

    // this step is not necessary.
        int fCount = 0;
        for(int i = 0;i<nums.length;i++){
            if(nums[i] == me)
                fCount++;
        }

        return (fCount > nums.length / 2) ?  me : -1;  
    }

}