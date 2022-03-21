import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Global Variables
    final private boolean[][] site; //2D array containing cells

    //n variable that is passed to constructor, assigned only once at instantiation
    final private int size;

    final private WeightedQuickUnionUF uf; //relation array that connects, unions nodes
    final private int  virtualTop;
    final private int virtualBottom;
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
        site = new boolean[n][n]; //no 2 because they are virtual and should not be represented
        // in site
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);

        if (site[row][col]) { //Do nothing in case the site is already opened
            return;
        }

        site[row][col] = true; //Opening site
        opensitesCount++;

        // connect cell to sites that are opened from left, right, up & down.
        int colU = col + 1; // Used to start count from 1 instead of 0 as virtualTop is at 0

        //connect left
        //check first if we are at the far left, so we don't reach out of bound error
        if (isOpen(row, col - 1)) {
            uf.union(indexOf(row, col), indexOf(row, colU - 1));
        }

        //connect right
        //check first if we are at the far right, so we don't reach out of bound error
        if (isOpen(row, col + 1)) {
            uf.union(indexOf(row, col), indexOf(row, colU + 1));
        }

        //connect up
        //check first if we are at the top, so we don't reach out of bound error
        if (isOpen(row - 1, col)) {
            uf.union(indexOf(row, col), indexOf(row - 1, colU));
        } else {
            uf.union(virtualTop, indexOf(row, colU));
        }

        //connect bottom
        //check first if we are at the bottom, so we don't reach out of bound error
        if (isOpen(row + 1, col)) {
            uf.union(indexOf(row, col), indexOf(row + 1, colU));
        } else {
            uf.union(virtualBottom, indexOf(row, colU));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
            return checkCellValidity(row, col) && site[row][col];
    }

    // is the site (row, col) isOpenfull?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.connected(row * size + col, 0); // is site connected to
        // virtualTop?
//        return isOpen(row, col) && (uf.find(virtualTop) == uf.find(indexOf(row, col)));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom); // is virtualTop connected to virtualBottom?
//        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    //Validate input
    private void validateInput(int row, int col) {
        if (!checkCellValidity(row, col)) {
            throw new IllegalArgumentException();
        }
    }

    // check if index is in bound (lies between 0 & n-1), return false otherwise
    private boolean checkCellValidity(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    private int indexOf(int row, int col) {
        return (row * size) + col;
    }

    private void testCreator(int row, int col) {
        System.out.println("isOpen(" + row + ", " + col +") Before opening: " + isOpen(row, col));
        System.out.println("isFull(" + row + ", " + col +") Before opening: " + isOpen(row, col));
        System.out.println("percolates(): " + percolates());
        System.out.println("Opening Index(" + row + "," + col+ ")"); open(row, col);
        System.out.println("isOpen(" + row + ", " + col +") After opening: " + isOpen(row, col));
        System.out.println("isFull(" + row + ", " + col +") After opening: " + isOpen(row, col));
        System.out.println("percolates(): " + percolates());
    }


    private void testCreator(int row, int col, int row2, int col2){

        testCreator(row, col);
        testCreator(row2, col2);
        System.out.println("Two points connected? " + uf.connected(indexOf(row, col), indexOf(row2,
                col2)));

    }

    // test client (optional)
    public static void main(String[] args) {

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
//                + (percolation.uf.find(percolation.indexOf(1,0)) == percolation.uf.find(percolation.virtualBottom)));
        System.out.println("True; index (1,0) connected to bottom? "
                + percolation.uf.connected(percolation.indexOf(1,0), percolation.virtualBottom));
        System.out.println();

        // Test percolates()
        System.out.println("#########percolates() Testing#########");
        System.out.println("True; percolation.percolates(): " + percolation.percolates());
        percolation.open(1, 1);*/

        /*// Instance
        int n = 4;
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

//        System.out.println("False; index(1,0) before opening isFull: " + percolation.isFull(1, 1));
//        percolation.open(1, 0);
//        System.out.println("True; index (1,0) after opening isFull: " + percolation.isFull(1, 0));
//
        System.out.println();
//        System.out.println("False; index (1,0) connected to bottom? "
//                + percolation.uf.connected(percolation.indexOf(1,0), percolation.virtualBottom));
//        System.out.println();

        // Test percolates()
        System.out.println("#########percolates() Testing#########");
        System.out.println("percolation.size: " + percolation.size);
        System.out.println("percolation.virtualTop: " + percolation.virtualTop);
        System.out.println("percolation.virtualBottom: " + percolation.virtualBottom);
        System.out.println("False; percolation.percolates(): " + percolation.percolates());*/

        // Instance
        int n = 4;
        Percolation percolation = new Percolation(n);

        percolation.testCreator(0,0);

    }
}
