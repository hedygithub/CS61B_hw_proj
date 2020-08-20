package byow.Core.WorldGenerator;

import edu.princeton.cs.algs4.Point2D;

import java.util.Set;

/**
 * General interface for the components in the world
 */
public interface Component {
    public Point2D pivot(); // a pivot point to represent the component
    // public boolean checkSpace(Set<Point2D> floorSet, Set<Point2D> wallSet); // check if there is enough space for this component to be placed
    public void generate(Set<Point2D> floorSet, Set<Point2D> wallSet);

//    public void addFloors(Set<Point2D> floorSet); // add points within the component (a.k.a floors) to the set
//    public void addWalls(Set<Point2D> wallSet); // add points bounding the component (a.k.a walls) to the set
}
