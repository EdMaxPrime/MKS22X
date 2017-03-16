import java.util.Random;

public class Quick {
    public static void main(String[] args) {
	for(int test = 0; test < 6; test++) {
	    int[] a = {0, 10, 20, 30, 40};
	    printArr("Start: %a", a);
	    System.out.printf("Returned %d%n",  part(a, 0, a.length-1));
	    printArr("And now: %a", a);
	}
	int[] b = {0, 20, 40, 30, 10};
	for(int i = 0; i < b.length; i++) {
	    System.out.printf("for k=%d, --> %d%n", i, quickselect(b, i));
	}
    }

    /**
       Returns the <i>k</i>th smallest value of <i>array</i>
     */
    public static int quickselect(int[] array, int k) {
	int start = 0, end = array.length-1, result;
	do {
	    result = part(array, start, end);
	    if(result < k)      start = result;
	    else if(result > k) end = result;
	} while(result != k);
	return array[k];
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
	if(data[end] < data[wall]) swap(data, wall, end);
	else wall++;
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
