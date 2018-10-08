package lab14;

import lab14lib.Generator;

/**
 * CS61B Lab 14: Fractal Sound
 * @author: Vinay Maruri, vmaruri1@berkeley.edu
 */
public class AcceleratingSawToothGenerator implements Generator {
    private double period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int x, double z) {
        this.period = x;
        this.state = 0;
        this.factor = z;
    }
    /**
     * method to generate values for our wave visualization
     * here, we want the slope of the waves to vary, hence we modify period by a new factor
     * and round the "period" of the wave downwards
     */
    @Override
    public double next() {
        this.state += 1;
        if (this.state % this.period == 0) {
            this.period = this.period * this.factor;
            this.period = Math.floor(this.period);
            this.state = 0;
        }
        return normalize(this.state);
    }
    public double normalize(int y) {
        double toreturn = ((y % this.period) * 2/(this.period - 1)) - 1;
        return toreturn;
    }
}
