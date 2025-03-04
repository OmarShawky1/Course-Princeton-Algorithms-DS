import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Board {

    // Global Variables
    private static final int BLANK_TILE = 0;
    private final int tilesLength;
    private final int[] tiles;
    private final int manhattan;
    private final int hamming;
    private Stack<Board> neighbors;
    private Board twin;

    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tilesIn) {
        assert ((2 <= tilesIn.length && tilesIn.length < 128)
                && (2 <= tilesIn[0].length && tilesIn[0].length < 128))
                : "I received unacceptable tiles";

        tilesLength = tilesIn.length;
        this.tiles = new int[tilesLength * tilesLength];
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                tiles[twoDto1D(i, j)] = tilesIn[i][j];
            }
        }
        hamming = hammingInst();
        manhattan = manhattanInst();
    }

    // string representation of this board
    public String toString() {
        StringBuilder tempString = new StringBuilder();

        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength - 1; j++) {
                tempString.append(tiles[twoDto1D(i, j)]).append("  ");
            }
            tempString.append(tiles[twoDto1D(i, tilesLength - 1)]);
            tempString.append("\n");
        }

        return tilesLength + "\n" + tempString;
    }

    // board dimension n
    public int dimension() {
        return tilesLength;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    private int hammingInst() {
        int numOfOut = 0;
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                int j1 = j + 1;

                if (tiles[twoDto1D(i, j)] != BLANK_TILE && tiles[twoDto1D(i, j)] != (i * tilesLength + j1)) {
                    numOfOut++;
                }
            }
        }
        return numOfOut;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // Calculate manhattan at instantiation
    private int manhattanInst() {
        int numOfOut = 0;
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                int tempTile = tiles[twoDto1D(i, j)];
                if (tiles[twoDto1D(i, j)] != BLANK_TILE && tempTile != (i * tilesLength + (j + 1))) {

                    // check if tile is in correct row
                    int goalRow = (tempTile - 1) / tilesLength;
                    int deltaRow = i - goalRow;
                    deltaRow = deltaRow < 0 ? deltaRow * -1 : deltaRow; // Getting absolute
                    numOfOut += deltaRow;

                    // check if tile is in correct col
                    int goalCol = (tempTile - 1) % tilesLength;
                    int deltaCol = j - goalCol;
                    deltaCol = deltaCol < 0 ? deltaCol * -1 : deltaCol; // Getting absolute
                    numOfOut += deltaCol;
                }
            }
        }
        return numOfOut;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0; // or manhattan but hamming is faster
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }

        if (y == null || y.getClass() != this.getClass()) { // Requested by Autograder
            return false;
        }

        if (((Board) y).tilesLength != tilesLength) {
            return false;
        }

        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                if (((Board) y).tiles[twoDto1D(i, j)] != tiles[twoDto1D(i, j)]) {
                    return false;
                }
            }
        }
        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        return neighbors == null ? neighborsInst() : neighbors;
    }

    private Iterable<Board> neighborsInst() {
        // First, find the index of the Blank tile
        int blankI = -1;
        int blankJ = -1;
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                if (tiles[twoDto1D(i, j)] == BLANK_TILE) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }

        // Second, try to switch it with the four directions (up, down, left, right)
        int[] tilesClone = tiles;
        neighbors = new Stack<>();
        // If not up, switch with up
        if (blankI != 0) {
            swapEmptyTiles(tilesClone, blankI - 1, blankJ, blankI, blankJ);
            neighbors.push(new Board(oneDto2D(tilesClone))); // Done as requested by Enrichment
            swapEmptyTiles(tilesClone, blankI, blankJ, blankI - 1, blankJ);
        }

        // If not down, switch with down
        if (blankI != tilesLength - 1) {
            swapEmptyTiles(tilesClone, blankI + 1, blankJ, blankI, blankJ);
            neighbors.push(new Board(oneDto2D(tilesClone))); // Done as requested by Enrichment
            swapEmptyTiles(tilesClone, blankI, blankJ, blankI + 1, blankJ);
        }

        // If not left, switch with left
        if (blankJ != 0) {
            swapEmptyTiles(tilesClone, blankI, blankJ - 1, blankI, blankJ);
            neighbors.push(new Board(oneDto2D(tilesClone))); // Done as requested by Enrichment
            swapEmptyTiles(tilesClone, blankI, blankJ, blankI, blankJ - 1);
        }

        // If not right, switch with right
        if (blankJ != tilesLength - 1) {
            swapEmptyTiles(tilesClone, blankI, blankJ + 1, blankI, blankJ);
            neighbors.push(new Board(oneDto2D(tilesClone))); // Done as requested by Enrichment
            swapEmptyTiles(tilesClone, blankI, blankJ, blankI, blankJ + 1);
        }

        return neighbors; // Should've cloned it before returning but wouldn't matter in this case
    }

    private void swapEmptyTiles(int[] tilesClone, int row1, int col1, int row2, int col2) {
        int swap = tilesClone[twoDto1D(row1, col1)];
        tilesClone[twoDto1D(row1, col1)] = BLANK_TILE;
        tilesClone[twoDto1D(row2, col2)] = swap;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return twin == null ? twinInst() : twin;
    }

    private Board twinInst() {
        int[] tilesClone = tiles;

        if (tileNotBlank(0, 0) && tileNotBlank(0, 1)) {
            swapTiles(tilesClone, 0, 0, 0, 1);
            twin = new Board(oneDto2D(tilesClone));
            swapTiles(tilesClone, 0, 1, 0, 0);
        } else {
            swapTiles(tilesClone, 1, 0, 1, 1);
            twin = new Board(oneDto2D(tilesClone));
            swapTiles(tilesClone, 1, 1, 1, 0);
        }
        return twin;
    }

    private void swapTiles(int[] tilesClone, int row1, int col1, int row2, int col2) {
        int swap = tilesClone[twoDto1D(row1, col1)];
        tilesClone[twoDto1D(row1, col1)] = tilesClone[twoDto1D(row2, col2)];
        tilesClone[twoDto1D(row2, col2)] = swap;
    }

    private boolean tileNotBlank(int row, int col) {
        return tiles[twoDto1D(row, col)] != BLANK_TILE;
    }

    private int twoDto1D(int row, int col) {
        return row * tilesLength + col;
    }

    private int[][] oneDto2D(int[] oneDimA) {
        int[][] tempArray = new int[tilesLength][tilesLength];
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                tempArray[i][j] = oneDimA[twoDto1D(i, j)];
            }
        }
        return tempArray;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        StdOut.println("###############Board Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");

        int numberOfTests = 0;
        int[][] exampleTiles = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

        // Testing construction & printing
        Board board = new Board(exampleTiles);
        assert (board.dimension() == 3) : "Dimensions didn't return 3 but instead"
                + board.dimension();
        StdOut.println("Printing board test: \n" + board);
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // Testing compare
        // When equal
        Board board1 = new Board(exampleTiles);
        assert board.equals(board1) : "Boards should be equal but they are not; board: " + board + "\n and board1: " + board1;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // When different length
        Board board2 = new Board(new int[][]{{1, 2, 7}, {3, 4, 8}, {5, 6, 0}});
        assert !board.equals(board2) : "Boards should not be equal but they are; board: " + board + "\n and board2: " + board2;
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // When tiles differ
        Board board3 = new Board(new int[][]{{5, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        assert !board.equals(board3) : "Boards should not be equal but they are; board: " + board + "\n and board3: " + board3;
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // Testing Hamming
        assert (board.hamming() == 3) : "hamming() didn't return 3 but instead: " + board.hamming();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        int[][] organizedInt = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board4 = new Board(organizedInt);
        assert (board4.hamming() == 0) : "hamming() didn't return 0 but instead: "
                + board4.hamming();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board5 = new Board(new int[][]{{8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}});
        assert (board5.hamming() == 5) : "hamming() didn't return 5 but instead: "
                + board5.hamming();

        // Testing Manhattan
        assert board.manhattan() == 3 : "manhattan() didn't return 3 but instead: "
                + board.manhattan();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert (board5.manhattan() == 10) : "manhattan() didn't return 10 but instead: "
                + board5.manhattan();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert (board4.manhattan() == 0) : "manhattan() didn't return 0 but instead: "
                + board4.manhattan();

        // Testing isGoal
        assert (board4.isGoal()) : "board4 should've been sorted but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert (!board3.isGoal()) : "board3 shouldn't be sorted but it is";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing twin
        Board board2x2 = new Board(new int[][]{{0, 2},
                {3, 1}});
        assert (board2x2.twin().equals(new Board(new int[][]{{0, 2},
                {1, 3}}))) :
                "board.twin for 2x2 failed by returning " + board2x2.twin();

        Board board6 = new Board(new int[][]{{1, 0, 3},
                {2, 4, 5},
                {7, 8, 6}});
        assert (board.twin().equals(board6)) : "board.twin should've been equal to board6 but "
                + "board6 is: \n" + board6 + "while board.twin() is: \n" + board.twin();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board7 = new Board(new int[][]{{1, 8, 3}, {4, 0, 2}, {7, 6, 5}});
        assert (board5.twin().equals(board7)) : "board5.twin should've been equal to board7 but "
                + "board7 is: \n" + board7 + "while board5.twin() is: \n" + board5.twin();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing Iterable
        Board board8 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Iterator<Board> iterator = board8.neighbors().iterator();

        Board rightBoard = iterator.next();
        assert (rightBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}}))) :
                "Right Blank tile switch failed and neighbour is:\n" + rightBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board leftBoard = iterator.next();
        assert (leftBoard.equals(new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}}))) :
                "Left Blank tile switch failed and neighbour is:\n" + leftBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board downBoard = iterator.next();
        assert (downBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}}))) :
                "Down Blank tile switch failed and neighbour is:\n" + downBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board upBoard = iterator.next();
        assert (upBoard.equals(new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}}))) :
                "Up Blank tile switch failed and neighbour is:\n" + upBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board9 = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Iterator<Board> iterator2 = board9.neighbors().iterator();

        Board rightBoard2 = iterator2.next();
        assert (rightBoard2.equals(new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}}))) :
                "Right Blank tile switch failed and neighbour is:\n" + rightBoard2;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board downBoard2 = iterator2.next();
        assert (downBoard2.equals(new Board(new int[][]{{4, 1, 3}, {0, 2, 5}, {7, 8, 6}}))) :
                "Down Blank tile switch failed and neighbour is:\n" + downBoard2;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board10 = new Board(new int[][]{{6, 1, 3}, {4, 2, 5}, {7, 8, 0}});
        Iterator<Board> iterator3 = board10.neighbors().iterator();

        Board leftBoard3 = iterator3.next();
        assert (leftBoard3.equals(new Board(new int[][]{{6, 1, 3}, {4, 2, 5}, {7, 0, 8}}))) :
                "Left Blank tile switch failed and neighbour is:\n" + leftBoard3;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board upBoard3 = iterator3.next();
        assert (upBoard3.equals(new Board(new int[][]{{6, 1, 3}, {4, 2, 0}, {7, 8, 5}}))) :
                "Up Blank tile switch failed and neighbour is:\n" + upBoard3;
        StdOut.println("Test: " + ++numberOfTests + " passed");

    }
}