package bearmaps;

import java.awt.geom.CubicCurve2D;
import java.util.List;

public class NaivePointSet implements PointSet{
    public List<Point> points; //if you don't say it is Point, you should cast Point when use get()

    /* assume points has at least size 1 */
    public NaivePointSet(List<Point> points){
        this.points = points;
    }

    /*  Returns the closest point to the inputted coordinates.
     *  This should take θ(N) time where N is the number of points.
     */
    public Point nearest(double x, double y) {
        Point targetPoint = new Point(x, y);
        Point nearestPoint = points.get(0);
        for(int i = 1; i < points.size(); i++) {
            double distance = Point.distance(targetPoint, nearestPoint);
            double newDistance = Point.distance(targetPoint, points.get(i));
            if(newDistance < distance) {
                nearestPoint = points.get(i);
            }
        }
        return nearestPoint;
    }
}
