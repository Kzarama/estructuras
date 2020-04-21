package hashs;

import java.util.NoSuchElementException;

public class TablaHash<T> {
	
	private int tamanio;
	
	private ItemHash<T>[] itemHash;
	
	public TablaHash(int tamanio) {
		this.tamanio = tamanio;
		itemHash = new ItemHash[tamanio];
	}
	
	public int hashCodeValue(int key) {
		return key % tamanio;
	}
	
	public void push(int key, T object) {
		int hashPosition = hashCodeValue(key);
		if(itemHash[hashPosition] == null) {
			itemHash[hashPosition] = new ItemHash<T>(key, object);
		} else {
			ItemHash<T> hashItemCurrent = itemHash[hashPosition];
			while(hashItemCurrent.getNextItemHash() != null) {
				hashItemCurrent = hashItemCurrent.getNextItemHash();
			}
			hashItemCurrent.setNextItemHash(new ItemHash<T>(key, object));
		}
	}
	
	public T search(int key) {
		int hashPosition = hashCodeValue(key);
		ItemHash<T> hashItemCurrent = itemHash[hashPosition];
		while(hashItemCurrent != null && hashItemCurrent.getKey() != key) {
			hashItemCurrent = hashItemCurrent.getNextItemHash();
		}
		if(itemHash[hashPosition] == null) {
			throw new NoSuchElementException();
		} else {
			return hashItemCurrent.getItem();
		}
	}
	
	public void delete(int key) {
		int hashPosition = hashCodeValue(key);
		while(itemHash[hashPosition] != null && itemHash[hashPosition].getNextItemHash().getKey() != key) {
			itemHash[hashPosition] = itemHash[hashPosition].getNextItemHash();
		}
		if(itemHash[hashPosition] == null) {
			throw new NoSuchElementException();
		} else {
			itemHash[hashPosition].setNextItemHash(itemHash[hashPosition].getNextItemHash().getNextItemHash());
		}
	}
	
}
