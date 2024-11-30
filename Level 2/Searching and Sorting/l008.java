public class l008 {
    
    //1. Single Element in a Sorted Array :- https://leetcode.com/problems/single-element-in-a-sorted-array/description/
    public int singleNonDuplicate(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        int idx = -1;
        while(lo <= hi){
            int mid = (lo + hi) / 2;

            if(mid == 0 || mid == nums.length -1){  //edge case
                idx = mid;
                break;
            }else if(nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]){
                idx = mid;
                break;
            }else if(nums[mid] == nums[mid - 1]){
                int countElements = mid - lo + 1;   //including mid / me
                if(countElements % 2 == 0){ // means every element coming twice including me, so go in opposite direction.
                    lo = mid + 1;
                }else{
                    hi = mid;   // means every element not coming twice including me, so go in same direction.
                } 
            }else{  
                int countElements = hi - mid + 1; //including mid / me
                if(countElements % 2 == 0){ 
                    hi = mid - 1; // means every element coming twice including me, so go in opposite direction.
                }else{
                    lo = mid;   // means every element not coming twice including me, so go in same direction. 
                }
            }
        }
        return nums[idx];
    }

    //2. Punish the students:- https://www.geeksforgeeks.org/problems/punish-the-students5726/1
    public static int bubbleSort(int arr[]){
        int swaps = 0;
        for(int j = 0;j < arr.length - 1;j++){      // how many rounds
            for(int i = 0;i < arr.length - 1 - j;i++){      // each round iteration
                if(arr[i] > arr[i+1]){      //swap needed
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i+1] = temp;
                    swaps += 2;     //since two rollno.s are swapped here
                }
            }
        }
        return swaps;
    }

    public static int shouldPunish (int roll[], int marks[], int n, double avg){
        int totalNoSwaps = bubbleSort(roll);
        int totalMarks = 0;
        for(int mark : marks){
            totalMarks += mark;
        }

        double resAvg = (totalMarks - totalNoSwaps) / n * 1.0;
        return resAvg >= avg ? 1 : 0;
    }
}
