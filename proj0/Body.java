

/*
    A class that implements the body in a universe
    Author: Yuchen Liu
 */
public class Body {
    /*
        Constants
     */
    static final double G = 6.67e-11;
    static final String imgFolder = "images/";

    /*
        Variables
     */

    public double xxPos; // Its current x position
    public double yyPos; // Its current y position
    public double xxVel; // Its current velocity in the x direction
    public double yyVel; // Its current velocity in the y direction
    public double mass; // Its mass
    public String imgFileName; // The name of the file that corresponds to the image that depicts the body

    // NOTE: the filename should be the FULL relative path from the master directory (which may contain subfolders)

    /*
        Constructors
     */

    public Body(double xP, double yP, double xV,
                double yV, double m, String img) {
        xxPos       = xP;
        yyPos       = yP;
        xxVel       = xV;
        yyVel       = yV;
        mass        = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos       = b.xxPos;
        yyPos       = b.yyPos;
        xxVel       = b.xxVel;
        yyVel       = b.yyVel;
        mass        = b.mass;
        imgFileName = b.imgFileName;

    }

    /*
        Function Methods
     */

    // calc the distance to the supplied body
    public double calcDistance(Body b){
        return Math.sqrt(Math.pow(xxPos - b.xxPos, 2.0)  + Math.pow(yyPos - b.yyPos, 2.0));
    }

    // calc the force exerted by the supplied body
    public double calcForceExertedBy(Body b){
        double distance = calcDistance(b);

        return G * mass * b.mass / Math.pow(distance, 2.0);

    }

    // calc the force exerted by the supplied body in x direction
    public double calcForceExertedByX(Body b){
        double distance = calcDistance(b);
        double force = calcForceExertedBy(b);

        return force * (b.xxPos - xxPos) / distance;
    }

    // calc the force exerted by the supplied body in y direction
    public double calcForceExertedByY(Body b){
        double distance = calcDistance(b);
        double force = calcForceExertedBy(b);

        return force * (b.yyPos - yyPos) / distance;
    }

    // calc the net force exerted by an array of bodies in x direction
    public double calcNetForceExertedByX(Body[] bs){
        // let's just use a poor-man for-loop to calc the net...
        double sum = 0.0;
        for (int i = 0; i < bs.length; i++) {

            // only apply for the body other than self
            if (!bs[i].equals(this)) {
                sum += calcForceExertedByX(bs[i]);
            }

        }

        return sum;
    }

    // calc the net force exerted by an array of bodies in y direction
    public double calcNetForceExertedByY(Body[] bs){
        // let's just use a poor-man for-loop to calc the net...
        double sum = 0.0;
        for (int i = 0; i < bs.length; i++) {

            // only apply for the body other than self
            if (!bs[i].equals(this)) {
                sum += calcForceExertedByY(bs[i]);
            }

        }

        return sum;
    }

    // update the position of the body
    // by dt (in seconds), Xforce and Yforce (both in Newtons)
    public void update(double dt, double fX, double fY){

        // calc the accelerations
        double aX = fX / mass;
        double aY = fY / mass;

        // update the velocities
        xxVel = xxVel + dt * aX;
        yyVel = yyVel + dt * aY;

        // update the positions
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;

    }

    // draw the image in its position
    public void draw() {
        StdDraw.picture(xxPos, yyPos, imgFolder + imgFileName);
    }
}
