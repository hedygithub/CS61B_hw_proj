package byow.Core.WorldGenerator;

import byow.Core.Constants.*;

import static byow.Core.Constants.WIDTH;

/**
 * A class holding a Point with Integer coordinates
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    //not applicable for non-number
    @Override
    public boolean equals(Object o) {
        if (this == null && o == null) {
            return true;
        } else if (this == null || o == null) {
            return false;
        } else if (getClass() == o.getClass()) {
            Point pO = (Point) o;
            if (x == pO.x() && y == pO.y()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //not applicable for non-number
    @Override
    public int hashCode() {
        return x * WIDTH + y;
    }

    // relative position and distance of this point compared to p
    public int distX(Point p) {
        return p.x - this.x;
    }

    public int distY(Point p) {
        return p.y - this.y;
    }

    public X_DIRECTION compareX(Point p) {
        int dist = distX(p);
        return (dist == 0)? X_DIRECTION.SAME: ((dist > 0)? X_DIRECTION.RIGHT: X_DIRECTION.LEFT);
    }

    public Y_DIRECTION compareY(Point p) {
        int dist = distY(p);
        return (dist == 0)? Y_DIRECTION.SAME: ((dist > 0)? Y_DIRECTION.TOP: Y_DIRECTION.BOTTOM);
    }

    // move only one coordinate and create the new point
    public Point move(int s, boolean isVertical) {
        return (isVertical)? move(0,s) : move(s,0);
    }

    // move the point based on inputs and create the new point
    public Point move(int dx, int dy) {
        return new Point(x + dx, y + dy);
    }


//    public static void main(String[] args) {
//        Point point = new Point(1,2);
//        System.out.println(point.x());
//    }
}