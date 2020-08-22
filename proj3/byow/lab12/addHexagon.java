package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class addHexagon {
    private TETile basicTile;
    private Boolean randomColor;

    private static final long SEED = System.currentTimeMillis();
    private static final Random RANDOM = new Random(SEED);

    private static final int DR = 50;
    private static final int DG = 50;
    private static final int DB = 50;

    /**
     * add Hexagon with a specific tile.
     */
    public addHexagon(TETile tile, Boolean randomColor) {
        this.basicTile = tile;
        this.randomColor = randomColor;
    }

    /**
     * add Hexagon with RANDOM tiles.
     */
    public addHexagon(Boolean randomColor) {
        this.basicTile = randomTile();
        this.randomColor = randomColor;
    }

    /**
     * add Hexagon.
     */
    public TETile[][] addHexagon(TETile[][] world, int xLB, int yLB, int sideLength) {
        world = addQuadrangle(world, xLB,yLB, sideLength, 1);
        world = addQuadrangle(world, xLB,yLB + sideLength * 2 - 1, sideLength, -1);
        return world;
    }

    /**
     * add addQuadrangle.
     * @param xSL: left point at short side.
     * @param ySL: left point at short side.
     * @param sideLength: short side length
     * @param direction: -1 from up to DOWN; 1 from DOWN to up.
     */
    private TETile[][] addQuadrangle(TETile[][] world, int xSL, int ySL, int sideLength, int direction) {
        for(int j = 0; j < sideLength; j ++) {
            world = addLine(world, xSL - j, ySL + j * direction, sideLength + j * 2);
        }
        return world;
    }

    /**
     * add Line.
     */
    private TETile[][] addLine(TETile[][] world, int xL, int y, int lineLength) {
        for(int i = xL; i < xL + lineLength; i++) {
            if (randomColor) {
                world[i][y] = TETile.colorVariant(basicTile, DR, DG, DB, RANDOM);
            } else {
                world[i][y] = basicTile;
            }
        }
        return world;
    }

    /**
     * Picks a RANDOM tile.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.SAND;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    //testing
    public static void main(String[] args) {
        int WIDTH = 50;
        int HEIGHT = 50;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon hexagon = new addHexagon(true);
        world = hexagon.addHexagon(world, 20,0,5);

        ter.renderFrame(world);
    }

}
