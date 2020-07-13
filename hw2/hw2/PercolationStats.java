package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    int [] openSites;
    int gridLength;
    private double confidenceInterval = 1.96;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        } else {
            //perform T times' simulations
            openSites = new int [T];
            gridLength = N;
            for(int i = 0; i < T; i++){
                PercolationFactory factory = new PercolationFactory();
                Percolation grid = factory.make(N);
                while(!grid.percolates()) {
                    int row = StdRandom.uniform(N);
                    int col = StdRandom.uniform(N);
                    if(!grid.isOpen(row, col)) {
                        grid.open(row, col);
                        openSites[i] += 1;
                    }
                }
            }


        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return(StdStats.mean(openSites)/gridLength);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double [] thresholdDiff = new double [openSites.length];
        for (int i = 0; i < openSites.length; i++) {
            thresholdDiff[i] = openSites[i]/gridLength - mean();
        }
        return(StdStats.stddev(thresholdDiff));
    }


    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return(mean() - confidenceInterval * stddev() / Math.sqrt(openSites.length));
    }
    // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return(mean() + confidenceInterval * stddev() / Math.sqrt(openSites.length));

    }

}
