import java.util.Deque;

public class MyDeque {
    String[] content;
    int front, back;
    public MyDeque() {
	content = new String[256];
	front = content.length/2;
	back = front;
    }
    public int size() {
	if(front < back) return back - front;
	return content.length - Math.abs(front - back);
    }

    public static void main(String[] args) {
	//tests
    }
}
