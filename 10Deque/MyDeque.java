import java.util.Deque;

public class MyDeque {
    String[] content;
    int front, back;
    public MyDeque() {
	this(100);
    }
    public MyDeque(int init) {
	content = new String[init];
	front = content.length/2;
	back = front;
    }
    public int size() {
	if(front < back) return back - front;
	return content.length - Math.abs(front - back);
    }
    public void addFirst(String s) {
	if(size() >= content.length - 1) {}
	content[front] = s;
	front--;
	if(front < 0) front += content.length;
    }
    public String toString() {
	return toString(false);
    }
    public String toString(boolean newLines) { //really bad
	String str = "[";
	for(String s : content) {
	    str += s + (newLines? "\n" : ",") + " ";
	}
	return str + "]";
    }

    public static void main(String[] args) {
	//tests
	MyDeque a = new MyDeque(5);
	a.addFirst("a");
	a.addFirst("z");
	System.out.println(a.toString(true));
    }
}
