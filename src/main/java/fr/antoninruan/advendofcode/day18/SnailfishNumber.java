package fr.antoninruan.advendofcode.day18;

import java.util.ArrayList;
import java.util.List;

import org.checkerframework.checker.units.qual.m;

public class SnailfishNumber {
    
    private static final char opening = '[';
    private static final char ending = ']';
    private static final char separator = ',';

    String representation;

    public SnailfishNumber(String representation) {
        this.representation = representation;
    }

    public void add(SnailfishNumber y) {
        this.representation = opening + this.representation + separator + y.representation + ending;
        this.reduce();
    }

    public List<String> toGroups() {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (char c : this.representation.toCharArray()) {
            if (c == opening) {
                current = new StringBuilder();
                current.append(c);
                result.add(current.toString());
                current = new StringBuilder();
            } else if (c == ending) {
                result.add(current.toString());
                if (current.toString().charAt(0) != ending) {
                    current = new StringBuilder();
                    current.append(c);
                }
            } else if (c == separator) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result;
    }

    public static SnailfishNumber fromGroups(List<String> groups) {
        StringBuilder builder = new StringBuilder();
        for (String s : groups) {
            if (s.charAt(0) == opening) {
                String current = builder.toString();
                if (current.length() > 0 && current.charAt(current.length() - 1) != opening) 
                    builder.append(separator);
            } else if(s.charAt(0) != ending) {
                String current = builder.toString();
                if (current.charAt(current.length() - 1) != opening) 
                    builder.append(separator);
                
            }
            builder.append(s);
        }
        return new SnailfishNumber(builder.toString());
    }

    public boolean explode() {
        boolean modified = false;
        List<String> newGroups = new ArrayList<>();
        int nested = 0;
        int exploding = 0;
        int index = 0, previous_number_index = -1;
        int previous_number = -1, to_add = -1;
        boolean skip_next_ending = false;
        for (String c : this.toGroups()) {
            // System.out.print(c);
            if (c.equals(String.valueOf(opening))) {
                nested ++;
                if (nested == 5 && exploding == 0) {
                    modified = true;
                    exploding = 1;
                } else {
                    newGroups.add(c);
                    index ++;
                }
            }
            else if (c.equals(String.valueOf(ending))) {
                nested --;
                if(!skip_next_ending) {
                    
                    newGroups.add(c);
                    index ++;
                } else {
                    skip_next_ending = false;
                }
            } else {
                if (exploding == 1) {
                    if(previous_number_index != -1) {
                        newGroups.set(previous_number_index, String.valueOf(previous_number + Integer.parseInt(c)));
                        previous_number_index = -1;
                    }
                    exploding ++;
                } else if (exploding == 2) {
                    to_add = Integer.parseInt(c);
                    skip_next_ending = true;
                    exploding = 3;
                    newGroups.add("0");
                } else {
                    if (to_add != -1) {
                        newGroups.add(String.valueOf(to_add + Integer.parseInt(c)));
                        to_add = -1;
                    } else 
                        newGroups.add(c);
                    previous_number_index = index;
                    previous_number = Integer.parseInt(c);
                    index ++;
                }

            }
            /* System.out.print(" " + fromGroups(newGroups));
            System.out.print(" " + previous_number_index);
            System.out.println(); */
        }

        this.representation = fromGroups(newGroups).toString();
        return modified;
    }

    public boolean split() {
        boolean modified = false;
        List<String> newGroups = new ArrayList<>();
        for (String s : toGroups()) {
            if (s.equals(String.valueOf(opening)) || s.equals(String.valueOf(ending))) {
                newGroups.add(s);
            } else {
                int value = Integer.parseInt(s);
                if (value >= 10 && !modified) {
                    modified = true;
                    newGroups.add(String.valueOf(opening));
                    newGroups.add(String.valueOf((int) Math.floor(value / 2.0)));
                    newGroups.add(String.valueOf((int) Math.ceil(value / 2.0)));
                    newGroups.add(String.valueOf(ending));
                } else 
                    newGroups.add(s);
            }
        }

        this.representation = fromGroups(newGroups).toString();
        return modified;
    }

    public void reduce() {
        boolean modified = false;
        do {
            modified = false;
            modified = this.explode();
            if (!modified) {
                modified = this.split();
            }
        } while (modified);
    }

    public SnailfishNumber[] getPairs() {
        int nested = 0, index = 0;
        for (char c : this.representation.toCharArray()) {
            if (c == opening) {
                nested ++;
            } else if (c == ending) {
                nested --;
            } else if (c == separator && nested == 1) {
                break;
            } 
            index ++;
        }
        return new SnailfishNumber[]
            {new SnailfishNumber(this.representation.substring(1, index)), 
            new SnailfishNumber(this.representation.substring(index + 1, this.representation.length() - 1))};
    } 

    public int getMagnitude() {
        int magnitude = 0;
        SnailfishNumber[] pairs = this.getPairs();
        try {
            magnitude += 3 * Integer.parseInt(pairs[0].toString());
        } catch(NumberFormatException ignored) {
            magnitude += 3 * pairs[0].getMagnitude();
        }
        try {
            magnitude += 2 * Integer.parseInt(pairs[1].toString());
        } catch (NumberFormatException ignored) {
            magnitude += 2 * pairs[1].getMagnitude();
        }
        
        return magnitude;
    }

    @Override
    public String toString() {
        return this.representation;
    }

}
