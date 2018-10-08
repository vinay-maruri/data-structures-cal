/**
 * @author: Vinay Maruri vmaruri1@berkeley.edu
 * @version: 2.3
 * A program to estimate the value of the
 * percolation threshold via Monte Carlo Simulation
 */
package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    // create N-by-N grid, with all sites initially blocked
    private boolean[][] openArray;
    private WeightedQuickUnionUF set;
    private int topSite;
    private int bottomSite;
    private int gridSize;
    private WeightedQuickUnionUF setNoBackWash;
    private int countopen;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        openArray = new boolean[N][N];
        set = new WeightedQuickUnionUF(N * N + 2);
        setNoBackWash = new WeightedQuickUnionUF(N * N + 1);
        topSite = N * N;
        bottomSite = N * N + 1;
        gridSize = N;
        countopen = 0;
    }

    /**
     * use for unit testing (not required)
     *
     * @param args
     */
    public static void main(String[] args) {
        Percolation r = new Percolation(5);
        //r.isFull(0, 1);
        r.open(0, 1);
        r.open(0, 1);
        r.isFull(0, 0);
    }

    /**
     * open the site (row, col) if it is not open already
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (row >= openArray[0].length || col >= openArray[0].length) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            openArray[row][col] = true;
            countopen += 1;
            makeconnection(row, col);
        }
    }

    /**
     * is the site (row, col) open?
     *
     * @param row
     * @param col
     */
    public boolean isOpen(int row, int col) {
        if (row >= openArray[0].length || col >= openArray[0].length) {
            throw new IndexOutOfBoundsException();
        }
        return openArray[row][col];
    }

    /**
     * is the site (row, col) full?
     *
     * @param row
     * @param col
     */
    public boolean isFull(int row, int col) {
        if (row >= openArray[0].length || col >= openArray[0].length) {
            throw new IndexOutOfBoundsException();
        }
        int pos = xyto1D(row, col);
        return setNoBackWash.connected(pos, topSite);
    }

    /**
     * helper method to convert x,y coordinate pair to
     * a number for weighted quick union
     *
     * @param r
     * @param c
     * @return
     */
    private int xyto1D(int r, int c) {
        return (r * gridSize) + c;
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return countopen;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return set.connected(topSite, bottomSite);
    }

    /**
     * helper method to create connections
     *
     * @param r
     * @param c
     */
    private void makeconnection(int r, int c) {
        int pos = xyto1D(r, c);
        if ((r + 1) < gridSize && isOpen(r + 1, c)) {
            int pos2 = xyto1D(r + 1, c);
            set.union(pos, pos2);
            setNoBackWash.union(pos, pos2);
        }
        if ((r - 1) >= 0 && isOpen(r - 1, c)) {
            int pos2 = xyto1D(r - 1, c);
            set.union(pos, pos2);
            setNoBackWash.union(pos, pos2);
        }
        if ((c + 1) < gridSize && isOpen(r, c + 1)) {
            int pos2 = xyto1D(r, c + 1);
            set.union(pos, pos2);
            setNoBackWash.union(pos, pos2);
        }
        if ((c - 1) >= 0 && isOpen(r, c - 1)) {
            int pos2 = xyto1D(r, c - 1);
            set.union(pos, pos2);
            setNoBackWash.union(pos, pos2);
        }
        if (r == 0) {
            set.union(pos, topSite);
            setNoBackWash.union(pos, topSite);
        }
        if (r == gridSize - 1) {
            set.union(pos, bottomSite);
        }
    }
}
