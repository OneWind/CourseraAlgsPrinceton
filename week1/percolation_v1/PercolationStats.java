import java.util.*;

public class PercolationStats {
    
    private int percolateCount[];
    private int size;
    private int iteration;
    
    public PercolationStats (int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("both N and T should be positive");
        }
        size = N;
        iteration = T;
        percolateCount = new int[T];
        for (int i = 0; i < T; i++) percolateCount[i] = 0;
    }
    
    public double mean() {
        double sum = 0;
        for (int n : percolateCount) sum += (float)n;
        return sum / (size * size) / iteration;
    }
    
    public double stddev() {
        double sum2 = 0;
        for (int n : percolateCount) sum2 += Math.pow(n / (float)size / size, 2);
        return Math.sqrt(sum2 / iteration - mean() * mean());
    }
    
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt((float)iteration);
    }
    
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt((float)iteration);        
    }
    
//    public void updateCount(int i, int c) {
//        percolateCount[i] = c;
//    }
        
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        ArrayList<Integer> arrList = new ArrayList<Integer>();
        for (int i = 0; i < N * N; i++) {
            arrList.add(i);
        }

        for (int t = 0; t < T; t++) {
            Collections.shuffle(arrList);
            Percolation perc = new Percolation(N);
            int step = 0;
            int iNew, jNew;
            int locOpen;
            while (!perc.percolates()) {
                locOpen = arrList.get(step++);
                iNew = locOpen / N;
                jNew = locOpen - iNew * N;
                perc.open(iNew, jNew);
            }
            ps.percolateCount[t] = step;
//            ps.updateCount(t, step);
        }
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}