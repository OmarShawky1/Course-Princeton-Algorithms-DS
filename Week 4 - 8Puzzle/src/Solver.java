import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    // Global Variables
    private boolean isSolvable;
    private final int moves;
    private final Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null argument to constructor");
        }

        /*
        Code Logic composition/order:
            1. Initialize local & global variables.
            2. Creating a twin board (to detect if it is solvable).
            3. Find solution -if any-.
            4. store solution/solutions
         */

        // Initializing default values
        solution = new Stack<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(initialNode);

        // Creating a twin board to check if its solvable (hence initial isn't)
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
        twinMinPQ.insert(new SearchNode(initial.twin(), 0, null));
        
        int solutionPriority = 0;
        int moves = 0;
        // Finding a solution
        while (!minPQ.isEmpty()) {
            // Add solutions that only has the same priority as previous goal
            if (!solution.isEmpty() && minPQ.min().priority > solutionPriority) {
                    break;
            }

            // Add every solution to goalNodes
            if (minPQ.min().board.isGoal()) {
                isSolvable = true;
                SearchNode currNode = minPQ.min();
                solutionPriority = currNode.priority;
                moves = currNode.moves;

                while (currNode != null) {
                    solution.push(currNode.board);
                    currNode = currNode.previous;
                }
                // break; // enhances the complexity but noy by much
            }

            // Check if the board is unsolvable
            if (twinMinPQ.min().board.isGoal()) {
                isSolvable = false;
                moves = -1;
                break;
            }

            addNeighbors(minPQ);
            addNeighbors(twinMinPQ);
        }
        this.moves = moves;
    }

    private static void addNeighbors(MinPQ<SearchNode> minPQ) {
        SearchNode parent = minPQ.delMin(); // Predecessor for new neighbors
        for (Board neighbor : parent.board.neighbors()) {
            if (parent.previous == null || !neighbor.equals(parent.previous.board)) {
                minPQ.insert(new SearchNode(neighbor, parent.moves + 1, parent));
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolvable ? solution : null;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode previous;
        private final int moves;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.previous = previous;
            this.board = board;
            this.moves = moves;
            priority = this.board.manhattan() + this.moves;
        }

        @Override
        public int compareTo(SearchNode searchNode) {
            if (this.priority > searchNode.priority) {
                return 1;
            } else if (this.priority < searchNode.priority) {
                return -1;
            }
            return 0;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        StdOut.println("###############Solver Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        int numberOfTests = 0;

        int[][] solvedPuzzle = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};
        Board board1 = new Board(solvedPuzzle);
        Solver solver1 = new Solver(board1);

        assert solver1.isSolvable() : "solver1: Should've been solvable but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver1.moves == 0 : "moves should've been 0 but instead it is " + solver1.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] oneMovePuzzle = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Board board2 = new Board(oneMovePuzzle);
        Solver solver2 = new Solver(board2);

        assert solver2.isSolvable() : "solver2: Should've been solvable but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver2.moves == 1 : "moves should've been 1 but instead it is " + solver1.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] mediumPuzzle = {{0, 1, 2},
                {4, 5, 3},
                {7, 8, 6}};
        Board board3 = new Board(mediumPuzzle);
        Solver solver3 = new Solver(board3);

        assert solver3.isSolvable() : "solver3: Should've been solvable but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver3.moves == 4 : "moves should've been 4 but instead it is " + solver3.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] impossiblePuzzle = {{4, 2, 3},
                {1, 5, 6},
                {7, 8, 0}};
        Board board4 = new Board(impossiblePuzzle);
        Solver solver4 = new Solver(board4);

        assert !solver4.isSolvable() : "solver4: Shouldn't been solvable but it is";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver4.moves == -1 : "moves should've been -1 but instead it is " + solver4.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] impossiblePuzzle1 = {{1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}};
        Board board5 = new Board(impossiblePuzzle1);
        Solver solver5 = new Solver(board5);

        assert !solver5.isSolvable() : "solver5: Shouldn't been solvable but it is";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver5.moves == -1 : "moves should've been -1 but instead it is " + solver5.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");


        StdOut.println("##########His Test Cases##########");
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
    }
}
