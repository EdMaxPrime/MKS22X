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
	System.out.println("Goal: "+board.end);
	Frontier f = null;
	if(style == 0) { //DFS
	    f = new StackFrontier();
	}
	f.add(board.start);
	String log = "";
	while(f.hasNext()) {
	    Node here = f.next();
	    char type = board.get(here.loc.row(), here.loc.col());
	    System.out.println(board.toString(800));
	    if(type == 'E') {
		System.out.println("E "+here);
		return; //we finished
	    }
	    Node[] more = getNeighbors(here);
	    for(int i = 0; i < 4; i++) {
		if(more[i] != null) {
		    //System.out.println(more[i]);
		    if(board.end.loc.equals(more[i].loc)) {
		        System.out.println("end:"+more[i]);
			return; //found the end
		    } else {
			log += "\n"+more[i]+"=="+board.end;
		    }
		    f.add(more[i]);
		    board.set(more[i].loc.row(), more[i].loc.col(), '?');
		}
	    }
	    board.set(here.loc.row(), here.loc.col(), '.'); //visited
	}
	System.out.println("\033[0;m finished loop");
	System.out.println(log);
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

    public String toString() {
	return board.toString();
    }

    public static void main(String[] args) {
	MazeSolver m = new MazeSolver("maze1.txt");
	System.out.println(m);
	m.solve(0);
        System.out.println(m.toString());
    }
}
