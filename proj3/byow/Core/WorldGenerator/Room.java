package byow.Core.WorldGenerator;

import byow.Core.Constants.*;
import byow.TileEngine.TETile;

import java.util.List;
import java.util.Set;

/**
 * A room in the world
 */
public class Room implements Component {

    private Point pLB; // left DOWN point of the room, not its wall
    private Point pRT; // right UP point of the room, not its wall

    public Room(Point lb, Point rt) {
        pLB = lb;
        pRT = rt;
    }


    // from pLB to pRT
    @Override
    public void generate(Set<Point> floorSet, List<Point> floorList, Set<Point> wallSet, TETile[][] world) {
        addFloors(floorSet, floorList);
        addWalls(wallSet);
    }

    // a bit cumbersome
    //@Override
    private void addFloors(Set<Point> floorSet, List<Point> floorList) {
        for (int i = pLB.x(); i <= pRT.x(); i += 1) {
            for (int j = pLB.y(); j <= pRT.y(); j += 1) {
                floorSet.add(new Point(i, j));
                floorList.add(new Point(i, j));
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
        boolean pXIncluded = (!point.dirToMeX(pLB).equals(X_DIRECTION.RIGHT)) && (!point.dirToMeX(pRT).equals(X_DIRECTION.LEFT));
        boolean pYIncluded = (!point.dirToMeY(pLB).equals(Y_DIRECTION.UP)) && (!point.dirToMeY(pRT).equals(Y_DIRECTION.DOWN));

        boolean xLBCloser = Math.abs(point.distToMeX(pLB)) <= Math.abs(point.distToMeX(pRT));
        boolean yLBCloser = Math.abs(point.distToMeY(pLB)) <= Math.abs(point.distToMeY(pRT));

        int xRes = xLBCloser? pLB.x() : pRT.x();
        int yRes = yLBCloser? pLB.y() : pRT.y();

        int a = point.dirToMeX(pLB).value();
        int b = point.dirToMeX(pRT).value();

        xRes = pXIncluded? point.x() : xRes;
        yRes = pYIncluded? point.y() : yRes;

        return new Point(xRes, yRes);
    }

}
