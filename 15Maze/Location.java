public class Location {
    private int row, col;
    private Location previous;

    public Location(int r, int c, Location p) {
	row = r;
	col = c;
	previous = p;
    }
}
