package bearmaps;

import java.util.List;

public class KDTree {
    private class Node{
        public Point point;
        public Node leftNode;
        public Node rightNode;

        public Node(Point point) {
            this.point = point;
            leftNode = null;
            rightNode = null;
        }
    }

    public Node root;
    private int initialBasedOnWhichAxis = 0; //0: x, 1: y
    private int dimensions = 2;

    /* assume points has at least size 1
    * not perfectly balanced version
    * start with x
    * */
    public KDTree(List<Point> points){
        root = new Node(points.get(0));
        for(int i = 1; i < points.size(); i++) {
            Node newNode = new Node(points.get(i));
            insertNewNode(root, newNode, initialBasedOnWhichAxis);
        }
    }
    private void insertNewNode(Node currentNode, Node newNode, int basedOnAxis) {
        boolean left0 = basedOnAxis == 0 && newNode.point.getX() < currentNode.point.getX();
        boolean left1 = basedOnAxis == 1 && newNode.point.getY() < currentNode.point.getY();
        if(left0 || left1) {
                if(currentNode.leftNode == null) {
                    currentNode.leftNode = newNode;
                    return;
                } else {
                    insertNewNode(currentNode.leftNode, newNode, (basedOnAxis + 1) % dimensions);
                }
            } else {
                if(currentNode.rightNode == null) {
                    currentNode.rightNode = newNode;
                    return;
                } else {
                    insertNewNode(currentNode.rightNode, newNode, (basedOnAxis + 1) % dimensions);
                }
            }
        }


    public Point nearest(double x, double y) {
        Point targetPoint = new Point(x, y);
        Node nearestNode = nearest(root, root, targetPoint, initialBasedOnWhichAxis);
        return nearestNode.point;
    }

    private Node nearest(Node currentNode, Node nearestNode, Point targetPoint, int basedOnAxis) {
        double distance = Point.distance(targetPoint, nearestNode.point);
        if(distance == 0) {
            //prune all
            return nearestNode;
        }
        //choose better side
        boolean left0 = basedOnAxis == 0 && targetPoint.getX() < currentNode.point.getX();
        boolean left1 = basedOnAxis == 1 && targetPoint.getY() < currentNode.point.getY();
        if(left0 || left1) {
            //good side in left
            if(currentNode.leftNode != null) {
                double newDistance = Point.distance(targetPoint, currentNode.leftNode.point);
                if(newDistance < distance) {
                    nearestNode = currentNode.leftNode;
                }
                nearestNode = nearest(currentNode.leftNode, nearestNode, targetPoint, (basedOnAxis + 1) % dimensions);
            }
            //bad side
            double nearestDistanceInBadArea;
            if (basedOnAxis == 0) {
                nearestDistanceInBadArea = Point.distance(targetPoint, new Point(currentNode.point.getX(), targetPoint.getY()));
            } else {
                nearestDistanceInBadArea = Point.distance(targetPoint, new Point(targetPoint.getX(), currentNode.point.getY()));
            }
            distance = Point.distance(targetPoint, nearestNode.point);

            if (nearestDistanceInBadArea >= distance) {
                //prune
                return nearestNode;
            } else {
                if(currentNode.rightNode != null) {
                    double newDistance = Point.distance(targetPoint, currentNode.rightNode.point);
                    if(newDistance < distance) {
                        nearestNode = currentNode.rightNode;
                    }
                    nearestNode = nearest(currentNode.rightNode, nearestNode, targetPoint, (basedOnAxis + 1) % dimensions);
                }
            }
        } else {
            //good side in right
            if(currentNode.rightNode != null) {
                double newDistance = Point.distance(targetPoint, currentNode.rightNode.point);
                if(newDistance < distance) {
                    nearestNode = currentNode.rightNode;
                }
                nearestNode = nearest(currentNode.rightNode, nearestNode, targetPoint, (basedOnAxis + 1) % dimensions);
            }

            //bad side
            double nearestDistanceInBadArea;
            if (basedOnAxis == 0) {
                nearestDistanceInBadArea = Point.distance(targetPoint, new Point(currentNode.point.getX(), targetPoint.getY()));
            } else {
                nearestDistanceInBadArea = Point.distance(targetPoint, new Point(targetPoint.getX(), currentNode.point.getY()));
            }
            distance = Point.distance(targetPoint, nearestNode.point);

            if (nearestDistanceInBadArea >= distance) {
                //prune
                return nearestNode;
            } else {
                if(currentNode.leftNode != null) {
                    double newDistance = Point.distance(targetPoint, currentNode.leftNode.point);
                    if(newDistance < distance) {
                        nearestNode = currentNode.leftNode;
                    }
                    nearestNode = nearest(currentNode.leftNode, nearestNode, targetPoint, (basedOnAxis + 1) % dimensions);
                }
            }
        }
        return nearestNode;
    }
}
