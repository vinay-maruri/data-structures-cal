/** @author Terry Drinkwater <tsd.berkeley.edu>
 *  @author Vinay Maruri <vmaruri1@berkeley.edu>
 *  @version 1.0
 */

public class Planet {
    // The gravitational constant.
    public static double CONSTANT_G = 6.67e-11;
    
    // The position of the Planet object in the xy-plane.
    public double xxPos;
    public double yyPos;
    
    // The velocity of the Planet object in the xy-plane.
    public double xxVel;
    public double yyVel;
    
    // Mass and appearance.
    public double mass;
    public String imgFileName;
    
    /**
     * Constructs a Planet with an initial position, velocity, mass, and appearance.
     * @param double xP: Initial x-position.
     * @param double yP: Initial y-position.
     * @param double xV: Initial x-velocity.
     * @param double yV: Initial y-velocity.
     * @param double mass: Planet's mass.
     * @param String img: Filename of image in images folder.
     */
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    
    /**
     * Constructs a Planet based on another Planet object.
     * @param Planet p
     */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }
    
    /**
     * Calculates the distance between this Planet and another Planet p
     * from x and y coordinates.
     * @param Planet p
     * @return double: distance between this Planet and p.
     */
     public double calcDistance(Planet p) {
         double dy = yyPos - p.yyPos;
         double dx = xxPos - p.xxPos;
         return Math.sqrt(dy*dy + dx*dx);
    }
     
    /**
     * Takes another Planet p and calculates the magnitude of the pairwise force
     * exerted by these Planets on each other.
     * @param Planet p
     * @return double: force exerted by P on this Planet and vice versa.
     */
    public double calcForceExertedBy(Planet p) {
        double dist = this.calcDistance(p);
        return CONSTANT_G * this.mass * p.mass / (dist * dist);
    }
    
    /**
     * Takes another Planet p and calculates the force exerted
     * by that Planet on this one along the x-axis.
     * @param Planet p
     * @return double: x-wise force exerted by P on this planet.
     */
    public double calcForceExertedByX(Planet p) {
        double dist = this.calcDistance(p);
        double ttlForce = this.calcForceExertedBy(p);
        double dx = p.xxPos - this.xxPos;
        return ttlForce * dx / dist;
    }
    
    /**
     * Takes another Planet p and calculates the force exerted
     * by that Planet on this one along the y-axis.
     * @param Planet p
     * @return double: y-wise force exerted by P on this planet.
     */
    public double calcForceExertedByY(Planet p) {
        double dist = this.calcDistance(p);
        double ttlForce = this.calcForceExertedBy(p);
        double dy = p.yyPos - this.yyPos;
        return ttlForce * dy / dist;
    }
    /**
    * Takes an array of planets and calculates the net force on this
    * planet in the x direction.
    * @param Planet[] planetarray
    * @return totalforcex: Net x-wise force exerted by planetarray on this Planet.
    */
    public double calcNetForceExertedByX(Planet[] planetarray) {
        double totalforcex = 0.0;
        for (Planet p : planetarray) {
            if (p.equals(this)) {
                continue;
            }
            totalforcex = totalforcex + this.calcForceExertedByX(p);
        }
        return totalforcex;
    }
    /**
    * Takes an array of planets and calculates the net force on a
    * given planet in the y direction
    * @param Planet[] planetarray
    * @return totalforcey: Net y-wise force exerted by planetarray on this Planet.
    */
    public double calcNetForceExertedByY(Planet[] planetarray) {
        double totalforcey = 0.0;
        for (Planet p : planetarray) {
            if (p.equals(this)) {
                continue;
            }
            totalforcey = totalforcey + this.calcForceExertedByY(p);
        }
        return totalforcey;
    }
    
    /**
     * Updates the Planet's position and velocity based on experiencing forces
     * fX and fY for dt seconds.
     * @param dt: Time passed.
     * @param fX: Force applied along x-axis.
     * @param fY: Force applied along y-axis.
     */
    public void update(double dt, double fX, double fY) {
        double accelx = fX/this.mass;
        double accely = fY/this.mass;
        xxVel = xxVel + dt*accelx;
        yyVel = yyVel + dt*accely;
        xxPos = xxPos + dt*xxVel;
        yyPos = yyPos + dt*yyVel;
    }
    
    /**
     * Draws the Planet at its given position.
     */
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
