import java.util.*;

public class l005{

    //Minimum moves to make array elements equal I:- https://leetcode.com/problems/minimum-moves-to-equal-array-elements/description/
    public int minMoves(int[] nums) {
        int minEle = (int)1e9;
        int moves = 0;
        for(int num : nums){
            minEle = Math.min(minEle,num); 
        }
        
        for(int num : nums){
            moves += Math.abs(minEle - num);
        }
        return moves;
    }

    //Boats to save people:- https://leetcode.com/problems/boats-to-save-people/
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int boats = 0;
        int i = 0, j = people.length - 1;
        
        while(i < j){
            int wt = people[i] + people[j];
            if(wt <= limit){
                i++;
                j--;
            }else{
                j--;
            }
            boats++;
        }

        if(i == j)
            boats++;

        return boats;    
    }

    //Key Pair:- https://www.geeksforgeeks.org/problems/key-pair5616/1
    boolean hasArrayTwoCandidates(int arr[], int x) {
        Arrays.sort(arr);
        int i = 0,j = arr.length - 1;

        while(i < j){
            int sum = arr[i] + arr[j];
            if(sum == x)
                return true;
            else if(sum > x)
                j--;
            else
                i++;        
        }

        return false;
    }

    //Find pair with given difference:- https://www.geeksforgeeks.org/problems/find-pair-given-difference1559/1
    public int findPair(int n, int x, int[] arr) {
        Arrays.sort(arr);
        int i = 0, j = 1;

        while(j < n){
            int diff = arr[j] - arr[i];
            if(diff < x)
                j++;
            else if(diff > x)
                i++;
            else
                return 1;    

            if(i == j)  //same value can't considered a pair.
                j++;    
        }

        return -1;
    }

    //Best meeting point:- https://leetcode.com/problems/best-meeting-point/
    public static int minDistance(int [][]grid){
        ArrayList<Integer> rows = new ArrayList<>();
        for(int i = 0;i<grid.length;i++){
            for(int j = 0;j<grid[0].length;j++){
                if(grid[i][j] == 1)
                    rows.add(i);
            }
        }

        ArrayList<Integer> cols = new ArrayList<>();
        for(int j = 0;j<grid[0].length;j++){
            for(int i = 0;i<grid.length;i++){
                if(grid[i][j] == 1)
                    cols.add(j);
            }
        }

        //median point
        int r = rows.get(rows.size() / 2);
        int c = cols.get(cols.size() / 2);

        int d1 = 0;
        for(int row : rows){
            d1 += Math.abs(row - r);
        }

        int d2 = 0;
        for(int col : cols){
            d2 += Math.abs(col - c);
        }

        return d1 + d2;
    }

}