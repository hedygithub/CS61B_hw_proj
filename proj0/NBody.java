public class NBody {
    /* Read the the universe's radius */
    public static double readRadius(String filename){
        In in = new In(filename);
        in.readInt();
        return (in.readDouble());
    }

    /* Read the five planets' body */
    public static Body[] readBodies(String filename){
        In in = new In(filename);
        int size = in.readInt();
        in.readDouble();
        int i = 0;
        Body[] Bodies = new Body[size];
        while (i < size){
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            Bodies[i] = new Body(xP, yP, xV, yV, m, img);
            i = i+1;
        }
        return Bodies;
    }
    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        //read universe's radius
        double radius = readRadius(filename);

        // Draw The Initial Universe State
        // set scales (see StdDrawDemo for details)
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();

        // draw the background
        /**Error when compile Nbody
         * error: unmappable character (0x93) for encoding GBK
         *The issue when using the StdDraw is caused by your system locale, try to change your system locale to US English,
         *or compile with the encoding flag:
         *javac -encoding UTF8 NBody.java*/
        StdDraw.picture(0,0, "images/starfield.jpg");

        Body[] bodies = readBodies(filename);
        for (int i=0; i < bodies.length; i++){
            bodies[i].draw();
        }

        // show the pic for 2000 milliseconds
        StdDraw.show();


        //Animation
        for (double time = 0; time <= T; time += dt){
            //calculate the forces
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            for (int i=0; i < bodies.length; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            //update the positions
            for (int i=0; i < bodies.length; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
            }

            //show the moving planets
            int milliseconds = 10;
            StdDraw.enableDoubleBuffering();
            StdDraw.clear();
            StdDraw.picture(0,0, "images/starfield.jpg");
            for (int i=0; i < bodies.length; i++){
                bodies[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(milliseconds);

        }
        //Print the Universe
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}
