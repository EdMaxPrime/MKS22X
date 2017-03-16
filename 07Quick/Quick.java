import java.util.Random;

public class Quick {
    public static void main(String[] args) {
	int[] a = {0, 10, 20, 30, 40};
	printArr("Start: %a", a);
	System.out.printf("Returned %d%n",  part(a, 0, a.length-1));
	printArr("And now: %a", a);
    }

    public static int part(int[] data, int start, int end) {
	int pivotIndex = (new Random()).nextInt(end - start + 1) + start;
	swap(data, end, pivotIndex);
	int pivot = data[end], wall = end - 1;
	for(int i = 0; i < wall; ) {
	    if(data[i] >= pivot) {
		swap(data, i, wall);
		wall--;
	    } else {
		i++;
	    }
	}
	System.out.println("Sorting around " + pivot);
	swap(data, wall, end);
	return wall;
    }

    /**
       Yuck
     */
    private static void swap(int[] array, int indexA, int indexB) {
	int temp = array[indexA];
	array[indexA] = array[indexB];
	array[indexB] = temp;
    }

    public static int partFake(int[] data, int start, int end) {
	int pivot = data[(new Random()).nextInt(end - start + 1) + start];
	System.out.println(pivot);
	int rightmost = end, leftmost = start;
	for(int i = start; i <= end; i++) {
	    String compare = " larger";
	    if(data[i] < pivot) {
		//swap with leftmost
		//System.out.printf("Swapped %d[%d] with %d[%d]%n", data[leftmost], leftmost, data[i], i);
		int temp = data[i];
		data[i] = data[rightmost];
		data[rightmost] = temp;
		rightmost--;
		i--;
		compare = "smaller";
	    } else {
		//swap with rightmost\
		//System.out.printf("Swapped %d[%d] with %d[%d]%n", data[rightmost], rightmost, data[i], i);
		if(data[i] == pivot) compare = "   same";
		int temp = data[i];
		data[i] = data[leftmost];
		data[leftmost] = data[i];
		leftmost++;
		i--;
	    }
	    printArr(compare + ":\t%a", data);
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
