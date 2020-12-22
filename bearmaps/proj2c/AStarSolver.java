package bearmaps.proj2c;

import java.util.*;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight;
    private double timeSpent;
    private int numStatesExplored;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;
    private LinkedList<Vertex> solution;
    private ArrayHeapMinPQ<Vertex> fringe;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        fringe = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new LinkedList<>();

        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        numStatesExplored = 0;
        Vertex current;

        Stopwatch sw = new Stopwatch();
        while (fringe.size() != 0 && !fringe.getSmallest().equals(end)) {
            if (sw.elapsedTime() >= timeout) {
                outcome = SolverOutcome.TIMEOUT;
                timeSpent = timeout;
                break;
            }
            current = fringe.removeSmallest();

            numStatesExplored++;
            relax(input, current, end);

        }

        timeSpent = sw.elapsedTime();

        if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (fringe.getSmallest().equals(end) && timeSpent < timeout) {
            solutionWeight = distTo.get(end);
            pathBuilder(end, start);
            outcome = SolverOutcome.SOLVED;
        }
    }

    private void pathBuilder(Vertex current, Vertex start) {
        while (current != null) {
            solution.addFirst(current);
            current = edgeTo.get(current);
        }
    }

    private void relax(AStarGraph<Vertex> input, Vertex v, Vertex end) {
        List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(v);
        for (WeightedEdge<Vertex> e : neighborEdges) {
            Vertex from = e.from();
            Vertex to = e.to();
            double totalDist = distTo.get(from) + e.weight();
            double priority = totalDist + input.estimatedDistanceToGoal(e.to(), end);

            if (!distTo.containsKey(to)) {
                // If not in distTo(some vertex), then the dist was infinity
                distTo.put(to, totalDist);
                edgeTo.put(to, from);
                fringe.add(to, priority);
            }


            if (totalDist < distTo.get(to)) {
                distTo.replace(to, totalDist);
                edgeTo.replace(to, from);

                if (fringe.contains(to)) {
                    fringe.changePriority(to, priority);
                } else {
                    fringe.add(to, priority);
                }
            }
        }
    }
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }
    @Override
    public List<Vertex> solution() {
        return solution;
    }
    @Override
    public double solutionWeight() {
        if (outcome.equals(SolverOutcome.SOLVED)) {
            return solutionWeight;
        } else {
            return 0;
        }
    }
    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }
    @Override
    public double explorationTime() {
        return timeSpent;
    }
}
