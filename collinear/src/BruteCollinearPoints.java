import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
import java.util.Arrays;


// finds all line segments containing 4 points
public class BruteCollinearPoints{
    private boolean DEBUG = false;
    private int nSegments = 0;
    public BruteCollinearPoints(Point[] points){
        // Corner cases. Throw an IllegalArgumentException if the argument to the constructor is null, if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if(points == null)
            throw new IllegalArgumentException();

        for(Point p: points)
            if(p==null)
                throw new IllegalArgumentException("point array contains null.");
        //  Sort array. This will be useful for compare neighboring points for equality as well as selecting the 2 farthest points when creating the line segment.
//        insertionSort(points);
        Arrays.sort(points);
        // Check for repeated points.
        for(int i = 1; i < points.length; i++)
            if(points[i].compareTo(points[i-1])==0)
                throw new IllegalArgumentException("array contains a repeated point.");

        // Get all 4 unique combinations
        // Create a stack of segments
        Stack<LineSegment> segStack = new Stack<LineSegment>();
        int nSegmentCnt = 0;
        if(points.length >= 4){
            for(int i = 0; i < points.length - 3; i++){
                Point p = points[i];
                for(int j = i+1; j < points.length - 2; j++ ){
                    Point q = points[j];
                    double slopePQ = p.slopeTo(q);
                    for(int k = j +1; k < points.length - 1; k++){
                        Point r = points[k];
                        double slopePR = p.slopeTo(r);
                        if(!DEBUG) {
                            if (!(slopePQ == slopePR))
                                continue;
                        }
                        for(int l = k +1; l < points.length; l++){
                            Point s = points[l];
                            double slopePS = p.slopeTo(s);
                            if(DEBUG){
                                StdOut.println(String.format("Points:(%d,%d,%d,%d)",i,j,k,l));
                                StdOut.println(String.format("Points:(p = %s,q = %s,r = %s,s = %s)",p.toString(),q.toString(),r.toString(),s.toString()));
                                StdOut.println(String.format("Slopes: PQ = %f, PR = %f, PS = %f",slopePQ, slopePR,slopePS));
                            }
                            if((slopePQ==slopePS)){
                                LineSegment ls = new LineSegment(p,s);
                                segStack.push(ls);
                                nSegmentCnt++;
                            }
                        }
                    }
                }
            }
        }

        this.nSegments = nSegmentCnt;
        this.segmentsAll = new LineSegment[nSegmentCnt];

        if(nSegmentCnt > 0){
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
        return Arrays.copyOf(this.segmentsAll,this.segmentsAll.length);
//        return this.segmentsAll;
    }

    private LineSegment[] segmentsAll;

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

//    private static boolean isUnique(Comparable[] a, int i){
//        if(i < 1)
//            throw new IllegalArgumentException("Index must be greater than zero!");
//        return !(a[i].compareTo(a[i-1]) == 0);
//    }

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

        StdOut.println("# Segments detected: " + collinear.numberOfSegments());
    }
}
