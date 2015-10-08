
public class Brute {
    public static void main(String[] args) {

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        double s1, s2, s3;
        
        Point[] points = new Point[N];
                
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        QuickX.sort(points);
//        for (int i = 0; i < N; i++) {
//            System.out.println(points[i]);
//        }
//        System.out.println("******************************");
        
        for (int i = 0; i < N-3; i++) {
            for (int j = i+1; j < N-2; j++) {
                for (int k = j+1; k < N-1; k++) {
                    for (int l = k + 1; l < N; l++) {
                        s1 = points[i].slopeTo(points[j]);
                        s2 = points[i].slopeTo(points[k]);
                        s3 = points[i].slopeTo(points[l]);
                        
                        if (s1 == s2 && s1 == s3) {
                            System.out.print(points[i]);
                            System.out.print(" -> ");
                            System.out.print(points[j]);
                            System.out.print(" -> ");
                            System.out.print(points[k]);
                            System.out.print(" -> ");
                            System.out.print(points[l]);
                            System.out.println();

                            StdDraw.setPenRadius(0.01);  // make the points a bit larger
                            points[i].draw();
                            points[j].draw();
                            points[k].draw();
                            points[l].draw();
                            
                            StdDraw.setPenRadius();
                            points[i].drawTo(points[l]);
                            StdDraw.show(0);
                        }
                    }
                }
            }
        }
    }
}