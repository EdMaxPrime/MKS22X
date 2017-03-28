public class MyLinkedList {
    private LNode start, last;
    private int size;

    public MyLinkedList() {
	start = null;
	last = null;
	size = 0;
    }

    public void add(int data) {
	add(size, data);
    }

    public void add(int index, int data) {
	if(index >= 0 && index <= size) {
	    if(size == 0) {
		start = new LNode(data, null, null);
	        last = start;
	    }
	    else if(index == 0) {
		start.prev = new LNode(data, null, start);
		start = start.prev;
	    }
	    else if(index == size) {
		last.next = new LNode(data, last, null);
		last = last.next;
	    }
	    else {
	        LNode before = getNode(index - 1),
		      after  = before.next;
		before.next = new LNode(data, before, after);
		after.prev = before.next;
	    }
	    size++;
	}
    }

    public int get(int index) throws IndexOutOfBoundsException{
	if(index < 0 || index >= size)
	    throw new IndexOutOfBoundsException("Invalid index "+index);
        return getNode(index).value;
    }

    private LNode getNode(int index) {
	if(index < 0) index += size; //backwards indexing
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
	public LNode(int data, LNode p, LNode n) {
	    value = data;
	    next = n;
	    prev = p;
	}
    }

    public static void main(String[] args) {
	MyLinkedList a = new MyLinkedList();
	System.out.println(a);
	a.add(1);
	System.out.println(a);
	a.add(0, -1);
	System.out.println(a);
	a.add(1, 2);
	a.add(1, 3);
	System.out.println(a);
	a.add(4);
	a.add(5);
	System.out.println(a);
	System.out.printf("2nd is %d%n", a.get(1));
    }
}
