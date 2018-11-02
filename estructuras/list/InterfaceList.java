package list;

public interface InterfaceList<K, T> {
	
	public void add(K key, T object);
	
	public boolean isEmpty();
	
	public List<K, T> getFirstElement();
	
	public List<K, T> getLastElement();
	
	public List<K, T> search(K key);
	
	public List<K, T> searchLast(K key);
	
	public void delete(K key);
	
	public void addBefore(K key, T object);
	
	public void addAfter(K key, T object);
	
}
