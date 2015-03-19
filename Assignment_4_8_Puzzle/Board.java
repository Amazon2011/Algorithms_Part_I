import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Board implements Comparable<Board>{
    private int[][] blocks, goal;
    private int outOfPlaceBlockNumber, manhattan, moves, priority;
    public Board previousBoard;
    
    /**
     * construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.blocks = blocks;
        calculateGoal();
        calculateOutOfPlaceBlockNumber();
        calculateManhattanDistance();
        priority = manhattan + moves;
    }
    
    private void calculateGoal() {
        goal = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                goal[i][j] = i * dimension() + j + 1;
            }
        }
        goal[dimension() - 1][dimension() - 1] = 0;
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
        int[][] twinBlocks = new int[dimension()][dimension()];
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        } 
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension() - 1; j++) {
                if (twinBlocks[i][j] != 0 && twinBlocks[i][j + 1] != 0) {
                    int tmp = twinBlocks[i][j];
                    twinBlocks[i][j] = twinBlocks[i][j + 1];
                    twinBlocks[i][j + 1] = tmp;
                }
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
                        Board neighborBoard = new Board(neighbor);
                        neighborBoard.moves = moves + 1;
                        neighborBoard.priority = neighborBoard.manhattan + neighborBoard.moves;
                        neighborBoard.previousBoard = this;
                        neighborList.add(neighborBoard);
                    }
                    if (i + 1 < dimension()) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i + 1][j];
                        neighbor[i + 1][j] = 0;
                        Board neighborBoard = new Board(neighbor);
                        neighborBoard.moves = moves + 1;
                        neighborBoard.priority = neighborBoard.manhattan + neighborBoard.moves;
                        neighborBoard.previousBoard = this;
                        neighborList.add(neighborBoard);
                    }
                    if (j - 1 >= 0) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i][j - 1];
                        neighbor[i][j - 1] = 0;
                        Board neighborBoard = new Board(neighbor);
                        neighborBoard.moves = moves + 1;
                        neighborBoard.priority = neighborBoard.manhattan + neighborBoard.moves;
                        neighborBoard.previousBoard = this;
                        neighborList.add(neighborBoard);
                    }
                    if (j + 1 < dimension()) {
                        int[][] neighbor = cloneBlocks();
                        neighbor[i][j] = neighbor[i][j + 1];
                        neighbor[i][j + 1] = 0;
                        Board neighborBoard = new Board(neighbor);
                        neighborBoard.moves = moves + 1;
                        neighborBoard.priority = neighborBoard.manhattan + neighborBoard.moves;
                        neighborBoard.previousBoard = this;
                        neighborList.add(neighborBoard);
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
        String string = "";
        
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                string += blocks[i][j] + " ";
            }
            string += "\n";
        }
        
        return string;
    }
    
    public int compareTo(Board board) {
        if (this.priority > board.priority) return 1;
        else if (this.priority < board.priority) return -1;
        return 0;
    }

    /**
     * unit tests (not graded)
     */
    public static void main(String[] args) {
        int[][] myBlocks = {{1, 3, 2}, {0, 4, 5}, {8, 6, 7}};
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