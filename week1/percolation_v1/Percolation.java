import java.io.*;

public class Percolation {
    
    private int size;
    private int[][] grid;
//    private QuickFindUF qf;
    private WeightedQuickUnionUF qf;
    
    public Percolation (int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException("nonnegative N");
        }
        size = N;
        grid = new int[N][N];
//        qf = new QuickFindUF(N*N);
        qf = new WeightedQuickUnionUF(N*N); //WeightedQuickUnionUF
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = 0;
            }
        }
    }
    
    public void open (int i, int j) {
        // open site (row i, column j) if it is not open already
        if (i >= 0 && i < size && j >= 0 && j < size) {
            grid[i][j] = 1;
            if (i > 0) {
                if (isOpen(i-1, j)) qf.union(i*size+j, (i-1)*size+j); 
            }
            if (i < size-1) {
                if (isOpen(i+1, j)) qf.union(i*size+j, (i+1)*size+j);
            }
            if (j > 0) {
                if (isOpen(i, j-1)) qf.union(i*size+j, i*size+(j-1));
            }
            if (j < size-1) {
                if (isOpen(i, j+1)) qf.union(i*size+j, i*size+(j+1));
            }
        }
        else throw new IndexOutOfBoundsException("index not in the range");
    }
    
    public boolean isOpen (int i, int j) {
        // is site (row i, column j) open?
        if (i >= 0 && i < size && j >= 0 && j < size) {
            if (grid[i][j] > 0) return true;
            else return false;
        } else {
            throw new IndexOutOfBoundsException("index not in the range");
        }
        
    }
    
    public boolean isFull (int i, int j) {
        // is site (row i, column j) full?
        if (i == 0) {
            if (grid[i][j] > 0) return true;
            else return false;
        } else {
            for(int s = 0; s < size; s++) {
                if (qf.connected(i*size+j, s)) return true;
            }
        }
        return false;
    }
    
    public boolean percolates () {
        // does the system percolate?
        for (int i = 0; i < size; i++) {
            if (isFull(size-1, i)) return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
//        String fileName = "/Users/fyi/Documents/algs/week1/percolation_data/input3.txt";
        String fileName = args[0];
        String line = null;
        String trimmedLine = null;
        int t;
        int[] node = new int[2];
        
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            Percolation perc = new Percolation(Integer.parseInt(line));
            while ( (line = bufferedReader.readLine()) != null ) {
                t = 0;
                trimmedLine = line.trim();
                if (trimmedLine.length() > 0) {
                    for (String s: trimmedLine.split("\\s+", 2)) {
                        node[t++] = Integer.parseInt(s) - 1;
                    }
                    perc.open(node[0], node[1]);                    
                }
            }
            System.out.println(perc.percolates());
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch (IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
    }
    
}
