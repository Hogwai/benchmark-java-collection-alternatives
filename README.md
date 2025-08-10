# benchmark-java-collection-alternatives
English version: [README_EN.md](README_en.md)
## Sommaire

- [Description](#description)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Exécution des benchmarks](#exécution-des-benchmarks)
- [Résultats](#résultats)
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
Ce dépôt contient des benchmarks JMH (Java Microbenchmark Harness) comparant les performances des collections standards de java.util avec leurs équivalents optimisés ([fastutil](https://github.com/vigna/fastutil), [agrona](https://github.com/aeron-io/agrona), [eclipse-collections](https://github.com/eclipse-collections/eclipse-collections)).

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

### fastutil
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

### agrona
#### ArrayList vs Agrona IntArrayList

Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.ArrayList<Integer>` et `org.agrona.collections.IntArrayList` pour les opérations `add` et `get`.

##### Opération `add`
| Taille   | java.util.ArrayList (µs/op) | agrona.IntArrayList (µs/op) | Gain (rapport) |
|----------|-----------------------------|-----------------------------|----------------|
| 100      | 0,506 ± 0,031               | 0,113 ± 0,010               | ~4,48x         |
| 1000     | 5,126 ± 0,283               | 1,440 ± 0,332               | ~3,56x         |
| 10 000   | 57,441 ± 5,968              | 14,603 ± 1,973              | ~3,93x         |
| 100 000  | 584,322 ± 23,313            | 112,660 ± 5,952             | ~5,19x         |

##### Opération `get`
| Taille   | java.util.ArrayList (µs/op) | agrona.IntArrayList (µs/op) | Gain (rapport) |
|----------|-----------------------------|-----------------------------|----------------|
| 100      | 1,259 ± 0,043               | 1,361 ± 0,030               | ~0,93x         |
| 1000     | 12,540 ± 0,214              | 15,924 ± 2,070              | ~0,79x         |
| 10 000   | 127,313 ± 3,796             | 143,565 ± 8,570             | ~0,89x         |
| 100 000  | 1678,622 ± 147,345          | 1812,784 ± 69,776           | ~0,93x         |

##### Observations
- **add** : `IntArrayList` est **3,56 à 5,19x plus rapide**, avec un gain maximal à 100 000 éléments.
- **get** : `ArrayList` est légèrement plus rapide (~0,79 à 0,93x), mais les différences sont faibles et dans les marges d'erreur.

#### HashMap vs Agrona Int2IntHashMap
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.HashMap<Integer, Integer>` et `org.agrona.collections.Int2IntHashMap` pour les opérations `put` et `get`.

##### Opération `put`
| Taille   | java.util.HashMap (µs/op) | agrona.Int2IntHashMap (µs/op) | Gain (rapport) |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 1,888 ± 0,145             | 1,526 ± 0,103                 | ~1,24x         |
| 1000     | 21,514 ± 2,826            | 16,841 ± 1,456                | ~1,28x         |
| 10 000   | 192,646 ± 19,421          | 135,527 ± 12,900              | ~1,42x         |
| 100 000  | 3041,915 ± 1165,324       | 4571,294 ± 516,363            | ~0,67x         |

##### Opération `get`
| Taille   | java.util.HashMap (µs/op) | agrona.Int2IntHashMap (µs/op) | Gain (rapport) |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 1,581 ± 0,115             | 2,077 ± 0,070                 | ~0,76x         |
| 1000     | 20,204 ± 1,313            | 25,864 ± 2,908                | ~0,78x         |
| 10 000   | 306,371 ± 36,816          | 611,495 ± 869,572             | ~0,50x         |
| 100 000  | 7610,090 ± 2464,821       | 4157,502 ± 644,385            | ~1,83x         |

##### Observations
- **put** : `Int2IntHashMap` est **1,24 à 1,42x plus rapide** pour les tailles 100 à 10 000, mais **~1,5x plus lent** à 100 000 éléments, avec une variabilité élevée.
- **get** : `HashMap` est plus rapide (~1,3-2x) pour 100 à 10 000 éléments, mais `Int2IntHashMap` est **~1,83x plus rapide** à 100 000 éléments.

### eclipse-collections
#### HashSet vs Eclipse IntHashSet
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.HashSet<Integer>` et `org.eclipse.collections.impl.set.mutable.primitive.IntHashSet` pour les opérations `add` et `contains`

##### Opération `add`
| Taille   | java.util.HashSet (µs/op) | Eclipse IntHashSet (µs/op) | Gain (rapport) |
|----------|---------------------------|----------------------------|----------------|
| 100      | 1,823 ± 0,190             | 0,494 ± 0,160              | ~3,69x         |
| 1000     | 23,480 ± 6,174            | 8,385 ± 2,312              | ~2,80x         |
| 10 000   | 231,990 ± 91,308          | 95,981 ± 4,390             | ~2,42x         |
| 100 000  | 4253,353 ± 5232,318       | 1605,101 ± 71,214          | ~2,65x         |

##### Opération `contains`
| Taille   | java.util.HashSet (µs/op) | Eclipse IntHashSet (µs/op) | Gain (rapport) |
|----------|---------------------------|----------------------------|----------------|
| 100      | 0,494 ± 0,057             | 0,346 ± 0,167              | ~1,43x         |
| 1000     | 5,620 ± 0,420             | 8,005 ± 2,673              | ~0,70x         |
| 10 000   | 66,996 ± 21,513           | 65,854 ± 5,298             | ~1,02x         |
| 100 000  | 1316,742 ± 230,694        | 1195,077 ± 44,682          | ~1,10x         |

##### Observations
- **add** : `IntHashSet` est **2,42 à 3,69x plus rapide**, avec un gain maximal à 100 éléments. Les performances restent solides à grande échelle malgré une variabilité élevée pour `HashSet` à 100 000.
- **contains** : Résultats mitigés ; `IntHashSet` est plus rapide (~1,43x) à 100 éléments, mais plus lent (~0,70x) à 1000. Les performances sont quasi identiques à 10 000 et 100 000 (~1,02-1,10x).

#### ArrayList vs Eclipse IntArrayList
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.ArrayList<Integer>` et `org.eclipse.collections.impl.list.mutable.primitive.IntArrayList` pour les opérations `add` et `get`.

##### Opération `add`
| Taille   | java.util.ArrayList (µs/op) | Eclipse IntArrayList (µs/op) | Gain (rapport) |
|----------|-----------------------------|------------------------------|----------------|
| 100      | 0,501 ± 0,030               | 0,121 ± 0,012                | ~4,14x         |
| 1000     | 5,288 ± 0,448               | 1,325 ± 0,102                | ~3,99x         |
| 10 000   | 57,737 ± 4,016              | 14,139 ± 0,655               | ~4,08x         |
| 100 000  | 587,736 ± 65,289            | 181,133 ± 155,147            | ~3,24x         |

##### Opération `get`
| Taille   | java.util.ArrayList (µs/op) | Eclipse IntArrayList (µs/op) | Gain (rapport) |
|----------|-----------------------------|------------------------------|----------------|
| 100      | 1,326 ± 0,057               | 1,356 ± 0,116                | ~0,98x         |
| 1000     | 12,806 ± 0,305              | 13,208 ± 0,600               | ~0,97x         |
| 10 000   | 136,481 ± 14,400            | 139,395 ± 7,133              | ~0,98x         |
| 100 000  | 1692,195 ± 40,706           | 2055,078 ± 1042,478          | ~0,82x         |

##### Observations
- **add** : `IntArrayList` est **3,24 à 4,14x plus rapide**, avec un gain constant pour les petites et moyennes tailles, mais légèrement réduit à 100 000 éléments en raison d'une variabilité élevée.
- **get** : Les performances sont **quasi identiques** (~0,97-0,98x) pour 100 à 10 000 éléments, avec `ArrayList` légèrement plus rapide (~1,22x) à 100 000.

#### HashMap vs MutableIntIntMap
Les tableaux ci-dessous présentent les résultats du benchmark comparant `java.util.HashMap<Integer, Integer>` et `org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap` pour les opérations `put` et `get`.

##### Opération `put`
| Taille   | java.util.HashMap (µs/op) | Eclipse IntIntHashMap (µs/op) | Gain (rapport) |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 2,077 ± 0,319             | 0,626 ± 0,038                 | ~3,32x         |
| 1000     | 21,543 ± 1,554            | 6,531 ± 0,574                 | ~3,30x         |
| 10 000   | 185,059 ± 13,089          | 65,336 ± 6,458                | ~2,83x         |
| 100 000  | 2657,581 ± 198,832        | 649,039 ± 100,905             | ~4,09x         |

##### Opération `get`
| Taille   | java.util.HashMap (µs/op) | Eclipse IntIntHashMap (µs/op) | Gain (rapport) |
|----------|---------------------------|-------------------------------|----------------|
| 100      | 1,768 ± 0,360             | 1,505 ± 0,140                 | ~1,17x         |
| 1000     | 20,620 ± 1,656            | 14,014 ± 0,273                | ~1,47x         |
| 10 000   | 312,758 ± 58,560          | 148,900 ± 3,901               | ~2,10x         |
| 100 000  | 7274,169 ± 1086,437       | 2318,541 ± 919,592            | ~3,14x         |

###### Observations
- **put** : `IntIntHashMap` est **2,83 à 4,09x plus rapide**, avec un gain croissant à grande échelle (~4,09x à 100 000).
- **get** : `IntIntHashMap` est **1,17 à 3,14x plus rapide**, avec un avantage marqué à 100 000 éléments (~3,14x).