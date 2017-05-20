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
	Frontier f = null;
	Node end = null;
	if(style == 0) { //DFS
	    f = new StackFrontier();
	}
	f.add(board.start);
	while(f.hasNext()) {
	    Node here = f.next();
	    char type = board.get(here.loc.row(), here.loc.col());
	    //System.out.println(board.toString(800));
	    Node[] more = getNeighbors(here);
	    for(int i = 0; i < 4; i++) {
		if(more[i] != null) {
		    if(board.end.loc.equals(more[i].loc)) {
			stamp(more[i], 'E');
			end = more[i];
			break; //found the end
		    }
		    f.add(more[i]);
		    stamp(more[i], '?');
		}
	    }
	    stamp(here, '.'); //visited
	}
	System.out.println("\033[0;m finished loop");
	if(end != null) {
	    while(end.getPrevious() != null) {
		end = end.getPrevious();
		stamp(end, '@');
	    }
	    stamp(end, 'S');
	}
    }

    public Node[] getNeighbors(Node center) {
	Node[] neighbors = new Node[4];
	int howMany = 0;
	int r = center.loc.row(), c = center.loc.col();
	if(board.get(r, c-1) == ' ') { //left
	    neighbors[0] = new Node(new Location(r, c-1), center, -1, -1, false);
	}
	if(board.get(r-1, c) == ' ') { //down
	    neighbors[1] = new Node(new Location(r-1, c), center, -1, -1, false);
	}
	if(board.get(r, c+1) == ' ') { //right
	    neighbors[2] = new Node(new Location(r, c+1), center, -1, -1, false);
	}
	if(board.get(r+1, c) == ' ') { //top
	    neighbors[3] = new Node(new Location(r+1, c), center, -1, -1, false);
	}
	return neighbors;
    }

    private void stamp(Node n, char c) {
	board.set(n.loc.row(), n.loc.col(), c);
    }

    public String toString() {
	return board.toString();
    }

    public static void main(String[] args) {
	MazeSolver m = new MazeSolver("maze2.txt");
	System.out.println(m);
	m.solve(0);
        System.out.println(m.toString());
    }
}
