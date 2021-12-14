package fr.antoninruan.advendofcode;

import fr.antoninruan.advendofcode.util.Util;

import java.util.*;

public class Day14 {

    private static Map<String, Character> INSERTION = new HashMap<>();

    public static void main(String[] args) {

        String[] data = Util.getInput("day14.txt");

        String start = data[0];

        for (int i = 2; i < data.length; i ++) {
            String[] rule = data[i].split(" -> ");
            INSERTION.put(rule[0], rule[1].toCharArray()[0]);
        }

        System.out.println("Problem 1: " + problem1(start));

        System.out.println("Problem 2: " + problem2(start));

    }

    private static int problem1(String start) {
        int step = 10;
        String current = start;
        for (int i = 0; i < step; i ++) {
            StringBuilder builder = new StringBuilder();
            char[] chars = current.toCharArray();
            for (int j = 0; j < chars.length - 1; j ++) {
                builder.append(chars[j]);
                String seq = String.valueOf(chars, j, 2);
                if (INSERTION.containsKey(seq))
                    builder.append(INSERTION.get(seq));
            }
            builder.append(chars[chars.length - 1]);
            current = builder.toString();
        }

        Map<Character, Integer> occ = new HashMap<>();
        for (char c : current.toCharArray()) {
            if (!occ.containsKey(c))
                occ.put(c, 1);
            else
                occ.replace(c, occ.get(c) + 1);
        }

        return occ.values().stream().max(Comparator.naturalOrder()).orElse(0) - occ.values().stream().min(Comparator.naturalOrder()).orElse(0);
    }

    private static long problem2(String start) {
        Map<String, Long> pairs = new HashMap<>();
        Map<Character, Long> occurences = new HashMap<>();
        char[] chars = start.toCharArray();
        for (int i = 0; i < chars.length - 1; i ++) {
            if(!occurences.containsKey(chars[i]))
                occurences.put(chars[i], 1L);
            else
                occurences.replace(chars[i], occurences.get(chars[i]) + 1);

            String pair = String.valueOf(chars, i, 2);
            if (pairs.containsKey(pair))
                pairs.replace(pair, pairs.get(pair) + 1);
            else
                pairs.put(pair, 1L);
        }
        if(!occurences.containsKey(chars[chars.length - 1]))
            occurences.put(chars[chars.length - 1], 1L);
        else
            occurences.replace(chars[chars.length - 1], occurences.get(chars[chars.length -1 ]) + 1);

        int step = 40;
        for (int i = 0; i < step; i ++) {
            Set<Map.Entry<String,Long>> entries = new HashMap<>(pairs).entrySet();
            for (Map.Entry<String, Long> set : entries) {
                if (INSERTION.containsKey(set.getKey())) {
                    if(pairs.get(set.getKey()) == set.getValue())
                        pairs.remove(set.getKey());
                    else
                        pairs.replace(set.getKey(), pairs.get(set.getKey()) - set.getValue());

                    char generated = INSERTION.get(set.getKey());
                    if (!occurences.containsKey(generated))
                        occurences.put(generated, 1L);
                    else
                        occurences.replace(generated, occurences.get(generated) + set.getValue());

                    char[] pair = set.getKey().toCharArray();
                    String p1 = String.valueOf(new char[]{pair[0], generated});
                    String p2 = String.valueOf(new char[]{generated, pair[1]});
                    if (!pairs.containsKey(p1))
                        pairs.put(p1, set.getValue());
                    else
                        pairs.replace(p1, pairs.get(p1) + set.getValue());
                    if (!pairs.containsKey(p2))
                        pairs.put(p2, set.getValue());
                    else
                        pairs.replace(p2, pairs.get(p2) + set.getValue());



                }
            }
        }

        return occurences.values().stream().max(Comparator.naturalOrder()).orElse(0L)
                - occurences.values().stream().min(Comparator.naturalOrder()).orElse(0L);
    }

}
