import java.util.Random;

public class Quick {
    public static void main(String[] args) {
	//
    }

    public static int part(int[] data, int start, int end) {
	int pivot = data[(new Random()).nextInt(start, end+1)];
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
}
