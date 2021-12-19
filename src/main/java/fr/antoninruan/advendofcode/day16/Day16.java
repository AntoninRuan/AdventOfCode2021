package fr.antoninruan.advendofcode.day16;

import fr.antoninruan.advendofcode.util.Util;

import java.math.BigInteger;
import java.util.Stack;

public class Day16 {

    public static void main(String[] args) {
        String[] data = Util.getInput("day16.txt");

        String msg = data[0];
        Packet p = Packet.readPacket(msg);

        System.out.println(p.toString());

        System.out.println("Problem 1: " + problem1(p));

        System.out.println("Problem 2: " + problem2(p));

    }

    private static int problem1(Packet p) {
        Stack<Packet> children = new Stack<>();
        children.add(p);
        int result = 0;
        while(!children.isEmpty()) {
            Packet c = children.pop();
            if (c.hasSubpacket())
                children.addAll(c.getSubpackets());
            result += c.getVersion();
        }
        return result;
    }

    private static long problem2(Packet p) {
        return p.evaluate();
    }

}
