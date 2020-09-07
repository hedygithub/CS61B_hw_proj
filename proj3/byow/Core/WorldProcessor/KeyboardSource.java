package byow.Core.WorldProcessor;

import byow.Core.Constants;
import byow.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;

/**
 * An input source to handle inputs from the keyboard
 * This will be useful for the real application case
 * (where the program interacts with the player via keypress)
 * Credit to hug
 */
public class KeyboardSource implements InputSource {
    private static final boolean PRINT_TYPED_KEYS = false;
    // TODO: draw some instructions in the screen??

    public KeyboardSource() {}

    @Override
    public char getNextKey() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char c = Character.toUpperCase(StdDraw.nextKeyTyped());
                if (PRINT_TYPED_KEYS) {
                    System.out.print(c);
                }
                return c;
            }
        }
    }

    @Override
    public boolean possibleNextInput() {
        return true;
    }

    @Override
    public Constants.INPUT_SOURCE type() {
        return Constants.INPUT_SOURCE.KEYBOARD;
    }

}
