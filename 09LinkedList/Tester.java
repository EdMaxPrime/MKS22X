import java.util.Random;

public class Tester {
    public static void main(String[] args) {
	
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
