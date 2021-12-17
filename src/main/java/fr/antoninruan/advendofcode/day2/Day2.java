package fr.antoninruan.advendofcode.day2;

import fr.antoninruan.advendofcode.util.Util;

public class Day2 {

    public static void main(String... args) {

        String[] data = Util.getInput("day2.txt");


        System.out.println("Problem 1: " + problem1(data));

        System.out.println("Problem 2: " + problem2(data));

    }

    private static int problem1(String[] data) {
        int depth = 0 , horizontal = 0;
        for (String instruction : data) {
            String[] temp = instruction.split(" ");
            String dir = temp[0];
            int value = Integer.parseInt(temp[1]);
            switch (dir) {
                case "forward" -> horizontal += value;
                case "up" -> depth -= value;
                case "down" -> depth += value;
            }
        }
        return depth * horizontal;
    }

    private static int problem2(String[] data) {
        int depth = 0;
        int horizontal = 0;
        int aim = 0;
        for(String instruction : data) {
            String temp[] = instruction.split(" ");
            String dir = temp[0];
            int value = Integer.parseInt(temp[1]);
            switch (dir) {
                case "forward" ->  {
                    horizontal += value;
                    depth += aim * value;
                }
                case "up" -> aim -= value;
                case "down" -> aim += value;
            }
        }
        return depth * horizontal;
    }

}
