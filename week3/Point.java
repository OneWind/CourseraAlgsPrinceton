import java.util.Comparator;

public class Point implements Comparable<Point> {
    
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point p, Point q) {
            double slopeP = Point.this.slopeTo(p);
            double slopeQ = Point.this.slopeTo(q);
            
            if (slopeP < slopeQ) return -1;
            else if (slopeP == slopeQ) return 0;
            else return 1;
        }
    }
    
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw() {
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public int compareTo(Point that) {
        if (y == that.y && x == that.x) {
            return 0;
        }
        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }
        return 1;
//        if (y < that.y) {
//            return -1;
//        } else if (x < that.x) {
//            return -1;
//        } else if (x == that.x) {
//            return 0;
//        } else {
//            return 1;
//        }
    }
    
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) {
                return -1 * Double.POSITIVE_INFINITY;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else {
            if (this.y == that.y) {
                return 0;
            } else {
                return ((float) (this.y - that.y)) / (this.x - that.x);
            }
        }
    }
    
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        Point p1 = new Point(1, 3);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(0, 3);
        Point p4 = new Point(3, 6);
        Point p5 = new Point(1, 3);
        
        System.out.println(p1.slopeTo(p2));
        System.out.println(p1.slopeTo(p3));
        System.out.println(p1.slopeTo(p4));
        System.out.println(p1.slopeTo(p5));
        
        Point p[] = new Point[]{p1, p2, p3, p4};
        System.out.println(p[0]);
        
        for (int i = 0; i < 4; i++) {
            p[i].draw();
        }

        p1.drawTo(p2);
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}