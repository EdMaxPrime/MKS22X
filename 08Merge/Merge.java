import java.util.Random;

public class Merge {
    public static void main(String[] args) {
	//tests
	System.out.printf("%10s=== Merge ===%n", "");
	int[] a = new int[10];
	merge(0, new int[] {0, 1, 3, 5, 5}, new int[] {2, 4, 4, 6, 8}, a);
	printArr(a);
	System.out.printf("%10s=== Slice  ===%n", "");
	int[] b = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	printArr(slice(b, 0, 2));
	printArr(slice(b, 2, 7));
	printArr(slice(b, 7, 10));
    }

    public static void mergesort(int[] array) {
	//base case: length of 1, return array
	//sort left half
	//sort right half
	//merge the two halves
    }

    public static void merge(int start, int[] a, int[] b, int[] dest) {
	int a_counter = 0, b_counter = 0;
	while(a_counter < a.length && b_counter < b.length) {
	    if(a[a_counter] <= b[b_counter]) {
		dest[start] = a[a_counter];
		a_counter++;
		start++;
	    } else {
		dest[start] = b[b_counter];
		b_counter++;
		start++;
	    }
	}
	if(a_counter < a.length) {
	    for(; a_counter < a.length; a_counter++) {
		dest[start] = a[a_counter];
		start++;
	    }
	}
	if(b_counter < b.length) {
	    for(; b_counter < b.length; b_counter++) {
		dest[start] = b[b_counter];
		start++;
	    }
	}
    }

    public static int[] slice(int[] original, int start, int end) {
	int[] slice = new int[end - start];
	for(int i = start; i < end; i++) {
	    slice[i - start] = original[i];
	}
	return slice;
    }

    /**
       Yuck
     */
    private static void swap(int[] array, int indexA, int indexB) {
	int temp = array[indexA];
	array[indexA] = array[indexB];
	array[indexB] = temp;
    }

    /**
       Makes an array of random positive integers less than 100
       @param len  if len is &lt; 0, array will be of random length,
                   otherwise the array will be of len length
       @return     the array
     */
    public static int[] randomArray(int len) {
	Random rng = new Random();
	if(len < 0) len = (int)(Math.abs(rng.nextInt()));
	int[] array = new int[len];
	while(len > 0) {
	    array[len - 1] = rng.nextInt(100);
	    len--;
	}
	return array;
    }

    /**
       Makes an array of random positive integers less than 100
       with some of them duplicated.
       @param len  if len is &lt; 0, array will be of random length,
                   otherwise the array will be of len length
       @return     the array
     */
    public static int[] randomArrayDuplicates(int len) {
	Random rng = new Random();
	if(len < 0) len = (int)(Math.abs(rng.nextInt()));
	int[] array = new int[len];
	int repeat = 0;
	while(len > 0) {
	    if(repeat == 0) {
		repeat = 1 + rng.nextInt(5);
		array[len - 1] = rng.nextInt(100);
	    } else {
		array[len - 1] = array[len];
	    }
	    repeat--;
	    len--;
	}
	shuffle(array);
	return array;
    }

    /**
       Shuffles an array by swapping elements randomly
       @param array  the array to be randomized
     */
    public static void shuffle(int[] array) {
	Random rng = new Random();
	for(int i = 0; i < array.length; i++) {
	    swap(array, i, rng.nextInt(array.length));
	}
    }

    /**
       Serves as a toString for integer arrays
       @param arr  the array
       @return     a string in the format "[a, b, c, ... z]"
     */
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

    /**
       Prints a formatted string where the only supported
       type is and integer array represented as %a.
       @param message  a string to be printed, should have %a
       @param arr      the array to replace %a with
     */
    public static void printArr(String message, int[] arr) {
	System.out.println(message.replaceFirst("%a", arr2str(arr)));
    }

    /**
       Makes a copy of an array. No references remain
       @param array  the array to copy
       @return       a copy of that array
     */
    public static int[] copy(int[] array) {
	int[] copy = new int[array.length];
	for(int i = 0; i < array.length; i++) {
	    copy[i] = array[i];
	}
	return copy;
    }
}
