import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Global Variables
    private boolean site[][]; //2D array containing cells
    private int size; //n variable that is passed to constructor, assigned only once at
    // instantiation.
    private int openSitesNumber; //number of open cells in the 2D array
    private WeightedQuickUnionUF uf; //relation array that connects, unions nodes

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n<1) throw new IllegalArgumentException(); //required error by assignment

        // Instantiation of n^2 number of cells + 2 virtual top & bottom cells initially blocked.
        uf = new WeightedQuickUnionUF(n*n+2);
        site = new boolean[n][n]; //no 2 because they are virtual and should not be represented
        // in site
        size = n;
        openSitesNumber = 0;
    }

    // opens the site (row, col) if it is not open already
    //TODO increase the openSitesNumber after opening new one.
    public void open (int row, int col) {
        validateInput(row, col);

        site[row][col] = true; //Opening site

        // connect cell to sites that are opened from left, right, up & down.

        //connect left
        //check first if we are at the far left, so we don't reach out of bound error
        if (isOpen(row, col-1)){
            uf.union((row*size)+col, (row*size)+(col-1));
        }

        //connect right
        //check first if we are at the far right, so we don't reach out of bound error
        if (isOpen(row, col+1)){
            uf.union((row*size)+col, (row*size)+(col+1));
        }

        //connect up
        //check first if we are at the top, so we don't reach out of bound error
        if (isOpen(row-1, col)){
            uf.union(((row-1)*size)+col, (row*size)+col);
        }

        //connect bottom
        //check first if we are at the bottom, so we don't reach out of bound error
        if (isOpen(row+1, col)) {
            uf.union(((row+1)*size)+col, (row*size)+col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
            return checkCellValidity (row, col) && site[row][col];
    }

    // is the site (row, col) isOpenfull?
    // i.e., is this site connected to the virtual upper site?
    // TODO: where & what to do with this unused function?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.find(row*size+col) == uf.find(size*size); //cell connected to
        // top?
        return isOpen(row, col) && uf.connected(row*size+col,0);
    }

    // returns the number of open sites
    public int getOpenSitesNumber() {
        return openSitesNumber;
    }

    // does the system percolate?
    // i.e., is the virtual upper point connected to virtual bottom point?
    // TODO: where & what to do with this unused function?
    public boolean percolates() {
        return uf.find(size*size) == uf.find(size*size+1); // is virtual top (at 0) connected to
        // virtual
        // bottom at array end (size^2+1)?
    }

    //Validate input
    private void validateInput(int row, int col){
        if (!checkCellValidity (row, col)) throw new IllegalArgumentException();
    }

    // check if index is in bound (lies between 0 & n-1), return false otherwise
    private boolean checkCellValidity (int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // test client (optional)
    public static void main(String args[]) {

        // Instance
        int n = 2;
        Percolation percolation = new Percolation(n);
        System.out.println("virtual top parent: " + percolation.uf.find(0));

        // Test open
//        System.out.println(percolation.isOpen(0,0)); // False, before opening
        percolation.open(0,0);
        System.out.println("virtual top parent: 2- " + percolation.uf.find(0));

//        System.out.println(percolation.isOpen(0,0)); // True, after opening

        // Test union (within open)
        percolation.open(0,1);
        System.out.println("virtual top parent: 3- " + percolation.uf.find(0));
//        System.out.println(percolation.isFull(0,1)); // True
//        System.out.println(percolation.isFull(1,1)); // False (not opened yet)

        // Test percolates()
        percolation.open(1,0);
        System.out.println("virtual top parent: 4- " + percolation.uf.find(0));
        System.out.println("index (0,1) connected to top? " + percolation.isFull(1,0)); // True

        System.out.println("virtual top parent: 5- " + percolation.uf.find(0));
        System.out.println("percolation.uf.find(1*n+0): " + percolation.uf.find(1*n+0)); //1

        //It appears that any top cell is connected by default to virtual top but not the same
        // for bottom
        System.out.println("percolation.uf.find(1*n+n+1): " + percolation.uf.find(1*n+n+1));
        System.out.println("0,1 connected to bottom? " +
                (percolation.uf.find(1*n+0) == percolation.uf.find(1*n+n+1))); // connected to bottom?

//        System.out.println("uf.find(size*size+2-1: " + percolation.uf.find(n*n+1)); //5 // virtual
//        bottom
//        System.out.println("percolation.percolates(): " + percolation.percolates()); //false,
//        should've been true
        percolation.open(1,1);

        //Testing all at once
        //TODO: create entire testing logic
    }
}
