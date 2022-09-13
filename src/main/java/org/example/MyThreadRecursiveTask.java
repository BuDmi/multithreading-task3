package org.example;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MyThreadRecursiveTask extends RecursiveTask<Integer> {

    private int[] array;
    private final int partsNum = 4;

    public MyThreadRecursiveTask(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if(array.length <= partsNum) {
            return Arrays.stream(array).sum();
        }
        MyThreadRecursiveTask firstHalfArrayValueSumCounter =
            new MyThreadRecursiveTask(Arrays.copyOfRange(array, 0, array.length / 4));
        MyThreadRecursiveTask secondHalfArrayValueSumCounter =
            new MyThreadRecursiveTask(Arrays.copyOfRange(array, array.length / 4, 2 * array.length / 4));
        MyThreadRecursiveTask thirdHalfArrayValueSumCounter =
            new MyThreadRecursiveTask(Arrays.copyOfRange(array, 2 * array.length / 4, 3 * array.length / 4));
        MyThreadRecursiveTask forthHalfArrayValueSumCounter =
            new MyThreadRecursiveTask(Arrays.copyOfRange(array, 3 * array.length / 4, array.length));
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();
        thirdHalfArrayValueSumCounter.fork();
        forthHalfArrayValueSumCounter.fork();
        return firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join() +
            thirdHalfArrayValueSumCounter.join() + forthHalfArrayValueSumCounter.join();
    }
}
