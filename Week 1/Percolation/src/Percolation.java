import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Scanner; //todo remove me

public class Percolation {

    // Global Variables
    private final boolean[][] site; //2D array containing cells

    //n variable that is passed to constructor, assigned only once at instantiation
    private final int size;

    private final WeightedQuickUnionUF uf; //relation array that connects, unions nodes
    private final WeightedQuickUnionUF uf2; //Used to solve backwash issue in submission
    private final int  virtualTop;
    private final int virtualBottom;
    private int opensitesCount; //number of open cells in the 2D array

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        //required error by assignment
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        //Constants Instantiation
        size = n;
        virtualTop = 0;
        virtualBottom = size * size + 1;
        opensitesCount = 0;

        // Instantiation of n^2 number of cells + virtualTop + virtualBottom initially blocked.
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 2);
        site = new boolean[n][n]; //no 2 because they are virtual and should not be represented in site
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);

        if (site[row - 1][col - 1]) { //Do nothing in case the site is already opened
            return;
        }

        site[row - 1][col - 1] = true; //Opening site
        opensitesCount++;

        // connect cell to sites that are opened from left, right, up & down.
        //connect left
        if (cellValidAndOpen(row, col - 1)) {
            uf.union(indexOf(row, col), indexOf(row, col - 1));
            uf2.union(indexOf(row, col), indexOf(row, col - 1));
        }

        //connect right
        if (cellValidAndOpen(row, col + 1)) {
            uf.union(indexOf(row, col), indexOf(row, col + 1));
            uf2.union(indexOf(row, col), indexOf(row, col + 1));
        }

        //connect up
        if (cellValidAndOpen(row - 1, col)) {
            uf.union(indexOf(row, col), indexOf(row - 1, col));
            uf2.union(indexOf(row, col), indexOf(row - 1, col));
        }

        //connect bottom
        if (cellValidAndOpen(row + 1, col)) {
            uf.union(indexOf(row, col), indexOf(row + 1, col));
            uf2.union(indexOf(row, col), indexOf(row + 1, col));
        }

        //Check if we are at top
        if (row == 1) {
            uf.union(virtualTop, indexOf(row, col));
            uf2.union(virtualTop, indexOf(row, col));
        }

        //used to be else if to decrease the number of checks but he checks against n=1 that
        // gives true for both row = 1 & row = size when size = 1.
        if (row == size) { //else check if we are at bottom
            uf.union(virtualBottom, indexOf(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        return site[row - 1][col - 1];
    }

    // is the site (row, col) isOpenfull?
    public boolean isFull(int row, int col) {
        validateInput(row, col);
//        return site[row - 1][col - 1] && isConnected(virtualTop, indexOf(row, col));
        //we use uf2 because we did not ever connect virtualBottom with bottom cells
        return site[row - 1][col - 1] && uf2.find(virtualTop) == uf2.find(indexOf(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnected(virtualTop, virtualBottom);
    }

    // check two points connection
    private boolean isConnected(int p1, int p2){
        return uf.find(p1) == uf.find(p2);
    }

    // is the site (row, col) open?
    private boolean cellValidAndOpen(int row, int col) {
        return cellValid(row, col) && site[row - 1][col - 1];
    }

    //Validate input
    private void validateInput(int row, int col) {
        if (!cellValid(row, col)) {
            throw new IllegalArgumentException("I was called with: row: " + row + " , col: " + col);
        }
    }

    // check if index is in bound (lies between 0 & n-1), return false otherwise
    private boolean cellValid(int row, int col) {
        return row >= 1 && row <= size && col >= 1 && col <= size;
    }

    private int indexOf(int row, int col) {
        return ((row - 1) * size) + col; // col+1-1 because we start at 1, as 0 is virtualTop
    }

    private void testCreator(int row, int col) {
        System.out.println("isOpen(" + row + ", " + col + ") Before opening: " + isOpen(row, col));
        System.out.println("isFull(" + row + ", " + col + ") Before opening: " + isFull(row, col));
        System.out.println("percolates(): " + percolates());
        System.out.println("Opening Index(" + row + "," + col + ")"); open(row, col);
        System.out.println("isOpen(" + row + ", " + col + ") After opening: " + isOpen(row, col));
        System.out.println("isFull(" + row + ", " + col + ") After opening: " + isFull(row, col));
        System.out.println("percolates(): " + percolates());
        System.out.println("####################################");
    }


    private void testCreator(int row, int col, int row2, int col2) {

        testCreator(row, col);
        testCreator(row2, col2);
        System.out.println("Two points connected? " + uf.connected(indexOf(row, col), indexOf(row2,
                col2)));
        System.out.println();

    }

    // test client (optional)
    public static void main(String[] args) {

        //Unit Test for each function
        /*// Instance
        int n = 2;
        Percolation percolation = new Percolation(n);

        // Test open
        System.out.println("#########open() Testing#########");

        System.out.println("False; Before opening index(0,0): " + percolation.isOpen(0, 0));
        percolation.open(0, 0);
        System.out.println("True; After opening index(0,0): " + percolation.isOpen(0, 0));

        System.out.println();

        // Test union (within open)
        System.out.println("#########union() Testing#########");

        percolation.open(0, 1);
        System.out.println("True; index(0,1) after opening isFull: " + percolation.isFull(0, 1));

        System.out.println("False; index(1,0) before opening isFull: " + percolation.isFull(1, 1));
        percolation.open(1, 0);
        System.out.println("True; index (1,0) after opening isFull: " + percolation.isFull(1, 0));

        System.out.println();
//        System.out.println("True; index (1,0) connected to bottom? "
//                + (percolation.uf.find(percolation.indexOf(1,0)) ==
                    percolation.uf.find(percolation.virtualBottom)));
        System.out.println("True; index (1,0) connected to bottom? "
                + percolation.uf.connected(percolation.indexOf(1,0), percolation.virtualBottom));
        System.out.println();

        // Test percolates()
        System.out.println("#########percolates() Testing#########");
        System.out.println("True; percolation.percolates(): " + percolation.percolates());
        percolation.open(1, 1);*/

        //Entire Percolation Test
        /*// Instance
        int n = 4;
        Percolation percolation = new Percolation(n);

        percolation.testCreator(0,0,1,1);
        percolation.testCreator(1,0);
        percolation.testCreator(2,0);
        percolation.testCreator(3,0);
        System.out.println("VirtualBottom & (3,0) connected? " +
                            percolation.uf.connected(percolation.virtualBottom,
                            percolation.indexOf(3,0)));*/

        //Live test
        //todo comment everything under this

        Scanner scanner = new Scanner(System.in);
        Percolation percolation = new Percolation(scanner.nextInt());
        while (scanner.hasNext()) {
            percolation.testCreator(scanner.nextInt(), scanner.nextInt());
        }

    }
}
