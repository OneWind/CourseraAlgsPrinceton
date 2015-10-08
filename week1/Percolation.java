public class Percolation {
    
    private int size;
    private boolean[][] grid;
    private WeightedQuickUnionUF qf;
    
    public Percolation(int N) {
        // create N-by-N grid, with all sites blocked
        if (N <= 0) {
            throw new IllegalArgumentException("nonnegative N");
        }
        size = N;
        grid = new boolean[N][N];
        qf = new WeightedQuickUnionUF(N*N); //WeightedQuickUnionUF
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
    }
    
    public void open(int I, int J) {
        // open site (row i, column j) if it is not open already
        int i = I - 1;
        int j = J - 1;
        if (i >= 0 && i < size && j >= 0 && j < size) {
            grid[i][j] = true;
            if (i > 0) {
                if (isOpen(I-1, J)) qf.union(i*size+j, (i-1)*size+j); 
            }
            if (i < size-1) {
                if (isOpen(I+1, J)) qf.union(i*size+j, (i+1)*size+j);
            }
            if (j > 0) {
                if (isOpen(I, J-1)) qf.union(i*size+j, i*size+(j-1));
            }
            if (j < size-1) {
                if (isOpen(I, J+1)) qf.union(i*size+j, i*size+(j+1));
            }
        }
        else throw new IndexOutOfBoundsException("index not in the range");
    }
    
    public boolean isOpen(int I, int J) {
        // is site (row i, column j) open?
        int i = I - 1;
        int j = J - 1;
        if (i >= 0 && i < size && j >= 0 && j < size) {
            if (grid[i][j]) return true;
            else return false;
        } else {
            throw new IndexOutOfBoundsException("index not in the range");
        }
        
    }
    
    public boolean isFull(int I, int J) {
        // is site (row i, column j) full?
        int i = I - 1;
        int j = J - 1;
        if (i >= 0 && i < size && j >= 0 && j < size) {
            if (i == 0) {
                if (grid[i][j]) return true;
                else return false;
            } else {
                for (int s = 0; s < size; s++) {
                    if (isOpen(1, s+1)) {
                        if ((s == 0) || (s > 0 && !isOpen(1, s))) {
                            if (qf.connected(i*size+j, s)) return true;                            
                        }
                    }
                }
            }
            return false;        
        } else {
            throw new IndexOutOfBoundsException("index not in the range");            
        }        
    }
    
    public boolean percolates() {
        // does the system percolate?
        for (int i = 0; i < size; i++) {
            if (isOpen(size, i+1)) {
                if ((i == 0) || (i > 0 && !isOpen(size, i))) {
                    if (isFull(size, i+1)) return true;                    
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        int N = StdIn.readInt();
        Percolation perc = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
//            System.out.println(i + " " + j);
            perc.open(i, j);
        }
        System.out.println(perc.percolates());
        

//        String fileName = args[0];
//        String line = null;
//        String trimmedLine = null;
//        int t;
//        int[] node = new int[2];
        
//        try {
//            FileReader fileReader = new FileReader(fileName);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            line = bufferedReader.readLine();
//            Percolation perc = new Percolation(Integer.parseInt(line));
//            while ( (line = bufferedReader.readLine()) != null ) {
//                t = 0;
//                trimmedLine = line.trim();
//                if (trimmedLine.length() > 0) {
//                    for (String s: trimmedLine.split("\\s+", 2)) {
//                        node[t++] = Integer.parseInt(s) - 1;
//                    }
//                    perc.open(node[0], node[1]);                    
//                }
//            }
//            System.out.println(perc.percolates());
//        }
//        catch (FileNotFoundException ex) {
//            System.out.println("Unable to open file '" + fileName + "'");
//        }
//        catch (IOException ex) {
//            System.out.println("Error reading file '" + fileName + "'");
//        }
    }
    
}
