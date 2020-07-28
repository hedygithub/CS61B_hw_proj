package bearmaps;

import java.util.List;

/**
 * KD Tree.
 * 2 dimensions.
 */

/* KD Tree
 */
public class KDTree {
    /* KD Tree
     * Node
     */
    private class Node {
        private Point point;
        private int direction;
        private Node leftNode;
        private Node rightNode;

        private Node(Point onePoint, int oneDirection) {
            point = onePoint;
            direction = oneDirection;
            leftNode = null;
            rightNode = null;
        }
    }

    private Node root;
    /* x, y
     */
    private int dimensions = 2;
    /* 0: x, 1: y */
    private int initialDirection = 0;

    /* assume points has at least size 1
    * not perfectly balanced version
    * start with x
    */
    public KDTree(List<Point> points) {
        root = new Node(points.get(0), initialDirection);
        for (int i = 1; i < points.size(); i++) {
            root = insertNewNode(root, points.get(i), initialDirection);
        }
    }

    private Node insertNewNode(Node currentNode, Point newPoint, int direction) {
        if (currentNode == null) {
            return new Node(newPoint, direction);
        }
        direction = (currentNode.direction + 1) % dimensions;
        boolean isGoLeft = isGoLeft(currentNode, newPoint);
        if (isGoLeft) {
            currentNode.leftNode = insertNewNode(currentNode.leftNode, newPoint, direction);
        } else {
            currentNode.rightNode = insertNewNode(currentNode.rightNode, newPoint, direction);
        }
        return currentNode;
    }

    private boolean isGoLeft(Node currentNode, Point onePoint) {
        boolean goLeft;
        if (currentNode.direction == 0) {
            goLeft = onePoint.getX() < currentNode.point.getX();
        } else {
            goLeft = onePoint.getY() < currentNode.point.getY();
        }
        return goLeft;
    }

    public Point nearest(double x, double y) {
        Point targetPoint = new Point(x, y);
        Node nearestNode = nearest(root, root, targetPoint);
        return nearestNode.point;
    }

    private Node nearest(Node currentNode, Node nearestNode, Point targetPoint) {
        if (currentNode == null) {
            return nearestNode;
        }
        /* old nearest distance. */
        double distance = Point.distance(targetPoint, nearestNode.point);
        /* new nearest distance. */
        double newDistance = Point.distance(targetPoint, currentNode.point);
        if(newDistance < distance) {
            nearestNode = currentNode;
        }

        /* prune all. */
        if(newDistance == 0) {
            return nearestNode;
        }

        /* choose better side. */
        boolean isGoLeft = isGoLeft(currentNode, targetPoint);

        if(isGoLeft) {
            /* good side in left. */
            nearestNode = nearest(currentNode.leftNode, nearestNode, targetPoint);
            distance = Point.distance(targetPoint, nearestNode.point);
            /* bad side. */
            double nearestDistanceInBadArea = possibleNearestDistance(currentNode, targetPoint);
            if (nearestDistanceInBadArea >= distance) {
                /* prune. */
                return nearestNode;
            } else {
                nearestNode = nearest(currentNode.rightNode, nearestNode, targetPoint);
            }
        } else {
            /* good side in right. */
            nearestNode = nearest(currentNode.rightNode, nearestNode, targetPoint);
            distance = Point.distance(targetPoint, nearestNode.point);
            /* bad side. */
            double nearestDistanceInBadArea = possibleNearestDistance(currentNode, targetPoint);
            if (nearestDistanceInBadArea >= distance) {
                /* prune. */
                return nearestNode;
            } else {
                nearestNode = nearest(currentNode.leftNode, nearestNode, targetPoint);
            }
        }
        return nearestNode;
    }

    private double possibleNearestDistance(Node currentNode, Point targetPoint) {
        double nearestDistanceInBadArea;
        if (currentNode.direction == 0) {
            nearestDistanceInBadArea = Point.distance(targetPoint, new Point(currentNode.point.getX(), targetPoint.getY()));
        } else {
            nearestDistanceInBadArea = Point.distance(targetPoint, new Point(targetPoint.getX(), currentNode.point.getY()));
        }
        return nearestDistanceInBadArea;
    }
}
