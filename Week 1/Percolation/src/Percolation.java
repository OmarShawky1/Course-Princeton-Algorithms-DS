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

        // Instantiation of n^2 number of cells + 2 virtual upper & lower cells initially blocked.
        uf = new WeightedQuickUnionUF(n*n+2);
//        site = new boolean[n][n]; // do not think it is necessary but kept in case it is

        size = n;
        openSitesNumber = 0;

    }
    /*
    TODO: all instance methods must take constant time plus a constant number of calls to union()
      and find().
     */
    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        checkCellValidity (row, col);

        //starting count from 0 instead of 1
        row -= 1;
        col -= 1;

        site[row][col] = true; //Opening site

        // connect sites that has been opened from left, right, up & down.

        //connect left
        //check first if we are at the far left, so we don't reach out of bound error
        if (row!=0 && isOpen(row, col-1)){
            uf.union((row*size)+col, (row*size)+(col-1));
        }

        //connect right
        //check first if we are at the far right, so we don't reach out of bound error
        if (row!=size && isOpen(row, col+1)){
            uf.union((row*size)+col, (row*size)+(col+1));
        }

        //connect up
        //check first if we are at the top, so we don't reach out of bound error
        if (col!=0 && isOpen(row, col-1)){
            uf.union(((row-1)*size)+col, (row*size)+col);
        }

        //connect bottom
        //check first if we are at the bottom, so we don't reach out of bound error
        if (col!=size && isOpen(row, col+1)) {
            uf.union(((row+1)*size)+col, (row*size)+col);
        }

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
        return openSitesNumber;
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
//        System.out.print("Hello World");
    }
}
