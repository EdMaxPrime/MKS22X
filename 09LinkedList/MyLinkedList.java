public class MyLinkedList {
    private LNode start;
    private int size;

    public MyLinkedList() {
	start = null;
	size = 0;
    }
    
    private class LNode {
	public LNode next, prev;
	public int value;
	public LNode(int data, LNode n, LNode p) {
	    value = data;
	    next = n;
	    prev = p;
	}
    }

    public static void main(String[] args) {
	MyLinkedList a = new MyLinkedList();
    }
}
