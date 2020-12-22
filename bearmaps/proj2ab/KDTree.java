package bearmaps;
import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;

    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node leftChild; // Also refers to downChild.
        private Node rightChild; // Also refers to upChild.

        Node(Point givenP, boolean o) { // Our Node constructor.
            p = givenP;
            orientation = o;
        }

    }
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }

    private Node add(Point p, Node n, boolean orientation) { // p is point we want to add
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(p, n.p, orientation);
        if (cmp < 0) {
            n.leftChild = add(p, n.leftChild, !orientation);
        } else if (cmp >= 0) {
            n.rightChild = add(p, n.rightChild, !orientation);
        }
        return n;
    }

    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }

    @Override
    public Point nearest(double x, double y) {
        Node nearest = nearestHelper(root, new Point(x, y), root);
        return nearest.p;
    }

    private Node nearestHelper(Node n, Point goal, Node best) {

        if (n == null) {
            return best;
        } else if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }

        Node goodSide;
        Node badSide;


        int cmp = comparePoints(goal, n.p, n.orientation);
        if (cmp < 0) {
            goodSide = n.leftChild;
            badSide = n.rightChild;
        } else {
            goodSide = n.rightChild;
            badSide = n.leftChild;
        }
        best = nearestHelper(goodSide, goal, best);

        Point bestBad;

        if (n.orientation == VERTICAL) {
            bestBad = new Point(goal.getX(), n.p.getY());
        } else {
            bestBad = new Point(n.p.getX(), goal.getY());
        }

        double bestBadDistance = Point.distance(bestBad, goal);
        if (bestBadDistance < Point.distance(best.p, goal)) {
            best = nearestHelper(badSide, goal, best);
        }
        
        return best;
    }
}
