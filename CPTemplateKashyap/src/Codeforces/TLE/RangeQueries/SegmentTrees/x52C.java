package Codeforces.TLE.RangeQueries.SegmentTrees;


//https://atcoder.jp/contests/dp/tasks/dp_i
import java.io.*;
import java.util.*;
 
public class x52C {

    static PrintWriter out;
    static Utility util;
    static HashMap<Integer, Long> factMap;
    static boolean ONLINE_JUDGE = false;
    static int itr;
    static int md = 32768;
    static int mod = (int)(1e9+7);
	static HashMap<Integer, List<GraphEdge>> graph;
    static PriorityQueue<Pair> q1;
    
    static int[] dr = {-1,0,1,0}, dc= {0,1,0,-1};
    
    static TreeSet<String> res;
    static int maxi;
    public static void main(String[] args) {
        // Comment this code while running in Online Judge
        try {
            // System.out.println(System.getProperty("ONLINE_JUDGE"));
            if (System.getProperty("ONLINE_JUDGE") == null && !ONLINE_JUDGE) {
                FileOutputStream output = new FileOutputStream("src/Codeforces/TLE/RangeQueries/output.txt");
                PrintStream out = new PrintStream(output);
                System.setOut(out);
 
                InputStream input = new FileInputStream("src/Codeforces/TLE/RangeQueries/input.txt");
                System.setIn(input);
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       
        try {
            Reader sc = new Reader();
            util = new Utility();
            out = new PrintWriter(System.out);
            int n =sc.nextInt();
            
            long[] nums = new long[n];
            for(int i=0;i<n;i++){
                nums[i]=sc.nextLong();
            }
            int m = sc.nextInt();
           
            SGT sg = new SGT(4*n);
            sg.build(0,0,n-1,nums);
            // out.println(Arrays.toString(sg.arr));
            Scanner x1 = new Scanner(System.in);
            while(m-->0){
                String s = sc.readLine();
                String[] a11= s.split(" ");

                if(a11.length==3){
                    int a = Integer.parseInt(a11[0]);
                    int b = Integer.parseInt(a11[1]);
                    int c = Integer.parseInt(a11[2]);
                    // out.println(a+" "+b+" "+c);
                   if(a>b){
                     b=b%n;
                     sg.update(0, 0, n-1, 0, b,c);
                     sg.update(0, 0, n-1, a, n-1,c);
                   }else{
                    sg.update(0, 0, n-1, a, b,c);
                   }
                    
                }else{
                    int a = Integer.parseInt(a11[0]);
                    int b = Integer.parseInt(a11[1]);
                    // out.println(a+" "+b);
                    long ans; 
                    if(a>b){
                        b=b%n;
                        long x2 = sg.query(0,0,n-1,0,b);
                        long x3 = sg.query(0,0,n-1,a,n-1);
                        ans = (Math.min(x2,x3));
                        System.out.println(ans);
                    }else{
                        ans= (sg.query(0,0,n-1,a,b));
                        System.out.println(ans);
                    }
                    

                }
            }
             

            out.flush();
        
        }
 
        catch (
 
        Exception e) {
            out.flush();
            System.out.println(e);
            return;
        }
    }
    static class SGT{
        int n; 
        long[] arr ;
        long[] lazy;
        public SGT(int n){
            this.n = n;
           arr = new long[n];
           lazy= new long[n];
        }
        public void build(int idx, int i, int j,long[] nums ){
            if(i==j){
                arr[idx]=nums[i];
                return;
            }
            int mid = (i+j)/2;
            build(2*idx+1, i,mid,nums);
            build(2*idx+2, mid+1,j,nums);
            arr[idx]= operation(arr[2*idx+1],arr[2*idx+2]);
        }
        public void update(int idx, int i, int j, int l, int r, long val){
            // update the previous and prop downwards 
            if(lazy[idx]!=0){
                arr[idx]+=lazy[idx];
                if(i!=j){
                    lazy[2*idx+1]+= lazy[idx];
                    lazy[2*idx+2]+= lazy[idx];
                }
                lazy[idx]=0;
            }
            // no overlap 

            if(l> j || i>r){
                return;
            }
            // complete overlap 

            if(l<=i && r>= j){
                arr[idx]+= val; 
                if(i!=j){
                    lazy[2*idx+1]+= val;
                    lazy[2*idx+2]+= val;
                }
                return;
                
            }
            int mid = (i+j) >>1; 

            update(2*idx+1, i,mid, l,r, val);
            update(2*idx+2,mid+1,j, l,r, val);
            arr[idx]= operation(arr[2*idx+1],arr[2*idx+2]);


        }
        public long query(int idx, int i, int j, int l, int r){
            if(lazy[idx]!=0){
                arr[idx]+=lazy[idx];
                if(i!=j){
                    lazy[2*idx+1]+= lazy[idx];
                    lazy[2*idx+2]+= lazy[idx];
                }
                lazy[idx]=0;
            }
            if(i>=l && r>=j){ // complete overlap
                return arr[idx];
            }
            if(i>r || j<l){ // disjoint
                return Long.MAX_VALUE;
            }
            // partial
            int mid = (i+j)/2;
            
            return operation(query(2*idx+1,i,mid,l,r) ,query(2*idx+2,mid+1,j,l,r));

        }
    }
    static long operation(long a, long b){
        return Math.min(a,b); 
    }
    static void solve(int n,long[] nums,int k){
        
       
        
    }
    
    
    
    
    
    
    static int bs(int r, int[] nums,int n){
        int s= r+1,e=n-1;
        int ans =e+1;
        while(s<=e){
            int m = s+(e-s)/2;
            if(nums[m]>nums[r]){
                ans =m;
                e=m-1;
            }else{
                s=m+1; 
            }
        }
        return ans;
    }
    static class Pair{
        int r;
        long c;
        public Pair(int r, long c){
            this.r=r;
            this.c=c;
        }

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
