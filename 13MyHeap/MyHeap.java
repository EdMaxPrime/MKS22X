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
    }
}
