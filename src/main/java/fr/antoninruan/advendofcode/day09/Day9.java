package fr.antoninruan.advendofcode.day09;

import fr.antoninruan.advendofcode.util.Point2D;
import fr.antoninruan.advendofcode.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day9.txt");

        int[][] grid = new int[data.length][data[0].length()];

        for (int i = 0; i < data.length; i ++) {
            for (int j = 0; j < data[i].length(); j++) {
                grid[i][j] = data[i].toCharArray()[j] - '0';
            }
        }

        System.out.println("Problem 1: " + problem1(grid));

        System.out.println("Problem 2: "+ problem2(grid));

    }

    private static int problem1(int[][] grid) {
        return getLowPoint(grid).stream().mapToInt(t -> 1 + grid[t.getX()][t.getY()]).sum();
    }

    private static boolean isDanger(int[][] grid, int x, int y) {
        if (x > 0 && (grid[x][y] >= grid[x-1][y]))
            return false;
        if (x < grid.length - 1 && (grid[x][y] >= grid[x+1][y]))
            return false;
        if (y > 0 && (grid[x][y] >= grid[x][y-1]))
            return false;
        return y >= grid[x].length - 1 || (grid[x][y] < grid[x][y + 1]);
    }

    private static List<Point2D> getLowPoint(int[][] grid) {
        List<Point2D> lowPoint = new ArrayList<>();
        for(int i = 0; i < grid.length; i ++) {
            for (int j = 0; j < grid[i].length; j ++) {
                if (isDanger(grid, i, j)) {
                    lowPoint.add(new Point2D(i, j));
                }
            }
        }
        return lowPoint;
    }

    private static int problem2(int[][] grid) {
        List<Point2D> lowPoint = getLowPoint(grid);

        List<Integer> sizes = new ArrayList<>();

        for (Point2D p : lowPoint) {
            int size = 1;
            List<Point2D> bassin = new ArrayList<>();
            bassin.add(p);
            List<Point2D> possible = new ArrayList<>(getFlowIn(grid, p));
            while (possible.size() > 0) {
                size ++;
                Point2D pt = possible.get(0);
                bassin.add(pt);
                possible.remove(0);
                possible.addAll(getFlowIn(grid, pt).stream().filter(t -> !bassin.contains(t) && !possible.contains(t)).collect(Collectors.toList()));
            }
            sizes.add(size);
        }

        sizes = sizes.stream().sorted((o1, o2) -> o2 - o1).collect(Collectors.toList());

        return sizes.get(0) * sizes.get(1) * sizes.get(2);
    }

    private static List<Point2D> getFlowIn(int[][] grid, Point2D p) {
        List<Point2D> possible = new ArrayList<>();
        if (p.getX() > 0 && grid[p.getX()-1][p.getY()] < 9 && grid[p.getX()-1][p.getY()] > grid[p.getX()][p.getY()])
            possible.add(new Point2D(p.getX()-1,p.getY()));
        if (p.getX() < grid.length - 1 && grid[p.getX()+1][p.getY()] < 9 && grid[p.getX()+1][p.getY()] > grid[p.getX()][p.getY()])
            possible.add(new Point2D(p.getX() + 1, p.getY()));
        if (p.getY() > 0 && grid[p.getX()][p.getY()-1] < 9 && grid[p.getX()][p.getY() - 1] > grid[p.getX()][p.getY()])
            possible.add(new Point2D(p.getX(), p.getY() - 1));
        if (p.getY() < grid[p.getX()].length - 1 && grid[p.getX()][p.getY()+1] < 9 && grid[p.getX()][p.getY()+1] > grid[p.getX()][p.getY()])
            possible.add(new Point2D(p.getX(), p.getY() + 1));
        return possible;
    }

}
