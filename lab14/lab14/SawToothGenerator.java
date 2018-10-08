package lab14;

import lab14lib.Generator;

/**
 * CS61B Lab 14: Fractal Sound
 * @author: Vinay Maruri, vmaruri1@berkeley.edu
 */

public class SawToothGenerator implements Generator {
    private double period;
    private int state;

    public SawToothGenerator(double x) {
        this.period = x;
        this.state = 0;
    }
    @Override
    /**
     * method to generate values for our wave visualization.
     * period is constant, unlike acceleratingsawtoothgenerator
     */
    public double next() {
        this.state += 1;
        return normalize(this.state);
    }
    private double normalize(int y) {
        double toreturn = ((y % this.period) * 2/(this.period - 1)) - 1;
        return toreturn;
    }
}
