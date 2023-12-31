/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double dy = that.y - this.y;
        double dx = that.x - this.x;
        if(dy==0.0 && dx==0.0)
            return Double.NEGATIVE_INFINITY;
        else if(dy==0.0)//horizontal
            return +0.0;
        else if (dx==0.0)
            return Double.POSITIVE_INFINITY;
        else
            return dy/dx;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if(this.y < that.y) return -1;
        if(this.y > that.y) return +1;
        if(this.x < that.x) return -1;
        if(this.x > that.x) return +1;
        return 0;
    }

    /*public static final Comparator<Point> BY_COORDINATE = new ByCoordinate();

    private static class ByCoordinate implements Comparator<Point>{
        public int compare(Point v, Point w){
            return v.compareTo(w);
        }
    }*/

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Comparator<Point> BY_SLOPE = new BySlope();
        return BY_SLOPE;
    }

    private class BySlope implements Comparator<Point>{
        public int compare(Point v, Point w){
//            double slopeV = Point.this.slopeTo(v);
            double slopeV = slopeTo(v);
//            double slopeW = Point.this.slopeTo(w);
            double slopeW = slopeTo(w);
            if(slopeV < slopeW) return -1;
            if(slopeV > slopeW) return +1;
            return 0;
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // read the n points from a file
        if(args == null)
            throw new IllegalArgumentException("Null input non allowed.");
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for(int i = 0; i < n; i++){
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x,y);
            StdOut.print(points[i] + ", ");
            // Check if arguments are legal
        }

        Arrays.sort(points, points[0].slopeOrder());

        // Test slope comparisons
//        double test =  Double.NEGATIVE_INFINITY - Double.NEGATIVE_INFINITY;
//        StdOut.println(test==0);
//        StdOut.println(test<0);
//        test =  Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY;
//        StdOut.println(test==0);
//        StdOut.println(test<0);
//        test = 9.8 - 9.8;
//        StdOut.println(test==0);
//        StdOut.println(Double.POSITIVE_INFINITY< Double.POSITIVE_INFINITY);
    }
}
