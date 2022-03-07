import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    //Global Variables
    boolean site[][]; //2D array containing objects
    int size; //n variable that is passed to constructor, assigned only once at instantiation.

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        try {
            site = new boolean[n][n];
            size = n;
        }
        catch (IllegalArgumentException e){
            System.out.print("error" + e.toString());
        }
    }
    /*
    TODO!!
     1. Corner cases:
        a. By convention, the row and column indices are integers between 1 and n,
     where (1, 1) is the upper-left site: Throw an IllegalArgumentException if any argument to open(), isOpen(), or isFull() is outside its prescribed range.
      b. Throw an IllegalArgumentException in the constructor if n ≤ 0.
     2. Performance requirements.  The constructor must take time proportional to n2; all instance methods must take constant time plus a constant number of calls to union() and find().
     */
    // opens the site (row, col) if it is not open already
    public void open (int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }
        //starting count from 0 instead of 1
        row -= 1;
        col -= 1;

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }

        //starting count from 0 instead of 1
        row -= 1;
        col -= 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }

        //starting count from 0 instead of 1
        row -= 1;
        col -= 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    public boolean percolates() {

    }

    // test client (optional)

    public static void main(String args[]) {
        System.out.print("Hello World");
    }
}
