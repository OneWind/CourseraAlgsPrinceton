public class PercolationStats {
    
    private int[] percolateCount;
    private int size;
    private int iteration;
    private int[] idx;
    
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("both N and T should be positive");
        }
        size = N;
        iteration = T;
        percolateCount = new int[T];
        Percolation[] percGroup = new Percolation[T];
        idx = new int[(int) size * size];

        for (int i = 0; i < size * size; i++) {
            idx[i] = i+1;
        }

        for (int i = 0; i < T; i++) {
            percGroup[i] = new Percolation(N);
            percolateCount[i] = experiment(percGroup[i]);
        }
        
    }
    
    private int experiment(Percolation perc) {
        StdRandom.shuffle(idx);
        int step = 0;
        int iNew, jNew;
        int locOpen;
        while (!perc.percolates()) {
            locOpen = idx[step++];
            iNew = (locOpen-1) / size + 1;
            jNew = locOpen - (iNew-1) * size;
            perc.open(iNew, jNew);
        }
        return step;
    }
    
    public double mean() {
        double sum = 0;
        for (int n : percolateCount) sum += (float) n;
        return sum / (size * size) / iteration;
    }
    
    public double stddev() {
        double sum2 = 0;
        double m = mean();
        double tmpNum;
        for (int n : percolateCount) {
            tmpNum = (float) n/size/size - m;
            sum2 += tmpNum * tmpNum;
        }
        return Math.sqrt(sum2 / (iteration - 1));

//        for (int n : percolateCount) sum2 += Math.pow(n / (float) size / size, 2);
//        return Math.sqrt(sum2 / iteration - Math.pow(mean(), 2));
    }
    
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt((float) iteration);
    }
    
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt((float) iteration);        
    }
    
        
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats ps = new PercolationStats(N, T);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}