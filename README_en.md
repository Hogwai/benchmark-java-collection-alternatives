# benchmark-java-collection-alternatives
## Table of Contents

- [Description](#description)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Benchmarks](#running-the-benchmarks)
- [Results](#results)
  - [FastUtil](#fastutil)
    - [HashSet vs IntOpenHashSet](#hashset-vs-intopenhashset)
    - [ArrayList vs IntArrayList](#arraylist-vs-intarraylist)
    - [HashMap vs Int2IntOpenHashMap](#hashmap-vs-int2intopenhashmap)

---
## Description
This repository contains JMH (Java Microbenchmark Harness) benchmarks comparing the performance of standard Java collections from `java.util` with their optimized equivalents (FastUtil, Agrona).

## Prerequisites
- Java 21
- Maven 3.6.3 or higher (or use the wrapper)

## Installation
- Clone the repository:
```bash
git clone https://github.com/Hogwai/benchmark-java-collection-alternatives.git
```
- Download the dependencies:
```bash
mvn clean install
```

## Running the Benchmarks
### IntelliJ
To run the benchmarks directly from IntelliJ, you need to install the [JMH Java Microbenchmark Harness](https://plugins.jetbrains.com/plugin/7529-jmh-java-microbenchmark-harness) plugin.

## Results

### FastUtil
Times are in microseconds (µs/op), measured with JMH (5 warm-up iterations, 5 measurement iterations, 2 forks).

#### HashSet vs IntOpenHashSet
The tables below present the benchmark results comparing `java.util.HashSet<Integer>` and `it.unimi.dsi.fastutil.ints.IntOpenHashSet` for the `add` and `contains` operations.

##### `add` Operation
| Size     | java.util.HashSet (µs/op) | fastutil.IntOpenHashSet (µs/op) | Gain (ratio) |
|----------|---------------------------|---------------------------------|----------------|
| 100      | 1.750 ± 0.098             | 0.359 ± 0.021                   | ~4.87x         |
| 1000     | 17.030 ± 0.211            | 3.180 ± 0.207                   | ~5.35x         |
| 10,000   | 142.175 ± 7.993           | 52.269 ± 8.926                  | ~2.72x         |
| 100,000  | 2000.463 ± 76.154         | 739.544 ± 30.210                | ~2.70x         |

##### `contains` Operation
| Size     | java.util.HashSet (µs/op) | fastutil.IntOpenHashSet (µs/op) | Gain (ratio) |
|----------|---------------------------|---------------------------------|----------------|
| 100      | 0.216 ± 0.020             | 0.104 ± 0.005                   | ~2.08x         |
| 1000     | 2.870 ± 0.400             | 1.035 ± 0.065                   | ~2.77x         |
| 10,000   | 28.112 ± 3.126            | 12.116 ± 0.740                  | ~2.32x         |
| 100,000  | 290.259 ± 28.938          | 189.197 ± 10.495                | ~1.53x         |

##### Observations
- **add**: `IntOpenHashSet` is **2.7 to 5.3x faster**, with the maximum gain at 1000 elements.
- **contains**: `IntOpenHashSet` is **1.5 to 2.8x faster**, with decreasing advantage for larger sizes.

#### ArrayList vs IntArrayList
The tables below present the benchmark results comparing `java.util.ArrayList<Integer>` and `it.unimi.dsi.fastutil.ints.IntArrayList` for the `add` and `get` operations.

##### `add` Operation
| Size     | java.util.ArrayList (µs/op) | fastutil.IntArrayList (µs/op) | Gain (ratio) |
|----------|-----------------------------|-------------------------------|----------------|
| 100      | 0.474 ± 0.020               | 0.115 ± 0.011                 | ~4.12x         |
| 1000     | 5.160 ± 0.543               | 1.148 ± 0.054                 | ~4.49x         |
| 10,000   | 52.625 ± 2.441              | 12.416 ± 0.239                | ~4.24x         |
| 100,000  | 563.262 ± 17.496            | 108.858 ± 4.085               | ~5.17x         |

##### `get` Operation
| Size     | java.util.ArrayList (µs/op) | fastutil.IntArrayList (µs/op) | Gain (ratio) |
|----------|-----------------------------|-------------------------------|----------------|
| 100      | 1.251 ± 0.098               | 1.222 ± 0.023                 | ~1.02x         |
| 1000     | 12.424 ± 0.320              | 12.194 ± 0.326                | ~1.02x         |
| 10,000   | 129.480 ± 5.467             | 129.017 ± 11.554              | ~1.00x         |
| 100,000  | 1622.351 ± 96.042           | 1811.133 ± 407.451            | ~0.90x         |

##### Observations
- **add**: `IntArrayList` is **4 to 5x faster**, with the maximum gain at 100,000 elements.
- **get**: Performance is **nearly identical** (~1.0-1.02x), with slight variability at large scale where `ArrayList` may be marginally faster.

#### HashMap vs Int2IntOpenHashMap
The tables below present the benchmark results comparing `java.util.HashMap<Integer, Integer>` and `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` for the `put` and `get` operations.

##### `put` Operation
| Size     | java.util.HashMap (µs/op) | fastutil.Int2IntOpenHashMap (µs/op) | Gain (ratio) |
|----------|---------------------------|-------------------------------------|----------------|
| 100      | 1.837 ± 0.126             | 0.482 ± 0.028                       | ~3.81x         |
| 1000     | 19.235 ± 1.882            | 4.870 ± 0.206                       | ~3.95x         |
| 10,000   | 174.106 ± 6.519           | 76.930 ± 13.478                     | ~2.26x         |
| 100,000  | 2751.915 ± 375.027        | 1122.377 ± 112.861                  | ~2.45x         |

##### `get` Operation
| Size     | java.util.HashMap (µs/op) | fastutil.Int2IntOpenHashMap (µs/op) | Gain (ratio) |
|----------|---------------------------|-------------------------------------|----------------|
| 100      | 1.420 ± 0.059             | 2.095 ± 0.089                       | ~0.68x         |
| 1000     | 19.008 ± 2.295            | 18.672 ± 0.557                      | ~1.02x         |
| 10,000   | 266.739 ± 19.672          | 208.464 ± 9.795                     | ~1.28x         |
| 100,000  | 6693.834 ± 1182.978       | 2468.793 ± 89.490                   | ~2.71x         |

##### Observations
- **put**: `Int2IntOpenHashMap` is **2.3 to 3.9x faster**, with the maximum gain at 1000 elements.
- **get**: Mixed results; `HashMap` is faster for 100 elements (~1.47x), but `Int2IntOpenHashMap` becomes **up to 2.7x faster** at 100,000 elements.