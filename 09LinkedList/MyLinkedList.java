public class MyLinkedList {
    private LNode start, last;
    private int size;

    public MyLinkedList() {
	start = null;
	last = null;
	size = 0;
    }

    public boolean add(int data) {
	add(size, data);
	return true;
    }

    public void add(int index, int data) throws IndexOutOfBoundsException {
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
	} else {
	    throw new IndexOutOfBoundsException("Cannot add to "+index);
	}
    }

    public int set(int index, int value) throws IndexOutOfBoundsException {
	if(index < 0 || index >= size)
	    throw new IndexOutOfBoundsException("Invalid index "+index);
	LNode affected = getNode(index);
	int old = affected.value;
	affected.value = value;
	return old;
    }

    public int remove(int index) throws IndexOutOfBoundsException {
	if(index < 0 || index >= size)
	    throw new IndexOutOfBoundsException("Can't remove from "+index);
	LNode affected = getNode(index);
	LNode before = affected.prev, after = affected.next;
	if(index > 0) before.next = after;
	else          start = after;
	if(index < size-1) after.prev = before;
	else               last = before;
	size--;
	return affected.value;
    }

    public int get(int index) throws IndexOutOfBoundsException{
	if(index < 0 || index >= size)
	    throw new IndexOutOfBoundsException("Invalid index "+index);
        return getNode(index).value;
    }

    private LNode getNode(int index) {
	if(index < 0) index += size; //backwards indexing
	LNode current = start;
	if(size == 1 || index < size/2) {
	    while(index > 0) {
		current = current.next;
		index--;
	    }
	} else {
	    current = last;
	    while(index < size-1) {
		current = current.prev;
		index++;
	    }
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
	a.set(0, 0);
	System.out.println(a);
	a.remove(a.size()-1);
	a.remove(0);
	System.out.println(a);
    }
}
