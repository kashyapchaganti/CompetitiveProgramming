import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import CSES.Graphs.FlightDiscount.Pair;

public class xD {
    int n = sc.nextInt();
            int m = sc.nextInt();
            List<List<Pair>> adj = new ArrayList<>();
            for(int i=0;i<=n;i++){
                adj.add(new ArrayList<>());
            }
            for(int i=0;i<m;i++){
                int a =sc.nextInt();
                int b =sc.nextInt();
                int c= sc.nextInt();
                adj.get(a).add(new Pair(b,c));
                adj.get(b).add(new Pair(a,c));
            }
            Queue<Pair> q = new LinkedList<>();
            q.add(new Pair(1,0,1));
            long min= (long)(2e17);
            long[]dis0= new long[n+1];
            long[]dis1= new long[n+1];
            Arrays.fill(dis0,(long)(2e17));
            Arrays.fill(dis1,(long)(2e17));
            while(!q.isEmpty()){
                Pair cur =q.poll();
                for(Pair x: adj.get(cur.r)){
                    if(cur.d==0){
                        if(dis0[x.r]>cur.c+x.c ){
                            dis0[x.r]=cur.c+x.c;
                            q.add(new Pair(x.r,dis0[x.r],0));
                        }
                    }else{
                        if(dis0[x.r]>cur.c + (long)(Math.ceil((x.c)/2))){
                            dis0[x.r]=cur.c + (long)(Math.ceil((x.c)/2));
                            q.add(new Pair(x.r,dis0[x.r],0));
                        }
                        if(dis1[x.r]>cur.c+x.c){
                            dis1[x.r]=cur.c+x.c;
                            q.add(new Pair(x.r,dis1[x.r],1));
                        }
                    }
                }
                
            }
           System.out.println(Math.min(dis0[n],dis1[n]));
            // check(grid,n,m, i1,j1, i2, j2);
        
            

}
