import java.util.*;

public class l002 {
    
    //1. Find pair with given difference:- https://www.geeksforgeeks.org/problems/find-pair-given-difference1559/1
    public boolean findPair(int n, int x, int[] arr) {
        Arrays.sort(arr);
        int i = 0, j = 1;

        while(j < n){
            int diff = arr[j] - arr[i];
            if(diff < x)
                j++;
            else if(diff > x)
                i++;
            else
                return true;    

            if(i == j)  //same value can't considered a pair.
                j++;    
        }

        return false;
    }

    //2. Roof top:- https://www.geeksforgeeks.org/problems/roof-top-1587115621/0
    public int maxStep(int arr[]) {
        int step = 0;
        int maxSteps = -(int) 1e9;

        int i = 0;
        while(i < arr.length - 1){
            if(arr[i + 1] > arr[i]){
                step++;
            }else{
                maxSteps = Math.max(maxSteps,step);
                step = 0; 
            }
            i++;
        }

        maxSteps = Math.max(maxSteps,step);
        return maxSteps;
    }

    //3. Max Value Permutation :- https://www.geeksforgeeks.org/problems/maximize-arrii-of-an-array0026/1
    int maxValue(int arr[]) {
        Arrays.sort(arr);
        long maxSum = 0;
        int mod = 1000000007;

        for(int i = 0;i<arr.length;i++){
            maxSum = (maxSum + (long) arr[i] * i) % mod;
        }
        return (int)maxSum;
    }

    //4. Max sum in configuration :- https://www.geeksforgeeks.org/problems/max-sum-in-the-configuration/1
    public long max_sum(int a[], int n) {
        long sum = 0;    
        long sIm1 = 0;   //sIm1 = sum i - 1

        for(int i =0;i<n;i++){
            sum += a[i];    // total sum
            sIm1 += (long)a[i] * i;       //initially sIm1 = s0 or we can say it is sum of rotation 0.
        }

        long res = sIm1; // max sum
        for(int i = 1;i<n;i++){    //rotation from 1 to n-1
            long sI = sIm1 + sum - (n * (long)a[n-i]);
            res = Math.max(sI,res);
            sIm1 = sI;
        }

        return res;
    }
}
