import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Solver {
    private boolean isSolvable;
    private int moves;
    private Iterable<Board> solution;
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        
        MinPQ<Board> boardPQ = new MinPQ<Board>();
        boardPQ.insert(initial);
        List<Board> solutionBoardList = new ArrayList<Board>();
        Board twinBoard = initial.twin();
        MinPQ<Board> twinBoardPQ = new MinPQ<Board>();
        twinBoardPQ.insert(twinBoard);
        //solve board and twinBoard simutaneously
        while (true) {
            Board minBoard = boardPQ.delMin();
            solutionBoardList.add(minBoard);
            if (minBoard.isGoal()) {
                isSolvable = true;
                solution = (Iterable<Board>) solutionBoardList;
                break;
            }
            
            Iterator<Board> neighbors = minBoard.neighbors().iterator();
            while (neighbors.hasNext()) {
                Board neighbor = neighbors.next();
                if (!neighbor.equals(minBoard.previousBoard)) boardPQ.insert(neighbor);
            }
            
            moves++;
            
            Board twinMinBoard = twinBoardPQ.delMin();
            if (twinMinBoard.isGoal()) {
                isSolvable = false;
                moves = -1;
                solution = null;
                break;
            }
            
            Iterator<Board> twinNeighbors = twinMinBoard.neighbors().iterator();
            while (twinNeighbors.hasNext()) {
                Board twinNeighbor = twinNeighbors.next();
                if (!twinNeighbor.equals(twinMinBoard.previousBoard)) twinBoardPQ.insert(twinNeighbor);
            }
        }
    }
    
    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }
        
    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return moves;
    }
        
    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        return solution;
    }

    /**
     * solve a slider puzzle (given below)    
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}