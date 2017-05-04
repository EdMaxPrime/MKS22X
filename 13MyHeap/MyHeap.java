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

    public String toString() {
	String str = "[";
	for(int i = 0; i < contents.length; i++) {
	    str += contents[i];
	    if(i < contents.length-1) str += "  ";
	}
	return str+"]";
    }

    public static void main(String[] args) {
	MyHeap space = new MyHeap();
	System.out.println(space);
	for(int i = 0; i < 20; i++) space.add(""+i);
	System.out.println(space);
    }
}
