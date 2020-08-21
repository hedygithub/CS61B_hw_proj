package byow.Core.WorldGenerator;

import byow.Core.Constants.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Set;

import static byow.Core.Constants.HEIGHT;
import static byow.Core.Constants.WIDTH;

/**
 * A class holding a Point with Integer coordinates
 */
public class Point {
    private int x;
    private int y;
    private TETile tile = Tileset.NOTHING;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, TETile tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public void changeTile (TETile newTile) {
        this.tile = newTile;
    }

    public int x() {
        return x;
    }

    public int y(){
        return y;
    }

    public TETile tile() {
        return tile;
    }


    public Point up(TETile[][] world) {
        if (y + 1 >= HEIGHT) {
            return null;
        } else {
            return new Point(x, y + 1, world[x][y + 1]);
        }
    }

    public Point down(TETile[][] world) {
        if (y - 1 <= 0) {
            return null;
        } else {
            return new Point(x, y - 1, world[x][y - 1]);
        }
    }

    public Point right(TETile[][] world) {
        if (x + 1 >= WIDTH) {
            return null;
        } else {
            return new Point(x + 1, y, world[x + 1][y]);
        }
    }

    public Point left(TETile[][] world) {
        if (x - 1 <= 0) {
            return null;
        } else {
            return new Point(x - 1, y, world[x - 1][y]);
        }
    }

    public Set<TETile> neighborsTiles(TETile[][] world) {
        Set<TETile> tileSet = new HashSet();
        if (up(world) != null) {
            tileSet.add(up(world).tile());
        }
        if (down(world) != null) {
            tileSet.add(down(world).tile());
        }
        if (right(world) != null) {
            tileSet.add(right(world).tile());
        }
        if (left(world) != null) {
            tileSet.add(left(world).tile());
        }
        return tileSet;
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

    // move only one coordinate and create the new point -- duplicate with another move funciton
    public Point move(int s, boolean isVertical) {
        return (isVertical)? move(0,s) : move(s,0);
    }

    // move the point based on inputs and create the new point
    public Point move(int dx, int dy) {
        return new Point(x + dx, y + dy);
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


//    public static void main(String[] args) {
//        Point point = new Point(1,2);
//        System.out.println(point.x());
//    }
}
