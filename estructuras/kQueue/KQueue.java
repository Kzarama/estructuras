package kQueue;

import java.util.NoSuchElementException;

public class KQueue<T> implements InterfaceQueue<T> {
	
	private int size;
	
	private NodeQueue<T> top;
	
	public KQueue() {
		this.size = 0;
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public void enQueue(T object) {
		if(isEmpty()) {
			top = new NodeQueue<T>(object);
			size++;
		} else {
			NodeQueue<T> aux = new NodeQueue<T>(object);
			while(aux.getNext() == null) {
				aux = aux.getNext();
			}
			aux = new NodeQueue<T>(object);
			size++;
		}
	}

	@Override
	public boolean isEmpty() {
		if(top == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T front() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return top.getObject();
		}
	}

	@Override
	public T deQueue() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		} else {
			T object = top.getObject();
			top = top.getNext();
			size--;
			return object;
		}
	}

}
