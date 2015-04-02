public class KdTree {
    Node kdTree;
    int size;
    
    /**
     * construct an empty set of points
     */
    public KdTree() {
        
    }
    
    private class Node {
        Point2D point;
        Node leftNode, rightNode;
        bool isVerticalSplitter;
        
        Node(Point2D point, bool isVerticalSplitter) {
            this.point = point;
            this.isVerticalSplitter = isVerticalSplitter;
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
        if (isEmpty()) kdTree = new Node(p, true);
        else {
            
        }
    }
    
    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        
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
        
        
    }
    
    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        if (isEmpty()) return null;
        
        
    }

    /**
     * unit testing of the methods (optional) 
     */
    public static void main(String[] args0) {
        
    }
}