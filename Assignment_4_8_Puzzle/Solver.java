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
        
        MinPQ<Node> boardNodePQ = new MinPQ<Node>();
        boardNodePQ.insert(new Node(initial, null));
        List<Board> solutionBoardList = new ArrayList<Board>();
        
        Board twinBoard = initial.twin();
        MinPQ<Node> twinBoardNodePQ = new MinPQ<Node>();
        twinBoardNodePQ.insert(new Node(twinBoard, null));
        
        while (true) {
            Node minBoardNode = boardNodePQ.delMin();
            solutionBoardList.add(minBoardNode.board);
            if (minBoardNode.board.isGoal()) {
                isSolvable = true;
                moves = minBoardNode.moves;
                solution = (Iterable<Board>) solutionBoardList;
                break;
            }
            
            Iterator<Board> neighbors = minBoardNode.board.neighbors().iterator();
            while (neighbors.hasNext()) {
                Board neighbor = neighbors.next();
                if (minBoardNode.previousNode == null || !neighbor.equals(minBoardNode.previousNode.board)) boardNodePQ.insert(new Node(neighbor, minBoardNode));
            }
            
            Node twinMinBoardNode = twinBoardNodePQ.delMin();
            if (twinMinBoardNode.board.isGoal()) {
                isSolvable = false;
                moves = -1;
                solution = null;
                break;
            }
            
            Iterator<Board> twinNeighbors = twinMinBoardNode.board.neighbors().iterator();
            while (twinNeighbors.hasNext()) {
                Board twinNeighbor = twinNeighbors.next();
                if (minBoardNode.previousNode == null || !twinNeighbor.equals(twinMinBoardNode.previousNode.board)) {
                    twinBoardNodePQ.insert(new Node(twinNeighbor, twinMinBoardNode));
                }
            }
        }
    }
    
    private class Node implements Comparable<Node> {
        Node previousNode;
        Board board;
        int moves, priority;
        
        Node(Board board, Node previousNode) {
            this.board = board;
            this.previousNode = previousNode;
            moves = previousNode == null ? 0 : previousNode.moves + 1;
            priority = board.manhattan() + moves;
        }
        
        public int compareTo(Node node) {
            if (this.priority > node.priority) return 1;
            else if (this.priority < node.priority) return -1;
            return 0;
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