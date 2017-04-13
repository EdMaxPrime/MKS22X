import java.io.File;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
	if(args.length == 0) args = new String[] {"test.txt"};
	/*
	  +F <str>
	  -F <str>
	  ?F <str>
	  +L <str>
	  -L <str>
	  ?L <str>
	  == <str>
	 */
	Scanner in = new Scanner("");
	try {
	    in = new Scanner(new File(args[0]));
	} catch(Exception e) {}
	String lastOP = "", error = "";
	MyDeque test = new MyDeque();
	int correct = 0, total = 0;
	while(in.hasNextLine()) {
	    String line = in.nextLine();
	    String op = line.substring(0, 2);
	    String rest = line.substring(3);
	    if(op.equals("  ")) op = lastOP;
	    if(op.equals("+F")) {
		test.addFirst(rest);
		lastOP = op;
	    }
	    else if(op.equals("+L")) {
		test.addLast(rest);
		lastOP = op;
	    }
	    else if(op.equals("-F")) {
		total++;
		String rem = test.removeFirst();
		if(rem.equals(rest)) correct++;
		lastOP = op;
	    }
	    else if(op.equals("-L")) {
		total++;
		String rem;
		rem = test.removeLast();
		if(rem.equals(rest)) correct++;
		lastOP = op;
	    }
	    else if(op.equals("?F")) {
		total++;
		String get;
		get = test.removeFirst();
		if(get.equals(rest)) correct++;
		lastOP = op;
	    }
	    else if(op.equals("?L")) {
		total++;
		String get;
		get = test.removeLast();
		if(get.equals(rest)) correct++;
		lastOP = op;
	    }
	    else if(op.equals("==")) {
		total++;
		if(test.toString("").equals(rest)) correct++;
		lastOP = op;
	    }
	}
	System.out.println(correct+"/"+total);
    }
}
