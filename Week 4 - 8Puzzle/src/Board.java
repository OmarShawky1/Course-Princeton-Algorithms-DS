import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Board {
    // TODO: Finish the enrichment in the FAQ
    // Global Variables
    private static final int BLANK_TILE = 0;
    private final int tilesLength; // n
    private final int[][] tiles;
    private Stack<Board> boardStack;

    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        assert ((2 <= tiles.length && tiles.length < 128) &&
                (2 <= tiles[0].length && tiles[0].length < 128))
                : "I received unacceptable tiles";

        tilesLength = tiles.length;
        this.tiles = tiles.clone();
        boardStack = new Stack<>();
    }

    // string representation of this board
    public String toString() {
        StringBuilder tempString = new StringBuilder();

        for (int[] tilesRow : tiles) {
            tempString.append(Arrays.toString(tilesRow)).append("\n");
        }

        return tilesLength + "\n" + tempString;
    }

    // board dimension n
    public int dimension() {
        return tilesLength;
    }

    // number of tiles out of place
    public int hamming() {
        int numOfOut = 0;
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                int j1 = j + 1;
//                StdOut.println("i: " + i + " & j: " + j); // TODO Remove line
                if (tiles[i][j] != BLANK_TILE && tiles[i][j] != (i * tilesLength + j1)) {
//                    StdOut.println("(i * tileLength + j1): " + (i * tileLength + j1)); // TODO Remove line
//                    StdOut.println("tiles[i][j]: " + tiles[i][j]); // TODO Remove line
                    numOfOut++;
//                    StdOut.println("numOfOut: " + numOfOut); // TODO Remove line
                }
            }
        }
        return numOfOut;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int numOfOut = 0;
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
//                StdOut.println("i: " + i + " & j: " + j); // TODO Remove line
                int tempTile = tiles[i][j];
                if (tiles[i][j] != BLANK_TILE && tempTile != (i * tilesLength + (j + 1))) {
//                    StdOut.println("tiles[" + i + "][" + j + "]: " + tiles[i][j] + ", (i * " + "tileLength + (j + 1)): " + (i * tileLength + (j + 1))); // TODO Remove line

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
//                    StdOut.println("numOfOut: " + numOfOut + ", deltaRow:" + deltaRow + ", deltaCol" + ": " + deltaCol); // TODO Remove line
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
        // Could've used Arrays.deepEquals() as prescribed in the FAQs
        assert (y instanceof Board) : "equals received a non-board object";

        if (((Board) y).tilesLength != tilesLength) {
            return false;
        }

        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                if (((Board) y).tiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        // First, find the index of the Blank tile
        int blankI = -1;
        int blankJ = -1;
        for (int i = 0; i < tilesLength; i++) {
            for (int j = 0; j < tilesLength; j++) {
                if (tiles[i][j] == BLANK_TILE) {
                    blankI = i;
                    blankJ = j;
                }
            }
        }

        // Second, try to switch it with the four directions (up, down, left, right)

        int[][] tilesClone = new int[tilesLength][tilesLength];
        // If not up, switch with up
        if (blankI != 0) {
            for (int i = 0; i < tilesLength; i++) {
                tilesClone[i] = tiles[i].clone();
            }
//            StdOut.println("U: tilesClone:\n" + Arrays.deepToString(tilesClone).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); //TODO remove line
            int swap = tilesClone[blankI - 1][blankJ];
            tilesClone[blankI - 1][blankJ] = BLANK_TILE;
            tilesClone[blankI][blankJ] = swap;

            boardStack.push(new Board(tilesClone));
        }

        // If not down, switch with down
        if (blankI != tilesLength - 1) {
            for (int i = 0; i < tilesLength; i++) {
                tilesClone[i] = tiles[i].clone();
            }
//            StdOut.println("D: tilesClone:\n" + Arrays.deepToString(tilesClone).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); //TODO remove line
            int swap = tilesClone[blankI + 1][blankJ];
            tilesClone[blankI + 1][blankJ] = BLANK_TILE;
            tilesClone[blankI][blankJ] = swap;

            boardStack.push(new Board(tilesClone));
        }

        // If not left, switch with left
        if (blankJ != 0) {
            for (int i = 0; i < tilesLength; i++) {
                tilesClone[i] = tiles[i].clone();
            }
//            StdOut.println("L: tilesClone:\n" + Arrays.deepToString(tilesClone).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); //TODO remove line
            int swap = tilesClone[blankI][blankJ - 1];
            tilesClone[blankI][blankJ - 1] = BLANK_TILE;
            tilesClone[blankI][blankJ] = swap;

            boardStack.push(new Board(tilesClone));
        }

        // If not right, switch with right
        if (blankJ != tilesLength - 1) {
            for (int i = 0; i < tilesLength; i++) {
                tilesClone[i] = tiles[i].clone();
            }
//            StdOut.println("R: tilesClone:\n" + Arrays.deepToString(tilesClone).replace("], ", "]\n").replace("[[", "[").replace("]]", "]")); //TODO remove line
            int swap = tilesClone[blankI][blankJ + 1];
            tilesClone[blankI][blankJ + 1] = BLANK_TILE;
            tilesClone[blankI][blankJ] = swap;

            boardStack.push(new Board(tilesClone));
        }

        return new BoardIterator();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tilesClone = new int[tilesLength][tilesLength];
        for (int i = 0; i < tilesLength; i++) {
            tilesClone[i] = tiles[i].clone();
        }

        int swap;

        for (int i = 1; i < tilesLength; i++) {
            if (!tileNotBlank(i - 1, 0)) continue;

//            StdOut.println("Loop i= " + i); // TODO: remove line
            swap = tilesClone[i - 1][0];
            if (tileNotBlank(i, 0)) {
//                StdOut.println("I will swap tilesClone[" + (i - 1) + "][" + 0 + "]: " + tilesClone[i - 1][0] + " with tilesClone[" + i + "][" + 0 + "]: " + tilesClone[i][0]); // TODO: remove line
                tilesClone[i - 1][0] = tilesClone[i][0];
                tilesClone[i][0] = swap;
            } else {
//                StdOut.println("I will swap tilesClone[" + (i - 1) + "][" + 0 + "]: " + tilesClone[i - 1][0] + " with tilesClone[" + i + "][" + 1 + "]: " + tilesClone[i][1]); // TODO: remove line
                tilesClone[i - 1][0] = tilesClone[i][1];
                tilesClone[i][1] = swap;
            }
            break;
        }
        return new Board(tilesClone);
    }

    private boolean tileNotBlank(int row, int col) {
        return tiles[row][col] != BLANK_TILE;
    }

    private class BoardIterator implements Iterable<Board> {
        @Override
        public Iterator<Board> iterator() {
            return boardStack.iterator(); // TODO: check if that is correct
        }
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
        assert (board.dimension() == 3) : "Dimensions didn't return 3 but instead" + board.dimension();
        StdOut.println("Printing board test: \n" + board);
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // Testing compare
        // When equal
        Board board1 = new Board(exampleTiles);
        assert board.equals(board1) : "Boards should be equal but they are not; board: " + board + "\n and board1: " + board1;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // When different length
        Board board2 = new Board(new int[][]{{1, 2}, {3, 4}, {5, 6}});
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
        assert (board4.hamming() == 0) : "hamming() didn't return 0 but instead: " + board4.hamming();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board5 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        assert (board5.hamming() == 5) : "hamming() didn't return 5 but instead: " + board5.hamming();

        // Testing Manhattan
        assert board.manhattan() == 3 : "manhattan() didn't return 3 but instead: " + board.manhattan();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert (board5.manhattan() == 10) : "manhattan() didn't return 10 but instead: " + board5.manhattan();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert (board4.manhattan() == 0) : "manhattan() didn't return 0 but instead: " + board4.manhattan();

        // Testing isGoal
        assert (board4.isGoal()) : "board4 should've been sorted but it isn't";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        assert (!board3.isGoal()) : "board3 shouldn't be sorted but it is";
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing twin
        Board board6 = new Board(new int[][]{{4, 0, 3}, {1, 2, 5}, {7, 8, 6}});
        assert (board.twin().equals(board6)) : "board.twin should've been equal to board6 but " +
                "board6 is: \n" + board6 + "while board.twin() is: \n" + board.twin();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board7 = new Board(new int[][]{{4, 1, 3}, {8, 0, 2}, {7, 6, 5}});
        assert (board5.twin().equals(board7)) : "board5.twin should've been equal to board7 but " +
                "board7 is: \n" + board7 + "while board5.twin() is: \n" + board5.twin();
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing Iterable
        Board board8 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        Iterator<Board> iterator = board8.neighbors().iterator();

        Board upBoard = iterator.next();
        assert (upBoard.equals(new Board(new int[][]{{8, 0, 3}, {4, 1, 2}, {7, 6, 5}}))) :
                "Up Blank tile switch failed and neighbour is:\n" + upBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board downBoard = iterator.next();
        assert (downBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 0, 5}}))) :
                "Down Blank tile switch failed and neighbour is:\n" + downBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board leftBoard = iterator.next();
        assert (leftBoard.equals(new Board(new int[][]{{8, 1, 3}, {0, 4, 2}, {7, 6, 5}}))) :
                "Left Blank tile switch failed and neighbour is:\n" + leftBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board rightBoard = iterator.next();
        assert (rightBoard.equals(new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}}))) :
                "Right Blank tile switch failed and neighbour is:\n" + rightBoard;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board9 = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        Iterator<Board> iterator2 = board9.neighbors().iterator();

        Board downBoard2 = iterator2.next();
        assert (downBoard2.equals(new Board(new int[][]{{4, 1, 3}, {0, 2, 5}, {7, 8, 6}}))) :
                "Down Blank tile switch failed and neighbour is:\n" + downBoard2;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board rightBoard2 = iterator2.next();
        assert (rightBoard2.equals(new Board(new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}}))) :
                "Right Blank tile switch failed and neighbour is:\n" + rightBoard2;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board board10 = new Board(new int[][]{{6, 1, 3}, {4, 2, 5}, {7, 8, 0}});
        Iterator<Board> iterator3 = board10.neighbors().iterator();

        Board upBoard3 = iterator3.next();
        assert (upBoard3.equals(new Board(new int[][]{{6, 1, 3}, {4, 2, 0}, {7, 8, 5}}))) :
                "Up Blank tile switch failed and neighbour is:\n" + upBoard3;
        StdOut.println("Test: " + ++numberOfTests + " passed");

        Board leftBoard3 = iterator3.next();
        assert (leftBoard3.equals(new Board(new int[][]{{6, 1, 3}, {4, 2, 5}, {7, 0, 8}}))) :
                "Left Blank tile switch failed and neighbour is:\n" + leftBoard3;
        StdOut.println("Test: " + ++numberOfTests + " passed");
    }
}