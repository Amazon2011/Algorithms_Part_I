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
                Point p = new Point(Integer.parseInt(pairs[0]), Integer.parseInt(pairs[1]));
                p.draw();
                points[index++] = p;
            }
            
            fr.close();
            br.close();
            
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}