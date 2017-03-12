import java.util.ArrayList;
import java.util.Collections;

public class Quiz2Redux {

    public static ArrayList<String> combinations(String s) {
	ArrayList<String> words = new ArrayList<String>();
	pieces(words, "", s);
	Collections.sort(words);
	return words;
    }

    /**
       A method that will create all possible unique unordered combinations of
       a given string orig. The results are added to the list words. The
       algorithm is to branch into two recursive calls: one that includes the
       first letter of orig and one that does not. The first letter is then
       stripped from orig in the subcalls. When the orig string is empty, the
       stiched pieces in the string remainder are added to the arraylist.
       Checks for repeats before adding.
     */
    private static void pieces(ArrayList<String> words, String remainder, String orig) {
	if(orig.length() == 0) {
	    if(!words.contains(remainder)) words.add(remainder);
	}
	else {
	    pieces(words, remainder + orig.charAt(0), orig.substring(1));
	    pieces(words, remainder, orig.substring(1));
	}
    }

    public static void main(String[] args) {
	if(args.length > 0) { //parameter was provided
	    ArrayList<String> words = combinations(args[0]);
	    int len = args[0].length();
	    boolean odd = true;
	    for(String s : words) {
		if(s.length() == 0) s = "\"\"";
		if(odd) {
		    System.out.printf(" %"+len+"s ", s);
		} else {
		    System.out.printf(" %"+len+"s%n", s);
		}
		odd = !odd;
	    }
	} else {
	    main(new String[] {"cat", "dog"});
	}
    }
}
