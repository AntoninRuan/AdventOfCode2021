package fr.antoninruan.advendofcode.day07;

import fr.antoninruan.advendofcode.util.Util;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class Day7 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day7.txt");

        int[] crabPos = Arrays.stream(data[0].split(",")).mapToInt(Integer::parseInt).toArray();

        System.out.println("Problem 1: " + problem1(crabPos));

        System.out.println("Problem 2: " + problem2(crabPos));

    }

    private static int calculateCost(int[] crabs, IntUnaryOperator costFunction) {
        return Arrays.stream(crabs).map(costFunction).sum();
    }

    private static int problem1(int[] data) {
        data = Arrays.stream(data).sorted().toArray();
        int finalPos = data[data.length / 2];

        return calculateCost(data, i -> Math.abs(i - finalPos));
    }

    private static int problem2(int[] data) {
        data = Arrays.stream(data).sorted().toArray();
        int min = data[0];
        int max = data[data.length - 1];

        int cost = Integer.MAX_VALUE;
        for (int i = 0; i <= max - min; i ++) {
            int finalI = i;
            int potentialCost = calculateCost(data, j -> {
                int n = Math.abs(j - finalI);
                return (n * (n+1)) / 2;
            });
            if (potentialCost < cost) {
                cost = potentialCost;
            }
        }
        return cost;
    }

}
