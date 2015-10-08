public class Board {
    
    private int N;
    private int[][] board;
    private int[][] goalBoard;
    private int iOfSpace;
    private int jOfSpace;

    public Board(int[][] blocks) {
        N = blocks[0].length;
        board = new int[N][N];
        goalBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
                goalBoard[i][j] = i * N + j + 1;
                if (board[i][j] == 0) {
                    iOfSpace = i;
                    jOfSpace = j;
                }
            }
        }
        goalBoard[N-1][N-1] = 0;
    }    
    
    public int dimension() {
        return(N);
    }
    
    public int hamming() {
        int priority = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i+1) * (j+1) < N * N) {
                    if (board[i][j] != i * N + j + 1) {
                        priority += 1;
                    }
                }
            }
        }
        return(priority);
    }
    
    public int manhattan() {
        int priority = 0;
        int iGoal, jGoal;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != 0) {
                    iGoal = (board[i][j]-1) / N;
                    jGoal = board[i][j] - iGoal * N - 1;
//                    System.out.println(board[i][j] + ": " + Math.abs(iGoal - i) + Math.abs(jGoal - j));
                    priority += Math.abs(iGoal - i) + Math.abs(jGoal - j);
                }
            }
        }
        return(priority);
    }
    
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != goalBoard[i][j]) {
                    return(false);
                }
            }
        }
        return(true);
    }
    
    public Board twin() {
        Board twinBoard = new Board(board);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N-1; j++) {
                if (board[i][j] * board[i][j+1] > 0) {
                    twinBoard.switchFun(i, j, i, j+1);
                    return(twinBoard);
                }
            }
        }
        return(twinBoard);
    }
    
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        } else{
            Board that = (Board) y;
            if (that.dimension() != dimension()) {
                return(false);
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (that.board[i][j] != board[i][j]) { // leak???
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void switchFun(int i1, int j1, int i2, int j2) {
        int tmp = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = tmp;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> stackOfBoard = new Stack<Board>();
        if (iOfSpace > 0) {
            Board tmpBoard = new Board(board);
            tmpBoard.switchFun(iOfSpace-1, jOfSpace, iOfSpace, jOfSpace);
            stackOfBoard.push(tmpBoard);
        }
        if (iOfSpace < N-1) {
            Board tmpBoard = new Board(board);
            tmpBoard.switchFun(iOfSpace+1, jOfSpace, iOfSpace, jOfSpace);
            stackOfBoard.push(tmpBoard);
        }
        if (jOfSpace > 0) {
            Board tmpBoard = new Board(board);
            tmpBoard.switchFun(iOfSpace, jOfSpace-1, iOfSpace, jOfSpace);
            stackOfBoard.push(tmpBoard);
        }
        if (jOfSpace < N-1) {
            Board tmpBoard = new Board(board);
            tmpBoard.switchFun(iOfSpace, jOfSpace+1, iOfSpace, jOfSpace);
            stackOfBoard.push(tmpBoard);
        }
        return stackOfBoard;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        int[] tmp = {2, 1, 6, 3, 5, 8, 7, 4, 0};
        int[][] a = new int[3][3];
        int[][] b = new int[3][3];
        int k = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = tmp[k];
                b[i][j] = tmp[8-k];
                k++;
            }
        }
        Board bd1 = new Board(a);
        Board bd2 = new Board(b);
        System.out.println(bd1);
        System.out.println("The twin board is: " + bd1.twin());
        System.out.println(bd2);
        System.out.println(bd1.equals(bd2));

        System.out.println("neighbors: ");
        for (Board item : bd1.neighbors()) {
            System.out.println(item);
        }
        System.out.println("Hamming: " + bd1.hamming());
        System.out.println("Manhattan: " + bd1.manhattan());
    }
}