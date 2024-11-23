public class l004 {
    
    //1. Count Inversion :- https://www.geeksforgeeks.org/problems/inversion-of-array-1587115620/
    static int countInv;
    public static int[] mergeTwoSortedArray(int left[],int right[]){
        int i = 0,j = 0,k = 0;
        int res[] = new int[left.length + right.length];
        while(i < left.length && j < right.length){
            if(left[i] <= right[j]){
                res[k] = left[i];
                i++;
            }else{
                res[k] = right[j];
                countInv += (left.length - i);   // counting inversion here as left[i] > right[j] and both left and right arr are sorted.
                j++;
            }
            k++;
        }

        while(i < left.length){
            res[k++] = left[i++];
        }
        while(j < right.length){
            res[k++] = right[j++];
        }
        return res;
    }

    public static int[] mergeSort(int arr[],int si,int ei){
        if(si == ei){
            return new int[]{arr[si]};
        }
        
        int mid = si + (ei - si) / 2;

        int leftRes[] = mergeSort(arr, si, mid);
        int rightRes[] = mergeSort(arr, mid + 1, ei);

        return mergeTwoSortedArray(leftRes,rightRes);
    }
    
    static int inversionCount(int arr[]) {
        countInv = 0;
        int res[] = mergeSort(arr,0,arr.length - 1);
        return countInv;
    }

    //2. Median of two sorted array :- https://leetcode.com/problems/median-of-two-sorted-arrays/description/
    public double findMedianSortedArrays(int[] a, int[] b) {
        int n1 = a.length;
        int n2 = b.length;

        if(n1 > n2){    //assuming a[] will be smaller than b[]
            int temp[] = a;
            a = b;
            b = temp;

            int t = n1;
            n1 = n2;
            n2 = t;
        }

        int lo = 0,hi = n1;
        int tel = n1 + n2;  //total elements

        while(lo <= hi){
            int ali = lo + (hi - lo) / 2;    // aleft is equivalent to mid.  ali -> a left index
            int bli = (tel + 1) / 2  - ali;  // why tel + 1 , because to handle even and odd both case. bli -> b left index  
            

            // Values 
            int alm1 = ali == 0 ? Integer.MIN_VALUE : a[ali - 1];
            int al = ali == n1 ? Integer.MAX_VALUE : a[ali];

            int blm1 = bli == 0 ? Integer.MIN_VALUE : b[bli - 1];
            int bl = bli == n2 ? Integer.MAX_VALUE : b[bli];


            //check for valid spliting 
            if(alm1 <= bl && blm1 <= al){
                double median = 0.0;
                if(tel % 2 == 0){   //even elements
                    median = (Math.max(alm1, blm1) + Math.min(al, bl)) / 2.0; 
                }else{  //odd elements
                    median = Math.max(alm1,blm1);  // Taken more elements in left set (while caluculating bli), so mid in case of odd will exist in left set.
                }
                return median;
            }else if(blm1 > al){
                lo = ali + 1;    //(Reajust the set) taaki left set mein aur elements accomadate krne honge right ke taaki left set mein saare smaller ho.
            }else{
                hi = ali - 1;   //(Reajust the set) taaki right set mein aur elements accomadate krne honge left ke taaki right set mein saare greater ho.
            }
        }
        return 0.0;
    }


}
