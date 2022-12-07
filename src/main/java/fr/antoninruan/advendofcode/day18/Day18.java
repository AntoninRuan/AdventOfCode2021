package fr.antoninruan.advendofcode.day18;

import fr.antoninruan.advendofcode.util.Util;

public class Day18 {
    
    public static void main(String[] args) {
        
        String[] lines = Util.getInput("day18.txt");

        part1(lines);
        part2(lines);
    }

    private static void part1(String[] lines) {
        SnailfishNumber current = new SnailfishNumber(lines[0]);
        for (int i = 1; i < lines.length; i ++) {
            SnailfishNumber toAdd = new SnailfishNumber(lines[i]);
            current.add(toAdd);
        }

        System.out.println("Part1: " + current.getMagnitude());
    }

    private static void part2(String[] lines) {
        int maxMagnitude = Integer.MIN_VALUE;
        for (String l1 : lines) {
            for (String l2 : lines) {
                SnailfishNumber x = new SnailfishNumber(l1);
                SnailfishNumber y = new SnailfishNumber(l2);

                x.add(y);
                int possible = x.getMagnitude();
                if (possible > maxMagnitude) 
                    maxMagnitude = possible;
                
                y.add(new SnailfishNumber(l1));
                possible = y.getMagnitude();
                if (possible > maxMagnitude)
                    maxMagnitude = possible;
            }
        }

        System.out.println("Part2: " + maxMagnitude);
    }

}
