package com.hogwai.eclipsecollections;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
public class MutableIntSetVsHashSet {

    @Param({"100", "1000", "10000", "100000"})
    public int size;

    private HashSet<Integer> javaHashSet;
    private MutableIntSet eclipseIntHashSet;
    private int[] data;

    @Setup
    public void setup() {
        javaHashSet = new HashSet<>(size);
        eclipseIntHashSet = IntSets.mutable.withInitialCapacity(size);
        data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = i;
            javaHashSet.add(i);
            eclipseIntHashSet.add(i);
        }
    }

    @Benchmark
    public void javaHashSetAdd(Blackhole blackhole) {
        HashSet<Integer> set = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            set.add(data[i]);
        }
        blackhole.consume(set);
    }

    @Benchmark
    public void eclipseIntHashSetAdd(Blackhole blackhole) {
        MutableIntSet set = IntSets.mutable.withInitialCapacity(size);
        for (int i = 0; i < size; i++) {
            set.add(data[i]);
        }
        blackhole.consume(set);
    }

    @Benchmark
    public void javaHashSetContains(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(javaHashSet.contains(data[i]));
        }
    }

    @Benchmark
    public void eclipseIntHashSetContains(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(eclipseIntHashSet.contains(data[i]));
        }
    }
}
