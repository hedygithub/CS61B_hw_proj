package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        Point ret1 = nn1.nearest(3, 6); // returns p1
        assertEquals(5, ret1.getX(), 0.1); // evaluates to 5
        assertEquals(6, ret1.getY(), 0.1); // evaluates to 6

        KDTree nn2 = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7, p8));
        Point ret2 = nn2.nearest(3, 6); // returns p1, pruned 4 points
        assertEquals(5, ret2.getX(), 0.1); // evaluates to 5
        assertEquals(6, ret2.getY(), 0.1); // evaluates to 6

        Point ret3 = nn2.nearest(7, 3); // returns p3
        assertEquals(7, ret3.getX(), 0.1);
        assertEquals(3, ret3.getY(), 0.1);
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

    @Test
    public void RandomTestKDTree() {
        int numbers = 100;
        List<Point> points = new ArrayList<>(numbers);
        Random random = new Random(100);
        for(int i = 0; i < numbers; i++) {
            points.add(new Point(random.nextDouble() - 0.5, random.nextDouble() - 0.5));
        }
        KDTree KD = new KDTree(points);
        NaivePointSet Naive = new NaivePointSet(points);

        int targetsNumbers = 100;
        for(int i = 0; i < targetsNumbers; i++) {
            double x = random.nextDouble() - 0.5;
            double y = random.nextDouble() - 0.5;
            Point NaivePoint = Naive.nearest(x, y);
            Point KDPoint = KD.nearest(x, y);
//            System.out.println("Point: "+ String. format("%.2f", x) +", " +String. format("%.2f", y));
//            System.out.println("Nearest Point KD: "+ String. format("%.2f", KDPoint.getX()) +", " +String. format("%.2f", KDPoint.getY()) + ". distance: " + Point.distance(new Point(x ,y), KDPoint));
//            System.out.println("Nearest Point Naive: "+ String. format("%.2f", NaivePoint.getX()) +", " +String. format("%.2f", NaivePoint.getY()) + ". distance: " + Point.distance(new Point(x ,y), NaivePoint));
//            assertEquals(KDPoint.getX(), NaivePoint.getX(), 0.0001);
//            assertEquals(KDPoint.getY(), NaivePoint.getY(), 0.0001);
            assertEquals(Point.distance(new Point(x ,y), KDPoint), Point.distance(new Point(x ,y), NaivePoint), 0.0001);
        }
    }

    @Test
    public void RunTimeTest() {
        int numbers = 100000;
        List<Point> points = new ArrayList<>(numbers);
        Random random = new Random(100);
        for (int i = 0; i < numbers; i++) {
            points.add(new Point(random.nextDouble() - 0.5, random.nextDouble() - 0.5));
        }

        int targetsNumbers = 10000;
        List<Point> targetPoints = new ArrayList<>(targetsNumbers);
        for (int i = 0; i < targetsNumbers; i++) {
            targetPoints.add(new Point(random.nextDouble() - 0.5, random.nextDouble() - 0.5));
        }

        long startKD = System.currentTimeMillis();
        KDTree KD = new KDTree(points);
        long endKDConstructor = System.currentTimeMillis();
        for (int i = 0; i < targetsNumbers; i++) {
            KD.nearest(targetPoints.get(i).getX(), targetPoints.get(i).getY());
        }
        long endKD = System.currentTimeMillis();
        System.out.println("Seconds elapsed for KDTree constructor: " + (endKDConstructor - startKD) / 1000.0 + " seconds.");
        System.out.println("Seconds elapsed for KDTree nearest: " + (endKD - endKDConstructor) / 1000.0 + " seconds.");


        long startNaive = System.currentTimeMillis();
        NaivePointSet Naive = new NaivePointSet(points);
        long endNaiveConstructor = System.currentTimeMillis();
        for (int i = 0; i < targetsNumbers; i++) {
            Naive.nearest(targetPoints.get(i).getX(), targetPoints.get(i).getY());
        }
        long endNaive = System.currentTimeMillis();
        System.out.println("Seconds elapsed for NaivePointSet constructor: " + (endNaiveConstructor - startNaive) / 1000.0 + " seconds.");
        System.out.println("Seconds elapsed for NaivePointSet nearest: " + (endNaive - endNaiveConstructor) / 1000.0 + " seconds.");
    }

}
