package kStack;

public class NodeStack<T> {
	
	private T object;
	
	private NodeStack<T> next;
	
	public NodeStack(T object) {
		this.object = object;
		this.next = null;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
	
	public void setNext(NodeStack<T> next) {
		this.next = next;
	}
	
	public NodeStack<T> getNext(){
		return next;
	}
	
}
