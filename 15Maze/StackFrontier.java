import java.util.Stack;

public class StackFrontier implements Frontier {
    private Stack<Node> frontier;

    public StackFrontier() {
	frontier = new Stack<Node>();
    }

    public void add(Node n) {
	frontier.push(n);
    }

    public Node next() {
	return frontier.pop();
    }

    public boolean hasNext() {
	return frontier.empty();
    }
}
