import java.util.Random;

public class Quick {
    public static boolean debug;
    public static void main(String[] args) {
	//used for tests
	debug = true;
	System.out.printf("%10s====A====%n", "");
	int[] a = {1, 0, 3, 2, 3, 4, 2, 3};
	printArr(a);
	System.out.println("Returned " + partition(a, 0, a.length-1, 2));
	printArr(a);
	debug = false;
	System.out.printf("%10s====B====%n", "");
	int[] b = {1, 0, 3, 2, 3, 4, 2, 3};
	printArr(b);
	for(int i = 0; i < 4; i++) {
	    int[] b2 = copy(b);
	    System.out.println("The " + i + "th is " + quickselect(b2, i));
	    printArr(b2);
	}
	System.out.printf("%10s====C====%n", ""); //test quicksort
	int[] c = {0, 90, 40, 20, 80, 30, 10, 70, 50, 60};
	printArr(c);
	quicksort(c);
	printArr(c);
	System.out.printf("%10s====D====%n", ""); //test partition en mass
	int[] d = randomArray(100);
	for(int test = 0; test < 20; test++) {
	    int[] d_copy = copy(d);
	    Range r = partition(d_copy, 0, d_copy.length-1, test*5);
	    for(int i = 0; i < d_copy.length; i++) {
		if((i < r.s && d_copy[i] >= d_copy[r.s]) ||
		   (r.includes(i) && d_copy[i] != d_copy[r.s]) ||
		   (i > r.e && d_copy[i] <= d_copy[r.s])) {
		    System.out.print(" F");
		    continue;
		}
	    }
	    System.out.print(" T");
	}
	System.out.println();
	System.out.printf("%10s====E====%n", ""); //test ranges
	int[] e = {0, 10, 30, 40, 40, 50, 60, 70, 80, 90};
	partition(e, 2, 6, 2);
	printArr(e);
    }

    public static void quicksort(int[] array) {
	quicksortHelp(array, 0, array.length-1);
    }

    public static void quicksortHelp(int[] array, int start, int end) {
	if(start < end) {
	    Range result = partition(array, start, end);
	    quicksortHelp(array, start, result.s-1);
	    quicksortHelp(array, result.e+1, end);
	}
    }

    public static int quickselect(int[] array, int k) {
	int start = 0, end = array.length-1;
	Range result;
	do {
	    result = partition(array, start, end);
	    //System.out.println("Got " + result + " in " + new Range(start,end) + arr2str(array));
	    if(result.toTheRightOf(k))     end = result.s - 1;
	    else if(result.toTheLeftOf(k)) start = result.e + 1;
	} while(!result.includes(k));
	return array[k];
    }

    /**
       Wrapper for partition, uses a random index as the pivot
       @see partition
     */
    public static Range partition(int[] array, int start, int end) {
	int pivotIndex = (new Random()).nextInt(end - start + 1) + start;
	return partition(array, start, end, pivotIndex);
    }

    /**
       Partitions an array into three sections based on a pivot value
       within the array. The first section of the array will contain
       all elements less than the pivot, the second section will have
       all elements equal to the pivot, and the last will contain all
       elements greater than the pivot. Returns the boundaries of these
       sections.
       @param end  the end of the range to be modified, inclusive.
       @param pi   the index of the element to be used as a pivot
       @return     an array of length two. The first element is the
                   leftmost boundary of middle section, second element
		   is the rightmost boundary of the middle section.
     */
    public static Range partition(int[] array, int start, int end, int pi) {
	int pivot = array[pi];
	int low = start, high = end;
	swap(array, start, pi);
	if(debug) {
	    System.out.print((start+1)+") ? ");
	    printTriFlag(array, low, high, start+1);
	}
	for(int i = start+1; i <= high; ) {
	    String tri = i + ") ";
	    if(array[i] < pivot) {
		swap(array, i, low);
		low++;
		i++; //we are in the equal section, dont need to validate
		tri += "< ";
	    }
	    else if(array[i] == pivot) {
		i++; //we are in the equal section, dont need to validate
		tri += "= ";
	    }
	    else {
		swap(array, i, high);
		high--;
		//we are in the unknown zone, must validate again
		tri += "> ";
	    }
	    if(debug) {
		System.out.print(tri);
		printTriFlag(array, low, high, i);
	    }
	}
	if(low > start) {swap(array, start, low-1);}
	return new Range(low, high);
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

    /**
       Prints an array according to what it would look like in
       a Dutch Flag arrangement. Expects a boundary for the first
       section and for the higher section. Both are exclusive of
       their sections but inclusive of the middle section. The low
       divider is printed in blue, the currently selected element
       is underlined, and the high divider is printed in green.
       @param a     the array of integers
       @param low   the low section divider
       @param hi    the high section divider
       @param curr  the currently selected element
     */
    public static void printTriFlag(int[] a, int low, int hi, int curr) {
	String str = "[";
	for(int i = 0; i < a.length; i++) {
	    String elem = ""+a[i];
	    if(i == low) {
		elem = "\033[34m" + elem;
	    }
	    if(i == hi) {
		elem = "\033[32m" + elem;
	    }
	    if(i == curr) {
		elem = "\033[4m" + elem;
	    }
	    elem += "\033[0m";
	    if(i != a.length-1) elem += ", ";
	    str += elem;
	}
	System.out.println(str + "]");
    }

    private static class Range {
	public int s, e;
	public Range(int s, int e) {
	    this.s = s;
	    this.e = e;
	}
	public boolean includes(int i) {
	    return (i >= s && i <= e);
	}
	public boolean toTheRightOf(int i) {
	    return i < s;
	}
	public boolean toTheLeftOf(int i) {
	    return e < i;
	}
	public String toString() {
	    return "[" + s + ", " + e + "]";
	}
    }
}
