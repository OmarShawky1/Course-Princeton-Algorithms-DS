import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver { //TODO: he is asking for immutable class (I/P no. 11) tho, he didn't make it final

    // Global Variables
    private final MinPQ<Board> minPQ;
    private final Board initialBoard;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial != null) {
            throw new IllegalArgumentException("null argument to constructor");
        }

        moves = 0;
        minPQ = new MinPQ<>();
        initialBoard = initial;
        minPQ.insert(initial);
        minPQ.delMin(); //TODO: not sure of the solution, This is requirement no.
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        // TODO
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return moves; //TODO: not sure
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return (new Board(new int[][]{})).neighbors();
    }


    // test client (see below)
    public static void main(String[] args) {
        StdOut.println("###############Solver Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        int[][] examplePuzzle = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board1 = new Board(examplePuzzle);

        StdOut.println("##########His Test Cases##########");
        /*
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
        */
    }
}