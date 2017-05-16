import java.lang.Comparable;

public class Location implements Comparable<Location> {
    private int row, col;

    public Location(int r, int c) {
	row = r;
	col = c;
    }

    public int compareTo(Location other) {
	return 0;
    }
}
