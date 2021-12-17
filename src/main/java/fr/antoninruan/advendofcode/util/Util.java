package fr.antoninruan.advendofcode.util;

import com.google.common.collect.HashBiMap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {

    public static String[] getInput(String file) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(Util.class.getClassLoader().getResourceAsStream("input/" + file)));
        return reader.lines().collect(Collectors.toList()).toArray(String[]::new);

    }

    private static final HashBiMap<Character, String> BIN_HEXA = HashBiMap.create();

    static {
        BIN_HEXA.put('0', "0000");
        BIN_HEXA.put('1', "0001");
        BIN_HEXA.put('2', "0010");
        BIN_HEXA.put('3', "0011");
        BIN_HEXA.put('4', "0100");
        BIN_HEXA.put('5', "0101");
        BIN_HEXA.put('6', "0110");
        BIN_HEXA.put('7', "0111");
        BIN_HEXA.put('8', "1000");
        BIN_HEXA.put('9', "1001");
        BIN_HEXA.put('A', "1010");
        BIN_HEXA.put('B', "1011");
        BIN_HEXA.put('C', "1100");
        BIN_HEXA.put('D', "1101");
        BIN_HEXA.put('E', "1110");
        BIN_HEXA.put('F', "1111");
    }

    public static String convertToBinary(String hexa) {
        StringBuilder builder = new StringBuilder();
        for(char c : hexa.toCharArray()) {
            builder.append(BIN_HEXA.get(c));
        }
        return builder.toString();
    }

}
