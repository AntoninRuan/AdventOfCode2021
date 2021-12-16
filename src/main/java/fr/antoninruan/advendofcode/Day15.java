package fr.antoninruan.advendofcode;

import fr.antoninruan.advendofcode.util.Point2D;
import fr.antoninruan.advendofcode.util.Util;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.function.ToIntFunction;

public class Day15 {

    public static void main(String[] args) {
        String[] data = Util.getInput("day15.txt");

        int[][] grid = new int[data.length][data[0].length()];

        for(int i = 0; i < data.length; i ++) {
            char[] chars = data[i].toCharArray();
            for(int j = 0; j < chars.length; j ++) {
                grid[i][j] = Integer.parseInt(String.valueOf(chars[j]));
            }
        }

        System.out.println("Problem 1: " + problem1(grid));

        System.out.println("Problem 2: " + problem2(grid));

    }

    private static int problem1(int[][] grid) {
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(grid.length-1, grid[0].length-1);
        ToIntFunction<Point2D> eval = p -> grid[p.getX()][p.getY()];
        List<Point2D> path = path(start, end, grid.length -1, grid[0].length - 1,eval);
        return path.stream().mapToInt(eval).sum();
    }

    private static int problem2(int[][] grid) {
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(grid.length * 5 - 1, grid[0].length * 5 - 1);
        ToIntFunction<Point2D> eval = p -> {
           int x = p.getX();
           int y = p.getY();
           int offsetX = Math.floorDiv(x, grid.length);
           int offsetY = Math.floorDiv(y, grid[0].length);
           int rX = x % grid.length;
           int rY = y % grid[0].length;
           int add = offsetX + offsetY;

           int value = grid[rX][rY];
           return ((value + add - 1) % 9) + 1;
        };
        List<Point2D> path = path(start, end, end.getX(), end.getY(), eval);
        return path.stream().mapToInt(eval).sum();
    }

    private static List<Point2D> path(Point2D from, Point2D to, int maxX, int maxY, ToIntFunction<Point2D> mapper) {

        Map<Point2D, Pair<Integer, Point2D>> marque = new HashMap<>();

        PriorityQueue<Triplet<Integer, Point2D, Point2D>> aVoir = new PriorityQueue<>(Comparator.comparingInt(Triplet::getValue0));
        aVoir.add(new Triplet<>(0, from, from));
        boolean trouve = false;
        while (!aVoir.isEmpty() && !trouve) {
            Triplet<Integer, Point2D, Point2D> current = aVoir.poll();
            trouve = current.getValue1().equals(to);
            if(trouve)
                marque.put(current.getValue1(), new Pair<>(current.getValue0(), current.getValue2()));

            if(!marque.containsKey(current.getValue1())) {

                marque.put(current.getValue1(), new Pair<>(current.getValue0(), current.getValue2()));

                for(Point2D v : voisins(maxX, maxY, current.getValue1())) {
                    Triplet<Integer, Point2D, Point2D> next = new Triplet<>(current.getValue0() + mapper.applyAsInt(v),
                            v, current.getValue1());
                    aVoir.add(next);
                }

            }
        }
        List<Point2D> path = new ArrayList<>();
        Point2D here = to;
        while (!here.equals(from)) {
            path.add(here);
            here = marque.get(here).getValue1();
        }
        return path;
    }

    private static List<Point2D> voisins(int maxX, int maxY, Point2D p) {
        List<Point2D> result = new ArrayList<>();
        if (p.getX() > 0)
            result.add(new Point2D(p.getX() - 1, p.getY()));
        if (p.getX() < maxX)
            result.add(new Point2D(p.getX() + 1, p.getY()));
        if (p.getY() > 0)
            result.add(new Point2D(p.getX(), p.getY() - 1));
        if (p.getY() < maxY)
            result.add(new Point2D(p.getX(), p.getY() + 1));

        return result;
    }

}
