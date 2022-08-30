import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    // Global Variables
    private static final int BLANK_TILE = 0;
    private final int tileLength; // n
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        assert ((2 <= tiles.length && tiles.length < 128) && (2 <= tiles[0].length && tiles[0].length < 128)) : "I received unacceptable tiles";

        tileLength = tiles.length;
        this.tiles = tiles.clone();
    }

    // string representation of this board
    public String toString() {
        StringBuilder tempString = new StringBuilder();

        for (int[] tilesRow : tiles) {
            tempString.append(Arrays.toString(tilesRow)).append("\n");
        }

        return tileLength + "\n" + tempString;
    }

    // board dimension n
    public int dimension() {
        return tileLength;
    }

    // number of tiles out of place
    public int hamming() {
        int numOfOut = 0;
        for (int i = 0; i < tileLength; i++) {
            for (int j = 0; j < tileLength; j++) {
                int j1 = j + 1;
//                StdOut.println("i: " + i + " & j: " + j); // TODO Remove line
                if (tiles[i][j] != BLANK_TILE && tiles[i][j] != (i * tileLength + j1)) {
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
        for (int i = 0; i < tileLength; i++) {
            for (int j = 0; j < tileLength; j++) {
                StdOut.println("i: " + i + " & j: " + j); // TODO Remove line
                int tempTile = tiles[i][j];
                if (tiles[i][j] != BLANK_TILE && tempTile != (i * tileLength + (j + 1))) {
                    StdOut.println("tiles[" + i + "][" + j + "]: " + tiles[i][j] + ", (i * " + "tileLength + (j + 1)): " + (i * tileLength + (j + 1))); // TODO Remove line

                    // check if tile is in correct row
                    int goalRow = (tempTile - 1) / tileLength;
                    int deltaRow = i - goalRow;
                    deltaRow = deltaRow < 0 ? deltaRow * -1 : deltaRow; // Getting absolute
                    numOfOut += deltaRow;

                    // check if tile is in correct col
                    int goalCol = (tempTile - 1) % tileLength;
                    int deltaCol = j - goalCol;
                    deltaCol = deltaCol < 0 ? deltaCol * -1 : deltaCol; // Getting absolute
                    numOfOut += deltaCol;
                    StdOut.println("numOfOut: " + numOfOut + ", deltaRow:" + deltaRow + ", deltaCol" + ": " + deltaCol); // TODO Remove line
                }
            }
        }
        return numOfOut;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // TODO
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        assert (y instanceof Board) : "equals received a non-board object";

        if (((Board) y).tileLength != tileLength) {
            return false;
        }

        for (int i = 0; i < tileLength; i++) {
            for (int j = 0; j < tileLength; j++) {
                if (((Board) y).tiles[i][j] != tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /*
    // all neighboring boards
    public Iterable<Board> neighbors() {
        // TODO
    }
    */
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // TODO
        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        StdOut.println("###############Board Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        int[][] exampleTiles = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};

        // Testing construction & printing
        Board board = new Board(exampleTiles);
        assert (board.dimension() == 3) : "Dimensions didn't return 3 but instead" + board.dimension();
        StdOut.println("Printing board test: \n" + board);
        StdOut.println();


        // Testing compare
        // When equal
        Board board1 = new Board(exampleTiles);
        assert board.equals(board1) : "Boards should be equal but they are not; board: " + board + "\n and board1: " + board1;
        StdOut.println();

        // When different length
        Board board2 = new Board(new int[][]{{1, 2}, {3, 4}, {5, 6}});
        assert !board.equals(board2) : "Boards should not be equal but they are; board: " + board + "\n and board2: " + board2;
        StdOut.println();


        // When tiles differ
        Board board3 = new Board(new int[][]{{5, 0, 3}, {4, 2, 5}, {7, 8, 6}});
        assert !board.equals(board3) : "Boards should not be equal but they are; board: " + board + "\n and board3: " + board3;
        StdOut.println();


        // Testing Hamming
        assert (board.hamming() == 3) : "hamming() didn't return 3 but instead: " + board.hamming();
        StdOut.println();

        int[][] organizedInt = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board4 = new Board(organizedInt);
        assert (board4.hamming() == 0) : "hamming() didn't return 0 but instead: " + board4.hamming();

        StdOut.println();
        Board board5 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        assert (board5.hamming() == 5) : "hamming() didn't return 5 but instead: " + board5.hamming();

        // Testing Manhattan
        assert board.manhattan() == 3 : "manhattan() didn't return 3 but instead: " + board.manhattan();
        StdOut.println();

        assert (board5.manhattan() == 10) : "manhattan() didn't return 10 but instead: " + board5.manhattan();

        StdOut.println();
        assert (board4.manhattan() == 0) : "manhattan() didn't return 0 but instead: " + board4.manhattan();


    }
}