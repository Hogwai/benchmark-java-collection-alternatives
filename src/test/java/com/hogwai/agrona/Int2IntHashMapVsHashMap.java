package com.hogwai.agrona;

import org.agrona.collections.Int2IntHashMap;
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
public class Int2IntHashMapVsHashMap {

    @Param({"100", "1000", "10000", "100000"})
    public int size;

    private HashMap<Integer, Integer> javaHashMap;
    private Int2IntHashMap agronaInt2IntHashMap;
    private int[] data;
    private Random random;

    @Setup
    public void setup() {
        javaHashMap = new HashMap<>(size);
        agronaInt2IntHashMap = new Int2IntHashMap(size, 0.75f, -1);
        data = new int[size];
        random = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = i;
            javaHashMap.put(i, i);
            agronaInt2IntHashMap.put(i, i);
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
    public void agronaInt2IntHashMapPut(Blackhole blackhole) {
        Int2IntHashMap map = new Int2IntHashMap(size, 0.75f, -1);
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
    public void agronaInt2IntHashMapGet(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(agronaInt2IntHashMap.get(random.nextInt(size)));
        }
    }
}
