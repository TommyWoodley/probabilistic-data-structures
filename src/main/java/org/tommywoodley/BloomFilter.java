package org.tommywoodley;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

/** Represents a Bloom Filter.
 *  A Bloom filter is a space-efficient probabilistic data structure used to test whether
 *  an element is a member of a set. The idea behind a Bloom filter is to use multiple
 *  hash functions to map elements to a bit array.
 * <p>
 *  When adding an element to the Bloom filter, the corresponding bits in the array are
 *  set to 1.
 *  When checking for membership, the filter checks if all the corresponding bits for a
 *  given element are set to 1. If any of them are not set, then the element is
 *  definitely not in the set. However, if all bits are set, the element might be in the
 *  set, or it might be a false positive due to potential hash collisions.

 *
 * @author tommywoodley
 * @param <E> the type of element in the bloom filter e.g. String, Integer, Long
 */
public class BloomFilter<E> {
  private BitSet bitset;
  private final int bitSetSize;
  private int numberOfAddedElements;
  private final int k;
  static final Charset charset = StandardCharsets.UTF_8; // encoding used for storing hash values as strings

  public BloomFilter(double c, int n, int k) {
    this.k = k;
    this.bitSetSize = (int)Math.ceil(c * n);
    numberOfAddedElements = 0;
    this.bitset = new BitSet(bitSetSize);
  }

  public BloomFilter(int bitSetSize, int expectedNumberOElements) {
    this(bitSetSize / (double)expectedNumberOElements,
        expectedNumberOElements,
        (int) Math.round((bitSetSize / (double)expectedNumberOElements) * Math.log(2.0)));
  }

  public BloomFilter(double falsePositiveProbability, int expectedNumberOfElements) {
    this(Math.ceil(-(Math.log(falsePositiveProbability) / Math.log(2))) / Math.log(2), // c = k / ln(2)
        expectedNumberOfElements,
        (int)Math.ceil(-(Math.log(falsePositiveProbability) / Math.log(2)))); // k = ceil(-log_2(false prob.))
  }

  public BloomFilter(int bitSetSize, int expectedNumberOfFilterElements, int actualNumberOfFilterElements, BitSet filterData) {
    this(bitSetSize, expectedNumberOfFilterElements);
    this.bitset = filterData;
    this.numberOfAddedElements = actualNumberOfFilterElements;
  }

  /**
   * Inserts an element into the Bloom filter.
   * <p>
   * The put method adds the specified element to the Bloom filter, by setting the
   * corresponding bits in the underlying bit array using multiple hash functions.
   *
   * @param element The element to be added to the Bloom filter.
   * @throws NullPointerException If the specified element is null.
   */
  public void put(E element) {
    put(element.toString().getBytes(charset));
  }

  private void put(byte[] bytes) {
    int[] hashes = Hasher.createHashes(bytes, k);

    for (int hash : hashes) {
      bitset.set(Math.abs(hash % bitSetSize), true);
    }
    numberOfAddedElements++;
  }

  /**
   * Checks if the Bloom filter might contain the specified element.
   * <p>
   * This method does not provide a definitive answer and may return false positives
   * due to potential hash collisions. If this method returns true for a given element, it
   * means that the element might be in the set. However, false positives are possible,
   * and it does not guarantee that the element is definitely in the set. If this method
   * returns false it can be guaranteed that the element is not in the set.
   *
   * @param element The element to be checked for presence in the bloom filter.
   * @return {@code true} if the element might be in the set, {@code false} if it is definitely not in the set.
   * @throws NullPointerException If the specified element is null.
   */
  public boolean mightContain(E element) {
    return mightContain(element.toString().getBytes(charset));
  }

  private boolean mightContain(byte[] bytes) {
    int[] hashes = Hasher.createHashes(bytes, k);

    for (int hash : hashes) {
      if (!bitset.get(Math.abs(hash % bitSetSize))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the number of elements in the Bloom filter.
   * <p>
   * Note that this exactly corresponds to the number of elements inserted and will
   * count duplicates.
   *
   * @return The number of elements in the bloom filter.
   */
  public int size() {
    return this.numberOfAddedElements;
  }

  /**
   * Get the current false positive probability of the Bloom Filter.
   *
   * @return The current false positive probability as a double value between 0 and 1.
   *         A value closer to 0 indicates a lower probability of false positives,
   *         while a value closer to 1 indicates a higher probability of false positives.
   *
   * @see #put(E)
   * @see #mightContain(E)
   */
  public double getFalsePositiveProbability() {
    return getFalsePositiveProbability(numberOfAddedElements);
  }

  private double getFalsePositiveProbability(double numberOfElements) {
    // (1 - e^(-k * n / m)) ^ k
    return Math.pow((1 - Math.exp(-k * numberOfElements
        / (double) bitSetSize)), k);

  }
}
