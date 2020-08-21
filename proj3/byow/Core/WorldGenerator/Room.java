package byow.Core.WorldGenerator;

import byow.Core.Constants.*;

import java.util.Set;

/**
 * A room in the world
 */
public class Room implements Component {

    private Point pLB; // left bottom point of the room, not its wall
    private Point pRT; // right top point of the room, not its wall

    public Room(Point lb, Point rt) {
        pLB = lb;
        pRT = rt;
    }

    @Override
    public Point pivot() {
        return pLB; // we pick left bottom point
    }

    // from pLB to pRT
    @Override
    public void generate(Set<Point> floorSet, Set<Point> wallSet) {
        addFloors(floorSet);
        addWalls(wallSet);
    }

    // a bit cumbersome
    //@Override
    private void addFloors(Set<Point> floorSet) {

        for (int i = pLB.x(); i <= pRT.x(); i += 1) {
            for (int j = pLB.y(); j <= pRT.y(); j += 1) {
                floorSet.add(new Point(i, j));
            }
        }
    }

    //@Override
    private void addWalls(Set<Point> wallSet) {

        for (int i = pLB.x()-1; i <= pRT.x()+1; i++) {
            wallSet.add(new Point(i, pLB.y()-1));
            wallSet.add(new Point(i, pRT.y()+1));
        }

        for (int j = pLB.y()-1; j <= pRT.y()+1; j++) {
            wallSet.add(new Point(pLB.x()-1, j));
            wallSet.add(new Point(pRT.x()+1, j));
        }
    }

    // check if there is enough space for this component to be placed
    public boolean checkSpace(Set<Point> floorSet, Set<Point> wallSet) {

        for (int i = pLB.x(); i <= pRT.x(); i += 1) {
            for (int j = pLB.y(); j <= pRT.y(); j += 1) {
                Point point = new Point(i, j);
                if (floorSet.contains(point) || wallSet.contains(point)) {
                    return false;
                }
            }
        }
        return true;
    }

    // find the nearest point on the wall of the room given a point (assume it's outside the room)
    public Point findNearestPoint(Point point) {
        //boolean pXIncluded = point.x() >= pLB.x() && point.x() <= pRT.x();
        boolean pXIncluded = (point.compareX(pLB) != X_DIRECTION.LEFT) && (point.compareX(pRT) != X_DIRECTION.RIGHT);
        //boolean pYIncluded = point.y() >= pLB.y() && point.y() <= pRT.y();
        boolean pYIncluded = (point.compareY(pLB) != Y_DIRECTION.BOTTOM) && (point.compareY(pRT) != Y_DIRECTION.TOP);

        boolean xLBCloser = Math.abs(point.distX(pLB)) <= Math.abs(point.distX(pRT));
        boolean yLBCloser = Math.abs(point.distY(pLB)) <= Math.abs(point.distY(pRT));

        int xRes = xLBCloser? pLB.x() : pRT.x();
        int yRes = yLBCloser? pLB.y() : pRT.y();

        xRes = pXIncluded? point.x() : xRes;
        yRes = pYIncluded? point.y() : yRes;

        return new Point(xRes, yRes);
    }

}
