import java.util.Iterator;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class PointSET {
    private TreeSet<Point2D> set;
    
    /**
     * construct an empty set of points
     */
    public PointSET() {
        set = new TreeSet();
    }
    
    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    /**
     * number of points in the set
     */
    public int size() {
        return set.size();
    }
    
    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        set.add(p);
    }
    
    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        return set.contains(p);
    }
    
    /**
     * draw all points to standard draw
     */
    public void draw() {
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) iterator.next().draw();
    }
    
    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect ==  null) throw new NullPointerException("Argument cannot be null!");
        
        List<Point2D> pointsInRange = new ArrayList<Point2D>();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Point2D point = iterator.next();
            if (rect.contains(point)) pointsInRange.add(p);
        }
        
        return (Iterable<Point2D>) pointsInRange;
    }
    
    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p ==  null) throw new NullPointerException("Argument cannot be null!");
        if (isEmpty()) return null;
        
        Iterator iterator = set.iterator();
        Point2D nearestPoint = iterator.next();
        
        while (iterator.hasNext()) {
            Point2D point = iterator.next();
            if (point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) nearestPoint = point;
        }
        
        return nearestPoint;
    }

    /**
     * unit testing of the methods (optional) 
     */
    public static void main(String[] args0) {
        
    }
}