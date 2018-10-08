package lab14;

import lab14lib.Generator;

/**
 * CS61B Lab 14: Fractal Sound
 * @author: Vinay Maruri, vmaruri1@berkeley.edu
 */

public class StrangeBitwiseGenerator implements Generator {
    private double period;
    private int state;

    public StrangeBitwiseGenerator(double x) {
        this.period = x;
        this.state = 0;
    }
    @Override
    /**
     * method to generate values for our wave visualization.
     * here we are doing bitwise operations to generate values
     * NOTE: to be finished later
     */
    public double next() {
        return 0.0;
    }
    private double normalize(int y) {
        return 1.0;
    }
}
