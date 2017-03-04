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
    
    public void generate(int seed) {
        rng = new Random(seed);
	
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
    }
}
