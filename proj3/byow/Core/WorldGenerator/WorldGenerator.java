package byow.Core.WorldGenerator;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

import static byow.Core.Constants.*;

/**
 * A world generator
 */
public class WorldGenerator {
    private static Random rng;
    private int width;
    private int height;

    private boolean finished;
    private TETile[][] world;
    private Set<Point> floorSet;
    private Set<Point> wallSet;

    /**
     * Constructors
     */

    public WorldGenerator(Long seed) {
        rng = new Random(seed);
        width = WIDTH;
        height = HEIGHT;
        init();
    }

    public WorldGenerator(Long seed, int w, int h) {
        rng = new Random(seed);
        width = w;
        height = h;
        init();
    }

    private void init() {
        world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        finished = false;
        floorSet = new HashSet<>();
        wallSet = new HashSet<>();

    }

    /**
     * Functions
     */

    public TETile[][] World() {
        if (!finished) {
            throw new IllegalStateException("The world is not yet generated! Please call generate() first.");
        }
        return world;
    }

    public void generate() {

        // build the first room
        Room room = designRoom();
        room.generate(floorSet, wallSet);
        Point pivot = room.pivot();

        // iteration to build rooms and hallways
        int overlap = 0;
        Hallway hallway;
        while (overlap < OVERLAP_LIMIT) {
            room = designRoom();
            if (room.checkSpace(floorSet, wallSet)) {
                room.generate(floorSet, wallSet);
                hallway = designHallway(room, pivot);
                hallway.generate(floorSet, wallSet);
                pivot = findRandomFloor();
            }
            else {
                overlap += 1;
            }
        }

        // IMPORTANT: always fill in walls before floors!
        fill(wallSet, Tileset.WALL);
        fill(floorSet, Tileset.FLOOR);

        finished = true;
    }

    /**
     * Internal helper functions
     */
    // make a Room randomly
    private Room designRoom() {
        int xLB = rng.nextInt((WIDTH - 1) - (1 + 1)) + 1; //-1,-(1 + 1), +1: will add 1, floor + wall in Right, wall int the Left
        int yLB = rng.nextInt((HEIGHT - 1) - (1 + 1)) + 1;
        int xRT = rng.nextInt(Math.min((WIDTH - xLB - 1) - 1, ROOM_WIDTH_LIMIT)) + xLB + 1; //-1, -1, +1: will add 1, wall int the Right`, at least 1 larger than xLB
        int yRT = rng.nextInt(Math.min((HEIGHT - yLB  - 1) - 1, ROOM_HEIGHT_LIMIT)) + yLB  + 1;
        return new Room(new Point(xLB, yLB), new Point(xRT, yRT));
    }

    // make a Hallway based on the given Room and a destination Point
    private Hallway designHallway(Room r, Point destination) {
        Point pivot = r.findNearestPoint(destination);
        // System.out.println("new pivot:" + pivot.toString() +"; destination:" + destination.toString());
        // form a hallway with these 2 pivots (should decide which is LB which is RT)
        return new Hallway(pivot, destination,rng.nextInt(2) == 0);
    }

    // TODO: find a better way to do it, better if without changing the overall structure
    private Point findRandomFloor() {
        // this is very slow
        int index = rng.nextInt(floorSet.size());
        int count = 0;
        for (Point p: floorSet) {
            if (count == index) {
                return p;
            }
            count += 1;
        }
        return null;
    }

    // fill in the tileset
    private void fill(Set<Point> set, TETile type) {
        for (Point p: set) {
            world[p.x()][p.y()] = type;
        }
    }

}
