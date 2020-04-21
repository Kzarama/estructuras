package kQueue;

public interface InterfaceQueue<T> {
	
	public void enQueue(T object);
	
	public boolean isEmpty();
	
	public T front();
	
	public T deQueue();
	
}
