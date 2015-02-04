public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[][] open;
    private boolean[][] full;
    private int nValue;
    
    /**
     * create N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be larger than 0!");
        }
        
        uf = new WeightedQuickUnionUF(N * N + 2);
               
        open = new boolean[N][N];
        nValue = N;
    }
    
    /**
     * open site (row i, column j) if it is not open already
     */
    public void open(int i, int j) {
        if (isOpen(i, j) || isOutOfBound(i, j)) return;

        open[i - 1][j - 1] = true;
        
        if (i == 1) uf.union(0, getNodeValue(i, j));
        if (i == nValue) uf.union(nValue * nValue + 1, getNodeValue(i, j));
        
        unionAdjacentSites(i, j);  
    }
    
    private void unionAdjacentSites(int i, int j) {
        if (!isOutOfBound(i - 1, j) && open[i - 2][j - 1]) {
            uf.union(getNodeValue(i - 1, j), getNodeValue(i, j));
        }
        if (!isOutOfBound(i + 1, j) && open[i][j - 1]) {
            uf.union(getNodeValue(i + 1, j), getNodeValue(i, j));
        }
        if (!isOutOfBound(i, j - 1) && open[i - 1][j - 2]) {
            uf.union(getNodeValue(i, j - 1), getNodeValue(i, j));
        }
        if (!isOutOfBound(i, j + 1) && open[i - 1][j]) {
            uf.union(getNodeValue(i, j + 1), getNodeValue(i, j));
        }
    }
    
    /**
     * is site (row i, column j) open?
     */    
    public boolean isOpen(int i, int j) {
        catchOutOfBoundException(i, j);
        return open[i - 1][j - 1];
    }
    
    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        catchOutOfBoundException(i, j);
        return uf.connected(0, getNodeValue(i, j));
    }
    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return uf.connected(0, nValue * nValue + 1);
    }
    
    private boolean isOutOfBound(int i, int j) {
        return i <= 0 || j <= 0 || i > nValue || j > nValue;
    }
    
    private void catchOutOfBoundException(int i, int j) {
        if (isOutOfBound(i, j)) {
            throw new IndexOutOfBoundsException("Argument outside range!");
        } 
    }
    
    private int getNodeValue(int i, int j) {
        return nValue * (i - 1) + j;
    }
}