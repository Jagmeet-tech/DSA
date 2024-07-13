import java.util.*;

public class l006{

    //Partition Labels:- https://leetcode.com/problems/partition-labels/
    public List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        HashMap<Character,Integer> map = new HashMap<>();
        for(int i = 0;i<s.length();i++){
            char ch = s.charAt(i);
            map.put(ch,i);      //last index
        }
        
        int j = -1,lastIndex = -1;
        for(int i = 0;i<s.length();i++){
            char ch = s.charAt(i);
            int lidx = map.get(ch);
            lastIndex = Math.max(lidx,lastIndex);

            if(i == lastIndex){
                int len = i - j;
                res.add(len);
                j = i;
            }
        }

        return res;
    }

    //Partition Array into disjoint intervals:- https://leetcode.com/problems/partition-array-into-disjoint-intervals/description/
    // T:O(n) S:O(n)
    public int partitionDisjoint(int[] nums) {
        int n = nums.length;
        int rightMin[] = new int[n];
        for(int i = n-1;i>=0;i--){
            if(i == n-1)
                rightMin[i] = nums[i];
            else{
                rightMin[i] = Math.min(nums[i],rightMin[i+1]);
            }    
        }

        int leftMax = 0;
        int leftSize = -1;
        for(int i = 0;i<n-1;i++){
            leftMax = Math.max(leftMax,nums[i]);
            if(leftMax <= rightMin[i+1]){
                leftSize = i+1;
                break;
            }
        }

        return leftSize;
    }

    //Optimized: T:O(n) S:O(1) 
    public int partitionDisjointOpti(int[] nums) {
        int mtp = 0;   //max till partition idx
        int mts = 0;   //max till scanned   idx

        int partitionIdx = 0;
        for(int i = 1;i<nums.length;i++){
            int num = nums[i];
            if(num < nums[mtp]){    //partition update
                mtp = mts; 
                partitionIdx = i; 
            }     
            
            else if(num > nums[mts])    
                mts = i;

        }

        return partitionIdx + 1;
    }

    //Reach a number:- https://leetcode.com/problems/reach-a-number/
    public int reachNumber(int target) {
        int x = 0;  //moves or jumps
        target = Math.abs(target);  

        while(true){
            int range = (x * (x + 1)) / 2;  //target +ve krke khali +ve range check krli.
            if(range >= target && target % 2 == range % 2)  //target evn hai toh n bhi even hona chahiye tabhi target achievable. 
                break;

            x++;    
        }

        return x;
    }
}