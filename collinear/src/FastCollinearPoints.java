import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
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

        int nPoints = points.length;
        // Sort array by their coordinates followed by stable sort of their slopes
//        insertionSort(points);
        Arrays.sort(points);
        // Check for repeated points.
        for(int i = 1; i < points.length; i++)
            if(points[i].compareTo(points[i-1])==0)
                throw new IllegalArgumentException("array contains a repeated point.");

        int nSegmentCnt = 0;
        Stack<LineSegment> segStack = new Stack<LineSegment>();
        if (points.length >= 4){
            // The sort by slopes is point dependant, so for each point we resort the array.
//            for (Point p : points) {
            for (int iP = 0; iP < nPoints-1; iP++){
                Point p = points[iP];
                Point[] pointsTmp = Arrays.copyOfRange(points,iP+1,points.length);
//                Point[] pointsTmp = new Point[nPoints];

//                for (int iP = 0; iP < nPoints; iP++)
//                    pointsTmp[iP] = points[iP];
                // p should be the first element in array since it should have a slope of negative infinity
                Arrays.sort(pointsTmp, p.slopeOrder());
//                Arrays.sort(points, p.slopeOrder());

                double prevSlope = p.slopeTo(pointsTmp[0]);
//                double prevSlope = p.slopeTo(points[1]);
                int slopeCnt = 0; // if slopeCnt == 2 then all 4 points are collinear
//                for (int i = 1; i < points.length; i++){
                for (int i = 1; i < pointsTmp.length; i++){
//                    double currentSlope = p.slopeTo(points[i]);
                    double currentSlope = p.slopeTo(pointsTmp[i]);
                    if(currentSlope == prevSlope)
                        slopeCnt++;
                    else
                        slopeCnt = 0;

                    prevSlope = currentSlope;

                    if(slopeCnt==2){
//                        LineSegment ls = new LineSegment(p,points[i]);
                        LineSegment ls = new LineSegment(p,pointsTmp[i]);
                        segStack.push(ls);
                        nSegmentCnt++;
                    }
                    else if (slopeCnt > 2) {
                        segStack.pop();
//                        LineSegment ls = new LineSegment(p,points[i]);
                        LineSegment ls = new LineSegment(p,pointsTmp[i]);
                        segStack.push(ls);
                    }
                }
            }//iP
        }

        this.nSegments = nSegmentCnt;

//        if(nSegmentCnt > 0){
        this.segmentsAll = new LineSegment[nSegmentCnt];
        if(nSegmentCnt > 0) {
            for (int i = 0; i < nSegmentCnt; i++)
                this.segmentsAll[i] = segStack.pop();
        }
//        }

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

/*    private static void insertionSort(Comparable[] a){
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
    }*/

    public static void main(String[] args){
        boolean DEBUG = false;
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

        if(!DEBUG) {
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            StdDraw.show();
        }
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        StdOut.println("# Segments detected: " + collinear.numberOfSegments());
        if(collinear.numberOfSegments() > 0) {
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                if(!DEBUG) {
                    segment.draw();
                }
            }
            if(!DEBUG) {
                StdDraw.show();
            }
        }
        else
            StdOut.println("No segments detected!");
    }
}
