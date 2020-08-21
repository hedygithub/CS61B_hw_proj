package byow.Core.WorldGenerator;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Door {

    public static Point generate(Set<Point> wallSet, TETile[][] world, Random rng) {
        List<Point> qualifiedWallList = new ArrayList<>();
        for (Point wallPoint : wallSet) {
            Set<TETile> neighborsTiles = wallPoint.neighborsTiles(world);
            boolean isQualifiedWall = neighborsTiles.contains(Tileset.FLOOR) && neighborsTiles.contains(Tileset.NOTHING);
            if (isQualifiedWall) {
                qualifiedWallList.add(wallPoint);
            }
        }
        int index = rng.nextInt(qualifiedWallList.size());
        Point doorPoint = qualifiedWallList.get(index);
        wallSet.remove(doorPoint);
        world[doorPoint.x()][doorPoint.y()] = Tileset.LOCKED_DOOR;
        doorPoint.changeTile(Tileset.LOCKED_DOOR);
        return doorPoint;
    }

}
