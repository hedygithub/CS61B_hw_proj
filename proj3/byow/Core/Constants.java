package byow.Core;

/**
 * Handle all static constants throughout the project
 */
public class Constants {
    // TODO: comments

    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int OVERLAP_LIMIT = 10;
    public static final int ROOM_WIDTH_LIMIT = 7;
    public static final int ROOM_HEIGHT_LIMIT = 5;

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
