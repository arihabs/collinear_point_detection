import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.Arrays;

public class FastCollinearPoints{
    private boolean DEBUG = false;
    private int nSegments = 0;

    private LineSegment[] segmentsAll;

    // finds all line segments containing 4 or more point
    public FastCollinearPoints(Point[] points) {
        // Corner cases. Throw an IllegalArgumentException if the argument to the constructor is null, if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if (points == null)
            throw new IllegalArgumentException();

        for (Point p : points)
            if (p == null)
                throw new IllegalArgumentException("point array contains null.");

        // Sort array by their coordinates followed by stable sort of their slopes
//        insertionSort(points);
        Arrays.sort(points);

        int nSegmentCnt = 0;
        Stack<LineSegment> segStack = new Stack<LineSegment>();
        if (points.length >= 4){
            // The sort by slopes is point dependant, so for each point we resort the array.
            for (Point p : points) {
                // p should be the first element in array since it should have a slope of negative infinity
                Arrays.sort(points, p.slopeOrder());

                double prevSlope = p.slopeTo(points[1]);
                // Use a FIFO to keep track of previous slopes
//                Queue<Double> queue = new LinkedList<Double>();
                int slopeCnt = 0; // if slopeCnt == 2 then all 4 points are collinear
                for (int i = 2; i < points.length; i++){
                    double currentSlope = p.slopeTo(points[i]);
                    if(currentSlope == prevSlope)
                        slopeCnt++;
                    else
                        slopeCnt = 0;

                    prevSlope = currentSlope;

                    if(slopeCnt==2){
                        LineSegment ls = new LineSegment(p,points[i]);
                        segStack.push(ls);
                        nSegmentCnt++;
                    }
                    else if (slopeCnt > 2) {
                        segStack.pop();
                        LineSegment ls = new LineSegment(p,points[i]);
                        segStack.push(ls);
                    }
                }
            }
        }

        this.nSegments = nSegmentCnt;

        if(nSegmentCnt > 0){
            this.segmentsAll = new LineSegment[nSegmentCnt];
            for(int i = 0; i < nSegmentCnt; i++)
                this.segmentsAll[i] = segStack.pop();
        }

        // Stack should be empty
        assert segStack.empty();
    }
    // the number of line segments
    public int numberOfSegments(){
        return this.nSegments;
    }
    
    // the line segments
    public LineSegment[] segments(){
        return this.segmentsAll;
    }

    private static void insertionSort(Comparable[] a){
        int N = a.length;
        for(int i = 0; i < N; i++){
            for (int j = i; j > 0; j--){
                if(less(a[j],a[j-1]))
                    exch(a,j,j-1);
                else
                    break;
            }
        }
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args){
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
            // Check if arguments are legal
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0,32768);
        StdDraw.setYscale(0,32768);
        for(Point p : points){
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        if(collinear.numberOfSegments() > 0) {
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        }
        else
            StdOut.println("No segments detected!");
    }
}
