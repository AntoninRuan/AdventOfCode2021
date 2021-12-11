package fr.antoninruan.advendofcode.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Util {

    public static String[] getInput(String day) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(Util.class.getClassLoader().getResourceAsStream("input/" + day)));
        return reader.lines().collect(Collectors.toList()).toArray(String[]::new);

    }

}
