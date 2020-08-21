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
        if (input == null) {
            throw new IllegalArgumentException();
        } else {

            char firstChar = input.charAt(0);
            if (firstChar == 'L' || firstChar == 'l') {
                input = SAVED_INPUT + input.substring(1, input.length());
            }

            String seed = "";
            int actionCharPos = input.length();

            if (firstChar == 'N' || firstChar == 'n') {
                for (int i = 1; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if (c >= '0' && c <= '9') {
                        seed += c;
                    } else {
                        actionCharPos = i;
                        break;
                    }
                }
            }

            System.out.println(seed);

            long Seed = Integer.parseInt(seed);
            WorldGenerator wg = new WorldGenerator(Seed);
            wg.generate();
            TETile[][] finalWorldFrame = wg.World();

            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(finalWorldFrame);

            for (int i = actionCharPos; i < input.length() - 1; i++) {
                char c = input.charAt(i);
                char n = input.charAt(i + 1);
                if (c + n == ':' + 'q' || c + n == ':' + 'Q') {
                    Constants.Save(input.substring(0, i));
                }
            }

            return finalWorldFrame;
        }
    }

}
