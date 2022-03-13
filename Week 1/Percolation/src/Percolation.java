import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    //Global Variables
    private boolean site[][]; //2D array containing cells
    private int size; //n variable that is passed to constructor, assigned only once at
    // instantiation.
    private int openSitesNumber; //number of open cells in the 2D array
    private WeightedQuickUnionUF uf;

    // TODO: Performance requirements. The constructor must take time proportional to n2;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n<1) throw new IllegalArgumentException();

        n++;

        // Instantiation of n^2 number of cells + 2 virtual upper & lower cells initially blocked.
        uf = new WeightedQuickUnionUF((n*n+2));
        site = new boolean[n][n]; // do not think it is necessary but kept in case it is

        size = n;
        openSitesNumber = 0;

    }
    /*
    TODO: all instance methods must take constant time plus a constant number of calls to union()
      and find().
     */
    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        if (!checkCellValidity (row, col)) throw new IllegalArgumentException();

        site[row-1][col-1] = true; //Opening site

        // connect sites that has been opened from left, right, up & down.

        //connect left
        //check first if we are at the far left, so we don't reach out of bound error
        if (checkCellValidity(row, col-1) && isOpen(row, col-1)){
            uf.union((row*size)+col, (row*size)+(col-1));
        }

        //connect right
        //check first if we are at the far right, so we don't reach out of bound error
        if (checkCellValidity(row, col+1) && isOpen(row, col+1)){
            uf.union((row*size)+col, (row*size)+(col+1));
        }

        //connect up
        //check first if we are at the top, so we don't reach out of bound error
        if (checkCellValidity(row-1, col) && isOpen(row-1, col)){
            uf.union(((row-1)*size)+col, (row*size)+col);
        }

        //connect bottom
        //check first if we are at the bottom, so we don't reach out of bound error
        if (checkCellValidity(row+1, col) && isOpen(row+1, col)) {
            uf.union(((row+1)*size)+col, (row*size)+col);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
            // check first if index is in bound, return false otherwise
            return checkCellValidity (row, col) &&
            //if it is in bound, check if it is true/false
                    site[row - 1][col - 1]; //check if site is open

    }

    // is the site (row, col) full?
    //i.e., is this site connected to the virtual upper or lower site?
    public boolean isFull(int row, int col) {
        checkCellValidity (row, col);
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesNumber;
    }

    // does the system percolte?
    //i.e., is the virtual upper point connected to virtual bottom point?
    public boolean percoltes() {
        return false;
    }

    // Determine if it is a valid cell (lies between 1 & n)
    private boolean checkCellValidity (int row, int col) {
        return row >= 1 && row <= size && col >= 1 && col <= size;
    }

    // test client (optional)
    public static void main(String args[]) {

        //Instance
        Percolation percolation = new Percolation(2);

        percolation.open(1,1);
        percolation.open(1,2);
        percolation.open(2,1);
        percolation.open(2,2);
    }
}
