package CSES.Graphs;
import java.io.*;
import java.util.*;
 
class ShortestRoutesII {

    static PrintWriter out;
    static Utility util;
    static HashMap<Integer, Long> factMap;
    static boolean ONLINE_JUDGE = false;
    static int itr;
    static int md = 32768;
	static HashMap<Integer, List<GraphEdge>> graph;
    
    static int[] dr = {-1,0,1,0}, dc= {0,1,0,-1};
    
    static TreeSet<String> res;
    static int f1;
    public static void main(String[] args) {
        // Comment this code while running in Online Judge
        try {
            // System.out.println(System.getProperty("ONLINE_JUDGE"));
            if (System.getProperty("ONLINE_JUDGE") == null && !ONLINE_JUDGE) {
                FileOutputStream output = new FileOutputStream("CompetitiveProgramming/CPTemplateKashyap/src/CSES/SortingandSearching/output.txt");
                PrintStream out = new PrintStream(output);
                System.setOut(out);
 
                InputStream input = new FileInputStream("CompetitiveProgramming/CPTemplateKashyap/src/CSES/SortingandSearching/input.txt");
                System.setIn(input);
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        try {
            Reader sc = new Reader();
            util = new Utility();
            out = new PrintWriter(System.out);
            int n = sc.nextInt();
            int m = sc.nextInt();
            int q = sc.nextInt();
            long[][] map= new long[n+1][n+1];
            for(long[] x: map) Arrays.fill(x,(long)(1e15));
            for(int i=0;i<m;i++){
                int a =sc.nextInt();
                int b =sc.nextInt();
                long c= sc.nextLong();
                     map[a][b]=Math.min(c,map[a][b]);
                        map[b][a]=Math.min(c,map[a][b]);
                
            }
            for(int i=1;i<=n;i++){
                map[i][i]=0;
            }
            
           for(int k=1;k<=n;k++){
                for(int i=1;i<=n;i++){
                    for(int j=1;j<=n;j++){
                        long min = map[i][j];
                        min= Math.min(min, map[i][k]+map[k][j]);
                        map[i][j]=min;
                    }
                }
           }
           
           while(q-->0){
            int a = sc.nextInt();
            int b = sc.nextInt();
            out.println(map[a][b]==((long)(1e15))?-1:map[a][b]);
           }
           
           
            // check(grid,n,m, i1,j1, i2, j2);
        
            
        
            out.flush();
        
        }
 
        catch (
 
        Exception e) {
            out.flush();
            System.out.println(e);
            return;
        }
    }
    static long[] shortestPath(List<List<Pair>> adj, int n, int src ){
        long[] dis  = new long[n+1]; 
           Arrays.fill(dis,(long)(1e15));
            dis[src]=0;
            PriorityQueue<Pair> q = new PriorityQueue<>((a,b)-> Long.compare(a.c,b.c));
            q.add(new Pair(src,0));
            while(!q.isEmpty()){
                Pair cur = q.poll();
                for(Pair x : adj.get(cur.r)){
                    if(dis[x.r]> cur.c+x.c){
                        dis[x.r]=cur.c+x.c;
                        q.add(new Pair(x.r,dis[x.r]));
                    }
                }
            }
            return dis;
    }
    static boolean  check(int i, int p, int [] vis, List<List<Integer>> adj){
        
        for(int x: adj.get(i)){
            if(vis[x]==0 && x!=p){
                vis[x]=i;
                
                if(check(x,i, vis,adj)){
                        return true;
                    }
            }else if(vis[x]!=0 && x!=p){
                vis[x]=i;
                f1=x;
                // out.println(x);
                return true;
            }
        }
        return false;    
    }
    static class DSU{
        List<Integer> p,size ;
        public DSU(int n){
            p =new ArrayList<>();
            size=new ArrayList<>();
            for(int i=0;i<=n;i++){
                p.add(i);
                size.add(1);
            }
        }
        public int find(int n){
            if(p.get(n)==n){
                return n;
            }
            int v= find(p.get(n));
            p.set(n,v);
            return v;
        }
        public void union(int a, int b){
            int p1 = find(a);
            int p2 = find(b);
            if(p1==p2){
                return;
            }
            if(size.get(p1)>size.get(p2)){
                p.set(p2, p1);
                size.set(p1, size.get(p1)+size.get(p2));
            }else{
                p.set(p1, p2);
                size.set(p2, size.get(p1)+size.get(p2));
            }
        }
    }
   
    static class Pair{
        int r;long c;
        public Pair(int  r, long c){
            this.r=r;
            this.c=c;
        }
        public String toString(){
            return r+" "+c;
        }
    }
    static long check(List<Long> arr, int L, int R){
        int n = arr.size();
    long[] pre = new long[n + 1];
 
    // calculating prefix sum
    // here pre[0] = 0
    for (int i = 1; i <= n; i++) {
        pre[i] = pre[i - 1]+arr.get(i - 1);
    }   
     
      // treemap for storing prefix sums for
      // subarray length L to R
    TreeMap<Long, Long> s1 = new TreeMap<>();
 
    long ans = Long.MIN_VALUE;
 
    for (int i = L; i <= n; i++) {
         
        // if i > R, erase pre[i - R - 1]
        // note that pre[0] = 0
        if (i > R) {
            // decrement count of pre[i - R - 1]
            s1.put(pre[i - R - 1], s1.get(pre[i - R - 1])-1);
            // if count is zero, element is not present
            // in map so remove it
            if (s1.get(pre[i - R - 1]) == 0)
                s1.remove(pre[i - R - 1]);
        }
 
        // insert pre[i - L]
        s1.put(pre[i - L], s1.getOrDefault(pre[i - L], 0l)+1l);
 
        // find minimum value in treemap.
        ans = Math.max(ans, pre[i] - s1.firstKey());
        
    }
    return ans;
    }
 

    
    static boolean bs(long m, long[] arr){
        int start =0; long s=0;
        for(int i=0;i<arr.length;i++){
            s+=arr[i];
            if(s==m) return false;
            while(s>m && start<i){
                s-=arr[start];
                if(s==m){
                    return false;
                }
                start++;
            }
            if(start==i && arr[i]>m){
                return true;
            }
        }
        return true;
    }
    

    static class GraphEdge{
        public int destination;
        public int weight;

        GraphEdge(int destination, int weight){
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString(){
            return " to "+this.destination+" weight "+this.weight+" ";
        }
    }
 
    public static int binarySearch(List<Integer> sub, int num) {
        int left = 0;
        int right = sub.size() - 1;
        int mid = (left + right) / 2;
        int ans = -1;
        while (left <= right) {
            mid = (left + right) / 2;
    
            if (sub.get(mid) <= num) {
                left = mid + 1;
            } else {
                ans = mid;
                right = mid-1;
            }
        }
        
        return ans;
    }
    public static List<Integer> isSpecial(int treeNodes, List<Integer> treeFrom, List<Integer> treeTo) {
        int[] res = new int[treeNodes + 1];
 
        for (int i = 0; i < treeFrom.size(); i++) {
            res[treeFrom.get(i)]++;
            res[treeTo.get(i)]++;
        }
 
        List<Integer> ans = new ArrayList<>();
 
        for (int i = 1; i <= treeNodes; i++)
            ans.add(res[i] > 1 ? 0 : 1);
        return ans;
    }
 
    public static int bsearch(int[] inp, int targ) {
        int l = 0;
        int r = inp.length - 1;
        int ans = -1;
        while (l <= r) {
            int mid = (l + r) / 2;
 
            if (inp[mid] <= targ) {
                ans = mid;
                l = mid + 1;
            } else
                r = mid - 1;
        }
        // out.println(targ + " ind " + ans + " " + (inp.length - (ans + 1)));
        return ans;
    }

    static class Utility {
        // Complexity: O(Log min(a, b))
        public long ecu_gcd(long a, long b) {
            if (a == 0)
                return b;
 
            return ecu_gcd(b % a, a);
        }
 
        public int[] extended_ecu_gcd(int a, int b) {
 
            if (a == 0) {
                return new int[] { b, 0, 1 };
            }
 
            int[] temp = extended_ecu_gcd(b % a, a);
 
            return new int[] { temp[0], (temp[2] - (b / a) * temp[1]), temp[1] };
        }
 
        static ArrayList<Integer> primesnums;
        static boolean hasprimes = false;
 
        // Complexity: O(NLogN)
        public boolean[] sieveOfEr_primes(int n) {
            hasprimes = true;
            boolean[] primes = new boolean[n + 1];
            primesnums = new ArrayList<Integer>();
            Arrays.fill(primes, true);
            int i = 2;
            for (i = 2; i * i <= n; i++) {
                if (primes[i]) {
                    for (int p = i * i; p <= n; p += i)
                        primes[p] = false;
                }
            }
 
            for (i = 2; i <= n; i++)
                if (primes[i])
                    primesnums.add(i);
            return primes;
        }
 
        public ArrayList<Integer> prime_Factors(int n) {
            ArrayList<Integer> res = new ArrayList<Integer>();
 
            while (n % 2 == 0) {
                res.add(2);
                n = n / 2;
            }
 
            for (int i = 3; i * i <= n; i = i + 2) {
                while (n % i == 0) {
                    res.add(i);
                    n = n / i;
                }
            }
            if (n > 2)
                res.add(n);
 
            return res;
        }
 
        public int prime_Factors2(int n) {
            HashMap<Integer, Integer> res = new HashMap<Integer, Integer>();
 
            while (n % 2 == 0) {
                res.put(2, res.getOrDefault(2, 0) + 1);
                n = n / 2;
            }
 
            for (int i = 3; i * i <= n; i = i + 2) {
                while (n % i == 0) {
                    res.put(i, res.getOrDefault(i, 0) + 1);
                    n = n / i;
                }
            }
            if (n > 2)
                res.put(n, res.getOrDefault(n, 0) + 1);
 
            int ress = 1;
            for (Map.Entry<Integer, Integer> en : res.entrySet()) {
                ress *= (en.getValue() + 1);
            }
 
            return ress;
        }
 
        public long[] fibnocnc(long k) {
 
            if (k == 0)
                return new long[] { 0, 1 };
 
            long[] t = fibnocnc(k >> 1);
            long a = (t[0] * (2 * t[1] - t[0]));
            long b = (t[0] * t[0] + t[1] * t[1]);
 
            if ((k & 1) == 1)
                return new long[] { b, (b + a) };
            return new long[] { a, b };
        }
 
        public long sumofN(long n) {
            return n * (n + 1) / 2;
        }
 
        public long pos_quadratic_root(long a, long b, long c) {
            return (-b + (long) Math.sqrt(b * b - 4 * a * c)) / 2 * a;
        }
 
        public long modInverse(long a, long m) {
            // out.println(a+" "+m);
            long m0 = m;
            long y = 0, x = 1;
 
            if (m == 1)
                return 0;
 
            while (a > 1) {
                // q is quotient
 
                long q = a / m;
 
                long t = m;
 
                // m is remainder now, process
                // same as Euclid's algo
                m = a % m;
                a = t;
                t = y;
 
                // Update x and y
                y = x - q * y;
                x = t;
            }
 
            // Make x positive
            if (x < 0)
                x += m0;
            // out.println(x);
            return x;
        }
 
        public TreeSet<Integer> getallFactors(int n) {
            TreeSet<Integer> hst = new TreeSet<Integer>();
            hst.add(1);
            for (int i = 2; i * 1l * i <= n; i++) {
                if (n % i == 0) {
                    hst.add(i);
                    if (i != n / i)
                        hst.add(n / i);
                }
            }
            return hst;
        }
 
        static int invertBits(int n) {
            // Calculate number of bits of N-1;
            int x = (int) (Math.log(n) / Math.log(2));
 
            int m = 1 << x;
 
            m = m | m - 1;
 
            n = n ^ m;
            // System.out.println(n);
            return n;
        }
 
        public static long bnc(int n, int r) {
            if (r > n)
                return 0;
            long m = 1000000007;
            long inv[] = new long[r + 1];
            inv[1] = 1;
 
            for (int i = 2; i <= r; i++) {
                inv[i] = m - (m / i) * inv[(int) (m % i)] % m;
            }
 
            int ans = 1;
 
            // for 1/(r!) part
            for (int i = 2; i <= r; i++) {
                ans = (int) (((ans % m) * (inv[i] % m)) % m);
            }
 
            // for (n)*(n-1)*(n-2)*...*(n-r+1) part
            for (int i = n; i >= (n - r + 1); i--) {
                ans = (int) (((ans % m) * (i % m)) % m);
            }
            return ans;
        }
 
        public static long fact(int n) {
            if (n <= 1)
                return 1;
 
            if (factMap.containsKey(n))
                return factMap.get(n);
 
            factMap.put(n, mod(n * 1l * fact(n - 1)));
            return factMap.get(n);
        }
 
        public static long mod(long n) {
            return n < 0 ? 1000000007 + n : n % 1000000007;
        }
        public static long power(long x, long n){
                long n1 = n;
                long m = 1000000007;
            if(n1<0){
                n1=-1*n1;
            }
            long ans=1;
            while(n1>0){
                if((n1 & 1)==1){
                    ans=(ans*x)%m;
                }
                x=(x*x)%m;
                n1=n1>>1;
            }
            if(n<0){
                return 1/ans;
            }
            return ans%m;
        }
 
    }
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;
 
        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
 
        public String readLine2() throws IOException {
            List<Byte> buf = new ArrayList<Byte>();
            // byte[] buf = new byte[1000]; // line length
            int cnt = 0, c;
 
            while ((c = read()) != -1) {
                if (c == '\n')
                    break;
                buf.add((byte) c);
                // buf[c] = (byte) c;
                cnt++;
            }
 
            byte[] buf2 = new byte[buf.size()];
            int i = 0;
            for (Byte b : buf)
                buf2[i++] = b;
 
            return new String(buf2, 0, cnt);
 
        }
 
        public String readLine() throws IOException {
            String inp = readLine2().trim();
 
            while (inp.length() == 0)
                inp = readLine2().trim();
            return inp;
        }
 
        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
 
            if (neg)
                return -ret;
            return ret;
        }
    
        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }
 
        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
 
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
 
            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }
 
            if (neg)
                return -ret;
            return ret;
        }
 
        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }
 
        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }
 
        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }
}

