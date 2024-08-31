import java.util.ArrayList;

public class l0015 {
    
    //Find minimum in rotated sorted array II:- https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/description/
    public int findMin(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;

        while(lo < hi){
            int mid = (lo + hi) / 2;
            if(nums[mid] < nums[hi]){
                hi = mid;
            }else if(nums[mid] > nums[hi]){
                lo = mid + 1;
            }else{  //duplicate
                hi--;
            }
        }

        return nums[lo];
    }

    //Longest Happy Prefix:- https://leetcode.com/problems/longest-happy-prefix/description/
    public String longestPrefix(String s) {
        int len = 0;    //prefix pointer
        int i = 1;      //suffix pointer
        int lps[] = new int[s.length()];        //longest suffix length which is also a prefix.(LPS)
        while(i < s.length()){
            if(s.charAt(i) == s.charAt(len)){
                lps[i] = len + 1;
                len++;
                i++;
            }else if(len == 0){          //koi pattern previous match nhi huya.
                lps[i] = len;
                i++;
            }else{              //ki chars. unequal hai but previous len exist krti hai
                len = lps[len - 1];
            }   
        }
        
        int k = lps[lps.length - 1];    //longest prefix suffix at the last index
        return s.substring(0,k);
    }


    //KMP Algorithm (Search Pattern):-https://www.geeksforgeeks.org/problems/search-pattern0205/1
    ArrayList<Integer> search(String pat, String txt){
        int lps[] = getLPS(pat);
        int i = 0;  //text pointer
        int j = 0;  //pattern pointer
        ArrayList<Integer> res = new ArrayList<>();

        while(i < txt.length()){
            if(txt.charAt(i) == pat.charAt(j)){
                i++;
                j++;

                if(j == pat.length()){
                    res.add(i - j + 1);     //1 based indexng while returning.
                    j = lps[j - 1];         //dubara se puri pattern ko match na krana pde.
                }
            }else if(j == 0){   //koi similar character nhi hai pattern ke txt mein.
                i++;
            }else{  //kuch similar characters ho skte hain pattern ke txt mein.
                j = lps[j - 1];
            }
        }
        return res;
    }

    public int[] getLPS(String pat){
        int len = 0;
        int i = 1;
        int lps[] = new int[pat.length()];
        while(i < pat.length()){
            if(pat.charAt(i) == pat.charAt(len)){
                lps[i] = len + 1;
                i++;
                len++;
            }else if(len == 0){
                lps[i] = 0;
                i++;
            }else{
                len = lps[len - 1];
            }
        }

        return lps;
    }

    //Shortest Palindrome:- https://leetcode.com/problems/shortest-palindrome/description/
    public String shortestPalindrome(String s) {
        String str = s + "$" + new StringBuilder(s).reverse().toString();       //Since palindrome check krna hai toh reverse krna pdega.
        int len = 0;
        int i = 1;
        int lps[] = new int[str.length()];

        while(i < str.length()){
            if(str.charAt(i) == str.charAt(len)){
                lps[i] = len + 1;
                len++;
                i++;
            }else if(len == 0){
                lps[i] = 0;
                i++;
            }else{
                len = lps[len - 1];
            }
        }

        int ppl = lps[lps.length - 1];      //Palindromic Prefix Length
        String npc = s.substring(ppl);      // not palindromic characters after ppl .  
        return new StringBuilder(npc).reverse().toString() + s; 
    }
}
