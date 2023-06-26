package Strings;
import java.util.*;

public class lps {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int[] arr = new int[s.length()];
        int i=1;
        int len =0;
        char[] x = s.toCharArray();
        while(i<s.length()){
            if(x[i]==x[len]){
                arr[i]= len+1;
                len++;
                i++;
            }else{
                if(len-1>=0){
                    len= arr[len-1];
                }else{
                    arr[i]=0;
                    i++;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
    
}
