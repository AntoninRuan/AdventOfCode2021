package fr.antoninruan.advendofcode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Day3 {

    public static void main(String[] args) {
        String[] data = Util.getInput("day3.txt");

        int[] ones = new int[data[0].length()];
        int[] zeros = new int[data[0].length()];

        for (String s : data) {

            char[] bits = s.toCharArray();

            for (int i = 0; i < ones.length; i ++) {

                if (bits[i] == '1') {
                    ones[i] ++;
                } else if (bits[i] == '0') {
                    zeros[i] ++;
                }

            }

        }

        StringBuilder gammaBuilder = new StringBuilder();
        StringBuilder epsilonBuilder = new StringBuilder();
        for (int i = 0; i < ones.length; i ++) {

            if (ones[i] >= zeros[i]) {
                gammaBuilder.append("1");
                epsilonBuilder.append("0");
            } else {
                gammaBuilder.append("0");
                epsilonBuilder.append("1");
            }

        }

        int gamma = Integer.parseInt(gammaBuilder.toString(), 2);
        int epsilon = Integer.parseInt(epsilonBuilder.toString(), 2);

        System.out.println("Gamma: 0b" + gammaBuilder + ", Espilon: 0b" + epsilonBuilder);
        System.out.println("Power: " + (gamma * epsilon));

        final AtomicReference<Character> mostCommon = new AtomicReference<>(gammaBuilder.charAt(0));
        final AtomicReference<Character> leastCommon = new AtomicReference<>(epsilonBuilder.charAt(0));

        ArrayList<String> oxygen = new ArrayList<>(List.of(data));
        final AtomicInteger reading = new AtomicInteger(0);
        while (oxygen.size() > 1 && reading.get() < data[0].length()) {
            int sum = 0;
            sum += oxygen.stream().filter(o -> o.charAt(reading.get()) == '1').count();
            sum -= oxygen.stream().filter(o -> o.charAt(reading.get()) == '0').count();
            if (sum >= 0)
                mostCommon.set('1');
            else
                mostCommon.set('0');
            oxygen.removeIf(o -> o.charAt(reading.get()) != mostCommon.get());
            reading.incrementAndGet();
        }

        ArrayList<String> co2 = new ArrayList<>(List.of(data));
        reading.set(0);
        while (co2.size() > 1 && reading.get() < data[0].length()) {
            int sum = 0;
            sum += co2.stream().filter(o -> o.charAt(reading.get()) == '0').count();
            sum -= co2.stream().filter(o -> o.charAt(reading.get()) == '1').count();
            if (sum > 0)
                leastCommon.set('1');
            else
                leastCommon.set('0');
            co2.removeIf(o -> o.charAt(reading.get()) != leastCommon.get());
            reading.incrementAndGet();
        }

        int oxygenI = Integer.parseInt(oxygen.get(0), 2);
        int co2I = Integer.parseInt(co2.get(0), 2);

        System.out.println("Oxygen: " + oxygenI);
        System.out.println("CO2: " + co2I);
        System.out.println("Life support Rating: " + (oxygenI * co2I));

    }

}
