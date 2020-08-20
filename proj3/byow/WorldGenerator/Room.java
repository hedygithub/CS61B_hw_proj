package byow.Core.WorldGenerator;

import edu.princeton.cs.algs4.Point2D;

import java.util.Set;

/**
 * A room in the world
 */
public class Room implements Component {

    private Point2D pLB; // left bottom point of the room, not its wall
    private Point2D pRT; // right top point of the room, not its wall

    public Room(Point2D lb, Point2D rt) {
        pLB = lb;
        pRT = rt;
    }

    public Room(int xlb, int ylb, int xrt, int yrt) {
        pLB = new Point2D(xlb, ylb);
        pRT = new Point2D(xrt, yrt);
    }

    @Override
    public Point2D pivot() {
        return pLB; // we pick left bottom point
    }

    // from pLB to pRT
    @Override
    public void generate(Set<Point2D> floorSet, Set<Point2D> wallSet) {
        addFloors(floorSet);
        addWalls(wallSet);
    }

    // a bit cumbersome
    //@Override
    private void addFloors(Set<Point2D> floorSet) {
        Point2D point;
        int xLB = (int)pLB.x();
        int xRT = (int)pRT.x();
        int yLB = (int)pLB.y();
        int yRT = (int)pRT.y();
        for (int i = xLB; i <= xRT; i += 1) {
            for (int j = yLB; j <= yRT; j += 1) {
                point = new Point2D(i, j);
                floorSet.add(point);
            }
        }
    }

    //@Override
    private void addWalls(Set<Point2D> wallSet) {
        Point2D point;
        int xLB = (int)pLB.x()-1;
        int xRT = (int)pRT.x()+1;
        int yLB = (int)pLB.y()-1;
        int yRT = (int)pRT.y()+1;
        for (int i = xLB; i <= xRT; i++) {
            point = new Point2D(i, yLB);
            wallSet.add(point);
            point = new Point2D(i, yRT);
            wallSet.add(point);
        }
        for (int j = yLB; j <= yRT; j++) {
            point = new Point2D(xLB, j);
            wallSet.add(point);
            point = new Point2D(xRT, j);
            wallSet.add(point);
        }
    }

    // check if there is enough space for this component to be placed
    public boolean checkSpace(Set<Point2D> floorSet, Set<Point2D> wallSet) {
        int xLB = (int)pLB.x();
        int xRT = (int)pRT.x();
        int yLB = (int)pLB.y();
        int yRT = (int)pRT.y();
        for (int i = xLB; i <= xRT; i += 1) {
            for (int j = yLB; j <= yRT; j += 1) {
                Point2D point = new Point2D(i, j);
                if (floorSet.contains(point) || wallSet.contains(point)) {
                    return false;
                }
            }
        }
        return true;
    }

    // find the nearest point on the wall of the room given a point (assume it's outside the room)
    public Point2D findNearestPoint(Point2D point) {

        // note there are small adjustments because we need to move to the wall
        int xLB = (int)pLB.x();
        int xRT = (int)pRT.x();
        int yLB = (int)pLB.y();
        int yRT = (int)pRT.y();
        int x = (int)point.x();
        int y = (int)point.y();
        int xRes = x, yRes = y;
        // pre-calc value
        boolean xLBCloser = (Math.abs(x - xLB) <= Math.abs(x - xRT));
        boolean yLBCloser = (Math.abs(y - yLB) <= Math.abs(y - yRT));

        // in these 2 case it's the closest perpendicular point on the edge
        if ((x - xLB) * (x - xRT) <= 0) {
            yRes = yLBCloser? yLB-1 : yRT+1;
        }
        else if ((y - yLB) * (y - yRT) <= 0) {
            xRes = xLBCloser? xLB-1 : xRT+1;
        }
        else {
            // it's completely outside the room. We can compare 4 corners
            // in this case we only adjust in x direction
            // because we go first horizontally when adding hallways
            xRes = xLBCloser? xLB-1 : xRT+1;
            yRes = yLBCloser? yLB : yRT;;
        }

        return new Point2D(xRes, yRes);
    }

//    public Point2D lb() {
//        return pLB;
//    }
//
//    public Point2D rt() {
//        return pRT;
//    }
}
