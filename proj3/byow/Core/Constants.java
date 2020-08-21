package byow.Core;

/**
 * Handle all static constants throughout the project
 */
public class Constants {
    // TODO: comments
    public static String SAVED_INPUT;
    public static void Save(String input) {
        SAVED_INPUT = input;
    }

    public static final int WIDTH = 90;
    public static final int HEIGHT = 48;
    public static final int OVERLAP_LIMIT = 30;
    public static final int ROOM_WIDTH_LIMIT = 7;
    public static final int ROOM_HEIGHT_LIMIT = 4;

    public enum X_DIRECTION  {
        LEFT(-1),RIGHT(1),SAME(0);

        private final int value;

        X_DIRECTION(int value) {
            this.value = value;
        }
        public int value() {return this.value;}
    }

    public enum Y_DIRECTION {
        BOTTOM(-1),TOP(1),SAME(0);

        private final int value;

        Y_DIRECTION(int value) {
            this.value = value;
        }
        public int value() {return this.value;}
    }
}
