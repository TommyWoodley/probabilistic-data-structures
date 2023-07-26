import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tommywoodley.BloomFilter;

public class BloomFilterTest {

  private BloomFilter<String> bloomFilter;

  @BeforeEach
  public void setUp() {
    bloomFilter = new BloomFilter<>(0.001, 100_000);
  }

  @Test
  public void testAddAndContains() {
    bloomFilter.put("apple");
    bloomFilter.put("banana");

    assertTrue(bloomFilter.mightContain("apple"));
    assertTrue(bloomFilter.mightContain("banana"));
    assertFalse(bloomFilter.mightContain("orange"));
    assertFalse(bloomFilter.mightContain("pear"));
  }

  @Test
  public void testFalsePositives() {
    bloomFilter.put("hello");
    bloomFilter.put("world");
    bloomFilter.put("java");

    // Check for false positives
    assertFalse(bloomFilter.mightContain("python"));
    assertFalse(bloomFilter.mightContain("c++"));
  }

  @Test
  public void testLargeDataset() {
    int dataSize = 100_000;
    for (int i = 0; i < dataSize; i++) {
      bloomFilter.put("element" + i);
    }

    for (int i = 0; i < dataSize; i++) {
      assertTrue(bloomFilter.mightContain("element" + i));
    }

    assertFalse(bloomFilter.mightContain("nonexistent_element"));
  }
}
