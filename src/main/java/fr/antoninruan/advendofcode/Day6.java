package fr.antoninruan.advendofcode;

import fr.antoninruan.advendofcode.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day6.txt");

        List<Short> lanternfishes = Arrays.stream(data[0].split(",")).map(Short::parseShort).collect(Collectors.toList());

        int day = 256; // A mettre à 80 pour le premier problème

        long[] dayLeft = new long[9];
        for (int dl :  lanternfishes) {
            dayLeft[dl] += 1;
        }

        for (int i = 0; i < day; i ++) {

            long[] updated = new long[9];
            updated[8] = dayLeft[0];
            updated[6] = dayLeft[0];
            for (int j = 1; j < dayLeft.length; j ++) {
                updated[j-1] += dayLeft[j];
            }

            dayLeft = updated;

        }

        System.out.println("Après " + day + " jours: " + Arrays.stream(dayLeft).sum());

    }

}
