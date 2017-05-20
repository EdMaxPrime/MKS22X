public class PriorityQueue implements Frontier {
    MyHeap heap;

    public PriorityQueue() {
	heap = new MyHeap(false);
    }

    public void add(Node n) {
	heap.add(n);
    }

    public boolean hasNext() {
	return heap.size() != 0;
    }

    public Node next() {
	return heap.remove();
    }
}
