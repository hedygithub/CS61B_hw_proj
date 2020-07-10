import org.junit.Test;
import static org.junit.Assert.*;

public class TestBubbleGrid {
    @Test
    public void testBubbleGrid() {
        int [][] bubbleGrid = {{1, 1, 0},{1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
        int [][] dartGrid = {{2, 2}, {2, 0}};
        int [] exceptedFalls = {0,4};
        BubbleGrid girdBubble = new BubbleGrid(bubbleGrid);
        int [] actualFalls = girdBubble.popBubbles(dartGrid);
        assertArrayEquals(exceptedFalls, actualFalls);
    }
}
