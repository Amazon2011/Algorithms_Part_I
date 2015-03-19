import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private int[][] blocks;
    private int outOfPlaceBlockNumber, manhattan;
    
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.blocks = blocks;
        calculateOutOfPlaceBlockNumber();
        calculateManhattanDistance();
    }
    
    private void calculateOutOfPlaceBlockNumber() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * dimension() + j + 1) outOfPlaceBlockNumber++;
            }
        }
    }
    
    private void calculateManhattanDistance() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] != 0) {
                    int x = (blocks[i][j] - 1) / dimension();
                    int y = blocks[i][j] - x * dimension() - 1;
                    manhattan += (Math.abs(x - i) + Math.abs(y - j));
                }
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
        int[][] twinBlocks = new int[dimension()][dimension()];
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        } 
        
        for (int i = 0; i < dimension(); i++) {
            if (twinBlocks[i][0] != 0 && twinBlocks[i][1] != 0) {
                int tmp = twinBlocks[i][0];
                twinBlocks[i][0] = twinBlocks[i][1];
                twinBlocks[i][1] = tmp;
                break;
            }
        }
        
        return new Board(twinBlocks);
    }
    
    private int[][] cloneBlocks() {
        int[][] cloneBlocks = new int[dimension()][dimension()];
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                cloneBlocks[i][j] = blocks[i][j];
            }
        }
        
        return cloneBlocks;
    }
    
    /**
     * does this board equal y?
     */
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (!(y instanceof Board)) return false;
        Board yBoard = (Board) y;
        
        if (this.dimension() != yBoard.dimension()) return false;
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (this.blocks[i][j] != yBoard.blocks[i][j]) return false;
            }
        }
        
        return true;
    }
    
    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        List<Board> neighborList = new ArrayList<Board>(4);
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    if (i - 1 >= 0) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i - 1][j];
                        neighbor[i - 1][j] = 0;
                        neighborList.add(new Board(neighbor));
                    }
                    if (i + 1 < dimension()) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i + 1][j];
                        neighbor[i + 1][j] = 0;
                        neighborList.add(new Board(neighbor));
                    }
                    if (j - 1 >= 0) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i][j - 1];
                        neighbor[i][j - 1] = 0;
                        neighborList.add(new Board(neighbor));
                    }
                    if (j + 1 < dimension()) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i][j + 1];
                        neighbor[i][j + 1] = 0;
                        neighborList.add(new Board(neighbor));
                    }
                }
            }
        }
        
        return (Iterable<Board>) neighborList;
    }
    
    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
        String string = dimension() + "\n";
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                string += " " + blocks[i][j];
            }
            string += "\n";
        }
        
        return string;
    }

    /**
     * unit tests (not graded)
     */
    public static void main(String[] args) {
        int[][] myBlocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(myBlocks);
        StdOut.println("dimension = " + board.dimension());
        StdOut.println("toString = \n" + board);
        StdOut.println("hamming = " + board.hamming());
        StdOut.println("manhattan = " + board.manhattan());
        StdOut.println("isGoal = " + board.isGoal());
        StdOut.println("twin = \n" + board.twin());
        
        Iterator<Board> neighbors = board.neighbors().iterator();
        while (neighbors.hasNext()) {
            StdOut.println(neighbors.next());
        }
    }
}