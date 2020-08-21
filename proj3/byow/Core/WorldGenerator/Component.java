package byow.Core.WorldGenerator;

import byow.TileEngine.TETile;

import java.util.List;
import java.util.Set;

/**
 * General interface for the components in the world
 */
public interface Component {

    // public boolean checkSpace(Set<Point2D> floorSet, Set<Point2D> wallSet); // check if there is enough space for this component to be placed
    void generate(Set<Point> floorSet, List<Point> floorList, Set<Point> wallSet, TETile[][] world);

//    public void addFloors(Set<Point2D> floorSet); // add points within the component (a.k.a floors) to the set
//    public void addWalls(Set<Point2D> wallSet); // add points bounding the component (a.k.a walls) to the set
}
