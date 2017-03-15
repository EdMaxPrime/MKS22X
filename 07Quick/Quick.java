import java.util.Random;

public class Quick {
    public static void main(String[] args) {
	int[] a = {0, 3, 5, 7, 6};
	printArr("Start: %a", a);
	System.out.printf("Returned %d%n",  part(a, 0, a.length-1));
	printArr("And now: %a", a);
    }

    public static int part(int[] data, int start, int end) {
	int pivot = data[(new Random()).nextInt(end - start + 1) + start];
	System.out.println(pivot);
	int rightmost = end, leftmost = start;
	for(int i = start; i <= end; i++) {
	    if(data[i] < pivot) {
		//swap with leftmost
		//System.out.printf("Swapped %d[%d] with %d[%d]%n", data[leftmost], leftmost, data[i], i);
		int temp = data[i];
		data[i] = data[rightmost];
		data[rightmost] = temp;
		rightmost--;
		i--;
	    } else {
		//swap with rightmost\
		//System.out.printf("Swapped %d[%d] with %d[%d]%n", data[rightmost], rightmost, data[i], i);
		int temp = data[i];
		data[i] = data[leftmost];
		data[leftmost] = data[i];
		leftmost++;
		i--;
	    }
	    printArr(data);
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
