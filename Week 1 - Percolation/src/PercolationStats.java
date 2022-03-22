import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    // Global Variables
//    final private int trials;
    private final int trials;
    private final double[] percolations;
    private static final double confidenceIntervalValue = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        //required error by assignment
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        percolations = new double[trials];
        //Generate numOfPer for "t" times
        for (int i = 0; i < trials; i++) {
            //generate value of percolation for trial "t"
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            //store value of probability "p" to calculate the mean of "t" trials
            percolations[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolations);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolations);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidenceIntervalValue * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidenceIntervalValue * stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){

        // Obtain n & t values
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
//        int n = StdIn.readInt();
//        int t = StdIn.readInt();

        //Measure time spent
//        Stopwatch sw = new Stopwatch();
        //Instantiate Class
        PercolationStats percolationStats = new PercolationStats(n,t);

        StdOut.println("mean                   = " + percolationStats.mean());
        StdOut.println("stddev                 = " + percolationStats.stddev());
        StdOut.println("95% confidence interval= [" + percolationStats.confidenceLo() + ","
                        + percolationStats.confidenceHi() + "]");
//        System.out.printf("Total time: %f secs. (for N=%d, T=%d)", sw.elapsedTime(), n, t);
    }
}
