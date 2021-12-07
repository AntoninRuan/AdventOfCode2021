package fr.antoninruan.advendofcode;

public class Day2 {

    public static void main(String... args) {

        String[] data = Util.getInput("day2.txt");

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
        System.out.println("Problem 1: " + (depth * horizontal));

        depth = 0;
        horizontal = 0;
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

        System.out.println("Problem 2: " + (depth * horizontal));

    }

}
