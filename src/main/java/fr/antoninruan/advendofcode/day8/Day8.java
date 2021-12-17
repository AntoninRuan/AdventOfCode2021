package fr.antoninruan.advendofcode.day8;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import fr.antoninruan.advendofcode.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day8.txt");

        System.out.println("Problem 1: " + problem1(data));

        System.out.println("Problem 2:" + problem2(data));

    }

    private static int problem1(String[] data) {
        int result = 0;
        for(String line : data) {
            String output = line.split(" \\| ")[1];
            String[] ouputs = output.split(" ");

            result += Arrays.stream(ouputs).filter(s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7).count();
        }
        return result;
    }

    private static int problem2(String[] data) {
        int result = 0;

        for(String line : data) {
            String[] observation = line.split(" \\| ")[0].split(" ");
            String[] output = line.split(" \\| ")[1].split(" ");

            BiMap<Character, Integer> representation = findRepresentation(observation);

            int pow = 1000;
            for(String nb : output) {
                boolean[] rep = new boolean[7];
                for (char c : nb.toCharArray()) {
                    rep[representation.get(c)] = true;
                }
                Integer value = NUMBERS.inverse().get(NUMBERS.values().stream()
                        .filter(t -> Arrays.equals(t, rep)).findFirst().orElse(ZERO));
                result += value * pow;
                pow /= 10;
            }

        }

        return result;
    }

    private static BiMap<Character, Integer> findRepresentation(String[] observation) {
        final String[] digitRepresentation = new String[10];
        final BiMap<Character, Integer> segment = HashBiMap.create();
        digitRepresentation[1] = Arrays.stream(observation).filter(s -> s.length() == 2).findFirst().orElse("");
        digitRepresentation[4] = Arrays.stream(observation).filter(s -> s.length() == 4).findFirst().orElse("");
        digitRepresentation[7] = Arrays.stream(observation).filter(s -> s.length() == 3).findFirst().orElse("");
        digitRepresentation[8] = Arrays.stream(observation).filter(s -> s.length() == 7).findFirst().orElse("");

        for (char c : digitRepresentation[7].toCharArray()) {
            if(! digitRepresentation[1].contains(String.valueOf(c))) {
                segment.put(c, 0);
                break;
            }
        }

        List<Character> group1 = digitRepresentation[4].chars().mapToObj(i -> (char) i)
                .filter(c -> digitRepresentation[1].indexOf(c) == -1).collect(Collectors.toList());
        List<Character> group2 = digitRepresentation[1].chars().mapToObj(i -> (char) i).collect(Collectors.toList());
        List<Character> group3 = digitRepresentation[8].chars().mapToObj(i -> (char) i)
                .filter(c -> !(group1.contains(c) || group2.contains(c) || segment.inverse().get(0) == c)).collect(Collectors.toList());

        BiMap<Character, Integer> test = HashBiMap.create(segment);

        List<Character> temp;
        find: for(int i = 0; i < group1.size(); i ++) {
            temp = new ArrayList<>(group1);
            test.put(temp.get(i), 1);
            temp.remove(i);
            test.put(temp.get(0), 3);
            for(int j = 0; j < group2.size(); j ++) {
                temp = new ArrayList<>(group2);
                test.put(temp.get(j), 2);
                temp.remove(j);
                test.put(temp.get(0), 5);
                for(int k = 0; k < group3.size(); k ++) {
                    temp = new ArrayList<>(group3);
                    test.put(temp.get(k), 4);
                    temp.remove(k);
                    test.put(temp.get(0), 6);

                    if (isValidRepresentation(observation, test))
                        break find;
                    else {
                        test.inverse().remove(4);
                        test.inverse().remove(6);
                    }
                }
                test.inverse().remove(2);
                test.inverse().remove(5);
            }
            test.inverse().remove(1);
            test.inverse().remove(3);
        }
        return test;
    }

    private static boolean isValidRepresentation(String[] observation, BiMap<Character, Integer> representation) {
        for(String nb : observation) {
            boolean[] rep = new boolean[7];
            for (char c : nb.toCharArray()) {
                rep[representation.get(c)] = true;
            }
            if (NUMBERS.values().stream().noneMatch(t -> Arrays.equals(t, rep))) {
                return false;
            }
        }

        return true;
    }

    private final static boolean[] ZERO = {true, true, true, false, true, true, true};
    private final static boolean[] ONE = {false, false, true, false, false, true, false};
    private final static boolean[] TWO = {true, false, true, true, true, false, true};
    private final static boolean[] THREE = {true, false, true, true, false, true, true};
    private final static boolean[] FOUR = {false, true, true, true, false, true, false};
    private final static boolean[] FIVE = {true, true, false, true, false, true, true};
    private final static boolean[] SIX = {true, true, false, true, true, true, true};
    private final static boolean[] SEVEN = {true, false, true, false, false, true, false};
    private final static boolean[] EIGHT = {true, true, true, true, true, true, true};
    private final static boolean[] NINE = {true, true, true, true, false, true, true};

    private final static BiMap<Integer, boolean[]> NUMBERS = HashBiMap.create();

    static {
        NUMBERS.put(0, ZERO);
        NUMBERS.put(1, ONE);
        NUMBERS.put(2, TWO);
        NUMBERS.put(3, THREE);
        NUMBERS.put(4, FOUR);
        NUMBERS.put(5, FIVE);
        NUMBERS.put(6, SIX);
        NUMBERS.put(7, SEVEN);
        NUMBERS.put(8, EIGHT);
        NUMBERS.put(9, NINE);
    }


}
