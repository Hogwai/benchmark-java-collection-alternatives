# benchmark-java-collection-alternatives
French version: [README_FR.md](README_fr.md)

## Table of Contents

- [Description](#description)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Benchmarks](#running-the-benchmarks)
- [Results](#results)
  - [fastutil](#fastutil)
    - [HashSet vs IntOpenHashSet](#hashset-vs-intopenhashset)
    - [ArrayList vs IntArrayList](#arraylist-vs-intarraylist)
    - [HashMap vs Int2IntOpenHashMap](#hashmap-vs-int2intopenhashmap)
  - [agrona](#agrona)
    - [ArrayList vs Agrona IntArrayList](#arraylist-vs-agrona-intarraylist)
    - [HashMap vs Agrona Int2IntHashMap](#hashmap-vs-agrona-int2inthashmap)
  - [eclipse-collections](#eclipse-collections)
    - [HashSet vs Eclipse IntHashSet](#hashset-vs-eclipse-inthashset)
    - [ArrayList vs Eclipse IntArrayList](#arraylist-vs-eclipse-intarraylist)
    - [HashMap vs MutableIntIntMap](#hashmap-vs-mutableintintmap)

---
## Description
This repository contains JMH (Java Microbenchmark Harness) benchmarks comparing the performance of standard Java collections from `java.util` with their optimized equivalents ([fastutil](https://github.com/vigna/fastutil), [agrona](https://github.com/aeron-io/agrona), [eclipse-collections](https://github.com/eclipse-collections/eclipse-collections)).

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

### fastutil
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

### agrona
#### ArrayList vs Agrona IntArrayList

The tables below present the benchmark results comparing `java.util.ArrayList<Integer>` and `org.agrona.collections.IntArrayList` for the `add` and `get` operations.

##### Operation `add`
| Size     | java.util.ArrayList (µs/op) | agrona.IntArrayList (µs/op) | Gain (ratio) |
|----------|-----------------------------|-----------------------------|----------------|
| 100      | 0.506 ± 0.031               | 0.113 ± 0.010               | ~4.48x         |
| 1000     | 5.126 ± 0.283               | 1.440 ± 0.332               | ~3.56x         |
| 10,000   | 57.441 ± 5.968              | 14.603 ± 1.973              | ~3.93x         |
| 100,000  | 584.322 ± 23.313            | 112.660 ± 5.952             | ~5.19x         |

##### Operation `get`
| Size     | java.util.ArrayList (µs/op) | agrona.IntArrayList (µs/op) | Gain (ratio) |
|----------|-----------------------------|-----------------------------|----------------|
| 100      | 1.259 ± 0.043               | 1.361 ± 0.030               | ~0.93x         |
| 1000     | 12.540 ± 0.214              | 15.924 ± 2.070              | ~0.79x         |
| 10,000   | 127.313 ± 3.796             | 143.565 ± 8.570             | ~0.89x         |
| 100,000  | 1678.622 ± 147.345          | 1812.784 ± 69.776           | ~0.93x         |

##### Observations
- **add**: `IntArrayList` is **3.56 to 5.19x faster**, with the maximum gain at 100,000 elements.
- **get**: `ArrayList` is slightly faster (~0.79 to 0.93x), but the differences are small and within the margins of error.

#### HashMap vs Agrona Int2IntHashMap
The tables below present the benchmark results comparing `java.util.HashMap<Integer, Integer>` and `org.agrona.collections.Int2IntHashMap` for the `put` and `get` operations.

##### Operation `put`
| Size     | java.util.HashMap (µs/op) | agrona.Int2IntHashMap (µs/op) | Gain (ratio) |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 1.888 ± 0.145             | 1.526 ± 0.103                 | ~1.24x         |
| 1000     | 21.514 ± 2.826            | 16.841 ± 1.456                | ~1.28x         |
| 10,000   | 192.646 ± 19.421          | 135.527 ± 12.900              | ~1.42x         |
| 100,000  | 3041.915 ± 1165.324       | 4571.294 ± 516.363            | ~0.67x         |

##### Operation `get`
| Size     | java.util.HashMap (µs/op) | agrona.Int2IntHashMap (µs/op) | Gain (ratio) |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 1.581 ± 0.115             | 2.077 ± 0.070                 | ~0.76x         |
| 1000     | 20.204 ± 1.313            | 25.864 ± 2.908                | ~0.78x         |
| 10,000   | 306.371 ± 36.816          | 611.495 ± 869.572             | ~0.50x         |
| 100,000  | 7610.090 ± 2464.821       | 4157.502 ± 644.385            | ~1.83x         |

##### Observations
- **put**: `Int2IntHashMap` is **1.24 to 1.42x faster** for sizes 100 to 10,000, but **~1.5x slower** at 100,000 elements, with high variability.
- **get**: `HashMap` is faster (~1.3-2x) for 100 to 10,000 elements, but `Int2IntHashMap` is **~1,83x faster** for 100 000 elements.

### eclipse-collections
#### HashSet vs Eclipse IntHashSet
The tables below present the benchmark results comparing `java.util.HashSet<Integer>` and `org.eclipse.collections.impl.set.mutable.primitive.IntHashSet` for the `add` and `contains` operations.

##### Operation `add`
| Size     | java.util.HashSet (µs/op) | Eclipse IntHashSet (µs/op) | Gain (ratio)  |
|----------|---------------------------|----------------------------|----------------|
| 100      | 1,823 ± 0,190             | 0,494 ± 0,160              | ~3.69x         |
| 1000     | 23,480 ± 6,174            | 8,385 ± 2,312              | ~2.80x         |
| 10,000   | 231,990 ± 91,308          | 95,981 ± 4,390             | ~2.42x         |
| 100,000  | 4253,353 ± 5232,318       | 1605,101 ± 71,214          | ~2.65x         |

##### Operation `contains`
| Size     | java.util.HashSet (µs/op) | Eclipse IntHashSet (µs/op) | Gain (ratio)  |
|----------|---------------------------|----------------------------|----------------|
| 100      | 0,494 ± 0,057             | 0,346 ± 0,167              | ~1.43x         |
| 1000     | 5,620 ± 0,420             | 8,005 ± 2,673              | ~0.70x         |
| 10,000   | 66,996 ± 21,513           | 65,854 ± 5,298             | ~1.02x         |
| 100,000  | 1316,742 ± 230,694        | 1195,077 ± 44,682          | ~1.10x         |

##### Observations
- **add**: `IntHashSet` is **2.42 to 3.69x faster**, with the maximum gain at 100 elements. Performance remains strong at scale despite high variability for `HashSet` at 100,000.
- **contains**: Mixed results; `IntHashSet` is faster (~1.43x) at 100 elements but slower (~0.70x) at 1,000. Performance is nearly identical at 10,000 and 100,000 (~1.02-1.10x).

### ArrayList vs Eclipse IntArrayList
The tables below present the benchmark results comparing `java.util.ArrayList<Integer>` and `org.eclipse.collections.impl.list.mutable.primitive.IntArrayList` for the `add` and `get` operations.

##### Operation `add`
| Size     | java.util.ArrayList (µs/op) | Eclipse IntArrayList (µs/op) | Gain (ratio)  |
|----------|-----------------------------|------------------------------|----------------|
| 100      | 0,501 ± 0,030               | 0,121 ± 0,012                | ~4.14x         |
| 1000     | 5,288 ± 0,448               | 1,325 ± 0,102                | ~3.99x         |
| 10,000   | 57,737 ± 4,016              | 14,139 ± 0,655               | ~4.08x         |
| 100,000  | 587,736 ± 65,289            | 181,133 ± 155,147            | ~3.24x         |

##### Operation `get`
| Size     | java.util.ArrayList (µs/op) | Eclipse IntArrayList (µs/op) | Gain (ratio)  |
|----------|-----------------------------|------------------------------|----------------|
| 100      | 1,326 ± 0,057               | 1,356 ± 0,116                | ~0.98x         |
| 1000     | 12,806 ± 0,305              | 13,208 ± 0,600               | ~0.97x         |
| 10,000   | 136,481 ± 14,400            | 139,395 ± 7,133              | ~0.98x         |
| 100,000  | 1692,195 ± 40,706           | 2055,078 ± 1042,478          | ~0.82x         |

##### Observations
- **add**: `IntArrayList` is **3.24 to 4.14x faster**, with a consistent gain for small and medium sizes, but slightly reduced at 100,000 elements due to high variability.
- **get**: Performance is **almost identical** (~0.97-0.98x) for sizes from 100 to 10,000, with `ArrayList` being slightly faster (~1.22x) at 100,000.

#### HashMap vs MutableIntIntMap
The tables below present the benchmark results comparing `java.util.HashMap<Integer, Integer>` and `org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap` for the `put` and `get` operations.

##### Operation `put`
| Size     | java.util.HashMap (µs/op) | Eclipse IntIntHashMap (µs/op) | Gain (ratio)  |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 2,077 ± 0,319             | 0,626 ± 0,038                 | ~3.32x         |
| 1000     | 21,543 ± 1,554            | 6,531 ± 0,574                 | ~3.30x         |
| 10,000   | 185,059 ± 13,089          | 65,336 ± 6,458                | ~2.83x         |
| 100,000  | 2657,581 ± 198,832        | 649,039 ± 100,905             | ~4.09x         |

##### Operation `get`
| Size     | java.util.HashMap (µs/op) | Eclipse IntIntHashMap (µs/op) | Gain (ratio)  |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 1,768 ± 0,360             | 1,505 ± 0,140                 | ~1.17x         |
| 1000     | 20,620 ± 1,656            | 14,014 ± 0,273                | ~1.47x         |
| 10,000   | 312,758 ± 58,560          | 148,900 ± 3,901               | ~2.10x         |
| 100,000  | 7274,169 ± 1086,437       | 2318,541 ± 919,592            | ~3.14x         |

##### Observations
- **put**: `IntIntHashMap` is **2.83 to 4.09x faster**, with increasing gains at scale (~4.09x at 100,000).
- **get**: `IntIntHashMap` is **1.17 to 3.14x faster**, with a significant advantage at 100,000 elements (~3.14x).