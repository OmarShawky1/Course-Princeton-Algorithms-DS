import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    // Global Variables
    private static final int BLANK_TILE = 0;
    private final int tileLength; // n
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        assert ((2 <= tiles.length && tiles.length < 128) &&
                (2 <= tiles[0].length && tiles[0].length < 128)) : "I received unacceptable tiles";

        tileLength = tiles.length;
        this.tiles = tiles.clone();
    }

    // string representation of this board
    public String toString() {
        StringBuilder tempString = new StringBuilder();

        for (int[] tilesRow: tiles) {
            tempString.append(Arrays.toString(tilesRow)).append("\n");
        }

        return tileLength + "\n" + tempString;
    }

    // board dimension n
    public int dimension() {
        // TODO
        return 0;
    }

    // number of tiles out of place
    public int hamming() {
        // TODO
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // TODO
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        // TODO
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // TODO
        return false;
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
        int[][] exampleTiles = {{1,0,3}, {4,2,5}, {7,8,6}};

        // Testing construction & printing
        Board board = new Board(exampleTiles);
        StdOut.println(board);

        
    }
}