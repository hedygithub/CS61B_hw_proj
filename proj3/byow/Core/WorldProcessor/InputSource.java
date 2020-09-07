package byow.Core.WorldProcessor;

import byow.Core.Constants.*;

/**
 * The interface to represent a generic input source
 * Credit to hug.
 */
public interface InputSource {
    public char getNextKey();
    public boolean possibleNextInput();
    public INPUT_SOURCE type();
}
