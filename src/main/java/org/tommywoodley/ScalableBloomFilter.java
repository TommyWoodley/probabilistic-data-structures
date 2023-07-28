package org.tommywoodley;

import java.util.ArrayList;
import java.util.List;

public class ScalableBloomFilter<E> implements ProbabilisticSet<E> {

  public static final int INITIAL_CAPACITY_POW = 4;
  private final List<BloomFilter<E>> bloomFilters;
  private int modCount;
  private int numberOfAddedElements;
  private final double falsePositiveProbability;

  public ScalableBloomFilter(double falsePositiveProbability) {
    this.bloomFilters = new ArrayList<>();
    this.bloomFilters.add(new BloomFilter<>(falsePositiveProbability, INITIAL_CAPACITY_POW));
    this.modCount = 1;
    this.numberOfAddedElements = 0;
    this.falsePositiveProbability = falsePositiveProbability;
  }

  @Override
  public void put(E element) {
    BloomFilter<E> lastFilter = bloomFilters.get(bloomFilters.size() - 1);
    lastFilter.put(element);
    numberOfAddedElements++;

    if (lastFilter.getFalsePositiveProbability() >= falsePositiveProbability) {
      // last filter has reached it maximum - add another filter
      bloomFilters.add(new BloomFilter<>(falsePositiveProbability, calculateNextSize()));
      modCount++;
    }
  }

  @Override
  public boolean mightContain(E element) {
    for (BloomFilter<E> filter : bloomFilters) {
      if (filter.mightContain(element)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int size() {
    return numberOfAddedElements;
  }

  private int calculateNextSize() {
    return (int) Math.pow(2, INITIAL_CAPACITY_POW + modCount);
  }
}
