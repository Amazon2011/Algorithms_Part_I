public class PercolationStats {
    private double[] x;
    private int nValue, tValue;
    
    /**
     * perform T independent experiments on an N-by-N grid
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should be larger than 0!");
        }
        nValue = N;
        tValue = T;
        x = new double[T];
        
        simulation();
    }
    
    private void simulation() {
        for (int t = 0; t < tValue; t++) {
            Percolation percolation = new Percolation(nValue);
            
            int openSitesNum = 0;
            while (!percolation.percolates()) {
                int i = (int) Math.floor(Math.random() * nValue + 1);
                int j = (int) Math.floor(Math.random() * nValue + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    openSitesNum++;
                }
            }
            
            x[t] = (double) openSitesNum / (nValue * nValue);
        }
    }
    
    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        double sum = 0.0;
        for (int t = 0; t < tValue; t++) {
            sum += x[t];
        }
        
        return sum / tValue;
    }
        
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        if (tValue == 1) return 0.0;
        
        double mean = mean();
        double variance = 0.0;
        for (int t = 0; t < tValue; t++) {
            variance += (x[t] - mean) * (x[t] - mean);
        }
        
        return Math.sqrt(variance / (tValue - 1));
    }
        
    /**
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(tValue);
    }
        
    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(tValue);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo()
                           + ", " + ps.confidenceHi());
    }
}