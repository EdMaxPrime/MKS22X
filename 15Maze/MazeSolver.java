public class MazeSolver {
    private Maze board;

    public MazeSolver(String filename) {
	this(filename, false);
    }

    public MazeSolver(String filename, boolean animate) {
	board = new Maze(filename);
    }

    public void solve() {
	solve(1);
    }

    public void solve(int style) {
	Frontier f;
	if(style == 0) { //DFS
	    f = new StackFrontier();
	}
	f.add(board.getStart());
    }
}
