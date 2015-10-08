public class SolverTest {
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
//        System.out.println(N);
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
//                System.out.print(blocks[i][j] + "\t");
            }
//            System.out.println();
        }
//        Board initial = new Board(blocks);
        System.out.println(blocks[0].length);
        System.out.println(Math.abs(-3));

        // solve the puzzle
//        Solver solver = new Solver(initial);

        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
//        }
    }
}