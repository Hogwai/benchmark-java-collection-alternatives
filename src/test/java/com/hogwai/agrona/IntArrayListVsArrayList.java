package com.hogwai.agrona;

import org.agrona.collections.IntArrayList;
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
public class IntArrayListVsArrayList {

    @Param({"100", "1000", "10000", "100000"})
    public int size;

    private ArrayList<Integer> javaArrayList;
    private IntArrayList agronaIntArrayList;
    private int[] data;
    private Random random;

    @Setup
    public void setup() {
        javaArrayList = new ArrayList<>(size);
        agronaIntArrayList = new IntArrayList(size, 0);
        data = new int[size];
        random = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = i;
            javaArrayList.add(i);
            agronaIntArrayList.add(i);
        }
    }

    @Benchmark
    public void javaArrayListAdd(Blackhole blackhole) {
        ArrayList<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(data[i]);
        }
        blackhole.consume(list);
    }

    @Benchmark
    public void agronaIntArrayListAdd(Blackhole blackhole) {
        IntArrayList list = new IntArrayList(size, 0);
        for (int i = 0; i < size; i++) {
            list.add(data[i]);
        }
        blackhole.consume(list);
    }

    @Benchmark
    public void javaArrayListGet(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(javaArrayList.get(random.nextInt(size)));
        }
    }

    @Benchmark
    public void agronaIntArrayListGet(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(agronaIntArrayList.get(random.nextInt(size)));
        }
    }
}
