package linkedlist;
public class LinkedList {
   private Node head;
   private Node tail;
   private int size;

   //노드 클래스 생성
   private class Node{
      
      private Object data;
      private Node next;
      
      public Node(Object data) {
         this.data = data;
         this.next = null;         
      }
      
      @Override
      public String toString() {
         return String.valueOf(data);
      }
   }
}