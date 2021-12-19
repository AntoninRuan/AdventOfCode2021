package fr.antoninruan.advendofcode.day16;

import fr.antoninruan.advendofcode.util.Util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Packet {

    private final int version;
    private final int type;
    private final boolean hasSubpacket;
    private List<Packet> subpackets;
    private final long value;

    private Packet(int version, int type, long value) {
        this.version = version;
        this.type = type;
        this.value = value;
        this.hasSubpacket = false;
    }

    private Packet(int version, int type, List<Packet> subpackets) {
        this.version = version;
        this.type = type;
        this.hasSubpacket = true;
        this.subpackets = subpackets;
        this.value = -1L;
    }

    public int getVersion() {
        return version;
    }

    public int getType() {
        return type;
    }

    public boolean hasSubpacket() {
        return hasSubpacket;
    }

    public List<Packet> getSubpackets() {
        return subpackets;
    }

    public long getValue() {
        return value;
    }

    protected static Packet readPacket(String msg) {
        return readPacket(msg, new AtomicInteger(0));
    }

    protected static Packet readPacket(String msg, AtomicInteger start) {

        String bin = Util.convertToBinary(msg);
        char[] bits = bin.toCharArray();

        int version = Integer.parseInt(bin.substring(start.get(), start.get() + 3), 2);
        int type = Integer.parseInt(bin.substring(start.get() + 3, start.get() + 6), 2);

        if (type == 4) {
            long value = 0;

            int offset = 6;
            boolean hasNext = true;
            while (hasNext) {
                if (bits[start.get() + offset] == '0')
                    hasNext = false;
                value = value << 4;
                value += Long.parseLong(bin.substring(start.get() + offset + 1, start.get() + offset + 5), 2);
                offset += 5;
            }

            start.addAndGet(offset);
            return new Packet(version, type, value);
        } else {
            int lengthTypeId = Integer.parseInt(bin.substring(start.get() + 6, start.get() + 7), 2);
            List<Packet> subpackets = new ArrayList<>();
            if (lengthTypeId == 0) {
                int subPacketsLength = Integer.parseInt(bin.substring(start.get() + 7, start.get() + 7 + 15), 2);

                AtomicInteger offset = new AtomicInteger(start.get() + 7 + 15);
                while (offset.get() - (start.get() + 7 + 15) < subPacketsLength) {
                    subpackets.add(readPacket(msg, offset));
                }
                start.set(offset.get());
            } else if (lengthTypeId == 1) {
                int nbSubPackets = Integer.parseInt(bin.substring(start.get() + 7, start.get() + 7 + 11), 2);

                AtomicInteger offset = new AtomicInteger(start.get() + 7 + 11);
                for (int i = 0; i < nbSubPackets; i ++) {
                    subpackets.add(readPacket(msg, offset));
                }
                start.set(offset.get());
            }
            return new Packet(version, type, subpackets);
        }

    }

    protected long evaluate() {
        if (this.hasSubpacket) {
            switch (this.getType()) {
                case 0 -> {
                    return this.getSubpackets().stream().mapToLong(Packet::evaluate).sum();
                }
                case 1 -> {
                    long result = 1;
                    for (Packet c : this.getSubpackets())
                        result *= c.evaluate();
                    return result;
                }
                case 2 -> {
                    return this.getSubpackets().stream().mapToLong(Packet::evaluate).min().orElseThrow();
                }
                case 3 ->  {
                    return this.getSubpackets().stream().mapToLong(Packet::evaluate).max().orElseThrow();
                }
                case 5 ->  {
                    Packet c0 = this.getSubpackets().get(0), c1 = this.getSubpackets().get(1);
                    if (c0.evaluate() > c1.evaluate())
                        return 1L;
                    else
                        return 0L;
                }
                case 6 -> {
                    Packet c0 = this.getSubpackets().get(0), c1 = this.getSubpackets().get(1);
                    if (c0.evaluate() < c1.evaluate())
                        return 0;
                    else
                        return 1;
                }
                case 7 -> {
                    Packet c0 = this.getSubpackets().get(0), c1 = this.getSubpackets().get(1);
                    if (c0.evaluate() == c1.evaluate())
                        return 1L;
                    else
                        return 0L;
                }
            }

        }
        return this.value;
    }

    @Override
    public String toString() {
       switch (this.type) {
           case 0 -> {
               StringBuilder builder = new StringBuilder("(");
               boolean first = true;
               for (Packet p : this.getSubpackets()) {
                   if(first)
                       first = false;
                   else
                       builder.append(" + ");
                   builder.append(p.toString());
               }
               builder.append(")");
               return builder.toString();
           }
           case 1 -> {
               StringBuilder builder = new StringBuilder("(");
               boolean first = true;
               for (Packet p : this.getSubpackets()) {
                   if(first)
                       first = false;
                   else
                       builder.append(" * ");
                   builder.append(p.toString());
               }
               builder.append(")");
               return builder.toString();
           }
           case 2 -> {
               StringBuilder builder = new StringBuilder("min(");
               boolean first = true;
               for (Packet p : this.getSubpackets()) {
                   if(first)
                       first = false;
                   else
                       builder.append(", ");
                   builder.append(p.toString());
               }
               builder.append(")");
               return builder.toString();
           }
           case 3 -> {
               StringBuilder builder = new StringBuilder("max(");
               boolean first = true;
               for (Packet p : this.getSubpackets()) {
                   if(first)
                       first = false;
                   else
                       builder.append(", ");
                   builder.append(p.toString());
               }
               builder.append(")");
               return builder.toString();
           }
           case 4 -> {
               return String.valueOf(this.value);
           }
           case 5 -> {
               return "(" + subpackets.get(0).toString() + " > " + subpackets.get(1).toString() + ")";
           }
           case 6 -> {
               return "(" + subpackets.get(0).toString() + " < " + subpackets.get(1).toString() + ")";
           }
           case 7 -> {
               return "(" + subpackets.get(0).toString() + " == " + subpackets.get(1).toString() + ")";
           }
       }
       return "Error";
    }
}
