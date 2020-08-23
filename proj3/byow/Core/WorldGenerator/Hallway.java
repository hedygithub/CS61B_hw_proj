package byow.Core.WorldGenerator;

import byow.Core.Constants.*;
import byow.TileEngine.TETile;

import java.util.List;
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
    // from pB to pT: first go horizontally, then go vertically
    public void generate(Set<Point> floorSet, List<Point> floorList, Set<Point> wallSet, TETile[][] world) {
//         System.out.println("near:" + pB.x() + "," + pB.y()  + "; xFirst:" + xFirst);

        // find the start point which should be a wall point
        X_DIRECTION xDirection = pB.dirToMeX(pT);
        Y_DIRECTION yDirection = pB.dirToMeY(pT);
        if (xDirection == X_DIRECTION.SAME || (!xFirst && yDirection != Y_DIRECTION.SAME)) {
            pB = pB.move(yDirection.value(), true);
            yDirection = pB.dirToMeY(pT);
        }
        else if (yDirection == Y_DIRECTION.SAME || (xFirst && xDirection != X_DIRECTION.SAME)) {
            pB = pB.move(xDirection.value(), false);
            xDirection = pB.dirToMeX(pT);
        }

//        System.out.println("begin:" + pB.x() + "," + pB.y() + "; target:" +  pT.x() + "," +  pT.y());
        // build the hallway
        if (xFirst) {
            // starting at pB(pB.x(), pB.y()), go left or right, may ending at (pT.x(), pB.y())
            addFloorsAndWalls(floorSet, floorList, wallSet, false, pB.y(), xDirection.value());

            // walls at the corner (pT.x(), pB.y())
            // add corner (ideally this should be another helper function, but probably not worth doing it)
            int cornerY = pB.y() - yDirection.value();
            wallSet.add(new Point(pT.x(), cornerY));
            wallSet.add(new Point(pT.x() + 1, cornerY));
            wallSet.add(new Point(pT.x() - 1, cornerY));

            // starting at (pT.x(), pB.y()), go DOWN or UP, may ending at pT(pT.x(), pT.y())
            addFloorsAndWalls(floorSet, floorList, wallSet, true, pT.x(), yDirection.value());

        } else {
            // starting at pB(pB.x(), pB.y()), go DOWN or UP, may ending at (pT.x(), pT.y())
            addFloorsAndWalls(floorSet, floorList, wallSet, true, pB.x(), yDirection.value());

            // walls at the corner (pB.x(), pT.y())
            // add corner (ideally this should be another helper function, but probably not worth doing it)
            int cornerX = pB.x() - xDirection.value();
            wallSet.add(new Point(cornerX, pT.y()));
            wallSet.add(new Point(cornerX, pT.y() + 1));
            wallSet.add(new Point(cornerX, pT.y() - 1));

            // starting at (pB.x(), pT.y()), go left or right, may ending at pT(pT.x(), pT.y())
            addFloorsAndWalls(floorSet, floorList, wallSet, false, pT.y(), xDirection.value());
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
     * @param valueOfFixedDim the level that is fixed when iterating
     * @param movingDir horizontal direction (in the case of goVertical = false), vertical direction (in the case of goVertical = true)
     *
     */
    private void addFloorsAndWalls(Set<Point> floorSet, List<Point> floorList, Set<Point> wallSet,
                                   boolean isVertical, int valueOfFixedDim, int movingDir) {
        int start = (isVertical)? pB.y(): pB.x();
        int end = (isVertical)? pT.y(): pT.x();
        int direction = movingDir;

        Point point;
        for (int i = start; i != end; i+= direction) {
            point = (isVertical)? new Point(valueOfFixedDim, i) : new Point(i, valueOfFixedDim);
            if (floorSet.contains(point)) {
                return;
            }
            floorSet.add(point);
            floorList.add(point);
            wallSet.add(point.move(-1, !isVertical));
            wallSet.add(point.move(1, !isVertical));
        }
    }
}
