import java.lang.Comparable;

public class Node implements Comparable<Node> {
    private Location loc;
    private Node previous;
    private int distToStart;
    private int distToGoal;

    public Node(Location l, Node prev, int s, int g) {
	loc = l;
	previous = prev;
	distToStart = s;
	distToGoal  = g;
    }

    public int compareTo(Node other) {
	return (distToStart+distToGoal) - (other.distToStart+other.distToGoal);
    }
}
