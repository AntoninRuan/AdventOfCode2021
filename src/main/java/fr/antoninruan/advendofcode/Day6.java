package fr.antoninruan.advendofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) {

        String[] data = Util.getInput("test.txt");

        List<Short> lanternfishes = Arrays.stream(data[0].split(",")).map(Short::parseShort).collect(Collectors.toList());

        int day = 256;
        List<Short> toAdd = new ArrayList<>();
//
//        for (int i = 0; i < day; i ++) {
//            toAdd.clear();
//            for (int j = 0; j < lanternfishes.size(); j ++) {
//                int fish = lanternfishes.get(j);
//                if (fish == 0) {
//                    lanternfishes.set(j, 6);
//                    toAdd.add(8);
//                } else {
//                    lanternfishes.set(j, fish - 1);
//                }
//            }
//            lanternfishes.addAll(toAdd);
//        }
//
//        System.out.println("Après " + day + " jours: " + lanternfishes.size());

        int numberOfFish = 0;

        // TODO Optimiser encore plus l'espace mémoire utiliser pour pouvoir run le programme
        List<Short> descendance = new ArrayList<>();
        int treated = 0;
        for (Short fish : lanternfishes) {
            descendance.add(fish);
            for (int i = 0; i < day; i ++) {
                for (int j = 0; j < descendance.size(); j ++) {
                    int f = descendance.get(j);
                    if (f == 0) {
                        descendance.set(j, (short) 6);
                        toAdd.add((short) 8);
                    } else {
                        descendance.set(j, (short) (f - 1));
                    }
                }
                descendance.addAll(toAdd);
                toAdd.clear();
            }
            System.out.println(treated ++);
            numberOfFish += descendance.size();
            descendance.clear();
        }

        System.out.println("Après " + day + " jours: " + numberOfFish);

    }

}
