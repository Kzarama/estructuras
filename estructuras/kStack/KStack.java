package kStack;

import java.util.EmptyStackException;

public class KStack<T> implements InterfaceStack<T> {
	
	private int size;
	
	private NodeStack<T> topStack;
	
	public KStack() {
		this.size= 0;
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public void push(T object) {
		if(object != null) {
			NodeStack<T> aux = new NodeStack<T>(object);
			aux.setNext(topStack);
			topStack = aux;
			size++;
		}
	}

	@Override
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		} else {
			T object = topStack.getObject();
			topStack = topStack.getNext();
			size--;
			return object;
		}
	}

	@Override
	public boolean isEmpty() {
		if(topStack == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T top() {
		if(isEmpty()) {
			throw new EmptyStackException();
		} else {
			return topStack.getObject();
		}
	}
		
}
