import java.util.Arrays;

public class Fast {
    
    public static void main(String[] args) {
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        ResizingArrayQueue<String> oneLineIndex = new ResizingArrayQueue<String>();
        
        class Node implements Comparable<Node> {
            Point p;
            double dist;
            int idx;
            
            @Override
            public int compareTo(Node that) {
                if (dist < that.dist) return -1;
                if (dist > that.dist) return 1;
                return 0;
            }
        }
        
        Node[] orgNodes = new Node[N];
        
        for (int i = 0; i < N; i++) {
            orgNodes[i] = new Node();
            int x = in.readInt();
            int y = in.readInt();
            orgNodes[i].p = new Point(x, y);
            orgNodes[i].dist = y * 32768 + x;
        }
        QuickX.sort(orgNodes);
        for (int i = 0; i < N; i++) {
            orgNodes[i].idx = i;
        }
//        for (int i = 0; i < N; i++) {
//            System.out.println(orgNodes[i].p);
//        }

        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) {
            for (int s = 0; s < N; s++) {
                nodes[s] = orgNodes[s];
            }
            
            if (i > 0) {
                Node originNode = nodes[i];
                nodes[i] = nodes[0];
                nodes[0] = originNode;
            }            
//            System.out.println("Before sort");
//            for (int t = 0; t < N; t++) {
//                System.out.println(nodes[t].p + " - " + nodes[t].p + " : " + nodes[t].dist);
//            }

            for (int j = 0; j < N; j++) {
                nodes[j].dist = nodes[0].p.slopeTo(nodes[j].p);
            }

            QuickX.sort(nodes);
            
//            System.out.println("After sort");
//            for (int t = 0; t < N; t++) {
//                System.out.println(nodes[t].p + " - " + nodes[t].p + " : " + nodes[t].dist);
//            }

            int first = 1;
            int last = 2;
            while (last < N) {
//                System.out.println(first + " -- " + last);
                if (nodes[last].dist == nodes[first].dist && last < N-1) {
                    last += 1;
                }
                else {
//                    System.out.println("Inside else: " + first + " -- " + last);
                    int diff = last - first;
                    if (last == N-1 && nodes[last].dist == nodes[first].dist) diff += 1;
//                    System.out.println("diff = " + diff);
                    if (diff >= 3) {
                        Integer[] oneLine = new Integer[diff+1];
                        oneLine[0] = nodes[0].idx;
                        for (int k = 0; k < diff; k++) {
                            oneLine[k+1] = nodes[first+k].idx;
                        }
                        QuickX.sort(oneLine);
                        String[] oneLineString = new String[diff+1];
                        StdDraw.setPenRadius(0.01);  // make the points a bit larger
                        for (int k = 0; k < diff+1; k++) {
                            oneLineString[k] = orgNodes[oneLine[k]].p.toString();
                            orgNodes[oneLine[k]].p.draw();
                        }
                        StdDraw.setPenRadius();
                        orgNodes[oneLine[0]].p.drawTo(orgNodes[oneLine[diff]].p);
//                        for (int k = 0; k < diff; k++) {
//                            orgNodes[oneLine[k]].p.drawTo(orgNodes[oneLine[k+1]].p);
//                        }
                        String strOneLine = Arrays.toString(oneLineString);
                        oneLineIndex.enqueue(strOneLine);
//                        for (int k = 0; k <= diff; k++) {
//                            System.out.print(oneLine[k] + " -> ");
//                        }
//                        System.out.println();
//                        System.out.print(nodes[0].p + " -> ");
//                        for (int k = 0; k < diff; k++) {
//                            System.out.print(nodes[first+k].p);
//                            if (k < diff - 1) {
//                                System.out.print(" -> ");
//                            }
//                        }
//                        System.out.println();
                    }
                    first = last;
                    last += 1;
                }
            }                
        }
        StdDraw.show(0);
        
        ResizingArrayQueue<String> newQ = new ResizingArrayQueue<String>();
        for (String s1 : oneLineIndex) {
//            System.out.println(s1);
            if (newQ.isEmpty()) {
                newQ.enqueue(s1);
            } else {
                boolean flag = false;
                for (String s2 : newQ) {
//                    System.out.println(s1 + " -- " + s2);
                    if (s1.equals(s2)) flag = true;
                }
                if (!flag) {
                    newQ.enqueue(s1);
                }
//                System.out.println(flag);
            }
        }
        for (String s : newQ) {
            System.out.println(s.replace("[", "").replace("]", "").replace("), (", ") -> ("));
        }
    }
}