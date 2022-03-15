import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Global Variables
    private boolean site[][]; //2D array containing cells
    private int size; //n variable that is passed to constructor, assigned only once at instantiation
    private int opensitesCount; //number of open cells in the 2D array
    private WeightedQuickUnionUF uf; //relation array that connects, unions nodes
    final private int  virtualTop = 0;
    final private int virtualBottom = size*size+1;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n<1) throw new IllegalArgumentException(); //required error by assignment

        // Instantiation of n^2 number of cells + virtualTop + virtualBottom initially blocked.
        uf = new WeightedQuickUnionUF(n*n+2);
        site = new boolean[n][n]; //no 2 because they are virtual and should not be represented
        // in site
        size = n;
        opensitesCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        validateInput(row, col);

        if (!site[row][col]) opensitesCount++; //if site is closed increment sites by 1

        site[row][col] = true; //Opening site

        // connect cell to sites that are opened from left, right, up & down.
        int colU = col + 1; // Used to start count from 1 instead of 0 so that virtual top is at 0

        //connect left
        //check first if we are at the far left, so we don't reach out of bound error
        if (isOpen(row, col-1)){
            uf.union(indexOf(row, col), indexOf(row, colU-1));
        }

        //connect right
        //check first if we are at the far right, so we don't reach out of bound error
        if (isOpen(row, col+1)){
            uf.union(indexOf(row, col), indexOf(row, colU+1));
        }

        //connect up
        //check first if we are at the top, so we don't reach out of bound error
        if (isOpen(row-1, col)){
            uf.union(indexOf(row-1, col), indexOf(row, colU));
        } else {
            uf.union(virtualTop, indexOf(row,colU));
        }

        //connect bottom
        //check first if we are at the bottom, so we don't reach out of bound error
        if (isOpen(row+1, col)) {
            uf.union(indexOf(row+1, col), indexOf(row, colU));
        } else {
            uf.union(virtualBottom, indexOf(row,colU));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
            return checkCellValidity (row, col) && site[row][col];
    }

    // is the site (row, col) isOpenfull?
    // TODO: where & what to do with this unused function?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.connected(row*size+col,0); // is site connected to virtualTop?
    }

    // returns the number of open sites
    public int getOpensitesCount() {
        return opensitesCount;
    }

    // does the system percolate?
    // TODO: where & what to do with this unused function?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom); // is virtualTop connected to virtualBottom?
    }

    //Validate input
    private void validateInput(int row, int col){
        if (!checkCellValidity (row, col)) throw new IllegalArgumentException();
    }

    // check if index is in bound (lies between 0 & n-1), return false otherwise
    private boolean checkCellValidity (int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private int indexOf (int row, int col){
        return (row*size)+col;
    }

    // test client (optional)
    public static void main(String args[]) {
        /*
        // Instance
        int n = 2;
        Percolation percolation = new Percolation(n);

        // Test open
        System.out.println(percolation.isOpen(0,0)); // False, before opening
        percolation.open(0,0);

        System.out.println(percolation.isOpen(0,0)); // True, after opening

        // Test union (within open)
        percolation.open(0,1);
        System.out.println(percolation.isFull(0,1)); // True
        System.out.println(percolation.isFull(1,1)); // False (not opened yet)

        // Test percolates()
        percolation.open(1,0);
        System.out.println("index (0,1) connected to top? " + percolation.isFull(1,0)); // True
        System.out.println("index (1,0) connected to bottom? " + (percolation.uf.find(1*n+1) == percolation.uf.find(percolation.virtualBottom))); // connected to bottom?
        System.out.println("percolation.percolates(): " + percolation.percolates()); // True
        percolation.open(1,1);
         */

        //Testing all at once
        //TODO: create entire testing logic
    }
}
