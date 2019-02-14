package algorhithm;
import java.util.Arrays;

public class BubbleSort {

	public static void main(String[] args) {
		BubbleSort bs = new BubbleSort();
		int[] array = {8,7,5,4,2,6,8,1};
		int temp = 0;
		
		System.out.println(Arrays.toString(array));
		
		boolean sorted = false;
		
		while(!sorted) {
			for(int i=0;i<array.length-1; i++) {
				if(array[i]> array[i+1]) {
					bs.swap(array, i, i+1);
					System.out.println(Arrays.toString(array));
				}
			}
		}
	}

	public void swap(int[] array, int source, int target) {
		int temp = array[source];
		array[source] = array[target];
		array[target] = temp;
	}
}