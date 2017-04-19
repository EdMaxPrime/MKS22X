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
	int[] c = {83, 81, 7, 6, 5, 4, 3, 2, 1, 0};
	radixSort(c, 2);
	System.out.println("sorted:"+arr2str(c, 2));
	int[] d = randomArray(15, 0, 100);
	System.out.println("random:"+arr2str(d, 2));
	radixSort(d, 2);
	System.out.println("sorted:"+arr2str(d, 2));
    }

    public static void radixSort(int[] array, int numDigits) {
	MyLinkedList[] digits = new MyLinkedList[10];
	int div = 1, mod = 10, insert = 0;
	while(numDigits > 0) {
	    //System.out.printf("//m=%d;d=%d;n=%d//  ", mod, div, numDigits);
	    for(int d = 0; d < digits.length; d++) {
		digits[d] = new MyLinkedList();
	    }
	    for(int i : array) {
		int digit = (i % mod) / div;
		digits[digit].add(i);
	    }
	    for(int d = 0; d < digits.length; d++) {
		for(int i : digits[d]) {
		    //System.out.printf("%d#%d->%d  ", i, d, insert);
		    array[insert] = i;
		    insert++;
		}
	    }
	    div *= 10;
	    mod *= 10;
	    insert = 0;
	    numDigits--;
	}
	//System.out.println();
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
	return randomArray(len, -250, 250);
    }

    private static int[] randomArray(int len, int min, int max) {
	Random rng = new Random();
	int[] arr = new int[len];
	for(int i = 0; i < len; i++) {
	    arr[i] = rng.nextInt(max-min)+min;
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
