public class MyHeap {
    int[] contents;
    int size, dir;

    public MyHeap(boolean maxMin) {
	contents = new int[10];
	size = 0;
	dir = maxMin? 1 : -1;
    }

    public MyHeap() {
	this(true);
    }
}
