import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Fast {
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
                if (pairs.length == 2) {
                    int x = Integer.parseInt(pairs[0]);
                    int y = Integer.parseInt(pairs[1]);
                    Point p = new Point(x, y);
                    p.draw();
                    points[index++] = p;
                }
            }
            
            fr.close();
            br.close();
            
            Point[] pointsCopy = new Point[pointNumber];
            
            for (int i = 0; i < pointNumber; i++) {
                pointsCopy[i] = points[i];
            }
            
            for (Point p : pointsCopy) {
                Arrays.sort(points, p.SLOPE_ORDER);
                
                double slope = Double.NEGATIVE_INFINITY;
                int contNumber = 0;
                for (int i = 0; i < points.length; i++) {
                    double slopeI = p.slopeTo(points[i]);
                    if (slopeI == slope) {
                        contNumber++;
                        if ((i == points.length - 1 || slope != p.slopeTo(points[i + 1])) && contNumber >= 3) {
                            Point[] segmentPoints = new Point[contNumber + 1];
                            segmentPoints[0] = p;
                            
                            for (int j = 1; j <= contNumber; j++) {
                                segmentPoints[j] = points[i + 1 - j];
                            }
                            
                            Arrays.sort(segmentPoints);
                            
                            if (p.slopeTo(segmentPoints[0]) == Double.NEGATIVE_INFINITY) {
                                String segmentStr = "";
                                for (int k = 0; k < contNumber + 1; k++) {
                                    if (k != contNumber) segmentStr += (segmentPoints[k] + " -> ");
                                    else segmentStr += segmentPoints[k];
                                }
                                StdOut.println(segmentStr);
                                segmentPoints[0].drawTo(segmentPoints[contNumber]);
                            }
                        }
                    } else {
                        slope = slopeI;
                        contNumber = 1;
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