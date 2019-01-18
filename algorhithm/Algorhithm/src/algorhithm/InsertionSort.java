package algorhithm;
import java.util.Arrays;

public class InsertionSort {
	public static void main(String[] args) {
		int[] array = {1,7,20,611,21};
		int temp = 0;
		int j=0;
		
		for(int i=1; i<array.length; i++) {
			temp = array[i];
//			System.out.println(Arrays.toString(array));
			for(j=i-1; j>=0 && temp<array[j]; j--) {
				array[j+1] = array[j];
			}
			array[j+1] = temp;
		}
		System.out.println(Arrays.toString(array));
		
	}
}