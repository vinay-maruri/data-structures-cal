/**
 * @author: Vinay Maruri vmaruri1@berkeley.edu
 * @version: 2.2
 * A program to estimate the value of the
 * percolation threshold via Monte Carlo Simulation
 */
package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private int x;
    private double y;
    private double[] count;
    private PercolationFactory h;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.x = N;
        this.y = T;
        this.h = pf;
        this.count = new double[T];
        for (int i = 0; i < T; i++) {
            count[i] = thresholdHelper(h.make(N));
        }
    }

    //helper method to calculate the percolation threshold
    private double thresholdHelper(Percolation z) {
        int countLength = 0;
        while (!z.percolates()) {
            int randomrow = StdRandom.uniform(this.x);
            int randomcol = StdRandom.uniform(this.x);
            while (z.isOpen(randomrow, randomcol)) {
                randomrow = StdRandom.uniform(this.x);
                randomcol = StdRandom.uniform(this.x);
            }
            z.open(randomrow, randomcol);
            countLength += 1;
        }
        return (double) countLength / (this.x * this.x);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(count);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(count);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return (mean() - (1.96 * (stddev() / Math.pow(y, 0.5))));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return (mean() + (1.96 * (stddev() / Math.pow(y, 0.5))));
    }
}
