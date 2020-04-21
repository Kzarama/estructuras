package kStack;

public interface InterfaceStack<T> {
	
	public void push(T object);
	
	public T pop();
	
	public boolean isEmpty();
	
	public T top();
	
}
