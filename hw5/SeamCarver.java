import edu.princeton.cs.algs4.Picture;

/**
 * Homework 5 CS61B
 * A program to "seam-carve" images
 * whereby the most-interesting features are preserved.
 *
 * @author: Vinay Maruri, vmaruri1@berkeley.edu
 * @version: 3.0
 */

public class SeamCarver {
    private Picture pic;
    private double[][] energy;
    private double[][] accum;

    /**
     * SeamCarver constructor
     * creates the picture to work on
     * as well as a running array of pixel energy
     *
     * @param picture
     */
    public SeamCarver(Picture picture) {
        pic = new Picture(picture);
        energy = new double[this.pic.width()][this.pic.height()];
        accum = new double[this.pic.width()][this.pic.height()];
    }
    // current picture

    /**
     * returns a copy of the current picture
     */
    public Picture picture() {
        return new Picture(this.pic);
    }
    // width of current picture

    /**
     * returns the width of current picture.
     */
    public int width() {
        return this.pic.width();
    }
    // height of current picture

    /**
     * returns height of current picture
     */
    public int height() {
        return this.pic.height();
    }
    // energy of pixel at column x and row y

    /**
     * calculates energy at a given row & column of picture
     * then stores result in my energy array for the picture
     *
     * @param x
     * @param y
     * @return
     */
    public double energy(int x, int y) {
        if (x < 0) {
            throw new IndexOutOfBoundsException("x is outside of picture dimensions");
        }
        if (x > this.pic.width() - 1) {
            throw new IndexOutOfBoundsException("x is outside of picture dimensions");
        }
        if (y < 0) {
            throw new IndexOutOfBoundsException("y is outside of picture dimensions");
        }
        if (y > this.pic.height() - 1) {
            throw new IndexOutOfBoundsException("y is outside of picture dimensions");
        }
        int origx = x;
        int origy = y;
        if (x + 1 > this.pic.width() - 1) {
            x = 0;
        } else {
            x = x + 1;
        }
        double red1 = this.pic.get(x, y).getRed();
        double blue1 = this.pic.get(x, y).getBlue();
        double green1 = this.pic.get(x, y).getGreen();

        x = origx;

        if (x - 1 < 0) {
            x = this.pic.width() - 1;
        } else {
            x = x - 1;
        }
        double red2 = this.pic.get(x, y).getRed();
        double blue2 = this.pic.get(x, y).getBlue();
        double green2 = this.pic.get(x, y).getGreen();

        x = origx;

        double redDiffx = red1 - red2;
        double greenDiffx = green1 - green2;
        double blueDiffx = blue1 - blue2;
        double xgrad = (redDiffx * redDiffx) + (greenDiffx * greenDiffx) + (blueDiffx * blueDiffx);

        if (y + 1 > this.pic.height() - 1) {
            y = 0;
        } else {
            y = y + 1;
        }
        double red3 = this.pic.get(x, y).getRed();
        double blue3 = this.pic.get(x, y).getBlue();
        double green3 = this.pic.get(x, y).getGreen();

        y = origy;

        if (y - 1 < 0) {
            y = this.pic.height() - 1;
        } else {
            y = y - 1;
        }
        double red4 = this.pic.get(x, y).getRed();
        double blue4 = this.pic.get(x, y).getBlue();
        double green4 = this.pic.get(x, y).getGreen();

        y = origy;
        x = origx;
        double redDiffy = red3 - red4;
        double greenDiffy = green3 - green4;
        double blueDiffy = blue3 - blue4;
        double ygrad = (redDiffy * redDiffy) + (greenDiffy * greenDiffy) + (blueDiffy * blueDiffy);

        double result = (xgrad) + (ygrad);
        return result;
    }
    // sequence of indices for horizontal seam

