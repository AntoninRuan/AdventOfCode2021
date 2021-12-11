package fr.antoninruan.advendofcode;

import fr.antoninruan.advendofcode.util.Point2D;
import fr.antoninruan.advendofcode.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Day11 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day11.txt");

        int[][] octopi = new int[data.length][data[0].length()];

        for (int i = 0; i < data.length; i ++) {
            char[] line = data[i].toCharArray();
            for (int j = 0; j < line.length; j ++) {
                octopi[i][j] = Integer.parseInt(String.valueOf(line[j]));
            }
        }

        System.out.println("Problem 1: " + problem1(octopi));

        System.out.println("Problem 2: " + problem2(octopi));

    }

    private static boolean isValid(int[][] current, int x, int y) {
        return 0 <= x &&  x < current.length && 0 <= y && y < current[0].length;
    }

    private static int step(int[][] current) {
        int flashes = 0;
        for(int j = 0; j < current.length; j ++) {
            for(int k = 0; k < current[j].length; k ++) {
                current[j][k] ++;
            }
        }
        flashes += flashOctopi(current);
        for(int j = 0; j < current.length; j ++) {
            for(int k = 0; k < current[j].length; k ++) {
                if (current[j][k] > 9) {
                    current[j][k] = 0;
                }
            }
        }
        return flashes;
    }

    private static int flashOctopi(int[][] current) {
        Stack<Point2D> toTreat = new Stack<>();
        List<Point2D> flashed = new ArrayList<>();

        for(int i = 0; i < current.length; i ++) {
            for (int j = 0; j < current[i].length; j ++) {
                if (current[i][j] > 9) {
                    Point2D p = new Point2D(i, j);
                    toTreat.add(p);
                    flashed.add(p);
                }
            }
        }

        while (!toTreat.isEmpty()) {
            Point2D p = toTreat.pop();
            for(int i = -1; i <= 1; i ++) {
                for (int j = -1; j <= 1; j ++) {
                    Point2D voisin = new Point2D(p.getX()+i, p.getY() + j);
                    if ((i != 0 || j != 0) && isValid(current, voisin.getX(), voisin.getY())) {
                        current[voisin.getX()][voisin.getY()] ++;
                        if (current[voisin.getX()][voisin.getY()] > 9 && !flashed.contains(voisin)) {
                            toTreat.add(voisin);
                            flashed.add(voisin);
                        }
                    }
                }
            }
        }

        return flashed.size();
    }

    private static int problem1(int[][] octopi) {
        int flashes = 0;

        int s = 100;

        for (int i = 0; i < s; i ++) {
            flashes += step(octopi);
        }

        return flashes;
    }

    private static boolean isSync(int[][] octopi) {
        return Arrays.stream(octopi)
                .flatMapToInt(Arrays::stream)
                .sum() == 0;
    }

    private static int problem2(int[][] octopi) {
        int step = 100;

        while (! isSync(octopi)) {
            step(octopi);
            step ++;
        }

        return step;
    }

}
