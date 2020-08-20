package byow.Core.WorldGenerator;

import java.util.Set;

/**
 * A hallway in the world
 */
public class Hallway implements Component{

    // 2 points of the hallway (not on the wall). Unclear relative positions.
    // pB -> Begin Point, pT -> Target Point
    private Point<Integer> pB;
    private Point<Integer> pT;
    private boolean xFirst;

    public Hallway(Point<Integer> b, Point<Integer> t, boolean xFirst) {
        pB = b;
        pT = t;
        this.xFirst = xFirst;
    }

    @Override
    public Point<Integer> pivot() {
        return pB; // we pick bottom point
    }

    @Override
    // from pB to pT: first go horizontally, then go vertically
    public void generate(Set<Point<Integer>> floorSet, Set<Point<Integer>> wallSet) {
        Point<Integer> point;

        int xDirection = (int)Math.signum(pT.x()-pB.x());
        int yDirection = (int)Math.signum(pT.y()-pB.y());
        System.out.println("begin:" + pB.x() + "," + pB.y() + "; target:" +  pT.x() + "," +  pT.y() + "; xFirst:" + xFirst);

        // in these 2 case it's the closest perpendicular point on the edge
        if (xDirection == 0 || (!xFirst && yDirection != 0)) {
            pB = new Point<>(pB.x(), pB.y() + yDirection);
            yDirection = (int)Math.signum(pT.y()-pB.y());
        }
        else if (yDirection == 0 || (xFirst && xDirection !=0)) {
            pB = new Point<>(pB.x() + xDirection, pB.y());
            xDirection = (int)Math.signum(pT.x()-pB.x());
        }

        if (xFirst) {
            // starting at pB(pB.x(), pB.y()), go left or right, may ending at (pT.x(), pB.y())
            for (int i = pB.x(); i != pT.x(); i += xDirection) {
                point = new Point(i, pB.y());
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                wallSet.add(new Point(i, pB.y() - 1));
                wallSet.add(new Point(i, pB.y() + 1));
            }

            // walls at the corner (pT.x(), pB.y())
            wallSet.add(new Point(pT.x(), pB.y() - yDirection));
            wallSet.add(new Point(pT.x() + 1, pB.y() - yDirection));
            wallSet.add(new Point(pT.x() - 1, pB.y() - yDirection));

            // starting at (pT.x(), pB.y()), go bottom or top, may ending at pT(pT.x(), pT.y())
            for (int j = pB.y(); j != pT.y(); j += yDirection) {
                point = new Point(pT.x(), j);
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                point = new Point(pT.x() - 1, j);
                wallSet.add(point);
                point = new Point(pT.x() + 1, j);
                wallSet.add(point);
            }
        } else {
            // starting at pB(pB.x(), pB.y()), go bottom or top, may ending at (pB.x(), pT.y())
            for (int j = pB.y(); j != pT.y(); j += yDirection) {
                point = new Point(pB.x(), j);
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                point = new Point(pB.x() - 1, j);
                wallSet.add(point);
                point = new Point(pB.x() + 1, j);
                wallSet.add(point);
            }

            // walls at the corner (pB.x(), pT.y())
            wallSet.add(new Point(pB.x() - xDirection, pT.y()));
            wallSet.add(new Point(pB.x() - xDirection, pT.y() + 1));
            wallSet.add(new Point(pB.x() - xDirection, pT.y() - 1));


            // starting at (pB.x(), pT.y()), go left or right, may ending at pT(pT.x(), pT.y())
            for (int i = pB.x(); i != pT.x(); i += xDirection) {
                point = new Point(i, pT.y());
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                wallSet.add(new Point(i, pT.y() - 1));
                wallSet.add(new Point(i, pT.y() + 1));
            }
        }
    }
}
