package byow.Core;

/**
 * Handle all static constants throughout the project
 */
public class Constants {
    // TODO: comments

    public static final int WIDTH = 60;
    public static final int HEIGHT = 45;
    public static final int HEIGHT_TOP = 3;

    public static final int OVERLAP_LIMIT = 30;
    public static final int ROOM_WIDTH_LIMIT = 6;
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
        DOWN(-1),UP(1),SAME(0);

        private final int value;

        Y_DIRECTION(int value) {
            this.value = value;
        }
        public int value() {return this.value;}
    }

    public enum DIRECTION  {
        UP(new int[]{0, 1}), DOWN(new int[]{0, -1}), SAME(new int[]{0, 0}), LEFT(new int[]{-1, 0}), RIGHT(new int[]{1, 0});

        private final int[] value;

        DIRECTION(int[] value) {
            this.value = value;
        }

        public int[] value() {return this.value;}
        public int valueX() {return this.value[0];};
        public int valueY() {return this.value[1];};
    }
}
