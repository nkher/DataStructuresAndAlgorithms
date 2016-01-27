package nkher.datastructures.bloomfilter;

import java.util.List;

import nkher.Interfaces.MyBloomFilter;
import nkher.algorithms.hash.FNV;
import nkher.algorithms.hash.HashMethod;
import nkher.algorithms.hash.Murmur3;
import nkher.datastructures.lists.DynamicArray;
import nkher.datastructures.map.BitMap;


/***
 * Some pieces of code taken from : 
 * https://github.com/Baqend/Orestes-Bloomfilter/blob/master/src/main/java/orestes/bloomfilter/FilterBuilder.java
 * 
 * @author nameshkher
 *
 * @param <T>
 */

public class BloomFilter<T> implements MyBloomFilter<T> {

	/** SERIAL ID GENERATED */
	private static final long serialVersionUID = 2328139686555028763L;
	
	private int numberOfExpectedElements;
	private double expectedFalsePositiveProbability;
	private int count;
	private int size;
	private int numberOfHashFunctions;
	private BitMap bloomDS;
	private HashMethod[] hashMethods;
	
	public BloomFilter() {
		initHashMethods(); // initializing hash methods
		numberOfHashFunctions = hashMethods.length;
		
		// still to decide on default expected elements and false positive probability

		bloomDS = new BitMap(); //  initializing the underlying bitmap		
	}
	
	public BloomFilter(int expectedElements, double falsePositiveProbability) {
		initHashMethods();
		expectedFalsePositiveProbability = falsePositiveProbability;
		numberOfExpectedElements = expectedElements;
		numberOfHashFunctions = hashMethods.length;
		size = optimialSize(expectedElements, falsePositiveProbability);
		bloomDS = new BitMap(size);
	}
	
	public BloomFilter(BloomFilter<T> other) {
		this(other.numberOfExpectedElements, other.expectedFalsePositiveProbability);
		bloomDS = other.bloomDS.clone();
	}
	
	/***
	 * A utility function to initialize our hash methods. 
	 * We use two methods for this BloomFilter. 1. FNV and 2. Murmur3. </br>
	 */
	private void initHashMethods() {
		hashMethods = new HashMethod[2];
		hashMethods[0] = new Murmur3();
		hashMethods[1] = new FNV();
	}

	@Override
	public boolean addBytes(byte[] bytes) {
		boolean inserted = false;
		int[] hashes = getHashedIndexes(bytes);
		for (int index : hashes) {
			if (bloomDS.get(index) == 0) {
				inserted = true;
				bloomDS.set(index);
			}
		}
		return inserted;
	}

	@Override
	public boolean addElement(T element) {
		return false;
	}

	@Override
	public List<Boolean> addAllElements(DynamicArray<T> elements) {
		return null;
	}

	@Override
	public void clear() {
		bloomDS.clear();
		this.count = 0;
	}

	@Override
	public boolean checkBloom(byte[] bytes) {
		int[] hashes = getHashedIndexes(bytes);
		for (int index : hashes) {
			if (bloomDS.get(index) == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkBloom(T element) {
		return false;
	}

	@Override
	public List<Boolean> checkAllElements(DynamicArray<T> elements) {
		return null;
	}

	@Override
	public MyBloomFilter<T> clone() {
		if (null == bloomDS) {
			throw new NullPointerException("BloomFilter not initialized.");
		}
		return new BloomFilter<>(this);
	}

	@Override
	public int count() {
		return this.count;
	}
	
	public int size() {
		return size;
	}

	@Override
	public int numberOfHashFunctionsUsed() {
		return this.numberOfHashFunctions;
	}

	@Override
	public double falsePositiveProbability() {
		return expectedFalsePositiveProbability;
	}
	
	public int numberOfExpectedElements() {
		return numberOfExpectedElements;
	}
	
	private int optimialSize(int n, double p) {
		return (int) Math.ceil(-1 * (n * Math.log(p)) / Math.pow(Math.log(2), 2));
	}
	
	public BitMap getUnerlyingBloomDS() {
		return this.bloomDS;
	}
	
	private int[] getHashedIndexes(byte[] data) {
		int[] hashes = new int[2];
		HashMethod hm;
		for (int i=0; i<hashMethods.length; i++) {
			hm = hashMethods[i];
			if (hm instanceof FNV) {
				hashes[0] = ((FNV) hm).hash_32(data)/this.size;
			}
			else if (hm instanceof Murmur3) {
				hashes[0] = ((Murmur3) hm).hash_32(data)/this.size;
			}
		}
		return hashes;
	}
}
