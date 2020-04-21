package a;


class Node<T> {

	private T iData;
   
   public Node(T key) { 
	   iData = key;
   }
   
   public T getKey() {
	   return iData;
   }
  
   public void setKey(T id) {
	   iData = id;
   }
}