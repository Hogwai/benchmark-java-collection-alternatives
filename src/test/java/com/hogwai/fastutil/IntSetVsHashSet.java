package com.hogwai.fastutil;


import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
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
public class IntSetVsHashSet {

    @Param({"100", "1000", "10000", "100000"})
    public int size;

    private HashSet<Integer> javaHashSet;
    private IntSet fastUtilSet;
    private int[] data;

    @Setup
    public void setup() {
        javaHashSet = new HashSet<>(size);
        fastUtilSet = new IntOpenHashSet(size);
        data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = i;
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
    public void fastUtilSetAdd(Blackhole blackhole) {
        IntSet set = new IntOpenHashSet(size);
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
    public void fastUtilSetContains(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(fastUtilSet.contains(data[i]));
        }
    }
}