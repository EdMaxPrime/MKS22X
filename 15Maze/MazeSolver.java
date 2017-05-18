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
	while(f.hasNext()) {
	    Node here = f.next();
	    char type = board.get(here.loc.row(), here.loc.col());
	    if(type == 'E') {
		return; //we finished
	    }
	    Node[] more = getNeighbors(here);
	    for(int i = 0; i < 4; i++) {
		if(more[i] != null)
		    f.add(more[i]); //also mark as ?
	    }
	    board.set(here.loc.row(), here.loc.col(), '.'); //visited
	}
    }

    public Node[] getNeighbors(Node center) {
	Node[] neighbors = new Node[4];
	int howMany = 0;
	int r = center.loc.row(), c = center.loc.col();
	if(board.get(r, c-1) == ' ') { //left
	    neighbors[0] = new Node(new Location(r, c-1), center, -1, -1, false);
	    howMany++;
	}
	return neighbors;
    }
}
