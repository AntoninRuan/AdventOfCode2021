package fr.antoninruan.advendofcode.day10;

import fr.antoninruan.advendofcode.util.Util;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    private static Map<Character, Integer> ILLEGAL_CHAR_VALUE = new HashMap<>();
    private static Map<Character, Integer> COMPLETE_CHAR_VALUE = new HashMap<>();

    static {
        ILLEGAL_CHAR_VALUE.put(')', 3);
        ILLEGAL_CHAR_VALUE.put(']', 57);
        ILLEGAL_CHAR_VALUE.put('}', 1197);
        ILLEGAL_CHAR_VALUE.put('>', 25137);

        COMPLETE_CHAR_VALUE.put(')', 1);
        COMPLETE_CHAR_VALUE.put(']', 2);
        COMPLETE_CHAR_VALUE.put('}', 3);
        COMPLETE_CHAR_VALUE.put('>', 4);

    }

    public static void main(String[] args) {

        String[] data = Util.getInput("day10.txt");

        System.out.println("Problem 1: " + problem1(data));

        System.out.println("Problem 2: " + problem2(data));

    }

    private static int problem1(String[] data) {

        int result = 0;

        for (String line : data) {
            Stack<Character> expected = new Stack<>();
            is_valid: for (char c : line.toCharArray()) {
                switch (c) {
                    case '{' -> expected.push('}');
                    case '[' -> expected.push(']');
                    case '(' -> expected.push(')');
                    case '<' -> expected.push('>');
                    default -> {
                        char e = expected.pop();
                        if (c != e) {
                            result += ILLEGAL_CHAR_VALUE.get(c);
                            break is_valid;
                        }
                    }
                }
            }
        }

        return result;
    }

    private static long problem2(String[] data) {

        List<String> incomplete = Arrays.stream(data).filter(s -> !isCorrupted(s)).collect(Collectors.toList());

        List<Long> scores = new ArrayList<>();

        for(String line : incomplete) {
            Stack<Character> expected = new Stack<>();
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '{' -> expected.push('}');
                    case '[' -> expected.push(']');
                    case '(' -> expected.push(')');
                    case '<' -> expected.push('>');
                    default -> expected.pop();
                }
            }
            List<Character> complete = new ArrayList<>(expected);
            Collections.reverse(complete);
            long score = complete.stream().mapToLong(c -> COMPLETE_CHAR_VALUE.get(c)).reduce(0, (left, right) -> 5 * left + right);
            scores.add(score);
        }

        scores.sort(Comparator.naturalOrder());

        return scores.get(scores.size() / 2);
    }

    private static boolean isCorrupted(String line) {
        Stack<Character> expected = new Stack<>();
        for (char c : line.toCharArray()) {
            switch (c) {
                case '{' -> expected.push('}');
                case '[' -> expected.push(']');
                case '(' -> expected.push(')');
                case '<' -> expected.push('>');
                default -> {
                    char e = expected.pop();
                    if (c != e)
                        return true;
                }
            }
        }
        return false;
    }

}
