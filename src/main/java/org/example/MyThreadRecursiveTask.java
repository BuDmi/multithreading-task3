package org.example;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MyThreadRecursiveTask extends RecursiveTask<Integer> {

    private int[] array;
    private final int partsNum = 2;

    public MyThreadRecursiveTask(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if(array.length <= partsNum) {
            return Arrays.stream(array).sum();
        }
        MyThreadRecursiveTask firstHalfArrayValueSumCounter =
            new MyThreadRecursiveTask(Arrays.copyOfRange(array, 0, array.length / 2));
        MyThreadRecursiveTask secondHalfArrayValueSumCounter =
            new MyThreadRecursiveTask(Arrays.copyOfRange(array, array.length / 2, array.length));
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();
        return firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join();
    }
}
