# Probabilistic Data Structures for Java

![License](https://img.shields.io/badge/license-MIT-blue.svg)

## Overview

Probabilistic Data Structures for Java is a repository that provides a collection of popular probabilistic data structures implemented in the Java programming language. These data structures offer efficient and approximate solutions to various data-related problems with probabilistic guarantees. They are particularly useful when dealing with massive datasets and high-velocity streams, as they can handle large amounts of data while maintaining low memory usage.

The repository aims to be a valuable resource for data engineers, data scientists, and Java developers interested in exploring and using probabilistic data structures in their applications.

## Table of Contents

- [Usage](#usage)
- [List of Data Structures](#list-of-data-structures)
- [Contributing](#contributing)
- [License](#license)

## Usage

To use the probabilistic data structures in your Java projects, you can add the relevant package as a dependency to your Maven project.

### Maven Dependency

Add the following dependency to your `pom.xml` file:

TODO

### Manual Installation

If you prefer to use the Java files directly, you can download the entire package and include it in your project.

## List of Data Structures

The repository includes the following probabilistic data structures:

1. [Bloom Filter](src/main/java/org/tommywoodley/BloomFilter.java)
2. [Scalable Bloom Filter](src/main/java/org/tommywoodley/ScalableBloomFilter.java)
3. [Counting Bloom Filter](src/main/java/org/tommywoodley/CountingBloomFilter.java)
4. Count-Min Sketch
5. Cuckoo Filter
6. Deletable Bloom Filter
7. HyperLogLog
8. Inverse Bloom Filter 
9. MinHash 
10. Partitioned Bloom Filter
12. Top-K

Each data structure is implemented as a separate Java class, and its usage is documented in the respective class file. To use a specific data structure, you can include the relevant Java class in your project and follow the usage instructions provided in the class documentation.
```java
import com.tommywoodley.BloomFilter;

public class Main {
    public static void main(String[] args) {
        // Create a BloomFilter with a given false positive probability and expected number of elements
        BloomFilter<String> bloomFilter = new BloomFilter<>(0.001, 1000);

        // Add elements to the filter
        bloomFilter.add("apple");
        bloomFilter.add("banana");
        bloomFilter.add("orange");

        // Check for membership
        boolean isPresent = bloomFilter.contains("apple");
        System.out.println("Is 'apple' present in the BloomFilter? " + isPresent);
    }
}

```

## Contributing

Contributions to this repository are highly appreciated! If you have implemented a new probabilistic data structure in Java or want to improve an existing one, follow these steps:

1. Fork this repository to your GitHub account.
2. Create a new branch for your feature: `git checkout -b feature/new-feature`.
3. Commit your changes: `git commit -m "Add new feature"`.
4. Push to the branch: `git push origin feature/new-feature`.
5. Submit a pull request to the `main` branch of this repository.

Please ensure that your code follows Java best practices, includes appropriate test cases, and is well-documented. Your contribution will be reviewed, and feedback may be provided to ensure the quality of the repository.

## License

This repository is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code for your purposes.

---
