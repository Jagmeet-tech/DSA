import org.omg.CORBA.MARSHAL;

public class l005{

    //1. Koko Eating bananas :- https://leetcode.com/problems/koko-eating-bananas/description/
    public boolean isPossible(int []piles,int h,int speed){
        int calHr = 0;
        for(int pile : piles){
            calHr += (int) Math.ceil(pile * 1.0 / speed);
        }
        return calHr <= h;
    }   

    public int minEatingSpeed(int[] piles, int actualHr) {
        int lo = 1;         // min eating speed to complete a pile.
        int hi = Integer.MIN_VALUE;     //max eating speed to complete a pile.
        for(int pile : piles){
            hi = Math.max(pile,hi);
        }   

        while(lo < hi){
            int mid = (lo + hi) / 2;    //minimum eating speed within h hrs.

            //check with time or not.
            if(isPossible(piles,actualHr,mid) == true){
                hi = mid;   // hours toh shi hai but speed koi aur minimize kro.
            }else{
                lo = mid + 1;   //  hours bad gye means speed kam reh gyi , toh usko badhao.
            }
        }
        return lo;  // lo pointer will reach at last at appropriate location.
    }

    //2. Find the Smallest Divisor Given a Threshold:-https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/description/
    public int smallestDivisor(int[] nums, int threshold) {
        int lo = 1;     // minimum divisor
        int hi = 1000000;     //maximum divisor (given in constraint)
        // for(int num : nums){
        //     hi = Math.max(hi,num);
        // }   

        while(lo < hi){
            int mid = (lo + hi) / 2;    //divisor
            
            int actualSum = 0;
            for(int num : nums){
                actualSum += (int) Math.ceil(num * 1.0 / mid);
            }

            //checking
            if(actualSum > threshold){  //divisor chota le liya, usko badhao.
                lo = mid + 1;
            }else{  // divisor toh shi hai tabhi division sum within threshold aa rha hai, but try to minimize divisor.
                hi = mid;   // as mid could be the ans.
            }   
        }

        return lo;
    }

    //3. Allocate Minimum Pages:- https://www.geeksforgeeks.org/problems/allocate-minimum-number-of-pages0937/1
    public static int findPages(int[] books, int k) {
        
        if(books.length < k){
            return -1;  // books kam hai , student zyada hai (not possible)
        }
        
        int lo = Integer.MIN_VALUE;; //min no of books / pages allocated to a student.
        int hi = 0; // max no of books / pages allocated to a student.

        for(int book : books){
            lo = Math.max(lo,book);
            hi += book;
        }

        while(lo < hi){
            int mid = (lo + hi) / 2;  //allowed pages.
            
            int students = 1;   //default 1 student to read krega hi.
            int pagesSum = 0;
            for(int book : books){
                if(pagesSum + book > mid){
                    students++;
                    pagesSum = book;
                }else{
                    pagesSum += book;
                }
            }

            //check
            if(students > k){   //allocated pages ko badhana hoga.
                lo = mid + 1;
            }else{    //allocated pages to shi hai tabhi within k students mein pages divide hogye ,but allocated pages aur minimize kro. 
                hi = mid;   
            }
        }

        return lo;
    }


    //h:W split largest sum array and capacity to ship within D Days.

    //4. Split array largest sum :- https://leetcode.com/problems/split-array-largest-sum/description/
    public int splitArray(int[] nums, int k) {
        int lo = -(int)1e9; // min allowed sum in a partition
        int hi = 0; // max allowed sum in a partition
         
        for(int num : nums){
            hi += num;
            lo = Math.max(lo,num);
        }

        while(lo < hi){
            int mid = (lo + hi) / 2;    // allowed sum 

            int partitions = 0;
            int pSum = 0;   //partition sum
            for(int num : nums){
                if(pSum + num > mid){
                    partitions++;   // next partition begin.
                    pSum = 0;
                }
                pSum += num;
            }

            partitions++;  // as k is subarray , so 1 partition = 2 subarrays. So in order to compare partition and subarray, incremented the partitions to make both comparable.
           
            //check
            if(partitions > k){     //allowed sum kam reh gya tabhi partitions bad gye, toh allowedsum badhao taaki more elements accomadate ho paye. 
                lo = mid + 1;
            }else{   // allowed sum shi hai tabhi within k patitions sum aa rha hai , but aur chota allowed sum try kro.
                hi = mid;
            }
        }
        return lo;
    }
    
    //5. Capacity To Ship Packages Within D Days :- https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/description/
    public int shipWithinDays(int[] weights, int days) {
        int lo = -(int)1e9;     // min wt to ship packages per day.
        int hi = 0;         // max wt. to ship packages ship per day.

        for(int wt : weights){
            lo = Math.max(wt,lo);
            hi += wt;
        }

        while(lo < hi){
            int mid = (lo + hi) / 2; // allowed wt. to ship packages.

            int countDays = 1;  //default day = 1
            int packagesWtSum = 0;
            for(int wt : weights){
                if(packagesWtSum + wt > mid){
                    packagesWtSum = 0;
                    countDays++;    //next day ship packages
                }
                packagesWtSum += wt;
            }

            //check
            if(countDays > days){   // wt. kam reh gya tabhi zyada days lag rhe hain, toh wt. of ship badhao.
                lo = mid + 1;
            }else{  // wt. toh shi hai ship ka tabhi within d days ship kr pa rhe hai , but aur minimum wt. dhundo ship ka. 
                hi = mid;
            }
        }
        return lo;
    }
}