package linkedlist;

import java.util.Arrays;
 
public class LinkedList {
    //데이터 초기화
    private Node head;
    private Node tail;
    private int size = 0;
    
    private class Node{
    	//값을 저장하는 data Object 생성
    	private Object data;

    	//다음 노드와 연결되는Node
        private Node next;
        public Node(Object input) {
            this.data = input;
            this.next = null;
        }

        //node내용 String 으로 출력 후 확인
        public String toString(){
            return String.valueOf(this.data);
        }
    }

    public void addFirst(Object input){
    	//새로운 노드 생성
    	Node newnode = new Node(input);
  
    	//이미 노드가 하나 존재한다는 조건 하에
    	//존재하는 노드의 값이 head이므로 head를 사용하여
    	//새로 만들어진 노드의 next에 값을 담는다.
    	//이렇게 하면은 다음노드의 head와 이전 노드의 next과 연결된다.
    	newnode.next = head;
    	
    	//새로운 노드의 값이 head로 변경된다.
    	head = newnode;
    	
    	//값이 하나 추가되었으니 size증가
    	size++;
    	
    	//노드가 1개일 경우 tail에다가 head값을 넣는다
    	if(newnode.next == null) {
    		tail = head;
    	}
    
    }
    
    public void addLast(Object input){

    	//새로운 노드 생성
    	Node newnode = new Node(input);
       
    	//노드가 없을 경우
       if(size == 0) {
    	   //input에 들어간 값을 첫번째 node생성 function으로 노드 생성
    	   addFirst(input);
       } else {
    	   //이전 노드의 next에 생성한 노드 data 데이터 넣기
    	   //새로운 노드의 data는 tail에 저장 후 노드 갯수 증가
    	   tail.next = newnode;
    	   tail = newnode;
    	   size++;
       }
    }
    
    //노드의 위치를 알려주는 코드
    Node node(int index) {
    	//head의 정보부터 x라는 변수에 담아준다.
        Node x = head;
        
        //for문을 돌며 입력한 index 값 전까지 next과 x의 값을 바꿔준 후 반환
        for (int i = 0; i < index; i++) {
        	x = x.next;
        }
        
        return x;
    }
    

public void addmid(int k, Object input){
    // 만약 k가 0이라면 첫번째 노드에 추가하는 것이기 때문에 addFirst를 사용합니다.
    if(k == 0){
        addFirst(input);
    } else {
    	//temp1 이라는 변수를 설정해서 선택된 노드보다 1개 이전의 노드의 값을 담는다.
    	Node temp1 = node(k-1);
    	
    	//temp1의 next을 변수에 담아준다.
    	Node temp2 = temp1.next;
    	
    	//새로운 노드 생성
    	Node newnode = new Node(input);
    	
    	//새로 만든 node를 temp1.next에 연결하고 
    	//temp2에 담아놨던 값을 새로운 node.next에 담는다.
    	temp1.next = newnode;
    	newnode.next = temp2;
    	size++;
    	
    	//만약 노드가 하나도 존재하지않는다면은
    	//첫번째 노드생성
    	if(size == 0) {
    		addFirst(input);
    	}
    	
    	
    }
}

public Object remove(int i){
    if(i == 0) {
    	return removeFirst();
    }
    // 선택된 노드의 이전 노드를 선택하여 temp에 저장
    Node temp = node(i-1);
    
    //temp.next를 하여 그 다음 노드를 선택하고 그 노드를 
    //지워질 노드라는 변수명에 담는다.
    Node todoDeleted = temp.next;

    //삭제할 노드 이전의 노드를 중심으로 다다음 노드를 선택.
    temp.next = temp.next.next;

    //삭제된 데이터가 무엇인지 나타내주려 지워질 노드 데이터를 returnData에 담는다
    Object returnData = todoDeleted.data; 
    
    //만약 지워질 노드가 tail이라면은 이전 노드의 값을 tail로 대체함
    if(todoDeleted == tail){
        tail = temp;
    }
    
    //null을 사용하여 노드 삭제
    todoDeleted = null; 
    size--;
    return returnData;
}

public int size() {
    return size;
}


public Object removeFirst(){
    // 첫번째 노드를 temp로 지정하고 head의 값을 두번째 노드로 변경합니다.
    Node temp = head;
    head = temp.next;
    // 데이터를 삭제하기 전에 리턴할 값을 임시 변수에 담습니다. 
    Object returnData = temp.data;
    temp = null;
    size--;
    return returnData;
}





public int indexOf(Object data){
    // head값을 temp라는 변수에 지정
    Node temp = head;

    int count = 0;

    //temp.data 가 존재하지 않을때까지 while문 반복
    while(temp.data != data){
        temp = temp.next;
        count++;
        
        //값이 없을 때 while문을 빠져나가는 if문
        if(temp == null)
            return -1;
    }
    return count;
}



public String toString() {
    // 노드가 존재하지 않는다면은 빈 객체 모양 반환
    if(head == null){
        return "[]";
    }       

    //head의 값을 temp에 저장
    Node temp = head;
    String str = "[";

    // 다음 노드가 존재하지않을때까지 
    // node의 데이터를 str에 담고, 그 다음 노드를 템프에 담아 반복시킨다.
    while(temp.next != null){
        str += temp.data + ",";
        temp = temp.next;
    }
    // 마지막 노드를 출력결과에 포함시킵니다.
    str += temp.data;
    return str+"]";
}




public static void main(String[] args) {
	LinkedList numbers = new LinkedList();
	numbers.addLast(5);
	numbers.addLast(10);
	System.out.println(numbers);
	System.out.println(numbers.indexOf(10));
	
	numbers.addLast(20);
	numbers.addLast(30);
	System.out.println(numbers);
	numbers.addmid(1, 400);
	numbers.addmid(3, 5000);
	
	System.out.println(numbers.removeFirst());
	
	System.out.println(numbers);
}


}
