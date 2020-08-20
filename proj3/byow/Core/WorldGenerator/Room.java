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
        for (int i = pLB.x(); i <= pRT.x(); i += 1) {
            for (int j = pLB.y(); j <= pRT.y(); j += 1) {
                point = new Point(i, j);
                floorSet.add(point);
            }
        }
    }

    //@Override
    private void addWalls(Set<Point<Integer>> wallSet) {
        Point<Integer> point;
        for (int i = pLB.x()-1; i <= pRT.x()+1; i++) {
            point = new Point(i, pLB.y()-1);
            wallSet.add(point);
            point = new Point(i, pRT.y()+1);
            wallSet.add(point);
        }
        for (int j = pLB.y()-1; j <= pRT.y()+1; j++) {
            point = new Point(pLB.x()-1, j);
            wallSet.add(point);
            point = new Point(pRT.x()+1, j);
            wallSet.add(point);
        }
    }

    // check if there is enough space for this component to be placed
    public boolean checkSpace(Set<Point<Integer>> floorSet, Set<Point<Integer>> wallSet) {
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
    public Point<Integer> findNearestPoint(Point<Integer> point) {
        boolean pXIncluded = point.x() >= pLB.x() && point.x() <= pRT.x();
        boolean pYIncluded = point.y() >= pLB.y() && point.y() <= pRT.y();

        boolean xLBCloser = (Math.abs(point.x() - pLB.x()) <= Math.abs(point.x() - pRT.x()));
        boolean yLBCloser = (Math.abs(point.y() - pLB.y()) <= Math.abs(point.y() - pRT.y()));

        int xRes = xLBCloser? pLB.x() : pRT.x();
        int yRes = yLBCloser? pLB.y() : pRT.y();

        xRes = pXIncluded? point.x() : xRes;
        yRes = pYIncluded? point.y() : yRes;

        return new Point(xRes, yRes);
    }

}
