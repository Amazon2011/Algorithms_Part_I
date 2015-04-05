import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class KdTree {
    private Node root;
    private int size;
    
    /**
     * construct an empty set of points
     */
    public KdTree() {
        
    }
    
    private static class Node {
        private Point2D point;
        private Node leftNode, rightNode;
        private RectHV rect;
        private int level;
        
        Node(Point2D point, int level, RectHV rect) {
            this.point = point;
            this.level = level;
            this.rect = rect;
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
            root = new Node(p, 1, new RectHV(0.0, 0.0, 1.0, 1.0));
            size = 1;
        } else {
            Node currentNode = root;
            while (true) {
                if (p.equals(currentNode.point)) return;
                if (currentNode.level % 2 == 1) {
                    if (p.x() < currentNode.point.x()) {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, currentNode.level + 1, new RectHV(currentNode.rect.xmin(),currentNode.rect.ymin(),currentNode.point.x(),currentNode.rect.ymax()));
                            size++;
                            return;
                        } else currentNode = currentNode.leftNode;
                    } else {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, currentNode.level + 1, new RectHV(currentNode.point.x(),currentNode.rect.ymin(),currentNode.rect.xmax(),currentNode.rect.ymax()));
                            size++;
                            return;
                        } else currentNode = currentNode.rightNode;
                    }
                } else {
                    if (p.y() < currentNode.point.y()) {
                        if (currentNode.leftNode == null) {
                            currentNode.leftNode = new Node(p, currentNode.level + 1, new RectHV(currentNode.rect.xmin(),currentNode.rect.ymin(),currentNode.rect.xmax(),currentNode.point.y()));
                            size++;
                            return;
                        } else currentNode = currentNode.leftNode;
                    } else {
                        if (currentNode.rightNode == null) {
                            currentNode.rightNode = new Node(p, currentNode.level + 1, new RectHV(currentNode.rect.xmin(),currentNode.point.y(),currentNode.rect.xmax(),currentNode.rect.ymax()));
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
        if (isEmpty()) return;
        
        Stack<Node> nodesStack = new Stack<Node>();
        nodesStack.push(root);
        
         while (!nodesStack.isEmpty()) {
             Node currentNode = nodesStack.pop();
             currentNode.point.draw();
             if (currentNode.rightNode != null) nodesStack.push(currentNode.rightNode);
             if (currentNode.leftNode != null) nodesStack.push(currentNode.leftNode);
         }
    }
    
    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect ==  null) throw new NullPointerException("Argument cannot be null!");
        List<Point2D> pointsInRange = new ArrayList<Point2D>();
        if (isEmpty()) return (Iterable<Point2D>) pointsInRange;
        
        Stack<Node> nodesStack = new Stack<Node>();
        nodesStack.push(root);
        
        while (!nodesStack.isEmpty()) {
            Node currentNode = nodesStack.pop();
            if (rect.contains(currentNode.point)) pointsInRange.add(currentNode.point);
            if (currentNode.level % 2 == 1) {
                if (rect.xmin() > currentNode.point.x()) {
                    if (currentNode.rightNode != null) nodesStack.push(currentNode.rightNode);
                } else if (rect.xmax() < currentNode.point.x()) {
                    if (currentNode.leftNode != null) nodesStack.push(currentNode.leftNode);
                } else {
                    if (currentNode.rightNode != null) nodesStack.push(currentNode.rightNode);
                    if (currentNode.leftNode != null) nodesStack.push(currentNode.leftNode);
                }
            } else {
                if (rect.ymin() > currentNode.point.y()) {
                    if (currentNode.rightNode != null) nodesStack.push(currentNode.rightNode);
                } else if (rect.ymax() < currentNode.point.y()) {
                    if (currentNode.leftNode != null) nodesStack.push(currentNode.leftNode);
                } else {
                    if (currentNode.rightNode != null) nodesStack.push(currentNode.rightNode);
                    if (currentNode.leftNode != null) nodesStack.push(currentNode.leftNode);
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
        
        Point2D nearestPoint = new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        double nearestDistanceSquare = Double.MAX_VALUE;
        
        Stack<Node> nodesStack = new Stack<Node>();
        nodesStack.push(root);
        
        while (!nodesStack.isEmpty()) {
            Node currentNode = nodesStack.pop();
            if (currentNode.rect.distanceSquaredTo(p) >= nearestDistanceSquare) continue;
            
            double distanceSquare = p.distanceSquaredTo(currentNode.point);
            if (distanceSquare < nearestDistanceSquare) {
                nearestDistanceSquare = distanceSquare;
                nearestPoint = currentNode.point;
            }
            
            if (currentNode.rightNode != null && currentNode.leftNode != null) {
                double disSqrRight = currentNode.rightNode.rect.distanceSquaredTo(p);
                double disSqrLeft = currentNode.leftNode.rect.distanceSquaredTo(p);
                
                if (disSqrRight > disSqrLeft) {
                    if (disSqrLeft < nearestDistanceSquare) {
                        if (disSqrRight < nearestDistanceSquare) nodesStack.push(currentNode.rightNode);
                        nodesStack.push(currentNode.leftNode);
                    } 
                } else {
                    if (disSqrRight < nearestDistanceSquare) {
                        if (disSqrLeft < nearestDistanceSquare) nodesStack.push(currentNode.leftNode);
                        nodesStack.push(currentNode.rightNode);
                    }
                }    
            } else {
                if (currentNode.rightNode != null && currentNode.rightNode.rect.distanceSquaredTo(p) < nearestDistanceSquare) nodesStack.push(currentNode.rightNode);
                if (currentNode.leftNode != null && currentNode.leftNode.rect.distanceSquaredTo(p) < nearestDistanceSquare) nodesStack.push(currentNode.leftNode);
            }
        }
        
        return nearestPoint;
    }

    /**
     * unit testing of the methods (optional) 
     */
    public static void main(String[] args0) {
        KdTree kdtree = new KdTree();
        
        kdtree.insert(new Point2D(0.35, 0.5));
        kdtree.insert(new Point2D(0.55, 0.35));
        kdtree.insert(new Point2D(0.25, 0.6));
        kdtree.insert(new Point2D(0.2, 0.08));
        kdtree.insert(new Point2D(0.1, 0.5));
        kdtree.insert(new Point2D(0.32, 0.7));
        kdtree.insert(new Point2D(0.4, 0.32));
        kdtree.insert(new Point2D(0.85, 0.9));
        kdtree.insert(new Point2D(0.8, 0.75));
        kdtree.insert(new Point2D(0.48, 0.1));
        
        Point2D p = new Point2D(0.05, 0.63);
        StdOut.println("The nearest point is " + kdtree.nearest(p));
    }
}