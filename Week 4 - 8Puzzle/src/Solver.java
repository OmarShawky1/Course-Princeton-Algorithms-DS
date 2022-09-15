import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    // Global Variables
    private boolean isSolvable;
    private final int moves;
    // private final ArrayList<SearchNode> goalNodes;
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
        ArrayList<SearchNode> goalNodes = new ArrayList<>();
        SearchNode initialNode = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(initialNode);

        // Creating a twin board to check if its solvable (hence initial isn't)
        MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
        twinMinPQ.insert(new SearchNode(initial.twin(), 0, null));
//        StdOut.println("Finished Initializing nodes & stacks, will start checking minPQ.isEmpty(): "); //TODO: Remove line

        // Finding a solution
        while (!minPQ.isEmpty()) {
//            StdOut.println("Started !minPQ.isEmpty()"); //TODO: Remove line
            // Add solutions that only has the same priority as previous goal
            if (!goalNodes.isEmpty()) {
//                StdOut.println("Started !goalNodes.isEmpty()"); //TODO: Remove line
                if (minPQ.min().priority > goalNodes.get(0).priority) {
//                    StdOut.println("Finished minPQ.min().priority > goalNodes.get(0).priority"); //TODO: Remove line
                    break;
                }
//                StdOut.println("Finished !goalNodes.isEmpty()"); //TODO: Remove line
            }

            // Add every solution to goalNodes
            if (minPQ.min().currentBoard.isGoal()) {
//                StdOut.println("Started minPQ.min().currentBoard.isGoal()"); //TODO: Remove line
                isSolvable = true;
                goalNodes.add(minPQ.min());
//                StdOut.println("Finished minPQ.min().currentBoard.isGoal()"); //TODO: Remove line
            }

            // Check if the board is unsolvable
            if (twinMinPQ.min().currentBoard.isGoal()) {
//                StdOut.println("Started twinMinPQ.min().currentBoard.isGoal()"); //TODO: Remove line
                isSolvable = false;
//                StdOut.println("Finished twinMinPQ.min().currentBoard.isGoal()"); //TODO: Remove line
                break;
            }
            
            // TODO: There is a bug in addNeighbor that makes the complexity non-linear
//            StdOut.println("Started addNeighbor(minPQ)"); //TODO: Remove line
            addNeighbor(minPQ);
//            StdOut.println("Finished addNeighbor(minPQ)"); //TODO: Remove line
//            StdOut.println("Started addNeighbor(twinMinPQ)"); //TODO: Remove line
            addNeighbor(twinMinPQ);
//            StdOut.println("Finished addNeighbor(twinMinPQ)"); //TODO: Remove line
        }

        // this.moves = isSolvable ? goalNodes.get(0).moves : -1; //TODO: Remove line

        // Creating the solution
        // Could've merged it into the previous step but kept it separate for readability
        if (isSolvable) {
            this.moves = goalNodes.get(0).moves;
            for (SearchNode goalSearchNode : goalNodes) {
                while (goalSearchNode != null) {
                    solution.push(goalSearchNode.currentBoard);
                    goalSearchNode = goalSearchNode.predecessorNode;
                }
            }
        } else {
            this.moves = -1;
        }
    }

    private static void addNeighbor(MinPQ<SearchNode> minPQ) {
        SearchNode currentParent = minPQ.delMin(); // Predecessor for new neighbors
        SearchNode currentNode = currentParent; // For iterating over predecessors
        for (Board neighbor : currentNode.currentBoard.neighbors()) {

            // check neighbor with all predecessors (pointed with current until it is null)
            while (currentNode != null) {
                if (neighbor.equals(currentNode.currentBoard)) {
                    break;
                }
                currentNode = currentNode.predecessorNode;
            }
            if (currentNode == null) {
                minPQ.insert(new SearchNode(neighbor, currentParent.moves + 1, currentParent));
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
        return isSolvable? solution: null;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        private final SearchNode predecessorNode;
        private final int moves;
        private final int priority;

        public SearchNode(Board currentBoard, int moves, SearchNode predecessorNode) {
            this.predecessorNode = predecessorNode;
            this.currentBoard = currentBoard;
            this.moves = moves;
            priority = this.currentBoard.manhattan() + this.moves;
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
