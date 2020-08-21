package byow.Core.WorldGenerator;

import byow.Core.Constants.*;

import java.util.Set;

/**
 * A hallway in the world
 */
public class Hallway implements Component{

    // 2 points of the hallway (not on the wall). Unclear relative positions.
    // pB -> Begin Point, pT -> Target Point
    private Point pB;
    private Point pT;
    private boolean xFirst;

    public Hallway(Point b, Point t, boolean xFirst) {
        pB = b;
        pT = t;
        this.xFirst = xFirst;
    }

    @Override
    public Point pivot() {
        return pB; // we pick bottom point
    }

    @Override
    // from pB to pT: first go horizontally, then go vertically
    public void generate(Set<Point> floorSet, Set<Point> wallSet) {

//        int xDirection = (int)Math.signum(pT.x()-pB.x());
//        int yDirection = (int)Math.signum(pT.y()-pB.y());
        X_DIRECTION xDirection = pB.compareX(pT);
        Y_DIRECTION yDirection = pB.compareY(pT);
        // System.out.println("begin:" + pB.x() + "," + pB.y() + "; target:" +  pT.x() + "," +  pT.y() + "; xFirst:" + xFirst);

        // in these 2 case it's the closest perpendicular point on the edge
        if (xDirection == X_DIRECTION.SAME || (!xFirst && yDirection != Y_DIRECTION.SAME)) {
            pB = new Point(pB.x(), pB.y() + yDirection.value());
            // yDirection = (int)Math.signum(pT.y()-pB.y()); // why again?
            yDirection = pB.compareY(pT);
        }
        else if (yDirection == Y_DIRECTION.SAME || (xFirst && xDirection != X_DIRECTION.SAME)) {
            pB = new Point(pB.x() + xDirection.value(), pB.y());
            // xDirection = (int)Math.signum(pT.x()-pB.x()); // why again?
            xDirection = pB.compareX(pT);
        }

        if (xFirst) {
            // starting at pB(pB.x(), pB.y()), go left or right, may ending at (pT.x(), pB.y())
            addFloorsAndWalls(floorSet, wallSet, false, pB.y(), xDirection, yDirection);
//            for (int i = pB.x(); i != pT.x(); i += xDirection) {
//                point = new Point(i, pB.y());
//                if (floorSet.contains(point)) {
//                    return;
//                }
//                floorSet.add(point);
//                wallSet.add(new Point(i, pB.y() - 1));
//                wallSet.add(new Point(i, pB.y() + 1));
//            }

            // walls at the corner (pT.x(), pB.y())
            // add corner (ideally this should be another helper function, but probably not worth doing it)
            int cornerY = pB.y() - yDirection.value();
            wallSet.add(new Point(pT.x(), cornerY));
            wallSet.add(new Point(pT.x() + 1, cornerY));
            wallSet.add(new Point(pT.x() - 1, cornerY));

            // starting at (pT.x(), pB.y()), go bottom or top, may ending at pT(pT.x(), pT.y())
            addFloorsAndWalls(floorSet, wallSet, true, pT.x(), xDirection, yDirection);
//            for (int j = pB.y(); j != pT.y(); j += yDirection) {
//                point = new Point(pT.x(), j);
//                if (floorSet.contains(point)) {
//                    return;
//                }
//                floorSet.add(point);
//                point = new Point(pT.x() - 1, j);
//                wallSet.add(point);
//                point = new Point(pT.x() + 1, j);
//                wallSet.add(point);
//            }
        } else {
            // starting at pB(pB.x(), pB.y()), go bottom or top, may ending at (pT.x(), pT.y())
            addFloorsAndWalls(floorSet, wallSet, true, pB.x(), xDirection, yDirection);
//            for (int j = pB.y(); j != pT.y(); j += yDirection) {
//                point = new Point(pB.x(), j);
//                if (floorSet.contains(point)) {
//                    return;
//                }
//                floorSet.add(point);
//                point = new Point(pB.x() - 1, j);
//                wallSet.add(point);
//                point = new Point(pB.x() + 1, j);
//                wallSet.add(point);
//            }

            // walls at the corner (pB.x(), pT.y())
            // add corner (ideally this should be another helper function, but probably not worth doing it)
            int cornerX = pB.x() - xDirection.value();
            wallSet.add(new Point(cornerX, pT.y()));
            wallSet.add(new Point(cornerX, pT.y() + 1));
            wallSet.add(new Point(cornerX, pT.y() - 1));

            // starting at (pB.x(), pT.y()), go left or right, may ending at pT(pT.x(), pT.y())
            addFloorsAndWalls(floorSet, wallSet, false, pT.y(), xDirection, yDirection);
//            for (int i = pB.x(); i != pT.x(); i += xDirection) {
//                point = new Point(i, pT.y());
//                if (floorSet.contains(point)) {
//                    return;
//                }
//                floorSet.add(point);
//                wallSet.add(new Point(i, pT.y() - 1));
//                wallSet.add(new Point(i, pT.y() + 1));
//            }
        }
    }

    /**
     * Add floors and walls under 4 cases
     * 1) start at point pB and move vertically (goVertical = true, atTarget = false)
     * 2) start at point pB and move horizontally (goVertical = false, atTarget = false)
     * 3) (possibly) end at point pT and move vertically (goVertical = true, atTarget = true)
     * 4) (possibly) end at point pT and move horizontally (goVertical = false, atTarget = true)
     * @param floorSet the set of Points holding floors
     * @param wallSet the set of Points holding walls
     * @param isVertical determine if we go vertically or horizontally
     * @param level the level that is fixed when iterating
     * @param xDir horizontal direction (in the case of goVertical = false)
     * @param yDir vertical direction (in the case of goVertical = true)
     *
     */
    private void addFloorsAndWalls(Set<Point> floorSet, Set<Point> wallSet,
                                   boolean isVertical, int level,
                                   X_DIRECTION xDir, Y_DIRECTION yDir) {
        int start = (isVertical)? pB.y(): pB.x();
        int end = (isVertical)? pT.y(): pT.x();
        int direction = (isVertical)? yDir.value(): xDir.value();

        Point point;
        for (int i = start; i != end; i+= direction) {
            point = (isVertical)? new Point(level, i) : new Point(i, level);
            if (floorSet.contains(point)) {
                return;
            }
            floorSet.add(point);
            wallSet.add(point.move(-1, !isVertical));
            wallSet.add(point.move(1, !isVertical));

        }

    }
}
