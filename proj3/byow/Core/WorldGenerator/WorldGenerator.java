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
    private DIRECTION[] moveDirs;

    private boolean finished;
    private TETile[][] world;
    private Set<Point> floorSet;
    private List<Point> floorList;
    private Set<Point> wallSet;
    private Point door;
    private Avatar avatar;
    private boolean succeed;

    /**
     * Constructors
     */

    public WorldGenerator(Long seed) {
        rng = new Random(seed);
        width = WIDTH;
        height = HEIGHT;
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
        floorList = new ArrayList<>();
        wallSet = new HashSet<>();

    }

    /**
     * Functions
     */

    public TETile[][] world() {
        if (!finished) {
            throw new IllegalStateException("The world is not yet generated! Please call generate() first.");
        }
        return world;
    }

    public void generate() {
        // build the first room
        Room room = designRoom();
        room.generate(floorSet, floorList, wallSet,world);
        Point targetPoint = findRandomFloor();

        // iteration to build rooms and hallways
        int overlap = 0;
        Hallway hallway;
        while (overlap < OVERLAP_LIMIT) {
            room = designRoom();
            if (room.checkSpace(floorSet, wallSet)) {
                room.generate(floorSet, floorList, wallSet, world);
                hallway = designHallway(room, targetPoint);
                hallway.generate(floorSet, floorList, wallSet, world);
                targetPoint = findRandomFloor();
            }
            else {
                overlap += 1;
            }
        }

        // Clean wall Set

        // IMPORTANT: always fill in walls before floors!
        wallSet = removeFakeWall();
        worldFillBatch(floorSet, Tileset.FLOOR);
        worldFillBatch(wallSet, Tileset.WALL);

        door = Door.generate(wallSet, world, rng);
        worldFill(door, Tileset.LOCKED_DOOR);

        avatar = new Avatar(floorList, rng);
        worldFill(avatar.aP, Tileset.AVATAR);

        finished = true;
    }

    public boolean moveManySteps(List<DIRECTION> dirList) {
        if (!succeed) {
            worldFill(avatar.aP, Tileset.FLOOR);
            for (DIRECTION dir : dirList) {
                succeed = avatar.move(world, dir);
            }
            worldFill(avatar.aP, Tileset.AVATAR);
        }
        return succeed;
    }

    public boolean moveOneStep(DIRECTION dir) {
        if (!succeed) {
            worldFill(avatar.aP, Tileset.FLOOR);
            succeed = avatar.move(world, dir);
            worldFill(avatar.aP, Tileset.AVATAR);
        }
        return succeed;
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
    private Hallway designHallway(Room r, Point targetPoint) {
        Point startPoint = r.findNearestPoint(targetPoint);
        return new Hallway(startPoint, targetPoint,rng.nextInt(2) == 0);
    }

    private Point findRandomFloor() {
        // use floorList to accelerate it
        int index = rng.nextInt(floorList.size());
        return floorList.get(index);
    }

    private Set<Point> removeFakeWall() {
        Set<Point> newWallSet = new HashSet<>();
        for (Point wallPoint : wallSet) {
            if (!floorSet.contains(wallPoint)) {
                newWallSet.add(wallPoint);
            }
        }
        return newWallSet;
    }




    // fill in the tileset
    private void worldFillBatch(Set<Point> set, TETile tile) {
        for (Point p: set) {
            worldFill(p, tile);
        }
    }

    private void worldFill(Point p, TETile tile) {
        world[p.x()][p.y()] = tile;
    }

}
