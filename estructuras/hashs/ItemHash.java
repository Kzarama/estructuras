package hashs;

public class ItemHash<T> {
	
	private int key;
	private T item;
	private ItemHash<T> nextItemHash;
	
	public ItemHash(int k, T object) {
		this.key = k;
		this.item = object;
	}
	
	public void setKey(int k) {
		this.key = k;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setItem(T object) {
		this.item = object;
	}
	
	public T getItem() {
		return item;
	}
	
	public void setNextItemHash(ItemHash<T> next) {
		this.nextItemHash = next;
	}
	
	public ItemHash<T> getNextItemHash() {
		return nextItemHash;
	}
	
}
