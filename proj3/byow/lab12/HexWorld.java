package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private int hexSideCounts;
    private int hexSideLength;
    private static final int EDGE = 1;
    private int WIDTH;
    private int HEIGHT;
    private TETile[][] world;

    private HexWorld(int hexSideCounts, int hexSideLength) {
        this.hexSideCounts = hexSideCounts;
        this.hexSideLength = hexSideLength;
        WIDTH = (hexSideCounts - 1) * hexSideLength + hexSideCounts * (3 * hexSideLength - 2) + EDGE * 2;
        HEIGHT = 2 * (hexSideCounts * 2 - 1) * hexSideLength + EDGE * 2;
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private void addHexagons() {
        for (int c = hexSideCounts; c > 0; c--) {
            addCircle(c);
        }
    }

    /**
     * add addCircle.
     * @param hexSideOrder: bigger, outsider
     */
    private void addCircle(int hexSideOrder) {
        int xL = hexSideLength + (hexSideLength * 2 - 1) * (hexSideCounts - hexSideOrder) + EDGE;
        int xM = xL + (hexSideLength * 2 - 1) * (hexSideOrder - 1);
        int xR = xM + (hexSideLength * 2 - 1) * (hexSideOrder - 1);

        int yB = hexSideLength * 2 * (hexSideCounts - hexSideOrder) + EDGE;
        int yMB = yB + hexSideLength * (hexSideOrder - 1);
        int yMT = yMB + hexSideLength * (hexSideOrder - 1) * 2;
        int yT = yMT + hexSideLength * (hexSideOrder - 1);

        //clockwise
        addLine(xM, yB, hexSideOrder, -(hexSideLength * 2 - 1), hexSideLength);
        addLine(xL, yMB, hexSideOrder, 0, hexSideLength * 2);
        addLine(xL, yMT, hexSideOrder, (hexSideLength * 2 - 1), hexSideLength);
        addLine(xM, yT, hexSideOrder, (hexSideLength * 2 - 1), -hexSideLength);
        addLine(xR, yMT, hexSideOrder, 0, -hexSideLength * 2);
        addLine(xR, yMB, hexSideOrder, -(hexSideLength * 2 - 1), -hexSideLength);
    }

    private void addLine(int x, int y, int hexSideOrder, int nextX, int nextY) {
        if (hexSideOrder == 1) {
            addHexagon hexagon = new addHexagon(true);
            world = hexagon.addHexagon(world, x, y, hexSideLength);
        } else {
            for (int c = 0; c < hexSideOrder - 1 ; c++) {
                addHexagon hexagon = new addHexagon(true);
                world = hexagon.addHexagon(world, x + nextX * c, y + nextY * c, hexSideLength);
            }
        }
    }

    public static void main(String[] args)  {
        HexWorld hexWorld = new HexWorld(3,4);
        hexWorld.addHexagons();

        TERenderer ter = new TERenderer();
        ter.initialize(hexWorld.WIDTH, hexWorld.HEIGHT);
        ter.renderFrame(hexWorld.world);
    }
}
