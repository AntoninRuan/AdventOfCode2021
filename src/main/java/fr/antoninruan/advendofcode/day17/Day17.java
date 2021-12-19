package fr.antoninruan.advendofcode.day17;

import fr.antoninruan.advendofcode.util.BoundingBox;
import fr.antoninruan.advendofcode.util.Point2D;
import fr.antoninruan.advendofcode.util.Util;

import java.util.Arrays;

public class Day17 {

    public static void main(String[] args) {
        String data = Util.getInput("day17.txt")[0];

        String[] bBox = data.replace("target area: ", "")
                .replace("x=", "")
                .replace("y=", "")
                .split(", ");

        int[] xDel = Arrays.stream(bBox[0].split("\\.\\.")).mapToInt(Integer::parseInt).toArray();
        int[] yDel = Arrays.stream(bBox[1].split("\\.\\.")).mapToInt(Integer::parseInt).toArray();

        BoundingBox boundingBox = new BoundingBox(new Point2D(xDel[0], yDel[1]), new Point2D(xDel[1], yDel[0]));

        System.out.println("Problem 1: " + problem1(boundingBox));

        System.out.println("Problem 2: " + problem2(boundingBox));

    }

    private static int problem1(BoundingBox bounding) {

        int maxY = bounding.getMinY();

        for (int x = bounding.getMaxX(); x > 0; x --) {
            for (int y = bounding.getMinY(); y < 5000; y ++) { /* Absolument honteux le 5000 sort de nulle part mais fallait bien un truc qui a une chance
             de majorer la valeur cherché */
                if (y > maxY && isValidTrajectory(new Point2D(x, y), bounding))
                    maxY = y;
            }
        }

        return (maxY*(maxY+1)) / 2;
    }

    private static int problem2(BoundingBox bounding) {
        int result = 0;

        for (int x = bounding.getMaxX(); x > 0; x --) {
            for (int y = bounding.getMinY(); y < 5000; y ++) { /* Absolument honteux le 5000 sort de nulle part mais fallait bien un truc qui a une chance
             de majorer la valeur cherché */
                if ( isValidTrajectory(new Point2D(x, y), bounding))
                    result ++;
            }
        }
        return result;
    }

    private static boolean isValidTrajectory(Point2D initialVelocity, BoundingBox bounding) {
        int vX = initialVelocity.getX(),  vY = initialVelocity.getY();
        int x = 0, y = 0;

        while (x <= bounding.getMaxX() && y >= bounding.getMinY()) {
            x += vX;
            y += vY;
            vX -= Math.signum(vX);
            vY -= 1;
            if (bounding.isIn(new Point2D(x, y)))
                return true;
        }
        return false;
    }

}
