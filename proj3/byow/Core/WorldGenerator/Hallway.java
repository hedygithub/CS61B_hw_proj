package byow.Core.WorldGenerator;

import edu.princeton.cs.algs4.Point2D;

import java.util.HashSet;
import java.util.Set;

/**
 * A hallway in the world
 */
public class Hallway implements Component {

    // 2 points of the hallway (not on the wall). Unclear relative positions.
    private Point2D pB;
    private Point2D pT;

    public Hallway(Point2D b, Point2D t) {
        pB = b;
        pT = t;
    }

    public Hallway(int xb, int yb, int xt, int yt) {
        pB = new Point2D(xb, yb);
        pT = new Point2D(xt, yt);
    }

    @Override
    public Point2D pivot() {
        return pB; // we pick bottom point
    }

    // from pB to pT: first go horizontally, then go vertically
    @Override
    public void generate(Set<Point2D> floorSet, Set<Point2D> wallSet) {
        Point2D point;

        int xB = (int)pB.x();
        int xT = (int)pT.x();
        int yB = (int)pB.y();
        int yT = (int)pT.y();

        int xDirection = (int)Math.signum(xT-xB);
        int yDirection = (int)Math.signum(yT-yB);

        // starting at pB, go left or right
        for (int i = xB; i != xT; i += xDirection) {
            point = new Point2D(i, yB);
            if (floorSet.contains(point)) {
                return;
            }
            floorSet.add(point);
            wallSet.add(new Point2D(i, yB - 1));
            wallSet.add(new Point2D(i, yB + 1));
        }

        // walls at the corner (xT, yB)
        wallSet.add(new Point2D(xT, yB - yDirection));
        wallSet.add(new Point2D(xT + 1, yB - yDirection));
        wallSet.add(new Point2D(xT - 1, yB - yDirection));

        // go bottom or top, ending at pRT
        for (int j = yB; j != yT; j += yDirection) {
            point = new Point2D(xT, j);
            if (floorSet.contains(point)) {
                return;
            }
            floorSet.add(point);
            point = new Point2D(xT - 1, j);
            wallSet.add(point);
            point = new Point2D(xT + 1, j);
            wallSet.add(point);
        }

        return;
    }
}
