package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private LinkedList<Vertex> solution;
    private int numStatesExplored;
    private double timeSpent;

    /*
     * Constructor which finds the solution,
     * computing everything necessary for all other methods
     * to return their results in constant time.
     * Note that timeout passed in is in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        HashMap<Vertex, Vertex> edgeTo = new HashMap<>();
        solution = new LinkedList<>();

        HashMap<Vertex, Double> distTo = new HashMap<>();
        distTo.put(start, 0.0);

        ArrayHeapMinPQ<Vertex> minPQ = new ArrayHeapMinPQ<>();
        minPQ.add(start, distTo.get(start) + input.estimatedDistanceToGoal(start,end));

        Stopwatch sw = new Stopwatch();
        timeSpent = sw.elapsedTime();
        while (minPQ.size() != 0 && !minPQ.getSmallest().equals(end) && timeSpent < timeout) {
            Vertex p = minPQ.removeSmallest();
            numStatesExplored ++;
            for (WeightedEdge<Vertex> edge : input.neighbors(p)) {
                Vertex q = edge.to();
                double w = edge.weight();
                if (!distTo.containsKey(q)) {
                    distTo.put(q, distTo.get(p) + w);
                    edgeTo.put(q, p);
                    minPQ.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                } else if (distTo.get(p) + w < distTo.get(q)) {
                    distTo.replace(q, distTo.get(p) + w);
                    edgeTo.replace(q, p);
                    if (minPQ.contains(q)) {
                        minPQ.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    } else {
                        minPQ.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                    }
                }
            }
            timeSpent = sw.elapsedTime();
        }
        if (minPQ.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            solutionWeight = 0;
        } else if (minPQ.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            solutionWeight = distTo.get(end);
            Vertex i = end;
            solution.addFirst(i);
            while (edgeTo.containsKey(i)) {
                i = edgeTo.get(i);
                solution.addFirst(i);
            }
        } else {
            outcome = SolverOutcome.TIMEOUT;
            solutionWeight = 0;
        }

    }

    /*
     * Returns one of SolverOutcome.SOLVED, SolverOutcome.TIMEOUT, or SolverOutcome.UNSOLVABLE.
     * Should be SOLVED if the AStarSolver was able to complete all work in the time given.
     * UNSOLVABLE if the priority queue became empty.
     * TIMEOUT if the solver ran out of time.
     * You should check to see if you have run out of time every time you dequeue.
     */
    public SolverOutcome outcome() {
        return outcome;
    }

    /*
     * A list of vertices corresponding to a solution.
     * Should be empty if result was TIMEOUT or UNSOLVABLE.
     */
    public List<Vertex> solution() {
        return solution;
    }

    /*
     * The total weight of the given solution, taking into account edge weights.
     * Should be 0 if result was TIMEOUT or UNSOLVABLE.
     */
    public double solutionWeight() {
        return solutionWeight;
    }

    /*
     * The total number of priority queue dequeue operations.
     */
    public int numStatesExplored() {
        return numStatesExplored;
    }
    /*
     * The total time spent in seconds by the constructor.
     */
    public double explorationTime() {
        return timeSpent;
    }
}
