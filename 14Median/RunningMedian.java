public class RunningMedian {
    private MyHeap less, more;
    private int l1, l2;
    public RunningMedian() {
	less = new MyHeap();
	more = new MyHeap(false);
	l1 = 0;
	l2 = 0;
    }

    public void add(int number) {
	//
    }

    public double getMedian() {
	if(l1 == l2) {
	    if(l1 == 0) return 0; //error?
	    return (getLess()+getMore()) / 2.0;
	}
	return 0; //other cases here
    }

    private double getLess() {}

    private double getMore() {}
}
