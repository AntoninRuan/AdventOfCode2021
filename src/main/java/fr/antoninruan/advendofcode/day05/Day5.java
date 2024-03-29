package fr.antoninruan.advendofcode.day05;

import fr.antoninruan.advendofcode.util.Point2D;
import fr.antoninruan.advendofcode.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {

    public static void main(String[] args) {
        String[] data = Util.getInput("day5.txt");

        List<Line> lines = new ArrayList<>();
        for (String s : data) {
            String[] split = s.split(" -> ");
            lines.add(new Line(new Point2D(split[0]), new Point2D(split[1])));
        }

        int max_x = lines.stream().mapToInt(l -> Math.max(l.start.getX(), l.end.getX())).max().getAsInt() + 1;
        int max_y = lines.stream().mapToInt(l -> Math.max(l.start.getY(), l.end.getY())).max().getAsInt() + 1;

        int[][] grid = new int[max_x][max_y];

        for (Line l : lines) {
            for (Point2D p : l.getLine()) {
                grid[p.getX()][p.getY()] += 1;
            }
        }

        long dangerPoint = Arrays.stream(grid).mapToLong(l -> Arrays.stream(l).filter(i -> i >= 2).count()).sum();
        System.out.println("Number of danger points: " + dangerPoint);

    }

    private static class Line {

        private List<Point2D> line;
        private Point2D start, end;

        public Line(Point2D start, Point2D end) {
            this.start = start;
            this.end = end;
            this.line = new ArrayList<>();
            /* La création des lignes est faite en supposant que les lignes sont
               soit horizontales, soit verticales, soit diagonales */
            if (start.getX() == end.getX()) {
                int min = Math.min(start.getY(), end.getY());
                int max = Math.max(start.getY(), end.getY());
                for (int i = 0; i <= max - min; i ++) {
                    this.line.add(new Point2D(start.getX(), min + i));
                }
            } else if (start.getY() == end.getY()){
                int min = Math.min(start.getX(), end.getX());
                int max = Math.max(start.getX(), end.getX());
                for (int i = 0; i <= max - min; i ++) {
                    this.line.add(new Point2D(min + i, end.getY()));
                }
            } else { // Enlever cette dernière partie pour la première partie du problème
                int xSign = (int) Math.signum(end.getX() - start.getX());
                int ySign = (int) Math.signum(end.getY() - start.getY());
                for (int i = 0; i <= Math.abs(end.getY() - start.getY()); i ++) {
                    this.line.add(new Point2D(start.getX() + xSign * i, start.getY() + ySign * i));
                }
            }
        }

        public List<Point2D> getLine() {
            return line;
        }
    }

}
