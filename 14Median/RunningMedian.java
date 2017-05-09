public class RunningMedian {
    private MyHeap less, more;
    private int l1, l2;
    public RunningMedian() {
	less = new MyHeap();
	more = new MyHeap(false);
	l1 = 0;
	l2 = 0;
    }

    /**
       Adds a number to the list of numbers used to calculate
       the median. If the two heaps are of equal length, then
       the number is added depending on its relation to the
       median(LESS or MORE). If one heap is bigger but the
       number belongs in the other, then it is added as so.
       If one heap is bigger *and* the number belongs in that
       heap, then the heaps are balanced out before the new
       number is added.
     */
    public void add(int number) {
	if(number < getMedian()) {
	    if(l1 <= l2) {
		less.add(number);
		l2++;
	    } else {
		more.add(less.remove());
		l2++;
		l1--;
		add(number);
	    }
	} else {
	    if(l2 <= l1) {
		more.add(number);
		l2++;
	    } else {
		less.add(more.remove());
		l1++;
		l2--;
		add(number);
	    }
	}
    }

    public double getMedian() {
	if(l1 == l2) {
	    if(l1 == 0) return 0; //error: nothing to get median of
	    return (less.peek()+more.peek()) / 2.0; //average
	}
	else if(l1 > l2) //median is in LESS
	    return less.peek();
	return more.peek(); //median is in MORE
    }

    public static void main(String[] args) {
	RunningMedian lol = new RunningMedian();
	print(new int[] {});
    }

    private static void print(RunningMedian rm, int[] nums) {
	String line = " median of ";
	for(int i : nums) {
	    rm.add(i);
	    line += i + " ";
	}
	System.out.println(String.format("% 3.2f", rm.getMedian()) + line);
    }

    private static void print(int[] nums) {
	print(new RunningMedian(), nums);
    }
}
