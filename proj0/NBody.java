/** @author Terry Drinkwater <tsd.berkeley.edu>
 *  @author Vinay Maruri <vmaruri1@berkeley.edu>
 *  @version 1.0
 */

public class NBody {
    
    /**
     * Given a file with the appropriate format, returns the radius of the universe
     * being simulated.  Line 1 of the file has the # of Planets being simulated;
     * line 2 has the radius.
     * @param filename: the path and name of the file being read from.
     * @return radius: a double representing the radius of the universe.
     */
    public static double readRadius(String filename) {
        In in = new In(filename);
        in.readInt();
        double radius = in.readDouble();
        return radius;
    }
    
    /**
     * Given a file with the appropriate format, returns an array of Planets
     * corresponding to the planets in the file.  The number of Planets to be
     * read is given in line 1 of the file; the planets are given after line 2.
     * @param filename: the path and name of the file being read from.
     * @return Planets[]: an array of Planets in the universe.
     */
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int numPlanets = in.readInt();
        Planet[] Planets = new Planet[numPlanets];
        in.readDouble();
        for (int i = 0; i < numPlanets; i++) {
            // Initializes Planet[i] by reading one line of the file.
            Planets[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),
                                in.readDouble(),in.readDouble(),in.readString());
        }
        return Planets;
    }
    
    /**
     * Simulates a universe with *comment to be completed later*
     * @param args[0]: T, the duration of the simulation in (simulated) seconds.
     * @param args[1]: dt, the duration of each increment in the simulation.
     * @param args[2]: filename, the file to be read from.
     * @source https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html for
     * string to double conversion.
     */
    public static void main(String[] args) {
        // Store arguments from args in variables.
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        
        // Read the universe's parameters from filename.
        double radius = readRadius(filename);
        Planet[] Planets = readPlanets(filename);
        
        // Draw the universe's initial state.
        StdDraw.clear();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0,0, "images/starfield.jpg");
        StdDraw.show();
        StdDraw.pause(1000);
        for (Planet p : Planets) {
           p.draw();
        }
        
        // Step through the simulation, drawing each step.
        StdDraw.enableDoubleBuffering();
        for (double time = 0.0; time < T; time = time + dt) {
            double[] xForces = new double[Planets.length];
            double[] yForces = new double[Planets.length];
            for (int i = 0; i < Planets.length; i++) {
                xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
                yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
            }
            for (int x = 0; x < Planets.length; x++) {
                Planets[x].update(dt, xForces[x], yForces[x]);
            }
        StdDraw.picture(0, 0, "images/starfield.jpg");
        for (Planet p : Planets) {
               p.draw();
        }
        StdDraw.show();
        StdDraw.pause(10);
        }
        
        // Print the simulation's end state.
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
        StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        } 
    }
}
