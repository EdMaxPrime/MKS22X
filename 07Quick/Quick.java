import java.util.Random;

public class Quick {
    public static void main(String[] args) {
	//
    }

    public static int part(int[] data, int start, int end) {
	int pivot = data[(new Random()).nextInt(end - start + 1) + start];
	int rightmost = end, leftmost = start;
	for(int i = start; i <= end; i++) {
	    if(data[i] < pivot) {
		//swap with leftmost
		int temp = data[i];
		data[i] = data[leftmost];
		data[leftmost] = temp;
		leftmost++;
	    } else {
		//swap with rightmost
		int temp = data[i];
		data[i] = data[rightmost];
		data[rightmost] = data[i];
		rightmost--;
	    }
	}
	return leftmost; //index of the pivot
    }

    public static String arr2str(int[] arr) {
	String str = "[";
	for(int i : arr) {str += i + ", ";}
	if(str.endsWith(", ")) {str = str.substring(0, str.length()-2);}
	str += "]";
	return str;
    }

    public static void printArr(int[] arr) {
	System.out.println(arr2str(arr));
    }

    public static void printArr(String message, int[] arr) {
	System.out.println(message.replaceFirst("%a", arr2str(arr)));
    }
}
