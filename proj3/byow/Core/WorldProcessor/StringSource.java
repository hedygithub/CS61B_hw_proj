package byow.Core.WorldProcessor;

import byow.Core.Constants.*;

/**
 * An input source to handle inputs from a complete string
 * This will be useful for debug and load/save purposes
 * Credit to hug
 */
public class StringSource implements InputSource {
    private String input;
    private int index;

    public StringSource(String s) {
        index = 0;
        input = s;
    }

    @Override
    public char getNextKey() {
        char returnChar = input.charAt(index);
        index += 1;
        return returnChar;
    }

    @Override
    public boolean possibleNextInput() {
        return index < input.length();
    }

    @Override
    public INPUT_SOURCE type() {
        return INPUT_SOURCE.STRING;
    }



}
