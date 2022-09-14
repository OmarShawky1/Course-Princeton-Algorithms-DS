import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    // Global Variables
    private boolean isSolvable;
    private final MinPQ<SearchNode> minPQ;
    private final Stack<SearchNode> gameTree;
    private final int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null argument to constructor");
        }

        // Initializing default values & references
        gameTree = new Stack<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        minPQ = new MinPQ<>(initialNode);
        // minPQ = new MinPQ<>(new CompareNodes()); // Same as aforementioned line (check in
        // class "CompareNodes" reasons for commenting this line in case you wanted explanation)
        minPQ.insert(initialNode); // Based on I/P no. 6;

        // Creating a twin board to check if its solvable (hence initial isn't)
        Board twin = initial.twin();
        SearchNode twinInitialNode = new SearchNode(twin, 0, null);
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>(twinInitialNode);
        twinMinPQ.insert(twinInitialNode);

        // Based on I/P no. 9;
        SearchNode currentNode = initialNode;
        int moves = -1;
        while (!minPQ.isEmpty() && !minPQ.min().currentBoard.isGoal()) { //TODO: wrong because doesn't use isSolvable
            currentNode = move(minPQ, gameTree, ++moves);
            move(twinMinPQ, new Stack<>(), moves); // References doesn't matter to twin
            if (twinMinPQ.min().currentBoard.isGoal()) {
                break;
            }
        }

        if (!minPQ.isEmpty() && minPQ.min().currentBoard.isGoal()) {
            this.moves = currentNode.moves;
            isSolvable = true;
        } else {
            this.moves = -1;
            isSolvable = false;
        }
    }

    private SearchNode move(MinPQ<SearchNode> minPQ, Stack<SearchNode> gameTree, int prevMoves) {

        // Based on I/P no. 13;
        SearchNode minPriorityNode = minPQ.delMin();
        gameTree.push(minPriorityNode); // Based on I/P no. 12;
        Board minPriorityBoard = minPriorityNode.currentBoard;
//        StdOut.println("Move: delMin().minPriorityNode.board: " + minPriorityBoard); //TODO: Remove line

        for (Board neighbor : minPriorityBoard.neighbors()) {
            // Based on I/P no. 18;
            if (neighborNotPredecessor(neighbor)) { // Don't add predecessor
//                StdOut.print("neighbor is not pred.: " + neighbor); //TODO: Remove line
                // Based on I/P no. 8 & 13;
                minPQ.insert(new SearchNode(neighbor, prevMoves + 1, minPriorityBoard));
            }
        }
        return minPQ.min();
    }

    private boolean neighborNotPredecessor(Board neighbor) {
        for (SearchNode searchNode : gameTree) {
            if (neighbor.equals(searchNode.currentBoard)) {
                return false;
            }
        }
        return true;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        //TODO: This is wrong because I need to use two priority queues

        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return moves;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solution = new Stack<>();
        if (isSolvable()){
            while (gameTree.iterator().hasNext()){
                solution.push(gameTree.pop().currentBoard);
            }
        }
        return solution;
    }

    private static class SearchNode implements Comparator<SearchNode> {

        // Based on I/P no. 5;
        private final Board currentBoard;
        private final Board predecessor;
        private final int moves;
        private final int priority;

        public SearchNode(Board currentBoard, int moves, Board predecessor) {
            this.predecessor = predecessor;
            this.currentBoard = currentBoard;
            this.moves = moves;
            priority = this.currentBoard.manhattan() + moves; // Based on I/P no. 11;
        }

        @Override
        public int compare(SearchNode searchNode, SearchNode t1) {
            if (searchNode.priority > t1.priority) {
                return 1;
            } else if (searchNode.priority < t1.priority) {
                return -1;
            }
            return 0;
        }
    }

    /*
    // It is better to leave comparator in its relative class rather than keeping it outside
    private static class CompareNodes implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode searchNode, SearchNode t1) {
            if (searchNode.priority > t1.priority) {
                return 1;
            } else if (searchNode.priority < t1.priority) {
                return -1;
            }
            return 0;
        }
    }
    */


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

        assert solver1.isSolvable(): "solver1: Should've been solvable but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver1.moves == 0: "moves should've been 0 but instead it is " + solver1.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] oneMovePuzzle = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        Board board2 = new Board(oneMovePuzzle);
        Solver solver2 = new Solver(board2);

        assert solver2.isSolvable(): "solver2: Should've been solvable but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver2.moves == 1: "moves should've been 1 but instead it is " + solver1.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] mediumPuzzle = {{0, 1, 2},
                                {4, 5, 3},
                                {7, 8, 6}};
        Board board3 = new Board(mediumPuzzle);
        Solver solver3 = new Solver(board3);

        assert solver3.isSolvable(): "solver3: Should've been solvable but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver3.moves == 4: "moves should've been 4 but instead it is " + solver3.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] impossiblePuzzle = {{1, 2, 3},
                                    {4, 6, 5},
                                    {7, 8, 0}};
        Board board4 = new Board(impossiblePuzzle);
        Solver solver4 = new Solver(board4);

        assert !solver4.isSolvable(): "solver4: Shouldn't been solvable but it is";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert solver4.moves == -1: "moves should've been -1 but instead it is " + solver4.moves;
        StdOut.println("Test: " + ++numberOfTests + " passed");



        /*
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
        */
    }
}