import java.util.Deque;

public class MyDeque {
    String[] content;
    int front, back;
    char wrapState;
    public MyDeque() {
	this(100);
    }
    public MyDeque(int init) {
	content = new String[init];
	front = content.length/2;
	back = front;
	wrapState = ' '; //no wrapping
    }
    public int size() {
	if(front < back) return back - front;
	return content.length - Math.abs(front - back);
    }
    public void addFirst(String s) {
	if(shouldIGrow()) grow();
	content[front] = s;
	front--;
	wrap();
    }
    private boolean shouldIGrow() {
        return (wrapState != ' ' && front == back);
    }
    private void grow() {
	String[] newstuff = new String[2*content.length];
	int front2 = newstuff.length/2, back2 = front2;
    }
    private boolean wrap() {
	if(front < 0) {
	    front += content.length; //usually length-1
	    wrapState = 'F';
	    return true;
	} else if(back >= content.length) {
	    back -= content.length;
	    wrapState = 'B';
	    return true;
	} else if(front >= content.length) {
	    front -= content.length;
	    wrapState = ' ';
	    return true;
	} else if(back < 0) {
	    back += content.length;
	    wrapState = ' ';
	    return true;
	}
	return false;
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
	else if(cmd.equals("grow?")) {
	    return ""+shouldIGrow();
	}
	else if(cmd.equals("wrap")) {
	    return ""+wrap()+" F:"+front+" B:"+back;
	}
	return "";
    }

    public static void main(String[] args) {
	//tests
	MyDeque a = new MyDeque(5);
	a.addFirst("a");
	a.addFirst("z");
	a.addFirst("m");
	a.addFirst("n");
	System.out.println(a.debug("str", 1));
	System.out.println(a.debug("wrap",0));
	System.out.println(a.toString());
    }
}
