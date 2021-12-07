package fr.antoninruan.advendofcode;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day1 {

    public static void main(String... args) {

        String[] split = Util.getInput("day1.txt");
        Integer[] data = Arrays.stream(split).map(Integer::parseInt).collect(Collectors.toList()).toArray(Integer[]::new);

        int augmentation = 0;
        for (int i = 0; i < data.length - 1; i++ ) {
            if (data[i] < data[i+1]) {
                augmentation ++;
            }
        }

        System.out.println("Problem 1: " + augmentation);

        augmentation = 0;
        int previous = Integer.MAX_VALUE;
        for (int i = 0; i < data.length - 2; i ++) {
            int sum = data[i] + data[i+1] + data[i+2];
            if (sum > previous) {
                augmentation ++;
            }
            previous = sum;
        }

        System.out.println("Problem 2: " + augmentation);
    }

}
