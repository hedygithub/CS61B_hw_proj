package byow.Core.WorldGenerator;

import static byow.Core.Constants.WIDTH;

public class Point<T> {
    private T x;
    private T y;

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T x(){
        return x;
    }

    public T y(){
        return y;
    }

    //not applicable for non-number
    @Override
    public boolean equals(Object o) {
        if (this == null && o == null) {
            return true;
        } else if (this == null || o == null) {
            return false;
        } else if (getClass() == o.getClass()) {
            Point pO = (Point) o;
            if (x == pO.x() && y == pO.y()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //not applicable for non-number
    @Override
    public int hashCode() {
        return (int) x * WIDTH + (int)y;
    }



//    public static void main(String[] args) {
//        Point point = new Point(1,2);
//        System.out.println(point.x());
//    }
}
