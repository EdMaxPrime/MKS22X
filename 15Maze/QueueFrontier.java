import java.util.LinkedList;

public class QueueFrontier implements Frontier {
    private LinkedList<Node> frontier;

    public QueueFrontier() {
	frontier = new LinkedList<Node>();
    }

    public Node next() {
	return frontier.removeFirst();
    }

    public boolean hasNext() {
	return frontier.size() != 0;
    }

    public void add(Node n) {
	frontier.addLast(n);
    }
}
