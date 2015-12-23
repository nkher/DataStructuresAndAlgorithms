package nkher.Interfaces;

import java.util.List;

public interface MyTrie {
	
	/**
	 * Returns the size of the trie or the number of elements/words/keys in the trie.
	 * @return
	 */
	int size();
	
	/**
	 * Returns true if there is no element in the trie else returns false.
	 * @return
	 */
	boolean isEmpty();
	
	/***
	 * Inserts a String into the trie.
	 * @param str
	 */
	void insert(String key);
	
	/***
	 * Checks if a String is present in the trie. Returns true if present else returns false.
	 * Checks for an exact match.
	 */
	boolean contains(String key);
	
	/***
	 * Returns all the keys in the trie starting with this prefix. Basically does a depth first search
	 * to find all the keys having the prefix and returns a list of all the keys. 
	 * @param prefix
	 * @return
	 */
	List<String> prefixSearch(String prefixKey);
	
	/***
	 * Returns true if the particular prefix is present within the trie. Utilizes the contains(Key)
	 * method to check if there is an exact match.
	 * 
	 * @param prefix
	 * @return
	 */
	boolean containsPrefix(String prefixKey);
		
	/***
	 * The trie can have a count associated to a single character and also a count for the whole String key. 
	 * Hence we can add the same key multiple times. This remove method, removes the key from the trie by
	 * decrementing its count in the trie. If you want to completely remove the key then the method
	 * removeKeyCompletely() would do that. </br></br>
	 * 
	 * the 
	 * 
	 * @param key
	 * @return
	 */
	boolean removeKey(String key);
	
	/***
	 * The trie can have a count associated to a single character and also a count for the whole String key. 
	 * Hence we can add the same key multiple times. This remove method, removes the key with the given prefix
	 * from the trie by decrementing its count in the trie. If you want to completely remove the key then the method
	 * removePrefixKeyCompletely() would do that. </br></br>
	 * 
	 * @param prefixKey
	 * @return
	 */
	boolean removePrefixKeys(String prefixKey);
	
	boolean removeKeyCompletely(String key);
	
	boolean removePrefixKeysCompletely(String prefixKey);
	
	/***
	 * Builds a trie from the list of keys passed.
	 * @param keys
	 */
	void buildTrie(List<String> keys);
}
