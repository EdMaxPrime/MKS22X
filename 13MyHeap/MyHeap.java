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
	space.tree();
    }
}
