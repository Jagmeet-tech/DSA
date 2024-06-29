import java.util.*;

import javafx.scene.layout.Priority;

public class l004 {

    //Sort matrix diagonally:- https://leetcode.com/problems/sort-the-matrix-diagonally/
    public int[][] diagonalSort(int[][] mat) {
        int n = mat.length, m = mat[0].length;
        HashMap<Integer,PriorityQueue<Integer>> map = new HashMap<>();
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                int gap = i - j;
                
                if(map.containsKey(gap) == false)
                    map.put(gap,new PriorityQueue<>());

                map.get(gap).add(mat[i][j]);    
            }
        }

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                int gap = i - j;
                mat[i][j] = map.get(gap).remove();
            }
        }
        
        return mat;
    }


    //First Missing positive :- https://leetcode.com/problems/first-missing-positive/description/
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        //these no. don't participate in first positive no
        for(int i = 0;i<n;i++){
            if(nums[i] <= 0 || nums[i] > n){
                nums[i] = n + 1;
            }
        }

        for(int i = 0;i<n;i++){
            int val = Math.abs(nums[i]);
            if(val <= n){
                int idx = val - 1;
                if(nums[idx] > 0)
                    nums[idx] = -1 * nums[idx]; //pechan daaldi
            }
        }

        for(int i = 0;i<n;i++){
            int val = nums[i];
            if(val > 0)
                return i + 1;
        }

        return n + 1;
    }

    //Pairs of song :- https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/
    public int numPairsDivisibleBy60(int[] time) {
        int fmap[] = new int[60];
        int count = 0;
        for(int t: time){
            int rem = t % 60;

            if(rem == 0)
                count += fmap[rem];
            else
                count += fmap[60 - rem];

            fmap[rem]++;        
        }

        return count;
    }

    // Minimum Platforms:- https://www.geeksforgeeks.org/problems/minimum-platforms-1587115620/1    (Similar like merge two sorted list)
    static int findPlatform(int arr[], int dep[], int n){
        Arrays.sort(arr);
        Arrays.sort(dep);

        int i = 0, j = 0;
        int max = 0 , countPlatforms = 0;

        while(i < arr.length && j < dep.length){
            if(arr[i] <= dep[j]){
                i++;
                countPlatforms++;   //arrival event
            }else{
                j++;
                countPlatforms--;   //departure event
            }

            max = Math.max(max,countPlatforms);
        }
        
        return max;
    }

    //Car Pooling:- https://leetcode.com/problems/car-pooling/
    public boolean carPooling(int[][] trips, int capacity) {
        TreeSet<Integer> stops = new TreeSet<>();   //processing order of stops in sorted order  (no duplicate entry)
        int []map = new int[1001];
        
        for(int trip[] : trips){
            int passenger = trip[0];
            int from = trip[1];
            int to = trip[2];

            map[from] += passenger;
            stops.add(from);

            map[to] += -passenger;
            stops.add(to);
        }

        int pic = 0;
        for(int stop : stops){
            pic += map[stop];

            if(pic > capacity)
                return false;
        }

        return true;
    }

    //(Priority Queue Solution of above question.)
    static class Pair implements Comparable<Pair>{
        int time;
        int delta;

        Pair(int time,int delta){
            this.time = time;
            this.delta = delta;
        }

        //Always think ablut "this" only.
        public int compareTo(Pair o){
            if(this.time != o.time){
                return this.time - o.time;
            }else{
                if(this.delta < 0)
                    return -1;      // this ko priority mile and vo remove ho pehle since delta -ve hai.
                else if(o.delta < 0)
                    return +1;
                else
                    return 0;       
            }    
        }

    }

    public boolean carPooling_PQ(int[][] trips, int capacity) {
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        for(int trip[] : trips){
            int passenger = trip[0];
            int from = trip[1];
            int to = trip[2];

            pq.add(new Pair(from, +passenger));
            pq.add(new Pair(to, -passenger));
        }

        int pic = 0;
        while(pq.size() > 0){
            Pair pair = pq.remove();
            pic += pair.delta;

            if(pic > capacity)
                return false;
        }
        return true;
    }

}
