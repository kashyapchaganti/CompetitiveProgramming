package CSES.DP;



import java.io.*;
import java.util.*;
 
class EditDistance {

    static PrintWriter out;
    static Utility util;
    static HashMap<Integer, Long> factMap;
    static boolean ONLINE_JUDGE = false;
    static int itr;
    static int md = 32768;
    static int mod = (int)(1e9+7);
	static HashMap<Integer, List<GraphEdge>> graph;
    
    
    static int[] dr = {-1,0,1,0}, dc= {0,1,0,-1};
    
    static TreeSet<String> res;
    public static void main(String[] args) {
        // Comment this code while running in Online Judge
        try {
            // System.out.println(System.getProperty("ONLINE_JUDGE"));
            if (System.getProperty("ONLINE_JUDGE") == null && !ONLINE_JUDGE) {
                FileOutputStream output = new FileOutputStream("src/CSES/DP/output.txt");
                PrintStream out = new PrintStream(output);
                System.setOut(out);
 
                InputStream input = new FileInputStream("src/CSES/DP/input.txt");
                System.setIn(input);
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 
        try {
            Reader sc = new Reader();
            util = new Utility();
            out = new PrintWriter(System.out);
            String s1 = sc.readLine();
            String s2 = sc.readLine();
            int n1 = s1.length();
            int n2 = s2.length();
            out.println(solve(n1,n2,s1,s2));
           
            
            out.flush();
        
        }
 
        catch (
 
        Exception e) {
            out.flush();
            System.out.println(e);
            return;
        }
    }
    static long solve(int n1 ,int n2, String s1, String s2){
        long[][] dp= new long[n1+1][n2+1];
        for(long[] x: dp){
            Arrays.fill(x,-1l);
        }
        return check(dp, 0,0, s1.toCharArray(), s2.toCharArray(),n1,n2);

    }
    static long check(long[][] dp, int i, int j, char[] s1, char[] s2, int n1, int n2){
        if(i==n1 && j==n2){
            return 0l;
        }
        if(i==n1 && j<n2){
            return n2-j;
        }
        if(i<n1 && j==n2){
            return n1-i;
        }
        if(dp[i][j]!=-1){
            return dp[i][j];
        }
        long a=0,b=0,c=0;
        long min = (int)(1e9);
        if(s1[i]==s2[j]){
            a=check(dp,i+1,j+1,s1,s2,n1,n2);
            min=Math.min(a,min);
        }else{ // add , remove, replace
            a= (1+ check(dp,i,j+1,s1,s2,n1,n2));
            b= (1+ check(dp,i+1,j,s1,s2,n1,n2));
            c=(1+ check(dp,i+1,j+1,s1,s2,n1,n2));
            min=Math.min(a,Math.min(min,Math.min(b,c)));
        }
        return dp[i][j]= min;
    }
    static int bs(int i, long x, long[][] nums ){
        int s= i+1,e= nums.length-1;
        int ans=e+1;
        while(s<=e){
            int m = s+(e-s)/2;
            if(x<nums[m][0]){
                ans=m;
                e=m-1;
            }else{
                s=m+1;
            }
        }
        return ans;
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
