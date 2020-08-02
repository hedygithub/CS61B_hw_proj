package bearmaps.hw4.integerhoppuzzle;

import bearmaps.hw4.AStarGraph;
import bearmaps.hw4.WeightedEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * The Integer Hop puzzle implemented as a graph.
 * Created by hug.
 */
public class IntegerHopGraph implements AStarGraph<Integer> {

    @Override
    public List<WeightedEdge<Integer>> neighbors(Integer v) {
        ArrayList<WeightedEdge<Integer>> neighbors = new ArrayList<>();
        neighbors.add(new WeightedEdge<>(v, v * v, 10));
        neighbors.add(new WeightedEdge<>(v, v * 2, 5));
        neighbors.add(new WeightedEdge<>(v, v / 2, 5));
        neighbors.add(new WeightedEdge<>(v, v - 1, 1));
        neighbors.add(new WeightedEdge<>(v, v + 1, 1));
        return neighbors;
    }

    @Override
    public double estimatedDistanceToGoal(Integer s, Integer goal) {
        // possibly fun challenge: Try to find an admissible heuristic that
        // speeds up your search. This is tough!

        /*
        // not correct fot 256, 4
        double h = 0.0;
        int larger = Math.max(Math.abs(goal), Math.abs(s));
        int smaller = Math.min(Math.abs(goal), Math.abs(s));

        // add or subtract is most effective
        if (goal <= 0 || Math.abs(goal - s) * 1 <= 5) {
            h = Math.abs(goal - s);
        } else if (smaller < 5) { // 5 * 5 -5 > 10
            h = Math.abs(4 - smaller);
            smaller = 5;
        }

        //square more effective
        while (smaller * smaller <= larger) {
            smaller = smaller * smaller;
            h += 10.0;
        }

        double hMD = h;
        int smallerMD = smaller;
        while (smallerMD * 2 <= larger) {
            smallerMD = smallerMD * 2;
            hMD += 5;
        }

        double hS = h + 10;
        int smallerS = smaller * smaller;
        while (smallerS / 2 >= larger) {
            smallerS = smallerS / 2;
            hS += 5;
        }

        return Math.min(hMD, hS);
        */


        return 0;
    }
}
