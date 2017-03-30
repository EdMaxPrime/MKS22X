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
	System.out.printf("%10s=== Base Case  ===%n", "");
	int[][] c = {
	    {}, {1}, {1, 2}, {4, 3}
	};
	for(int i = 0; i < c.length; i++) {
	    System.out.print(arr2str(c[i])+" --> ");
	    mergesort(c[i]);
	    printArr(c[i]);
	}
	System.out.printf("%10s=== Small Arrays ===%n", "");
	int[][] d = {
	    {1, 2, 3, 4}, {-9, 0, 9}, {7, 6, 5, 4}, {2, 0, 9, 1, 8, 7}
	};
	for(int i = 0; i < d.length; i++) {
	    System.out.print(arr2str(d[i])+" --> ");
	    mergesort(d[i]);
	    printArr(d[i]);
	}
	System.out.printf("%10s=== Big Arrays ===%n", "");
	int e_counter = 80, e_size = 100000;
	while(e_counter > 0) {
	    int[] e = randomArray(e_size); //yuge
	    mergesort(e);
	    if(isSorted(e)) System.out.printf("T ");
	    else System.out.printf("F ");
	    if((e_counter-1) % 20 == 0) {
		System.out.println();
		e_size *= 2;
	    }
	    e_counter--;
	}
    }

    public static void mergesort(int[] array) {
	/*
	//base case: length of 1, return array
	if(array.length <= 1) return;
	//sort left half
	int[] left =  slice(array, 0, array.length/2);
	mergesort(left);
	//sort right half
	int[] right = slice(array, array.length/2, array.length);
	mergesort(right);
	//merge the two halves
	merge(0, left, right, array);
	*/
	mergeHelp(array, 0, array.length);
    }

    private static void mergeHelp(int[] array, int start, int end) {
	//base case: length of 1, return array
	if(end-start <= 1) return;
	//sort left half
	mergeHelp(array, start, start+(end-start)/2);
	//sort right half
	mergeHelp(array, start+(end-start)/2, end);
	//merge the two halves
	int[] left =  slice(array, start, start+(end-start)/2);
	int[] right = slice(array, start+(end-start)/2, end);
	merge(start, left, right, array);
    }

    public static void merge2(int start, int div, int end, int[] src, int[] dest) {
	
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
       Tests if an array is sorted.
       @param array  the array to be tested
       @return       true if the array is sorted
                     in ascending order, else false.
     */
    public static boolean isSorted(int[] array) {
	for(int i = 1; i < array.length; i++) {
	    if(array[i] < array[i-1]) return false;
	}
	return true;
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
