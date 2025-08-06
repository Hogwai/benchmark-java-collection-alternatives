# benchmark-java-collection-alternatives
## Description
Ce dépôt contient des benchmarks JMH (Java Microbenchmark Harness) comparant les performances des collections standards de java.util avec leurs équivalents optimisés (FastUtil, agrona).

## Prérequis
- Java 21
- Maven 3.6.3 ou plus (ou utiliser le wrapper)

## Installation
- Cloner le dépôt:
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