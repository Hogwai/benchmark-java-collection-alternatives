package com.hogwai.eclipsecollections;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.impl.factory.primitive.IntIntMaps;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
public class MutableIntIntMapVsHashMap {

    @Param({"100", "1000", "10000", "100000"})
    public int size;

    private HashMap<Integer, Integer> javaHashMap;
    private MutableIntIntMap eclipseIntIntHashMap;
    private int[] data;
    private Random random;

    @Setup
    public void setup() {
        javaHashMap = new HashMap<>(size);
        eclipseIntIntHashMap = IntIntMaps.mutable.withInitialCapacity(size);
        data = new int[size];
        random = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = i;
            javaHashMap.put(i, i);
            eclipseIntIntHashMap.put(i, i);
        }
    }

    @Benchmark
    public void javaHashMapPut(Blackhole blackhole) {
        HashMap<Integer, Integer> map = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            map.put(data[i], data[i]);
        }
        blackhole.consume(map);
    }

    @Benchmark
    public void eclipseIntIntHashMapPut(Blackhole blackhole) {
        MutableIntIntMap map = IntIntMaps.mutable.withInitialCapacity(size);
        for (int i = 0; i < size; i++) {
            map.put(data[i], data[i]);
        }
        blackhole.consume(map);
    }

    @Benchmark
    public void javaHashMapGet(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(javaHashMap.get(random.nextInt(size)));
        }
    }

    @Benchmark
    public void eclipseIntIntHashMapGet(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(eclipseIntIntHashMap.get(random.nextInt(size)));
        }
    }
}
