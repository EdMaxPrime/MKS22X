import java.lang.Comparable;

public class Location implements Comparable<Location> {
    private int row, col;
    private Location previous;

    public Location(int r, int c, Location p) {
	row = r;
	col = c;
	previous = p;
    }

    public int compareTo(Location other) {
	return 0;
    }
}
