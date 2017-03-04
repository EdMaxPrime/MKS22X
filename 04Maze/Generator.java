import java.util.Random;

public class Generator {
    private int rows, cols;
    boolean border;
    Random rng;
    private Cell[][] maze;
    public Generator(int width, int height) {
	rows = height;
	cols = width;
    }

    /**
       Uses Eller's algorithm to create a maze in linear time.
     */
    public void generate(int seed) {
        rng = new Random(seed);
	reset();
	for(int row = 0; row < rows; row++) {
	    for(int col = 1; col < cols; col++) {
		if(rng.nextBoolean() && !maze[row][col].connected(maze[row][col-1])) {
		    maze[row][col].absorb(maze[row][col-1], 'W');
		}
	    }
	}
    }

    public String dump() {
	String str = "";
	char[][] chars = new char[rows*3][cols*3];
	for(int r = 0; r < rows; r++) {
	    for(int c = 0; c < cols; c++) {
		overwrite(chars, maze[r][c].toChar(), r*3, c*3);
	    }
	}
	for(int r = 0; r < rows; r++) {
	    str += (new String(chars[r])) + "\n";
	}
	str = str.substring(0, str.length()-1);
	return str;
    }

    private void overwrite(char[][] orig, char[][] _new, int row, int col) {
	for(int r = 0; r < _new.length; r++) {
	    for(int c = 0; c < _new[r].length; c++) {
		orig[row+r][col+c] = _new[r][c];
	    }
	}
    }

    private void reset() {
	maze = new Cell[rows][cols];
	int id = 0;
	for(int r = 0; r < rows; r++) {
	    for(int c = 0; c < cols; c++) {
		maze[r][c] = new Cell(new Set(id));
		id++;
	    }
	}
    }

    public String toString() {
	String str = "";
	for(Cell[] line : maze) {
	    for(Cell c : line) {
		str += String.format("%3s", c.toString());
	    }
	    str += "\n";
	}
	return str;
    }

    private class Cell {
	private boolean north, south, east, west;
	private Set set;
	public Cell(Set s) {
	    set = s;
	    north = false;
	    south = false;
	    east = false;
	    west = false;
	}
	public void absorb(Cell neighbor, char dir) {
	    if(!sameFamily(neighbor)) {
		neighbor.set.joinFamily(this.set.getFamily());
	    }
	    switch(dir) {
	    case 'N':
		north = true;
		neighbor.south = true;
		break;
	    case 'E':
		east = true;
		neighbor.west = true;
		break;
	    case 'S':
		south = true;
		neighbor.north = true;
		break;
	    case 'W':
		west = true;
		neighbor.east = true;
		break;
	    }
	}
	public boolean sameFamily(Cell other) {
	    return other.set.getFamily().equals(this.set.getFamily());
	}
	public boolean connected(Cell neighbor) {
	    return (north && neighbor.south) || //above
		(east && neighbor.west) ||      //left
		(south && neighbor.north) ||    //below
		(west && neighbor.east);        //right
	}
	public char[][] toChar() {
	    char[][] grid = {
		{' ', ' ', ' '},
		{' ', ' ', ' '},
		{' ', ' ', ' '}
	    };
	    if(north || west) grid[0][0] = '#';
	    if(north || east) grid[0][2] = '#';
	    if(south || west) grid[2][0] = '#';
	    if(south || east) grid[2][2] = '#';
	    if(north) grid[0][1] = '#';
	    if(east)  grid[1][2] = '#';
	    if(south) grid[2][1] = '#';
	    if(west)  grid[1][0] = '#';
	    return grid;
	}
	public String toString() {
	    return set.getFamily().getID() + "";
	}
    }

    private class Set {
	private int id;
	private Set parent;
	public Set(int _id) {
	    id = _id;
	    parent = null;
	}
	public void joinFamily(Set family) {
	    parent = family;
	}
	public boolean independent() {
	    return parent == null;
	}
	public Set getFamily() {
	    if(independent()) return this;
	    else              return parent.getFamily();
	}
	public boolean equals(Set other) {
	    return other.id == this.id;
	}
	public int getID() {return id;}
    }

    public static void main(String[] args) {
	Generator g = new Generator(10, 1);
	g.generate(0);
	System.out.println(g.dump());
	System.out.println(g);
    }
}
