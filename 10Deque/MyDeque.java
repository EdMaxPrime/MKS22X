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
	if(wrapState == ' ') return back - front;
	return content.length - Math.abs(front - back);
    }
    public void addFirst(String s) {
	if(shouldIGrow()) grow();
	content[front] = s;
	front--;
	wrap();
    }
    public void addLast(String s) {
	if(shouldIGrow()) grow();
	/*boolean wrapped = (wrapState == 'B');
	if(wrapped)  content[back] = s;
	back++;
	wrap();
        if(!wrapped) content[back] = s;
	*/
	back++;
	wrap();
	content[back] = s;
    }
    public String removeFirst() {
	front++;
	wrap();
	return content[front];
    }
    public String removeLast() {
	String removed = content[back];
	back--;
	wrap();
	return removed;
    }
    public String getFirst() {
	System.out.println("getFirst: "+front);
	if(wrapState != 'F' && front < content.length-1) return content[front+1];
	front++;
	wrap();
	String get = content[front];
	front--;
	wrap();
	return get;
    }
    public String getLast() {
	return content[back];
    }
    private boolean shouldIGrow() {
        return (wrapState != ' ' && front == back);
    }
    private void grow() {
	String[] newstuff = new String[2*content.length];
	int front2 = newstuff.length/2, back2 = front2;
	while(size() > 0) {
	    newstuff[back2] = removeFirst();
	    back2++;
	    System.out.println(size()+" "+debug("str", 1));
	}
	front = front2-1;
	back = back2-1;
	this.content = newstuff;
	wrap();
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
		if(back == front && back == i) {
		    str += Text.esc("<u cG>"+piece+"<R>");
		} else if(front == i) {
		    str += Text.esc("<cG>"+piece+"<R>");
		} else if(back == i) {
		    str += Text.esc("<u>"+piece+"<R>");
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
	else if(cmd.equals("grow")) {
	    grow();
	    return debug("str", arg);
	}
	return "";
    }
    /**
       Returns a string of all the elements in this
       deque in order from first to last separated
       by the sep.
       @param sep  the separator between strings
       @return     a run of all the strings
     */
    public String toString(String sep) {
	String all = "";
	MyDeque temp = new MyDeque();
	while(size() > 0) {
	    System.out.println(all);
	    all += this.getFirst();
	    temp.addLast(this.removeFirst());
	    if(size() > 1) all += sep;
	}
	while(temp.size() > 0) {
	    this.addFirst(temp.removeLast());
	}
	return all;
    }

    public static void main(String[] args) {
	//tests
	MyDeque a = new MyDeque(5);
	a.addLast("a");
	System.out.println(a.getLast());
	a.addLast("b");
	System.out.println(a.getLast());
	a.addLast("c");
	a.addLast("d");
	a.addLast("e");
	a.addLast("f");
	a.addLast("g");
        System.out.println(a.debug("str", 1));
	System.out.println(a.toString(" "));
	System.out.println(a.debug("str", 1));
    }
}
