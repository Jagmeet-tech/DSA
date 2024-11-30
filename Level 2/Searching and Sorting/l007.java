import java.lang.reflect.Array;
import java.util.*;

public class l007{

    //1. Largest Perimeter triangle:- https://leetcode.com/problems/largest-perimeter-triangle/description/
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        int idx = nums.length - 3;

        while(idx >= 0){
            int a = nums[idx];      //third last element
            int b = nums[idx + 1];  //second last element
            int c = nums[idx + 2];  // last element

            if(a + b > c){
                return a + b + c;
            }
            idx--;    
        }
        return 0;
    }

    //2. Largest Number :- https://leetcode.com/problems/largest-number/description/
    public String largestNumber(int[] nums) {
        String [] nos = new String[nums.length];
        for(int i = 0;i<nums.length;i++){
            nos[i] = nums[i] + "";
        }
        
        Arrays.sort(nos,(a,b) -> {
            String case1 = a+b;
            String case2 = b+a;

            return case2.compareTo(case1);  //to get largest number at front (decreasing order)
        });

        if(nos[0].equals("0"))
            return "0";

        StringBuilder ans = new StringBuilder();
        for(String s : nos){
            ans.append(s);
        }

        return ans.toString();
    }

    //3. Ishaan and sticks :- Portal
    public static ArrayList<Integer> solve(int []arr){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int val : arr){
            if(map.containsKey(val)){
                map.put(val,map.get(val) + 1);
            }else{
                map.put(val, 1);
            }
        }

        int maxArea = -(int)1e9;
        int countSq = 0;
        for(int stick : map.keySet()){
            int freq = map.get(stick);
            int area = stick * stick;
            if(freq >= 4 && area > maxArea){
                maxArea = area;
                countSq = freq / 4;
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        if(maxArea == -(int)1e9){
            res.add(-1);
        }else{
            res.add(maxArea);
            res.add(countSq);
        }
        return res;
    }

    //4. Toppers of class:- https://www.geeksforgeeks.org/problems/toppers-of-class3826/1
    class Node{
        int marks;
        int idx;

        Node(int marks,int idx){
            this.marks = marks;
            this.idx = idx;
        }
    }

    public List<Integer> kToppers(int[] arr, int k) {
        Node node[] = new Node[arr.length]; 
        for(int i = 0;i < arr.length;i++){
            node[i] = new Node(arr[i],i);
        }

        Arrays.sort(node,(a,b)->{
            if(a.marks != b.marks){ 
                return b.marks - a.marks;   //decreasing order of marks
            }else{
                return a.idx - b.idx;   // increasing order of indexes
            }
        });

        List<Integer> res = new ArrayList<>();
        for(int i = 0;i<node.length;i++){
            int idx = node[i].idx;
            if(i == 0){
                res.add(idx);
                k--;
            }else{
                if(k > 0){
                    int lastIdxInserted = res.get(res.size() - 1);
                    int lastValueOfThatIdx = arr[lastIdxInserted];
                    if(lastValueOfThatIdx != node[i].marks){    // k decrement will not take place if previous and current value equal.
                         k--;
                    }
                    res.add(idx);       // everytime whether value equals to last inserted or not.
                }else{  // k <= 0 && similar elements needs to be added.
                    int lastIdxInserted = res.get(res.size() - 1);
                    int lastValueOfThatIdx = arr[lastIdxInserted];
                    if(lastValueOfThatIdx == node[i].marks){
                        res.add(idx);
                    }
                }
            }
        }
        return res;
    }
}