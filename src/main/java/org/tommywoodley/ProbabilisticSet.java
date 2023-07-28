package org.tommywoodley;

public interface ProbabilisticSet<E> {

  void put(E element);

  boolean mightContain(E element);

  int size();
}
