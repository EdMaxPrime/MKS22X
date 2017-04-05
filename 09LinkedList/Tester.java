import java.util.Random;
import java.util.Iterator;

public class Tester {
    public static void main(String[] args) {
	System.out.println(arr2str(randomArray(getParam(args, 0, 10)), 4));
	Iterator<Integer> a = new MyLinkedList().iterator();
	System.out.println("Empty list hasNext() --> "+a.hasNext());
	MyLinkedList b = new MyLinkedList();
	b.add(5);
	b.add(4);
	b.add(3);
	b.add(2);
	b.add(1);
	for(int i : b) System.out.print(i + " ");
	System.out.println();
    }

    private static int getParam(String[] args, int index, int _default) {
	if(index >= args.length)
	    return _default;
	try {
	    return Integer.parseInt(args[index]);
	} catch(Exception e) {
	    return _default;
	}
    }

    private static int[] randomArray(int len) {
	Random rng = new Random();
	int[] arr = new int[len];
	for(int i = 0; i < len; i++) {
	    arr[i] = rng.nextInt(500)-250;
	}
	return arr;
    }

    private static String arr2str(int[] arr, int digits) {
	String str = "[";
	for(int i = 0; i < arr.length; i++) {
	    str += String.format("%+"+digits+"d", arr[i]);
	    if(i < arr.length - 1) str += ", ";
	}
	return str + "]";
    }
}
