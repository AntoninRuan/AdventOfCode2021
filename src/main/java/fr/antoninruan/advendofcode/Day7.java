package fr.antoninruan.advendofcode;

import java.util.Arrays;

public class Day7 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day7.txt");

        int[] crabPos = Arrays.stream(data[0].split(",")).mapToInt(Integer::parseInt).sorted().toArray();

        int finalPos = crabPos[crabPos.length / 2];

        System.out.println(calculateCost(crabPos, finalPos));

    }

    private static int calculateCost(int[] crabs, int dest) {
        return Arrays.stream(crabs).map(i -> Math.abs(i - dest)).sum();
    }

}
