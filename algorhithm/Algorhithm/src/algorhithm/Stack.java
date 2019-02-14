package algorhithm;
import java.util.Arrays;

public class Stack {
	static Object[] array = new Object[10];	
	int top = -1;
	
	public Stack() {
		Arrays.fill(array, null);
		System.out.println(Arrays.toString(array));
	}
	
	//push
	public void push() {
		
		if(array[0] == null) {
			array[0] = ++top;
			System.out.println("0번째 push는");
			System.out.println(Arrays.toString(array));
		} else {
			
			for(int i=1; i<array.length; i++) {
				if(array[i] == null && array[i] == array[top+1]) {
					array[i] = ++top;
					System.out.println(i + "번째 push는");
					System.out.println(Arrays.toString(array));
					break;
				}
			}

		}
	}
	
	//pop
	public void pop() {
		if(array[0] == null) {
			System.out.println("pop은 불가능합니다.");
		} else {
			for(int i=0; i<array.length;i++) {
				if(array[i] == null) {
					i--;
					array[i] = null;
					System.out.println(i + "번째 pop");
					System.out.println(Arrays.toString(array));
					break;
				}
				
			}
		}
	}
	
	//peek
	public void peek() {
			if (array[0] != null) {
				System.out.println(top);
				System.out.println("peek의 값은" + array[top-1] + "입니다");
			}	else {
				System.out.println("값이 존재하지않습니다.");
			}
	}
	
	public static void main(String[] args) {
		Stack stack = new Stack();
//		System.out.println(Arrays.toString(array));
		stack.push();
		stack.push();
		stack.push();
		stack.pop();
		stack.peek();
		stack.push();
		stack.push();
		stack.peek();
	}
}