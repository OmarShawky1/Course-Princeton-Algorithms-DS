import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    //Global Variables
    private boolean site[][]; //2D array containing cells
    private int size; //n variable that is passed to constructor, assigned only once at
    // instantiation.
    private int openSites; //number of open cells in the 2D array
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n<1) throw new IllegalArgumentException();

        site = new boolean[n][n];
        size = n;
        openSites = 0;

        // Instantiation of n^2 number of cells + 2 virtual upper & lower cells
        uf = new WeightedQuickUnionUF(n*n+2);
    }
    /*
    TODO!!
     1. Performance requirements. The constructor must take time proportional to n2; all instance methods must take constant time plus a constant number of calls to union() and find().
     */
    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        checkCellValidity (row, col);

        //starting count from 0 instead of 1
        row -= 1;
        col -= 1;

        site[row][col] = true; //Opening site
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkCellValidity (row, col);

        return site[row][col]; //check if site is open (return it)
    }

    // is the site (row, col) full?
    //i.e., is this site connected to the virtual upper or lower site?
    public boolean isFull(int row, int col) {
        checkCellValidity (row, col);

        //starting count from 0 instead of 1
        row -= 1;
        col -= 1;

        // check if the cell is open before we check it is connected to upper/lower
        if (!isOpen(row, col)) {

            //check if current point is connected to virtual uppper/lower

            return false;
        }
        else {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    //i.e., is the virtual upper point connected to virtual bottom point?
    public boolean percolates() {
        return false;
    }

    // Determine if it is a valid cell (lies between 1 & n)
    private void checkCellValidity (int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    // test client (optional)

    public static void main(String args[]) {
        System.out.print("Hello World");
    }
}
