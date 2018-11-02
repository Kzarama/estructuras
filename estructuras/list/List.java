package list;

public class List<K, T> {
	
	private K key;
	
	private T object;
	
	private List next;
	
	public List(K key, T object) {
		this.object = object;
		this.key = key;
		this.next = null;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}
	
	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setNext(List next) {
		this.next = next;
	}
	
	public List getNext() {
		return next;
	}
	
	public void turnOffNext() {
		next = next.next;
	}
	
	public void insertAfter(List object) {
		object.next = next;
		next = object;
	}
	
}
