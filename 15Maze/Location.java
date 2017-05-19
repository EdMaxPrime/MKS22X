public class Location {
    private int row, col;

    public Location(int r, int c) {
	row = r;
	col = c;
    }

    public int row() {return row;}
    public int col() {return col;}

    public boolean equals(Location l) {
	return l.row == row && l.col == col;
    }
}
