public class MyLinkedList {
    private LNode start;
    private int size;

    public MyLinkedList() {
	start = null;
	size = 0;
    }

    private LNode getNode(int index) {
	LNode current = start;
	while(index > 0) {
	    current = current.next;
	    index--;
	}
	return current;
    }

    public int size() {return size;}

    public String toString() {
	LNode current = start;
	if(current == null) return "[]";
	String str = "[";
	while(current != null) {
	    str += current.value;
	    if(current.next == null) {
		str += "]";
	    } else {
		str += ", ";
	    }
	    current = current.next;
	}
	return str;
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
	System.out.println(a);
    }
}
