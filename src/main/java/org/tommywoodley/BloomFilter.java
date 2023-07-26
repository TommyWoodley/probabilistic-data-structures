package org.tommywoodley;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

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

  public int size() {
    return this.numberOfAddedElements;
  }
}
