import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver { //TODO: he is asking for immutable class (I/P no. 14) tho, he didn't make it final

    // Global Variables
    private final MinPQ<SearchNode> minPQ;
    private final Stack<SearchNode> gameTree;
    private final Board initialBoard;
    private final int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("null argument to constructor");
        }

        minPQ = new MinPQ<>();
        gameTree = new Stack<>();
        initialBoard = initial;


        SearchNode initialNode = new SearchNode(initialBoard, 0, null);
        minPQ.insert(initialNode); // Based on I/P no. 6;
        gameTree.push(initialNode); // Based on I/P no. 12;


        // Based on I/P no. 9;
        SearchNode currentNode;
        Board currentBoard;
        int moves = 0;
        do { //TODO: wrong because doesn't use isSolvable
            currentNode = move(moves);
            currentBoard = currentNode.currentBoard;
        } while (!currentBoard.isGoal());

        this.moves = currentNode.moves;
    }

    private SearchNode move(int prevMoves) {

        // Based on I/P no. 13;
        SearchNode minPriorityNode = minPQ.delMin(); //TODO: Check how to make delMin using "compare"
        // by priority
        gameTree.push(minPriorityNode);
        Board minPriorityBoard = minPriorityNode.currentBoard;

        for (Board neighbor : minPriorityBoard.neighbors()) {

            // Based on I/P no. 18;
            if (!neighbor.equals(minPriorityNode.predecessor)) { // Don't add predecessor
                // Based on I/P no. 8 & 13;
                minPQ.insert(new SearchNode( neighbor, prevMoves + 1, minPriorityBoard));
            }
        }
        return minPriorityNode;
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

    private class SearchNode implements Comparator<SearchNode> {

        // Based on I/P no. 5;
        private final Board currentBoard;
        private final Board predecessor;
        private final int moves;
        private final int priority;

        public SearchNode(Board currentBoard, int moves ,Board predecessor) {
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