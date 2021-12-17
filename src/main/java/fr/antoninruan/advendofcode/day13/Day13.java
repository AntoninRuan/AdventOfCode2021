package fr.antoninruan.advendofcode.day13;

import fr.antoninruan.advendofcode.util.Point2D;
import fr.antoninruan.advendofcode.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day13.txt");

        List<Point2D> points = new ArrayList<>();
        Iterator<String> iterator = Arrays.stream(data).iterator();
        String line = iterator.next();
        do {
            if (line.equals(""))
                break;

            String[] coord = line.split(",");

            points.add(new Point2D(Integer.parseInt(coord[1]), Integer.parseInt(coord[0])));

            line = iterator.next();
        } while (iterator.hasNext() && !line.equals(""));

        int maxX = points.stream().mapToInt(Point2D::getX).max().orElse(0);
        int maxY = points.stream().mapToInt(Point2D::getY).max().orElse(0);

		int[][] grid = new int[maxX+1][maxY+1];
	
		for (Point2D p : points) {
	
			grid[p.getX()][p.getY()] = 1;
		
		}
		
		List<String> instructions = new ArrayList<>();
		
		while(iterator.hasNext()) {
			line = iterator.next();
			instructions.add(line);		
		}
		
		System.out.println("Problem 1: " + problem1(grid, instructions));

        problem2(grid, instructions);

    }

    private static long problem1(int[][] grid, List<String> instructions) {
        int[][] current = new int[grid.length][];

        for (int i = 0; i < grid.length; i ++)
            current[i] = Arrays.copyOf(grid[i], grid[i].length);

        String[] interest = instructions.get(0).split(" ")[2].split("=");
        String axis = interest[0];
        int xy = Integer.parseInt(interest[1]);
        fold(current, axis, xy);

        return Arrays.stream(current).mapToLong(t -> Arrays.stream(t).filter(i -> i > 0).count()).sum();
    }

    private static void fold(int[][] current, String axis, int xy) {
        for (int i = 0; i < current.length; i ++) {
            for (int j = 0; j < current[i].length; j ++) {
                if (current[i][j] > 0) {
                    if (axis.equals("y")) {
                        if (i > xy) {
                            current[i][j] = 0;
                            current[2 * xy - i][j] = 1;
                        }
                    } else if(axis.equals("x")) {
                        if (j > xy) {
                            current[i][j] = 0;
                            current[i][2 * xy  - j] = 1;
                        }
                    }
                }
            }
        }
    }

    private static void problem2(int[][] grid, List<String> instructions) {
    	int[][] current = new int[grid.length][];

        for (int i = 0; i < grid.length; i ++)
    		current[i] = Arrays.copyOf(grid[i], grid[i].length);

        int maxX = current.length;
        int maxY = current[0].length;

    	for (String instr : instructions) {
    	    String[] interest = instr.split(" ")[2].split("=");
            String axis = interest[0];
            int xy = Integer.parseInt(interest[1]);
            if (axis.equals("x"))
                maxX = xy;
            else if (axis.equals("y"))
                maxY = xy;
            fold(current, axis, xy);
        }

        int[][] display = new int[maxY + 1][];
        for (int i = 0; i <= maxY; i ++)
            display[i] = Arrays.copyOf(current[i], maxX + 1);

        List<String> lines = Arrays.stream(display).map(t -> {
           StringBuilder builder = new StringBuilder();
           for (int i : t) {
               if (i > 0)
                   builder.append('█');
               else
                   builder.append(' ');
           }
           return builder.toString();
        }).collect(Collectors.toList());

        for (String l : lines) {
            System.out.print(l);
            System.out.println("");
        }

    }

}
