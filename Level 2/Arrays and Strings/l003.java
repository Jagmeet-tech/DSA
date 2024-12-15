import java.util.*;

public class l003 {
    
    //Sort array by parity:- https://leetcode.com/problems/sort-array-by-parity/
    public void swap(int []nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    public int[] sortArrayByParity(int[] nums) {
        int i = 0,j = 0;
        while(i < nums.length){
            if(nums[i] % 2 != 0){   //odd
                i++;
            }else{              //even
                swap(nums,i,j);
                i++;
                j++;
            }
        }
        return nums;
    }

    // Reverse vowels of string:- https://leetcode.com/problems/reverse-vowels-of-a-string/description/
    public String reverseVowels(String s) {
        StringBuilder sb = new StringBuilder(s);
        String vowel = "aeiouAEIOU";
        int i = 0;
        int j = s.length() - 1;
        while(i < j){
            while(i < j && vowel.indexOf(sb.charAt(i)) == -1)
                i++;
            while(i < j && vowel.indexOf(sb.charAt(j)) == -1)
                j--;

            if(i < j){
                swap(sb,i,j);
                i++;
                j--;
            }        
        }

        return sb.toString();
    }

    public void swap(StringBuilder sb,int i,int j){
        char temp = sb.charAt(i);
        sb.setCharAt(i,sb.charAt(j));
        sb.setCharAt(j,temp); 
    }


    //Min moves to make array elements equal II:- https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length -1;

        int minMoves = 0;
        while(i < j){
            minMoves += Math.abs(nums[j] - nums[i]);        //making elements equal to median.
            i++;
            j--;
        }

        return minMoves;
    }

    // Product of Array Except Self :- https://leetcode.com/problems/product-of-array-except-self/description/
    public int[] productExceptSelf(int[] nums) {
        int right[] = new int[nums.length];     // storing right side product.

        for(int i = nums.length - 1;i>=0;i--){
            if(i == nums.length - 1){
                right[i] = 1;
            }else{
                right[i] = right[i+1] * nums[i+1];
            }
        }

        int lProd = 1;  // left side product calculating while iterate.
        int res[] = new int[nums.length];
        for(int i = 0;i<nums.length;i++){
            res[i] = lProd * right[i];
            lProd *= nums[i];
        }

        return res;
    }

    // Maximum swap :- https://leetcode.com/problems/maximum-swap/description/
    public int maximumSwap(int num) {
        char []arr = (num + "").toCharArray();
        int maxElemIdxOnRight[] = new int[arr.length];  //storing idx
        
        for(int i = arr.length - 1 ;i>=0;i--){
            if(i == arr.length - 1){
                maxElemIdxOnRight[i] = -1;
            }else{
                if(maxElemIdxOnRight[i + 1] == -1){
                    maxElemIdxOnRight[i] = i + 1;
                }else{
                    int idx = maxElemIdxOnRight[i + 1];
                    int val = arr[idx];  
                    if(val >= arr[i+1]){        //equal ex:- input :- 1993  output :- 9913
                        maxElemIdxOnRight[i] = maxElemIdxOnRight[i + 1];
                    }else{
                        maxElemIdxOnRight[i] = i + 1;
                    }
                }
            }
        }

        for(int i = 0;i<arr.length;i++){
            int maxDigitOnRightIdx = maxElemIdxOnRight[i];
            if(maxDigitOnRightIdx != -1 && arr[maxDigitOnRightIdx] > arr[i]){   //swap posible
                //swapping
                char temp = arr[maxDigitOnRightIdx];
                arr[maxDigitOnRightIdx] = arr[i];
                arr[i] = temp;

                return Integer.parseInt(new String(arr));
            } 
        }
        return num;
    }

    // Maximize Distance to Closest Person :- https://leetcode.com/problems/maximize-distance-to-closest-person/description/
    public int maxDistToClosest(int[] seats) {
        int prev = -1;
        int i = 0;

        int maxDist = -(int) 1e9;
        while(i < seats.length){
            if(seats[i] == 1){
                if(prev == -1){     // first time 1 encounter ex: 0001
                    maxDist = i;
                }else{      //middle pe seat allot ex: 10001
                    int dist = (i - prev) / 2;
                    maxDist = Math.max(dist,maxDist);
                }
                prev = i;
            }
            i++;
        }

        //one case left, where it needs to calculate max distance again. (last one ex: 1000)
        if(seats[i-1] == 0)
            maxDist = Math.max(maxDist,i - 1 - prev);
        
        return maxDist;
    }
}
 