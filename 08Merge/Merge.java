public class Merge {
    public static void main(String[] args) {
	//tests
	System.out.printf("%10s=== Merge ===%n", "");
	int[] a = new int[10];
	merge(0, new int[] {0, 1, 3, 5, 5}, new int[] {2, 4, 4, 6, 8}, a);
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
}
