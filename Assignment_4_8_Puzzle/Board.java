public class Board {
    private int[][] blocks, goal;
    private int outOfPlaceBlockNumber, manhattan;
    
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.blocks = blocks;
        calculateGoal();
        calculateOutOfPlaceBlockNumber();
        calculateManhattanDistance();
    }
    
    private void calculateGoal() {
        goal = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                goal[i][j] = i * dimension() + j + 1;
            }
        }
        goal[dimension()][dimension()] = 0;
    }
    
    private void calculateOutOfPlaceBlockNumber() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != goal[i][j]) outOfPlaceBlockNumber++;
            }
        }
    }
    
    private void calculateManhattanDistance() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int x = (blocks[i][j] - 1) / dimension();
                int y = blocks[i][j] - x * dimension() - 1;
                manhattan += (Math.abs(x - i) + Math.abs(y - j));
            }
        }
    }
    
    /**
     * board dimension N
     */
    public int dimension() {
        return blocks.length;
    }
    
    /**
     * number of blocks out of place
     */
    public int hamming() {
        return outOfPlaceBlockNumber;
    }
    
    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        return manhattan;
    }
    
    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return outOfPlaceBlockNumber == 0;
    }
    
    /**
     * a boadr that is obtained by exchanging two adjacent blocks in the same row
     */
    public Board twin() {
        
    }
    
    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
        
    }
    
    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        
    }
    
    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
        
    }

    /**
     * unit tests (not graded)
     */
    public static void main(String[] args) {
        
    }
}