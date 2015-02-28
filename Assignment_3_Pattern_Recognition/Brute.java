import java.io.*;
import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        File f = new File(args[0]);
        
        try {
            
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
        
            String line = br.readLine();
            int pointNumber = Integer.parseInt(line);
            Point[] points = new Point[pointNumber];
            
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
        
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] pairs = line.trim().split("\\s+");
                Point p = new Point(Integer.parseInt(pairs[0]), Integer.parseInt(pairs[1]));
                p.draw();
                points[index++] = p;
            }
            
            fr.close();
            br.close();
            
            for (int i = 0; i < pointNumber; i++) {
                Point p1 = points[i];
                for (int j = i + 1; j < pointNumber; j++) {
                    Point p2 = points[j];
                    for (int k = j + 1; k < pointNumber; k++) {
                        Point p3 = points[k];
                        for (int l = k + 1; l < pointNumber; l++) {
                            Point p4 = points[l];
                            
                            double slope1 = p1.slopeTo(p2);
                            double slope2 = p1.slopeTo(p3);
                            double slope3 = p1.slopeTo(p4);
                            
                            if (slope1 == slope2 && slope1 == slope3) {
                                Point[] segmentPoints = new Point[4];
                                segmentPoints[0] = p1;
                                segmentPoints[1] = p2;
                                segmentPoints[2] = p3;
                                segmentPoints[3] = p4;
                                
                                Arrays.sort(segmentPoints);
                                
                                StdOut.println(segmentPoints[0] + " -> " + segmentPoints[1] + " -> " + segmentPoints[2] + " -> " + segmentPoints[3]);
                                segmentPoints[0].drawTo(segmentPoints[3]);
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}