    /**
     * finds a horizontal "seam" to carve out of picture
     * i.e. least important continuous horizontal spots
     */
    public int[] findHorizontalSeam() {
        Picture invert = invert();
        SeamCarver sc = new SeamCarver(invert);
        return sc.findVerticalSeam();
    }
    private Picture invert() {
        Picture inv = new Picture(this.height(), this.width());
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                inv.set(j, i, this.pic.get(i, j));
            }
        }
        return inv;
    }
    // sequence of indices for vertical seam

    /**
     * finds a vertical "seam" to carve out of the picture
     * i.e. least important continuous vertical spots
     *
     * @return
     */
    private void accumset() {
        for (int z = 0; z < height(); z++) {
            for (int a = 0; a < width(); a++) {
                if (z == 0) {
                    this.accum[a][z] = energy(a, z);
                } else {
                    double small = 0.0;
                    if (valid(a - 1, z - 1) && valid(a, z - 1) && valid(a + 1, z - 1)) {
                        small = Math.min(this.energy[a - 1][z - 1], Math.min(this.energy[a][z - 1],
                                this.energy[a + 1][z - 1]));
                    } else if (valid(a + 1, z - 1)
                            && valid(a, z - 1)) {
                        small = Math.min(this.energy[a + 1][z - 1], this.energy[a][z - 1]);
                    } else if (valid(a - 1, z - 1)
                            && valid(a, z - 1)) {
                        small = Math.min(this.energy[a - 1][z - 1], this.energy[a][z - 1]);
                    } else {
                        small = this.energy[a][z - 1];
                    }
                    this.accum[a][z] = energy(a, z) + small;
                }
            }
        }
    }
    private void energyset() {
        for (int x = 0; x < width(); x++) {
            for (int y = 0; y < height(); y++) {
                this.energy[x][y] = energy(x, y);
            }
        }
    }
    public int[] findVerticalSeam() {
        int[] result = new int[height()];
        if (this.pic.width() == 1) {
            for (int i = 0; i < width(); i++) {
                result[i] = 0;
            }
            return result;
        }
        energyset();
        accumset();
        int xx = -1;
        double minenergi = Double.MAX_VALUE;
        for (int zz = 0; zz < width(); zz++) {
            if (accum[zz][height() - 1] < minenergi) {
                minenergi = accum[zz][height() - 1];
                xx = zz;
            }
        }
        result[height() - 1] = xx;
        int indice = height() - 2;
        for (int index = height() - 2; index >= 0; index--) {
            if (valid(xx + 1, index) && valid(xx - 1, index)) {
                if (accum[xx + 1][index] < accum[xx][index]
                        && accum[xx - 1][index] > accum[xx + 1][index]) {
                    xx++;
                    result[indice] = xx;
                    indice--;
                } else if (accum[xx][index] < accum[xx + 1][index]
                        && accum[xx][index] < accum[xx - 1][index]) {
                    result[indice] = xx;
                    indice--;
                } else {
                    xx--;
                    result[indice] = xx;
                    indice--;
                }
            } else if (valid(xx + 1, index)) {
                if (accum[xx + 1][index] < accum[xx][index]) {
                    xx++;
                    result[indice] = xx;
                    indice--;
                } else {
                    result[indice] = xx;
                    indice--;
                }
            } else if (valid(xx - 1, index)) {
                if (accum[xx - 1][index] < accum[xx][index]) {
                    xx--;
                    result[indice] = xx;
                    indice--;
                } else {
                    result[indice] = xx;
                    indice--;
                }
            } else {
                result[indice] = xx;
                indice--;
            }
        }
        return result;
    }
    private boolean valid(int x, int y) {
        return x >= 0 && y >= 0 && x < this.pic.width() && y < this.pic.height();
    }
    // remove horizontal seam from picture

    /**
     * removes a requested horizontal seam from current picture
     *
     * @param seam
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != (this.pic.width())) {
            throw new IllegalArgumentException("seam array is not the correct length");
        }
        for (int i = 0; i < (seam.length - 1); i++) {
            if ((seam[i + 1] - seam[i]) > 1 || (seam[i + 1] - seam[i]) < -1) {
                throw new IllegalArgumentException("seam array is not a valid seam");
            }
        }
        this.pic = SeamRemover.removeHorizontalSeam(this.pic, seam);
    }
    // remove vertical seam from picture

    /**
     * removes a requested vertical seam from current picture
     *
     * @param seam
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != (this.pic.height())) {
            throw new IllegalArgumentException("seam array is not the correct length");
        }
        for (int i = 0; i < (seam.length - 1); i++) {
            if ((seam[i + 1] - seam[i]) > 1 || (seam[i + 1] - seam[i]) < -1) {
                throw new IllegalArgumentException("seam array is not a valid seam");
            }
        }
        this.pic = SeamRemover.removeVerticalSeam(this.pic, seam);
    }
}
