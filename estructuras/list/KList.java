package list;

import java.util.NoSuchElementException;

public class KList<K, T> implements InterfaceList<K, T> {
	
	private int size;
	private int numberOfElements;
	private List<K, T> top;
	
	public KList(){
		numberOfElements = 0;
		top = null;
	}
	
	@Override
	public void add(K key, T object) {
		if(isEmpty()) {
			top = new List<K, T>(key, object);
			numberOfElements++;
		} else {
			List<K, T> aux = top;
			while(aux.getNext() != null) {
				aux = aux.getNext();
			}
			aux.setNext(new List<K, T>(key, object));
			numberOfElements++;
		}
	}

	@Override
	public boolean isEmpty() {
		if(top == null) 
			return true;
		 else 
			return false;
	}

	@Override
	public List<K, T> getFirstElement() {	
		return top;
	}

	@Override
	public List<K, T> getLastElement() {
		List<K, T> aux = top;
		while(aux != null) {
			aux = aux.getNext();
		}
		return aux;
	}

	@Override
	public List<K, T> search(K key) {
		List<K, T> aux = top;
		while(aux != null && !aux.getKey().equals(key)) {
			aux = aux.getNext();
		}
		if(aux == null) throw new NoSuchElementException();
		return aux;
	}

	@Override
	public List<K, T> searchLast(K key) {
		List<K, T> aux = top;
		List<K, T> last = null;
		while(aux != null && !aux.getKey().equals(key)) {
			last = aux;
			aux = aux.getNext();
		}
		if(aux == null) throw new NoSuchElementException();
		return last;
	}

	@Override
	public void delete(K key) {
		if(top == null) {
			throw new NoSuchElementException();
		} else if(top.getKey().equals(key)) {
			top = top.getNext();
			numberOfElements--;
		} else {
			List<K, T> aux = search(key);
			if(aux == null) throw new NoSuchElementException();
			aux.turnOffNext();
			numberOfElements--;
		}
	}

	@Override
	public void addBefore(K key, T object) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		} else {
			List<K, T> aux = search(key);
			aux.insertAfter(new List<K, T>(key, object));
		}
	}

	@Override
	public void addAfter(K key, T object) {
		if(isEmpty()) {
			throw new NoSuchElementException();
		} else if(key == top.getKey()) {
			List<K, T> aux = new List<K, T>(key, object);
			aux.setNext(top);
			top = aux;
			numberOfElements++;
		} else {
			List<K, T> aux = searchLast(key);
			aux.insertAfter(new List<K, T>(key, object));
			numberOfElements++;
		}
	}
	
}
