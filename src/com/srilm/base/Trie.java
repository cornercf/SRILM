package com.srilm.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Trie<KeyT, DataT> {
	/*HashMap of child nodes*/
	private Map<KeyT, Trie<KeyT, DataT>> sub;
	
	/*data stored at this node*/
	private DataT data;
	
//	public Trie(int size) {
//		//sub(size)
//		/*
//	     * Data starts out zero-initialized for convenience
//	     */
//	    //memset(&data, 0, sizeof(data));
//	}
	
	DataT value() {
		return data;
	}
	
	DataT find(final List<KeyT> keys, boolean foundP) {
		Trie<KeyT, DataT> node = findTrie(keys, foundP);
		return node == null ? node.data : 0;
	}
	
	DataT find(KeyT key, boolean foundP) {
		Trie<KeyT, DataT> node = findTrie(key, foundP);
		return node == null ? node.data : 0;
	}
	
	DataT find(final List<KeyT> keys) {
		boolean found = false;
		return find(keys, found);
	}
	
	DataT find(KeyT key) {
		boolean found = false;
		return find(key, found);
	}
	
	
	DataT findPrefix(final List<KeyT> keys, int depth) {
		return findPrefixTrie(keys, depth).data;
	}
	
	DataT findPrefix(final List<KeyT> keys) {
		int depth = 0;
		return findPrefixTrie(keys, depth).data;
	}
	
	DataT insert(final List<KeyT> keys, boolean foundP) {
		return insertTrie(keys, foundP).data;
	}
	
	DataT insert(KeyT key, boolean foundP) {
		return insertTrie(key, foundP).data;
	}
	
	DataT insert(final List<KeyT> keys) {
		boolean found = false;
		return insert(keys, found);
	}
	
	DataT insert(KeyT key) {
		boolean found = false;
		return insert(key, found);
	}
	
	
	boolean remove(final List<KeyT> keys, DataT removedData) {
		Trie<KeyT, DataT> node = null;
		boolean ret = removeTrie(keys, node);
		if(removedData != null) /*if removedData is not init, the data can't be assigned*/
			removedData = node.data;
		return ret;
	}
	
	boolean remove(KeyT key, DataT removedData) {
		Trie<KeyT, DataT> node = null;
		boolean ret = removeTrie(key, node);
		if(removedData != null)
			removedData = node.data;
		return ret;
	}
	
	
	public Trie<KeyT, DataT> findTrie(final KeyT[] keys, int start, boolean foundP) {
		if(keys == null) { // || Map_noKeyP(keys[0])
			foundP = true;
			return (Trie<KeyT,DataT>)this;
		} else {
			Trie<KeyT, DataT> subtrie = sub.get(keys[start]);
			
			if(subtrie == null) {
				foundP = false;
				return null;
			} else {
				return subtrie.findTrie(keys, start+1, foundP);
			}
		}
		
	}
	
	Trie<KeyT, DataT> findTrie(KeyT key, boolean foundP) {
		return sub.find(key, foundP);
	}
	
	Trie<KeyT, DataT> findTrie(final List<KeyT> keys) {
		 boolean found = false;
		 return findTrie(keys, found);
	}
	
	Trie<KeyT, DataT> findTrie(final KeyT key) {
		boolean found = false;
		return findTrie(key, found);
	}
	
	
	public Trie<KeyT, DataT> findPrefixTrie(final KeyT[] keys, int start, Integer depth) {
		if(keys == null) { //Map_noKeyP(keys[0])
			depth = 0;
			return (Trie<KeyT, DataT>) this;
		} else {
			Trie<KeyT, DataT> subtrie = sub.get(keys[start]);
			
			if(subtrie == null) {
				depth = 0;
				return (Trie<KeyT, DataT>) this;
			} else {
				Integer subDepth = new Integer(0);
				Trie<KeyT, DataT> node = subtrie.findPrefixTrie(keys, start+1, subDepth);
				depth = subDepth.intValue() + 1;
				return node;
			}
		}
		
	}
	
	Trie<KeyT, DataT> findPrefixTrie(final List<KeyT> keys) {
		int depth = 0;
		return findPrefixTrie(keys, depth);
	}
	
	public abstract Trie<KeyT, DataT> insertTrie(final List<KeyT> keys, boolean foundP);
	
	Trie<KeyT, DataT> insertTrie(KeyT key, boolean foundP) {
		Trie<KeyT, DataT> subtrie = sub.insert(key, foundP);
		//if(!foundP)
			//new (&subtrie->sub) KeyT(0);
		return subtrie;	
	}
	
	Trie<KeyT, DataT> insertTrie(final List<KeyT> keys) {
		boolean found = false;
		return insertTrie(keys, found);
	}
	
	Trie<KeyT, DataT> insertTrie(KeyT key) {
		boolean found = false;
		return insertTrie(key, found);
	}
	
	
	public abstract boolean removeTrie(final List<KeyT> keys, Trie<KeyT, DataT> removedData);
	
	boolean removeTrie(KeyT key, Trie<KeyT, DataT> removedData) {
		List<KeyT> keys = new ArrayList<KeyT>();
		keys.add(key);
		keys.add(null);
		return removeTrie(keys, removedData);
	}
	
	
	public void clear() {
		sub.clear();
	}
	
	public abstract int numEntries(final List<KeyT> keys);
	
	public abstract void dump(int indent);
	
	//public void memState(MemStats &stats);

}
