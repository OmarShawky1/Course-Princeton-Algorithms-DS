import edu.princeton.cs.algs4.*;

public class PercolationStats {

    // Global Variables
    private final int trials;
    private double[] numOfPercolations;
    private final double confidanceIntervalValue = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        numOfPercolations = new double[trials];

        //Generate numOfPer for "t" times
        for (int i=0; i<trials; i++){
            //generate value of percolation for trial "t"
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(n+1), StdRandom.uniform(n+1));
            }
            //store value of probability "p" to calculate the mean of "t" trials
            numOfPercolations[i] = percolation.getOpensitesCount() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(numOfPercolations);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(numOfPercolations);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((confidanceIntervalValue * stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((confidanceIntervalValue * stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){

        // Obtain n & t values
        int n = StdIn.readInt();
        int t = StdIn.readInt();

        //Instantiate Class
        PercolationStats percolationStats = new PercolationStats(n,t);

        //Measure time spent
        Stopwatch sw = new Stopwatch();
        StdOut.println("mean                   = " + percolationStats.mean());
        StdOut.println("stddev                 = " + percolationStats.stddev());
        StdOut.println("95% confidence interval= [" + percolationStats.confidenceLo() + "," + percolationStats.confidenceHi() + "]");
        System.out.printf("Total time: %f secs. (for N=%d, T=%d)", sw.elapsedTime(), n, t);
    }
}
