package byow.Core;

import byow.Core.WorldGenerator.WorldGenerator;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;

import java.util.*;

import static byow.Core.Constants.*;

public class Engine {
    TERenderer ter = new TERenderer();

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        // TODO
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        long Seed = 1234589340;
        WorldGenerator wg = new WorldGenerator(Seed);
        wg.generate();
        TETile[][] finalWorldFrame = wg.World();

//        Random Random = new Random(Seed);
//
//        TETile[][] finalWorldFrame = initialWorld();
//        Set<Point2D> floorSet = new HashSet<>();
//        Set<Point2D> wallSet = new HashSet<>();
//
//        int overlap = 0;
//
//        //build first room + wall +connection point
//        Map<String, Integer> roomInfo = randomRoom(Random);
//        floorSet = addRoom(floorSet, roomInfo.get("xLB"), roomInfo.get("yLB"), roomInfo.get("xRT"), roomInfo.get("yRT"));
//        wallSet = addRoomWall(wallSet, roomInfo.get("xLB") - 1, roomInfo.get("yLB") - 1, roomInfo.get("xRT") + 1, roomInfo.get("yRT") + 1);
//        Point2D connectedPoint = new Point2D(roomInfo.get("xLB"), roomInfo.get("yLB"));
//
//        while (overlap < OVERLAP_LIMIT) {
//            roomInfo = randomRoom(Random);
//            if (enoughSpaceForRoom(floorSet, wallSet, roomInfo.get("xLB"), roomInfo.get("yLB"), roomInfo.get("xRT"), roomInfo.get("yRT"))) {
//                floorSet = addRoom(floorSet, roomInfo.get("xLB"), roomInfo.get("yLB"), roomInfo.get("xRT"), roomInfo.get("yRT"));
//                wallSet = addRoomWall(wallSet, roomInfo.get("xLB") - 1, roomInfo.get("yLB") - 1, roomInfo.get("xRT") + 1, roomInfo.get("yRT") + 1);
//
//                Point2D startPoint = findNearestPoint(roomInfo.get("xLB"), roomInfo.get("yLB"), roomInfo.get("xRT"), roomInfo.get("yRT"), (int)connectedPoint.x(), (int)connectedPoint.y());
//                Set<Point2D>[] bothSet = addHallway(floorSet, wallSet, (int)startPoint.x(), (int)startPoint.y(), (int)connectedPoint.x(), (int)connectedPoint.y());
////                System.out.println(((int)startPoint.x() +","+ (int)startPoint.y()+"  "+(int)connectedPoint.x()+","+(int)connectedPoint.y()));
//                floorSet = bothSet[0];
//                wallSet = bothSet[1];
//                connectedPoint = randomPoint(floorSet, Random);
//            } else {
//                overlap += 1;
//            }
//        }
//
//        finalWorldFrame = fillTile(finalWorldFrame, wallSet, Tileset.WALL);
//        finalWorldFrame = fillTile(finalWorldFrame, floorSet, Tileset.FLOOR);

        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

//    private Point2D randomPoint(Set floorSet, Random Random) {
//        int index = Random.nextInt(floorSet.size());
//        ArrayList<Point2D> floorArray = new ArrayList<>(floorSet.size());
//        floorArray.addAll(floorSet);
//        return floorArray.get(index);
//    };
//
//    private Point2D findNearestPoint(int xLB, int yLB, int xRT, int yRT, int connectedX, int connectedY) {
//        int nearX;
//        int nearY;
//        if (Math.abs(yLB - connectedY) <= Math.abs(yRT - connectedY)) {
//            nearY = yLB;
//        } else {
//            nearY = yRT;
//        }
//
//        if (xLB <= connectedX && xRT >= connectedX) {
//            nearX = connectedX;
//            nearY += (connectedY - nearY) / Math.abs(connectedY -nearY);
//        } else if (Math.abs(xLB - connectedX) <= Math.abs(xRT - connectedX)) {
//            nearX = xLB;
//        } else {
//            nearX = xRT;
//        }
//
//        if (nearX < connectedX) {
//            nearX += 1;
//        } else if (nearX > connectedX){
//            nearX -= 1;
//        }
//
//        return new Point2D(nearX, nearY);
//    }
//
//    //build hallway first x---, then y|
//    private Set<Point2D>[] addHallway(Set floorSet, Set wallSet, int x, int y, int connectedX, int connectedY) {
//        Set<Point2D>[] bothSet = new HashSet[2];
//        Point2D point;
//        int xDirection = 0;
//        int yDirection = 0;
//        if (connectedX != x) {
//            xDirection = (connectedX - x) / Math.abs(connectedX -x);
//        }
//        if (connectedY != y) {
//            yDirection = (connectedY - y) / Math.abs(connectedY - y);
//        }
//
//        for (int i = x; i != connectedX; i += xDirection) {
//            point = new Point2D(i, y);
//            if (floorSet.contains(point)) {
//                bothSet[0] = floorSet;
//                bothSet[1] = wallSet;
//                return bothSet;
//            }
//
//            floorSet.add(point);
//            wallSet.add(new Point2D(i, y - 1));
//            wallSet.add(new Point2D(i, y + 1));
//        }
//
//        //wall at the corner, set +1, -1 to avoid xDirection = 0
//        wallSet.add(new Point2D(connectedX, y - yDirection));
//        wallSet.add(new Point2D(connectedX + 1, y - yDirection));
//        wallSet.add(new Point2D(connectedX - 1, y - yDirection));
//
//        for (int j = y; j != connectedY; j += yDirection) {
//            point = new Point2D(connectedX, j);
//            if (floorSet.contains(point)) {
//                bothSet[0] = floorSet;
//                bothSet[1] = wallSet;
//                return bothSet;
//            }
//            floorSet.add(point);
//            point = new Point2D(connectedX - 1, j);
//            wallSet.add(point);
//            point = new Point2D(connectedX + 1, j);
//            wallSet.add(point);
//        }
//        bothSet[0] = floorSet;
//        bothSet[1] = wallSet;
//        return bothSet;
//    };
//
//    private Map<String, Integer> randomRoom(Random Random) {
//        Map<String, Integer> roomInfo = new HashMap(4);
//        roomInfo.put("xLB", Random.nextInt(WIDTH - 3) + 1);
//        roomInfo.put("yLB", Random.nextInt(HEIGHT - 3) + 1);
//        roomInfo.put("xRT", Random.nextInt(Math.min(WIDTH - roomInfo.get("xLB") - 2, ROOM_WIDTH_LIMIT)) + roomInfo.get("xLB") + 1);
//        roomInfo.put("yRT", Random.nextInt(Math.min(HEIGHT - roomInfo.get("yLB")  - 2, ROOM_HEIGHT_LIMIT)) + roomInfo.get("yLB")  + 1);
//        return roomInfo;
//    }
//
//    private Set<Point2D> addRoom(Set floorSet, int x, int y, int xRT, int yRT) {
//        Point2D point;
//        for (int i = x; i <= xRT; i += 1) {
//            for (int j = y; j <= yRT; j += 1) {
//                point = new Point2D(i, j);
//                floorSet.add(point);
//            }
//        }
//        return floorSet;
//    };
//
//    private Set<Point2D> addRoomWall(Set wallSet, int x, int y, int xRT, int yRT) {
//        Point2D point;
//        for (int i = x; i <= xRT; i++) {
//            point = new Point2D(i, y);
//            wallSet.add(point);
//            point = new Point2D(i, yRT);
//            wallSet.add(point);
//        }
//        for (int j = y; j <= yRT; j++) {
//            point = new Point2D(x, j);
//            wallSet.add(point);
//            point = new Point2D(xRT, j);
//            wallSet.add(point);
//        }
//        return wallSet;
//    };
//
//    private TETile[][] fillTile(TETile[][] world, Set set, TETile tile) {
//        for (Object i : set) {
//            Point2D point = (Point2D) i;
//            world[(int)point.x()][(int)point.y()] = tile;
//        }
//        return world;
//    }
//
//
//
//    private boolean enoughSpaceForRoom(Set floorSet, Set wallSet, int x, int y, int xRT, int yRT) {
//        for (int i = x; i <= xRT; i += 1) {
//            for (int j = y; j <= yRT; j += 1) {
//                Point2D point = new Point2D(i, j);
//                if (floorSet.contains(point) || wallSet.contains(point)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    };
//
//    private TETile[][] initialWorld() {
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                world[x][y] = Tileset.NOTHING;
//            }
//        }
//        return world;
//    };


}
