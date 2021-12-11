package fr.antoninruan.advendofcode;

import fr.antoninruan.advendofcode.util.Util;

import java.util.Arrays;

public class Day1 {

    public static void main(String... args) {

        String[] split = Util.getInput("day1.txt");
        int[] data = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();

        int augmentation = problem1(data);

        System.out.println("Problem 1: " + augmentation);

        augmentation = problem2(data);

        System.out.println("Problem 2: " + augmentation);
    }

    private static int problem1(int[] data) {
        int augmentation = 0;
        for (int i = 0; i < data.length - 1; i++ ) {
            if (data[i] < data[i+1]) {
                augmentation ++;
            }
        }
        return augmentation;
    }

    private static int problem2(int[] data) {
        int augmentation = 0;
        int previous = Integer.MAX_VALUE;
        for (int i = 0; i < data.length - 2; i ++) {
            int sum = data[i] + data[i+1] + data[i+2];
            if (sum > previous) {
                augmentation ++;
            }
            previous = sum;
        }
        return augmentation;
    }

}
