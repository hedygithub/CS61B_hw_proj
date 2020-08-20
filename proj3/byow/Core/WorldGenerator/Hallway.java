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

    public Hallway(int xb, int yb, int xt, int yt, boolean xFirst) {
        pB = new Point(xb, yb);
        pT = new Point(xt, yt);
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

        int xB = pB.x();
        int xT = pT.x();
        int yB = pB.y();
        int yT = pT.y();

        int xDirection = (int)Math.signum(xT-xB);
        int yDirection = (int)Math.signum(yT-yB);

        // in these 2 case it's the closest perpendicular point on the edge
        if (xDirection == 0 || !xFirst) {
            yB += yDirection;
            yDirection = (int)Math.signum(yT-yB);
        }
        else if (yDirection == 0 || xFirst) {
            xB += xDirection;
            xDirection = (int)Math.signum(xT-xB);
        }

        if (xFirst) {
            // starting at pB(xB, yB), go left or right, may ending at (xT, yB)
            for (int i = xB; i != xT; i += xDirection) {
                point = new Point(i, yB);
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                wallSet.add(new Point(i, yB - 1));
                wallSet.add(new Point(i, yB + 1));
            }

            // walls at the corner (xT, yB)
            wallSet.add(new Point(xT, yB - yDirection));
            wallSet.add(new Point(xT + 1, yB - yDirection));
            wallSet.add(new Point(xT - 1, yB - yDirection));

            // starting at (xT, yB), go bottom or top, may ending at pT(xT, yT)
            for (int j = yB; j != yT; j += yDirection) {
                point = new Point(xT, j);
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                point = new Point(xT - 1, j);
                wallSet.add(point);
                point = new Point(xT + 1, j);
                wallSet.add(point);
            }
        } else {
            // starting at pB(xB, yB), go bottom or top, may ending at (xB, yT)
            for (int j = yB; j != yT; j += yDirection) {
                point = new Point(xB, j);
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                point = new Point(xB - 1, j);
                wallSet.add(point);
                point = new Point(xB + 1, j);
                wallSet.add(point);
            }

            // walls at the corner (xB, yT)
            wallSet.add(new Point(xB - xDirection, yT));
            wallSet.add(new Point(xB - xDirection, yT + 1));
            wallSet.add(new Point(xB - xDirection, yT - 1));


            // starting at (xB, yT), go left or right, may ending at pT(xT, yT)
            for (int i = xB; i != xT; i += xDirection) {
                point = new Point(i, yT);
                if (floorSet.contains(point)) {
                    return;
                }
                floorSet.add(point);
                wallSet.add(new Point(i, yT - 1));
                wallSet.add(new Point(i, yT + 1));
            }
        }
    }
}
