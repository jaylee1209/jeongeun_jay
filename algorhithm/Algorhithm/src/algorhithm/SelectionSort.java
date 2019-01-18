package algorhithm;
import java.util.Arrays;

public class SelectionSort {
	public static void main(String[] args) {
		int[] array = {5,1,3,8,2,7,6};
		int temp = 0;
		int minIndex = 0;
		boolean sorted = false;
		
			for(int i=0; i <array.length-1; i++) {
				minIndex = i;
				for(int j=i+1; j<array.length; j++) {
					if(array[minIndex]>array[j]) {
						minIndex = j;
					}
				}
				temp = array[minIndex];
				array[minIndex] = array[i];
				array[i] = temp;
			}
			System.out.println(Arrays.toString(array));
	}
}
