package byow.Core.WorldGenerator;

import byow.Core.Constants;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.Random;

public class Avatar {
    public Point aP;

    public Avatar(List<Point> floorList, Random rng) {
        aP = generate(floorList, rng);
    }

    private static Point generate(List<Point> floorList, Random rng) {
        int index = rng.nextInt(floorList.size());
        Point avatarPoint = floorList.get(index);
        return avatarPoint;
    }

    public void move(TETile[][] world, Constants.DIRECTION moveDir) {
        TETile towardTile = aP.nbhTile(world, moveDir);
        if (towardTile == null || towardTile == Tileset.WALL || towardTile == Tileset.NOTHING) {
        } else if (towardTile == Tileset.LOCKED_DOOR) {
            aP = aP.move(moveDir);
        } else {
            aP = aP.move(moveDir);
        }
    }
}
