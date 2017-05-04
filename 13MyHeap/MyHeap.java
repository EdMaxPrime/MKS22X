import java.util.NoSuchElementException;

public class MyHeap {
    String[] contents;
    int size, dir;

    public MyHeap(boolean maxMin) {
	contents = new String[16];
	size = 0;
	dir = maxMin? 1 : -1;
    }

    public MyHeap() {
	this(true);
    }

    public void add(String str) {
	makeSpace();
	contents[1+size] = str;
	size++;
	bubbleUp();
    }

    public String remove() {
	if(size < 1)
	    throw new NoSuchElementException("Cant remove from empty heap!");
	String biggest = contents[1];
	//move a bottom row element to the root
	contents[1] = contents[size];
	size--;
	//now move it back down and find a replacement root
	bubbleDown();
	return biggest;
    }

    private void makeSpace() {
	if(contents.length <= size+1) { //because index#0 isnt used
	    String[] bigger = new String[contents.length*2];
	    for(int i = 1; i < contents.length; i++) {
		bigger[i] = contents[i];
	    }
	    contents = bigger;
	}
    }

    /**
       Puts the last added element in the right position
     */
    private void bubbleUp() {
	int index = size;
	int parent = index/2;
	while(index > 1) {
	    if(dir*contents[index].compareTo(contents[parent]) > 0) {
		String temp = contents[index];
		contents[index] = contents[parent];
		contents[parent] = temp;
		index = parent;
		parent /= 2;
	    } else {
		break;
	    }
	}
    }

    /**
       Puts the root element in the right position
     */
    private void bubbleDown() {
	int index = 1, child0 = index*2, child1 = index*2 + 1;
	while(index < size) {
	    if(child0 > size) break; //no children
	    else if(child1 > size) { //one child
		if(!ordered(index, child0)) {
		    swap(index, child0);
		}
		break;
	    }
	    else { //two children
		if(ordered(index, child0) && ordered(index, child1)) {
		    break; //everything in its right place
		}
		else if(!ordered(index, child0) && ordered(index, child1)) {
		    swap(index, child0);
		    index = child0;
		    child0 = index * 2;
		    child1 = child0 + 1;
		}
		else if(ordered(index, child0) && !ordered(index, child1)) {
		    swap(index, child1);
		    index = child1;
		    child0 = index * 2;
		    child1 = child0 + 1;
		}
		else {
		    int which = child0;
		    if(!ordered(child0, child1)) which = child1;
		    swap(index, which);
		    index = which;
		    child0 = index * 2;
		    child1 = child0 + 1;
		}
	    }
	}
    }

    /**
       Swaps the strings at the two indices
     */
    private void swap(int index1, int index2) {
	String temp = contents[index1];
	contents[index1] = contents[index2];
	contents[index2] = temp;
    }

    /**
       Returns true if the heap's property is maintained
       between the two elements(either min or max, depends
       on the value of dir).
     */
    private boolean ordered(int parent, int child) {
	return dir*contents[parent].compareTo(contents[child]) > 0;
    }

    /**
       Compares first element to the second and then the third.
       Returns 0 if everything is in order, 1 if the 2nd element
       is out of order and 2 is the 3rd element is out of order.
       @param parent   index of parent
       @param child1   index of first child to check
       @param child2   index of second child to check
     */
    private int check(int parent, int child1, int child2) {
	if(!ordered(parent, child1)) return 1;
	if(!ordered(parent, child2)) return 2;
	return 0;
    }

    public String toStringDebug() {
	String str = "[";
	for(int i = 0; i < contents.length; i++) {
	    str += contents[i];
	    if(i < contents.length-1) str += "  ";
	}
	return str+"]";
    }

    public String toString() {
	String str = "[";
	for(int i = 1; i <= size; i++) {
	    str += contents[i];
	    if(i < size) str += "  ";
	}
	return str+"]";
    }

    public void tree() {
	tree(1, "");
    }
    private void tree(int index, String tabs) {
	if(index <= size) {
	    System.out.println(tabs + contents[index]);
	    tree(index*2, tabs+"  ");
	    tree(index*2 + 1, tabs+"  ");
	}
    }

    public static void main(String[] args) {
	MyHeap space = new MyHeap();
	System.out.println(space);
	space.add("a");
	space.add("b");
	space.add("c");
	space.add("d");
	space.add("z");
	space.add("e");
	space.tree();
	System.out.println("Removed: "+space.remove());
	System.out.println("Removed: "+space.remove());
	space.tree();
    }
}
