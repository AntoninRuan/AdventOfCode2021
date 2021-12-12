package fr.antoninruan.advendofcode;

import fr.antoninruan.advendofcode.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day12 {

    public static void main(String[] args) {

        String[] data = Util.getInput("day12.txt");
        Map<String, List<String>> graph = new HashMap<>();

        for (String line : data) {

            String[] connection = line.split("-");
            String start = connection[0];
            String dest = connection[1];

            if (!graph.containsKey(start))
                graph.put(start, new ArrayList<>());
            graph.get(start).add(dest);

            if (!graph.containsKey(dest))
                graph.put(dest, new ArrayList<>());
            graph.get(dest).add(start);

        }

        System.out.println("Problem 1: " + problem1(graph));

        System.out.println("Problem 2: " + problem2(graph));

    }

    private static int problem1(Map<String, List<String>> graph) {
        List<String> v = new ArrayList<>();
        v.add("start");
        List<List<String>> paths = pathFrom(graph, "start", v, true);
        return paths.size();
    }

    private static int problem2(Map<String, List<String>> graph) {
        List<String> v = new ArrayList<>();
        v.add("start");
        List<List<String>> paths = pathFrom(graph, "start", v, false);
        return paths.size();
    }

    private static List<List<String>> pathFrom(Map<String, List<String>> graph, String start, List<String> visited, boolean twice) {
        List<List<String>> result = new ArrayList<>();
        if(start.equals("end")) {
            List<String> l = new ArrayList<>();
            l.add("end");
            result.add(l);
            return result;
        }
        List<String> voisins = graph.get(start).stream()
                .filter(s -> (!visited.contains(s) || s.toUpperCase().equals(s) || !twice) && !s.equals("start"))
                .collect(Collectors.toList());

        for(String v : voisins) {
            List<String> newVisited = new ArrayList<>(visited);
            newVisited.add(v);
            boolean t = twice;
            if (v.toLowerCase().equals(v) && visited.contains(v))
                t = true;
            List<List<String>> p = pathFrom(graph, v, newVisited, t);
            result.addAll(p);
        }
        for (List<String> l : result)
            l.add(start);

        return result;
    }

}
