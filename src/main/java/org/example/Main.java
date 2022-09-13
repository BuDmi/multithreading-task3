package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int randomListSize = 10000000;
        int min = 1000000;
        int max = 100000000;
        int[] randomArray = getRandomList(randomListSize, min, max);
        MyThreadRecursiveTask myThreadRecursiveTask = new MyThreadRecursiveTask(randomArray);

        long startOneThread = System.currentTimeMillis();
        int sumOneThread = getSumOneThread(randomArray);
        long finishOneThread = System.currentTimeMillis();
        System.out.println(sumOneThread + " for " + (finishOneThread - startOneThread) + " ms");

        long startManyThreads = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int sumManyThread = forkJoinPool.invoke(myThreadRecursiveTask);
        long finishManyThreads = System.currentTimeMillis();
        System.out.println(sumManyThread + " for " + (finishManyThreads - startManyThreads) + " ms");
    }

    private static int getSumOneThread(int[] randomList) {
        return Arrays.stream(randomList).sum();
    }

    private static int[] getRandomList(int randomListSize, int min, int max) {
        int[] randomList = new int[randomListSize];
        Random random = new Random();
        for (int i = 0; i < randomListSize; i++) {
            randomList[i] = random.nextInt(max - min) + min + 1;
        }
        return randomList;
    }
}