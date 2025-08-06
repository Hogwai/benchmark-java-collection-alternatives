# benchmark-java-collection-alternatives
## Sommaire

- [Description](#description)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Exécution des benchmarks](#exécution-des-benchmarks)
- [Résultats](#résultats)
  - [FastUtil](#fastutil)
    - [ArrayList vs IntArrayList](#arraylist-vs-intarraylist)
    - [HashMap vs Int2IntOpenHashMap](#hashmap-vs-int2intopenhashmap)

---
## Description
Ce dépôt contient des benchmarks JMH (Java Microbenchmark Harness) comparant les performances des collections standards de java.util avec leurs équivalents optimisés (FastUtil, agrona).

## Prérequis
- Java 21
- Maven 3.6.3 ou plus (ou utiliser le wrapper)

## Installation
- Cloner le répôt:
```bash
git clone https://github.com/Hogwai/benchmark-java-collection-alternatives.git
```
- Télécharger les dépendances:
```bash
mvn clean install
```

## Exécution des benchmarks
### Intellij
Pour lancer les benchmarks directement depuis intellij, il est nécessaire d'installer le plugin [JMH Java Microbenchmark Harness](https://plugins.jetbrains.com/plugin/7529-jmh-java-microbenchmark-harness)

## Résultats

### FastUtil
Les temps sont en microsecondes (µs/op), mesurés avec JMH (5 itérations d'échauffement, 5 itérations de mesure, 2 forks).

#### HashSet vs IntOpenHashSet
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.HashSet<Integer>` et `it.unimi.dsi.fastutil.ints.IntOpenHashSet` pour les opérations `add` et `contains`.

##### Opération `add`
| Taille   | java.util.HashSet (µs/op) | fastutil.IntOpenHashSet (µs/op) | Gain (rapport) |
|----------|---------------------------|---------------------------------|----------------|
| 100      | 1,750 ± 0,098             | 0,359 ± 0,021                   | ~4,87x         |
| 1000     | 17,030 ± 0,211            | 3,180 ± 0,207                   | ~5,35x         |
| 10 000   | 142,175 ± 7,993           | 52,269 ± 8,926                  | ~2,72x         |
| 100 000  | 2000,463 ± 76,154         | 739,544 ± 30,210                | ~2,70x         |

##### Opération `contains`
| Taille   | java.util.HashSet (µs/op) | fastutil.IntOpenHashSet (µs/op) | Gain (rapport) |
|----------|---------------------------|---------------------------------|----------------|
| 100      | 0,216 ± 0,020             | 0,104 ± 0,005                   | ~2,08x         |
| 1000     | 2,870 ± 0,400             | 1,035 ± 0,065                   | ~2,77x         |
| 10 000   | 28,112 ± 3,126            | 12,116 ± 0,740                  | ~2,32x         |
| 100 000  | 290,259 ± 28,938          | 189,197 ± 10,495                | ~1,53x         |

##### Observations
- **add** : `IntOpenHashSet` est **2,7 à 5,3x plus rapide**, avec un gain maximal à 1000 éléments.
- **contains** : `IntOpenHashSet` est **1,5 à 2,8x plus rapide**, avec un avantage décroissant pour les grandes tailles.

#### ArrayList vs IntArrayList
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.ArrayList<Integer>` et `it.unimi.dsi.fastutil.ints.IntArrayList` pour les opérations `add` et `get`.

##### Opération `add`
| Taille   | java.util.ArrayList (µs/op) | fastutil.IntArrayList (µs/op) | Gain (rapport) |
|----------|-----------------------------|-------------------------------|----------------|
| 100      | 0,474 ± 0,020               | 0,115 ± 0,011                 | ~4,12x         |
| 1000     | 5,160 ± 0,543               | 1,148 ± 0,054                 | ~4,49x         |
| 10 000   | 52,625 ± 2,441              | 12,416 ± 0,239                | ~4,24x         |
| 100 000  | 563,262 ± 17,496            | 108,858 ± 4,085               | ~5,17x         |

##### Opération `get`
| Taille   | java.util.ArrayList (µs/op) | fastutil.IntArrayList (µs/op) | Gain (rapport) |
|----------|-----------------------------|-------------------------------|----------------|
| 100      | 1,251 ± 0,098               | 1,222 ± 0,023                 | ~1,02x         |
| 1000     | 12,424 ± 0,320              | 12,194 ± 0,326                | ~1,02x         |
| 10 000   | 129,480 ± 5,467             | 129,017 ± 11,554              | ~1,00x         |
| 100 000  | 1622,351 ± 96,042           | 1811,133 ± 407,451            | ~0,90x         |

##### Observations
- **add** : `IntArrayList` est **4 à 5x plus rapide**, avec un gain maximal à 100 000 éléments.
- **get** : Les performances sont **quasi identiques** (~1,0-1,02x), avec une légère variabilité à grande échelle où `ArrayList` peut être marginalement plus rapide.

#### HashMap vs Int2IntOpenHashMap
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.HashMap<Integer, Integer>` et `it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap` pour les opérations `put` et `get`.

##### Opération `put`
| Taille   | java.util.HashMap (µs/op) | fastutil.Int2IntOpenHashMap (µs/op) | Gain (rapport) |
|----------|---------------------------|-------------------------------------|----------------|
| 100      | 1,837 ± 0,126             | 0,482 ± 0,028                       | ~3,81x         |
| 1000     | 19,235 ± 1,882            | 4,870 ± 0,206                       | ~3,95x         |
| 10 000   | 174,106 ± 6,519           | 76,930 ± 13,478                     | ~2,26x         |
| 100 000  | 2751,915 ± 375,027        | 1122,377 ± 112,861                  | ~2,45x         |

##### Opération `get`
| Taille   | java.util.HashMap (µs/op) | fastutil.Int2IntOpenHashMap (µs/op) | Gain (rapport) |
|----------|---------------------------|-------------------------------------|----------------|
| 100      | 1,420 ± 0,059             | 2,095 ± 0,089                       | ~0,68x         |
| 1000     | 19,008 ± 2,295            | 18,672 ± 0,557                      | ~1,02x         |
| 10 000   | 266,739 ± 19,672          | 208,464 ± 9,795                     | ~1,28x         |
| 100 000  | 6693,834 ± 1182,978       | 2468,793 ± 89,490                   | ~2,71x         |

##### Observations
- **put** : `Int2IntOpenHashMap` est **2,3 à 3,9x plus rapide**, avec un gain maximal à 1000 éléments.
- **get** : Résultats mitigés ; `HashMap` est plus rapide pour 100 éléments (~1,47x), mais `Int2IntOpenHashMap` devient **jusqu'à 2,7x plus rapide** à 100 000 éléments.