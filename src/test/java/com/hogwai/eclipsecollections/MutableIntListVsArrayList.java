package com.hogwai.eclipsecollections;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.factory.primitive.IntLists;
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
public class MutableIntListVsArrayList {

    @Param({"100", "1000", "10000", "100000"})
    public int size;

    private ArrayList<Integer> javaArrayList;
    private MutableIntList eclipseIntArrayList;
    private int[] data;
    private Random random;

    @Setup
    public void setup() {
        javaArrayList = new ArrayList<>(size);
        eclipseIntArrayList = IntLists.mutable.withInitialCapacity(size);
        data = new int[size];
        random = new Random(42);
        for (int i = 0; i < size; i++) {
            data[i] = i;
            javaArrayList.add(i);
            eclipseIntArrayList.add(i);
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
    public void eclipseIntArrayListAdd(Blackhole blackhole) {
        MutableIntList list = IntLists.mutable.withInitialCapacity(size);
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
    public void eclipseIntArrayListGet(Blackhole blackhole) {
        for (int i = 0; i < size; i++) {
            blackhole.consume(eclipseIntArrayList.get(random.nextInt(size)));
        }
    }
}
