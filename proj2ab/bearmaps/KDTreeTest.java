package bearmaps;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void TestKDTree1() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        Point p4 = new Point(-5, 2);

        KDTree nn = new KDTree(List.of(p1, p2, p3, p4));
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(3.3, ret.getX(), 0.1); // evaluates to 3.3
        assertEquals(4.4, ret.getY(), 0.1); // evaluates to 4.4
    }

    @Test
    public void TestKDTree2() {
        Point p1 = new Point(5, 6); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(1, 5);
        Point p3 = new Point(7, 3);
        Point p4 = new Point(2, 2);
        Point p5 = new Point(4, 9);
        Point p6 = new Point(9, 1);
        Point p7 = new Point(8, 7);
        Point p8 = new Point(6, 2);

        KDTree nn1 = new KDTree(List.of(p2, p3, p4, p5, p6, p7, p8, p1));
        Point ret1 = nn1.nearest(3, 6); // returns p1, pruned 4 points
        assertEquals(5, ret1.getX(), 0.1); // evaluates to 5
        assertEquals(6, ret1.getY(), 0.1); // evaluates to 6

        KDTree nn2 = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        Point ret2 = nn2.nearest(3, 6); // returns p1, pruned 4 points
        assertEquals(5, ret2.getX(), 0.1); // evaluates to 5
        assertEquals(6, ret2.getY(), 0.1); // evaluates to 6
    }

    @Test
    public void TestKDTree3() {
        Point p1 = new Point(7, 2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(5, 4);
        Point p3 = new Point(9, 6);
        Point p4 = new Point(2, 3);
        Point p5 = new Point(4, 7);
        Point p6 = new Point(8, 1);
        Point p7 = new Point(10, 10);

        KDTree nn = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        Point ret1 = nn.nearest(3, 6);
        assertEquals(4, ret1.getX(), 0.1);
        assertEquals(7, ret1.getY(), 0.1);

        Point ret2 = nn.nearest(2, 5);
        assertEquals(2, ret2.getX(), 0.1);
        assertEquals(3, ret2.getY(), 0.1);
    }

}
