package algorhithm;
import java.util.Arrays;

public class BinarySearch {
	
	public static void main(String[] args) {
		
		
		int[] array = {-3, 0, 1, 4, 7, 9, 11, 16, 20};
		int mid = 4;
		int index = mid;
		int data = 20;
		
		System.out.println(Arrays.toString(array));
		
		if(mid > data) {
			for(int i=mid; i<array.length; i--) {
				if(array[i] > data) {
					index--;
				}
				
				if(array[i] == data) {
					System.out.println("index �� ����?" + index);
					break;
				}
			}
		} else {
			for(int i=mid; i<array.length; i++) {
				if(array[i] < data) {
					index++;
				}
				
				if(array[i] == data) {
					System.out.println("index�� ����" + index);
					break;
				}
			}
		}
		
		
	
		
		
		
		
	}
}