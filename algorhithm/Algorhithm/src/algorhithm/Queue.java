package algorhithm;
import java.util.Arrays;

public class Queue {
	static Object[] array = new Object[10];
	int top = -1;
	
	public Queue() {
		Arrays.fill(array, null);
		System.out.println("기본값은 : " + Arrays.toString(array));
	}
	
	public void enqueue() {
		if(array[0] == null) {
			array[0] = ++top;
			System.out.println("enqueue!!");
			System.out.println(Arrays.toString(array));
		} else {
			
			for(int i=1; i<array.length; i++) {
				if(array[i] == null && array[i] == array[top+1]) {
					array[i] = ++top;
					System.out.println(i + "번째 enqueue");
					System.out.println(Arrays.toString(array));
					break;
				}
			}
		}
	}
	
	public void dequeue() {
		if(array[0] == null) {
			System.out.println("dequeue 는 불가능합니다.");
		} else {
			array[0] = null;
			System.out.println("dequeue");
			System.out.println(Arrays.toString(array));
			
			for(int i=1; i<array.length; i++) {
				array[i-1] = array[i];
			}
			System.out.println(Arrays.toString(array));
		}
	}
	
	
	public void peek() {
		if(array[0] != null) {
			System.out.println("peek의 값은 " + array[top-1] + "입니다.");
		} else {
			System.out.println("값이 존재하지않습니다.");
		}
	}
	
	
	public static void main(String[] args) {
		Queue queue = new Queue();
		queue.enqueue();
		queue.enqueue();
		queue.enqueue();
		queue.dequeue();
		queue.dequeue();
		queue.enqueue();
	}
}