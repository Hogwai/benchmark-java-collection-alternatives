package com.hogwai.fastutil;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
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
public class Int2IntOpenHashMapVsHashMap {

    @Param({"100", "1000", "10000", "100000"})
    public int mapSize;

    private HashMap<Integer, Integer> javaHashMap;
    private Int2IntOpenHashMap fastUtilMap;
    private int[] keys;
    private Random random;

    @Setup
    public void setup() {
        javaHashMap = new HashMap<>(mapSize);
        fastUtilMap = new Int2IntOpenHashMap(mapSize);
        keys = new int[mapSize];
        random = new Random(42);
        for (int i = 0; i < mapSize; i++) {
            keys[i] = i;
            javaHashMap.put(i, i);
            fastUtilMap.put(i, i);
        }
    }
    @Benchmark
    public void javaHashMapPut(Blackhole blackhole) {
        HashMap<Integer, Integer> map = new HashMap<>(mapSize);
        for (int i = 0; i < mapSize; i++) {
            map.put(keys[i], keys[i]);
        }
        blackhole.consume(map);
    }

    @Benchmark
    public void fastUtilMapPut(Blackhole blackhole) {
        Int2IntOpenHashMap map = new Int2IntOpenHashMap(mapSize);
        for (int i = 0; i < mapSize; i++) {
            map.put(keys[i], keys[i]);
        }
        blackhole.consume(map);
    }

    @Benchmark
    public void javaHashMapGet(Blackhole blackhole) {
        for (int i = 0; i < mapSize; i++) {
            blackhole.consume(javaHashMap.get(random.nextInt(mapSize)));
        }
    }

    @Benchmark
    public void fastUtilMapGet(Blackhole blackhole) {
        for (int i = 0; i < mapSize; i++) {
            blackhole.consume(fastUtilMap.get(random.nextInt(mapSize)));
        }
    }
}