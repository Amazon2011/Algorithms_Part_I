import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class KdTree {
    Node root;
    int size;
    
    /**
     * construct an empty set of points
     */
    public KdTree() {
        
    }
    
    private class Node {
        Point2D point;
        Node leftNode, rightNode;
        int level;
        
        Node(Point2D point, int level) {
            this.point = point;
            this.level = level;
        }
    }
    
    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * number of points in the set
     */
    public int size() {
        return size;
    }
    
    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        if (isEmpty()) {
            root = new Node(p, 1);
            size = 1;
        } else {
            Node currentNode = root;
            while (true) {
                if (p.equals(currentNode.point)) return;
                if (currentNode.level % 2 == 1) {
                    if (p.x() < currentNode.point.x()) {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, currentNode + 1);
                            size++;
                            return;
                        } else currentNode = currentNode.leftNode;
                    } else {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, currentNode + 1);
                            size++;
                            return;
                        } else currentNode = currentNode.rightNode;
                    }
                } else {
                    if (p.y() < currentNode.point.y()) {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, currentNode + 1);
                            size++;
                            return;
                        } else currentNode = currentNode.leftNode;
                    } else {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, currentNode + 1);
                            size++;
                            return;
                        } else currentNode = currentNode.rightNode;
                    }
                }
            }
        }
    }
    
    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        Node currentNode = root;
        while (currentNode != null) {
            if (p.equals(currentNode.point)) return true;
            if (currentNode.level % 2 == 1) {
                if (p.x() < currentNode.point.x()) currentNode = currentNode.leftNode;
                else currentNode = currentNode.rightNode;
            } else {
                if (p.y() < currentNode.point.y()) currentNode = currentNode.leftNode;
                else currentNode = currentNode.rightNode;  
            }
        }
        return false;
    }
    
    /**
     * draw all points to standard draw
     */
    public void draw() {
        
    }
    
    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect ==  null) throw new NullPointerException("Argument cannot be null!");
        List<Point2D> pointsInRange = new ArrayList<Point2D>();
        if (isEmpty()) return (Iterable<Point2D>) pointsInRange;
        
        List<Node> checkNodesQueue = new LinkedList<Node>();
        checkNodesQueue.addLast(root);
        
        while(!checkNodesQueue.isEmpty()) {
            Node currentNode = checkNodesQueue.poll();
            if (rect.contains(currentNode.point)) pointsInRange.add(currentNode.point);
            if (currentNode.level % 2 == 1) {
                if (rect.xmin() > currentNode.point.x()) {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                } else if (rect.xmax() < currentNode.point.x()) {
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                } else {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                }
            } else {
                if (rect.ymin() > currentNode.point.y()) {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                } else if (rect.ymax() < currentNode.point.y()) {
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                } else {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                }
            }
        }
        
        return (Iterable<Point2D>) pointsInRange;
    }
    
    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        if (isEmpty()) return null;
        
        Point2D nearestPoint = Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        
        List<Node> checkNodesQueue = new LinkedList<Node>();
        checkNodesQueue.addLast(root);
        
        while(!checkNodesQueue.isEmpty()) {
            Node currentNode = checkNodesQueue.poll();
            if (currentNode.point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) nearestPoint = currentNode.point;
            
            if (currentNode.level % 2 == 1) {
                if (rect.xmin() > currentNode.point.x()) {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                } else if (rect.xmax() < currentNode.point.x()) {
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                } else {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                }
            } else {
                if (rect.ymin() > currentNode.point.y()) {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                } else if (rect.ymax() < currentNode.point.y()) {
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                } else {
                    if (currentNode.rightNode != null) checkNodesQueue.addLast(currentNode.rightNode);
                    if (currentNode.leftNode != null) checkNodesQueue.addLast(currentNode.leftNode);
                }
            }
        }
        
    }

    /**
     * unit testing of the methods (optional) 
     */
    public static void main(String[] args0) {
        
    }
}