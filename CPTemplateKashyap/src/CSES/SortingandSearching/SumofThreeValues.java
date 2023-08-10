package CSES.SortingandSearching;
import java.io.*;
import java.util.*;
public class SumofThreeValues{
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int n =sc.nextInt();
        int t= sc.nextInt();
        long[][] nums = new long[n][2];
        for(int i=0;i<n;i++){
            nums[i][0]=sc.nextLong();
            nums[i][1]=i+1;
        }
        Arrays.sort(nums, (a,b)-> Long.compare(a[0],b[0]));
        boolean f=false;
        long a1= -1, b1=-1, c1=-1;
        for(int i=0;i<n;i++){
            if(i>0 && nums[i][0]==nums[i-1][0]){
                continue;
            }
            long a = t- nums[i][0];
            int s=i+1 ,e = n-1;
            while(s<e){
                long v=nums[s][0]+nums[e][0];
                if(v==a){
                        a1=nums[i][1];
                        b1=nums[s][1];
                        c1=nums[e][1];
                        f=true;
                        break;
                }else{
                    if(v<a){
                        s++;
                    }else{
                        e--;
                    }
                }
            }
            if(f){
                break;
            }
        }
        
        if(f){
            System.out.println(a1 +" " +b1+" "+c1);
        }else{
            System.out.println("IMPOSSIBLE");
        }
    }
}
