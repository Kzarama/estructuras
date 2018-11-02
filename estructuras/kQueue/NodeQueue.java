package kQueue;

public class NodeQueue<T> {
	
	private T object;
	
	private NodeQueue<T> next;
	
	public NodeQueue(T object) {
		this.object = object;
		this.next = null;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
	
	public void setNext(NodeQueue<T> next) {
		this.next = next;
	}
	
	public NodeQueue<T> getNext() {
		return next;
	}
	
}
