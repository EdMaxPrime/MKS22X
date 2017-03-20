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
	for(int test = 0; test < 5; test++) {
	    int[] c = randomArray(5);
	    System.out.printf("%s 3rd is %d%n", arr2str(c), quickselect(c, 3));
	}
	/*System.out.println();
	int[] d = {9, 3, 7, 0, 1, 5};
	for(int test = 0; test < 1; test++) {
	    part(d, 0, d.length-1, 5);
	}
	printArr("%a", d);
	*/
	System.out.println();
	int[] e = {7, 0, 6, 9, 2, 6};
	System.out.println("Ended as " + partition(e, 0, e.length-1));
	printArr(e);
	System.out.println();
	int[] f = {1, 1, 0, 1, 1, 1, 1, 1};
	System.out.println("Ended as " + partition(f, 0, f.length-1));
	printArr(f);
	System.out.println();
	for(int test = 0; test < 5; test++) {
	    int[] g = randomArray(5);
	    int[] sorted = new int[g.length];
	    for(int k = 0; k < g.length; k++) {
		sorted[k] = quickselect(copy(g), k);
	    }
	    System.out.printf("%22s --> %22s%n", arr2str(g), arr2str(sorted));
	}
    }

    /**
       Returns the <i>k</i>th smallest value of <i>array</i>
     */
    public static int quickselect_old(int[] array, int k) {
	int start = 0, end = array.length-1, result;
	do {
	    result = part(array, start, end);
	    if(result < k)      start = result;
	    else if(result > k) end = result;
	} while(result != k);
	return array[k];
    }

    public static int quickselect(int[] array, int k) {
	int start = 0, end = array.length-1, result;
	do {
	    result = partition(array, start, end);
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
	//System.out.println("Sorting around " + pivot);
	if(data[end] < data[wall]) swap(data, wall, end);
	else wall++;
	return wall;
    }

    public static int part(int[] data, int start, int end, int pivotIndex) {
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
	if(data[end] < data[wall]) swap(data, wall, end);
	else {wall++; swap(data, wall, end);}
	System.out.println(wall);
	return wall;
    }

    public static int partition(int[] data, int start, int end) {
	int pivotIndex = (new Random()).nextInt(end - start + 1) + start;
	swap(data, end, pivotIndex);
	int pivot = data[end], greater = end - 1, equal = end - 1;
	//System.out.print("Sorting around " + pivot);
	for(int i = 0; i <= equal; ) {
	    if(data[i] == pivot) {
		swap(data, i, equal);
		equal--;
	    }
	    else if(data[i] > pivot) {
		swap(data, i, greater);
		greater--;
		equal--;
	    }
	    else {
		i++;
	    }
	}
	//System.out.printf(" with e=%d and g=%d%n",equal,greater);
	greater++;
	swap(data, greater, end);
	return (equal + greater + 2) / 2;
    }

    /**
       Yuck
     */
    private static void swap(int[] array, int indexA, int indexB) {
	int temp = array[indexA];
	array[indexA] = array[indexB];
	array[indexB] = temp;
    }

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

    public static int[] copy(int[] array) {
	int[] copy = new int[array.length];
	for(int i = 0; i < array.length; i++) {
	    copy[i] = array[i];
	}
	return copy;
    }
}
