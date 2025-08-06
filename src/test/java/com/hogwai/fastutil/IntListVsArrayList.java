package com.hogwai.fastutil;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
public class IntListVsArrayList {

    @Param({"100", "1000", "10000", "100000"})
    public int listSize;

    private ArrayList<Integer> javaArrayList;
    private IntList fastUtilList;
    private int[] data;
    private Random random;

    @Setup
    public void setup() {
        javaArrayList = new ArrayList<>(listSize);
        fastUtilList = new IntArrayList(listSize);
        data = new int[listSize];
        random = new Random(42);
        for (int i = 0; i < listSize; i++) {
            data[i] = i;
            javaArrayList.add(i);
            fastUtilList.add(i);
        }
    }

    @Benchmark
    public void javaArrayListAdd(Blackhole blackhole) {
        ArrayList<Integer> list = new ArrayList<>(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(data[i]);
        }
        blackhole.consume(list);
    }

    @Benchmark
    public void fastUtilListAdd(Blackhole blackhole) {
        IntList list = new IntArrayList(listSize);
        for (int i = 0; i < listSize; i++) {
            list.add(data[i]);
        }
        blackhole.consume(list);
    }

    @Benchmark
    public void javaArrayListGet(Blackhole blackhole) {
        for (int i = 0; i < listSize; i++) {
            blackhole.consume(javaArrayList.get(random.nextInt(listSize)));
        }
    }

    @Benchmark
    public void fastUtilListGet(Blackhole blackhole) {
        for (int i = 0; i < listSize; i++) {
            blackhole.consume(fastUtilList.getInt(random.nextInt(listSize)));
        }
    }
}