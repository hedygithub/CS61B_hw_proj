package byow.Core.WorldGenerator;

import java.util.Set;

/**
 * A room in the world
 */
public class Room implements Component {

    private Point<Integer> pLB; // left bottom point of the room, not its wall
    private Point<Integer> pRT; // right top point of the room, not its wall

    public Room(Point lb, Point rt) {
        pLB = lb;
        pRT = rt;
    }

    public Room(int xlb, int ylb, int xrt, int yrt) {
        pLB = new Point(xlb, ylb);
        pRT = new Point(xrt, yrt);
    }

    @Override
    public Point pivot() {
        return pLB; // we pick left bottom point
    }

    // from pLB to pRT
    @Override
    public void generate(Set<Point<Integer>> floorSet, Set<Point<Integer>> wallSet) {
        addFloors(floorSet);
        addWalls(wallSet);
    }

    // a bit cumbersome
    //@Override
    private void addFloors(Set<Point<Integer>> floorSet) {
        Point point;
        int xLB = pLB.x();
        int xRT = pRT.x();
        int yLB = pLB.y();
        int yRT = pRT.y();
        for (int i = xLB; i <= xRT; i += 1) {
            for (int j = yLB; j <= yRT; j += 1) {
                point = new Point(i, j);
                floorSet.add(point);
            }
        }
    }

    //@Override
    private void addWalls(Set<Point<Integer>> wallSet) {
        Point<Integer> point;
        int xLB = pLB.x()-1;
        int xRT = pRT.x()+1;
        int yLB = pLB.y()-1;
        int yRT = pRT.y()+1;
        for (int i = xLB; i <= xRT; i++) {
            point = new Point(i, yLB);
            wallSet.add(point);
            point = new Point(i, yRT);
            wallSet.add(point);
        }
        for (int j = yLB; j <= yRT; j++) {
            point = new Point(xLB, j);
            wallSet.add(point);
            point = new Point(xRT, j);
            wallSet.add(point);
        }
    }

    // check if there is enough space for this component to be placed
    public boolean checkSpace(Set<Point<Integer>> floorSet, Set<Point<Integer>> wallSet) {
        int xLB = pLB.x();
        int xRT = pRT.x();
        int yLB = pLB.y();
        int yRT = pRT.y();
        for (int i = xLB; i <= xRT; i += 1) {
            for (int j = yLB; j <= yRT; j += 1) {
                Point point = new Point(i, j);
                if (floorSet.contains(point) || wallSet.contains(point)) {
                    return false;
                }
            }
        }
        return true;
    }

    // find the nearest point on the wall of the room given a point (assume it's outside the room)
    public Point<Integer> findNearestPoint(Point<Integer> point) {

        // note there are small adjustments because we need to move to the wall
        int xLB = pLB.x();
        int xRT = pRT.x();
        int yLB = pLB.y();
        int yRT = pRT.y();
        int x = point.x();
        int y = point.y();
        boolean xLBCloser = (Math.abs(x - xLB) <= Math.abs(x - xRT));
        boolean yLBCloser = (Math.abs(y - yLB) <= Math.abs(y - yRT));

        int xRes = xLBCloser ? xLB : xRT;
        int yRes = yLBCloser? yLB : yRT;

        return new Point(xRes, yRes);
    }

}
