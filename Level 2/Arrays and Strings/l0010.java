import java.util.*;

public class l0010{

    //Balance Array:- https://www.interviewbit.com/problems/balance-array/
    public int solve(int[] arr) {
        int os = 0;  //odd sum
        int es = 0; //even sum
        
        for(int i = 0;i<arr.length;i++){
            if(i % 2 == 0)
                es += arr[i];
            else 
                os += arr[i];    
        }

        int los = 0;    //left odd sum
        int les = 0;    //left even sum

        int count = 0;
        for(int i = 0;i<arr.length;i++){
            int ros,res = 0;        //right odd sum ,even sum
            if(i % 2 == 0){
                ros = os - los;    
                res = es -  les - arr[i];
            }else{
                res = es - les;
                ros = os - los - arr[i];             
            }

            if(los + res == les + ros)
                count++;

            if(i % 2 == 0)
                les += arr[i];
            else 
                los += arr[i];        
        }

        return count;
    }

    //Car fleet :- https://leetcode.com/problems/car-fleet/
    static class Pair implements Comparable<Pair>{
        int position;
        int speed;

        Pair(int position,int speed){
            this.position = position;
            this.speed = speed;
        }

        public int compareTo(Pair o){
            return this.position - o.position;
        }
    }  
    public int carFleet(int target, int[] position, int[] speed) {
            Pair []cars = new Pair[position.length];
            for(int i = 0;i<position.length;i++){
                Pair p = new Pair(position[i],speed[i]);
                cars[i] = p; 
            }

            Arrays.sort(cars);
            int fleets = 0;
            double maxTime = 0;
            for(int i = cars.length - 1;i>=0;i--){
                Pair currCar = cars[i];
                double cTime = (target - currCar.position) * 1.0 / currCar.speed;         // speed = dist / time

                if(cTime > maxTime){
                    fleets++;
                    maxTime = Math.max(cTime,maxTime);
                }
            }

            return fleets;
       }

    //Maximum Score from Subarray Minimums:- https://www.geeksforgeeks.org/problems/max-sum-in-sub-arrays0824/0
    public int pairWithMaxSum(List<Integer> arr) {
        int maxSum = 0;
        for(int i = 0;i<arr.size() - 1;i++){
            if(arr.get(i) + arr.get(i+1) > maxSum)
                maxSum = arr.get(i) + arr.get(i+1);
        }
        return maxSum;
    }

    //Smallest Range Covering Elements from K Lists:- https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/description/
    public int[] smallestRange(List<List<Integer>> nums) {
        class Pair{
            int li;        //list index
            int di;        //data index
            int val;       //value

            Pair(int li,int di,int val){
                this.li = li;
                this.di = di;
                this.val = val;
            }
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b)->{
            return a.val - b.val;
        });

        int res[] = new int[2];
        int maxVal = -(int)1e9; 
        for(int i = 0;i<nums.size();i++){   //every list first element inserted
            int val = nums.get(i).get(0);
            Pair p = new Pair(i, 0,val);
            maxVal = Math.max(maxVal,val);
            pq.add(p);
        }

        int rstart = 0,rend = 0,rsize = Integer.MAX_VALUE; //range start ,end,size
        while(pq.size() == nums.size()){    //pq ka size k jitna hai(matlab har list ka element hai tab tak)
            Pair p = pq.remove();
            int rangeDiff = maxVal - p.val;
            if(rangeDiff < rsize){
                rend = maxVal;
                rstart = p.val;
                rsize = rangeDiff;
            }

            if(p.di < nums.get(p.li).size() - 1){
                p.di++;
                int val = nums.get(p.li).get(p.di);
                Pair newPair = new Pair(p.li,p.di,val);
                pq.add(newPair);
                maxVal = Math.max(maxVal,val);
            }
        }
        
        res[0] = rstart;
        res[1] = rend;
        return res;
    }

    //First negative in every window of size k:- https://www.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1
    public long[] printFirstNegativeInteger(long A[], int N, int K)
    {
      long res[] = new long[N - K + 1];
      
      int i = 0;    //k window maintain krega.
      int j = 0;    //first negative pe shift hota rhega.
      
      while(i <= N - K){
          
        if(j < i){    //new window mai first new nagtive dhunde ke liyej ko set kiya.
            j = i;
          }
          
        while(j < i + K - 1 && A[j] > 0){ //first negative dhunda.
              j++;
          }
          
        if(A[j] < 0)
            res[i] = A[j];
            
        i++;    
      }
      
      return res;
    }
}