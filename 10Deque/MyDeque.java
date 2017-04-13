import java.util.Deque;

public class MyDeque {
    String[] content;
    int front, back;
    char wrapMode;
    public MyDeque() {
	this(100);
    }
    public MyDeque(int init) {
	content = new String[init];
	front = content.length/2;
	back = front;
	wrapMode = ' '; //no wrapping
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
    private void grow() {
	String[] newstuff = new String[2*content.length];
	int front2 = newstuff.length/2, back2 = front2;
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
    public String debug(String cmd, int arg) {
	if(cmd.equals("str")) {
	    String str = "[";
	    for(int i = 0; i < content.length; i++) {
		String piece = content[i];
		if(content[i] == null) piece = "-----";
		if(piece.length() < arg) piece = String.format("%"+arg+"s", piece);
		else piece = piece.substring(0, arg);
		if(front == i) {
		    str += Text.esc("<cG>"+piece+"<R>");
		} else if(back == i) {
		    str += Text.esc("<cB>"+piece+"<R>");
		} else {
		    str += piece;
		}
		if(i < content.length - 1) str += ", ";
		else str += "]";
	    }
	    return str;
	}
	return "";
    }

    public static void main(String[] args) {
	//tests
	MyDeque a = new MyDeque(5);
	a.addFirst("a");
	a.addFirst("z");
	System.out.println(a.debug("str", 1));
	System.out.println(a.toString());
    }
}
