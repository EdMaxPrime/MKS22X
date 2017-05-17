import java.lang.Comparable;

public class Node implements Comparable<Node> {
    private Location loc;
    private Node previous;
    private int distToStart;
    private int distToGoal;
    private boolean astar;

    public Node(Location l, Node prev, int s, int g, boolean _astar) {
	loc = l;
	previous = prev;
	distToStart = s;
	distToGoal  = g;
	astar = _astar;
    }

    public int compareTo(Node other) {
	if(astar)
	    return (distToStart+distToGoal) - (other.distToStart+other.distToGoal);
	return distToGoal - other.distToGoal;
    }
}