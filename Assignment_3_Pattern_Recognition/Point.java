import java.util.Comparator;

public class Point implements Comparable<Point> {
   // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {  
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            
            if (slope1 < slope2) return -1;
            else if (slope1 > slope2) return 1;
            return 0;
        }  
    };       
   
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // construct the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draw this point
    public   void draw() {
        StdDraw.point(x, y);
    }
   
    // draw the line segment from this point to that point
    public   void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
   
    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // is this point lexicographically smaller than that point?
    public    int compareTo(Point that) {
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        return 0;
    }
   
    // the slope between this point and that point
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;
        if (this.y == that.y && this.x != that.x) return 0;
        if (this.y != that.y && this.x == that.x) return Double.POSITIVE_INFINITY;
       
        return (double) (that.y - this.y) / (that.x - this.x);
    }
}                  