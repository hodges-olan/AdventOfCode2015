//--- Day 12: JSAbacusFramework.io ---
//
//Santa's Accounting-Elves need help balancing the books after a recent order. Unfortunately, their accounting software uses a peculiar storage format. That's where you come in.
//
//They have a JSON document which contains a variety of things: arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings. Your first job is to simply find all of the numbers throughout the document and add them together.
//
//For example:
//
//[1,2,3] and {"a":2,"b":4} both have a sum of 6.
//[[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
//{"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
//[] and {} both have a sum of 0.
//You will not encounter any strings containing numbers.
//
//What is the sum of all numbers in the document?
//
//Your puzzle answer was 156366.
//
//--- Part Two ---
//
//Uh oh - the Accounting-Elves have realized that they double-counted everything red.
//
//Ignore any object (and all of its children) which has any property with the value "red". Do this only for objects ({...}), not arrays ([...]).
//
//[1,2,3] still has a sum of 6.
//[1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
//{"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
//[1,"red",5] has a sum of 6, because "red" in an array has no effect.
//Your puzzle answer was 96852.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day12;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Day12 {
    private final static String filePath = "day12.txt";
    private static String input = new String();
    private final static ArrayList<Double> values = new ArrayList<>();
    private static double total = 0.0;

    public static void main(String[] args) {
        Day12.readFile(Day12.filePath);
        
        // Part 1
        String str = Day12.input.replaceAll("[^-?0-9]+", " ");
        for (String temp : Arrays.asList(str.trim().split(" "))) { Day12.values.add(Double.parseDouble(temp)); }
        for (double temp : Day12.values) { Day12.total = Day12.total + temp; }
        System.out.println("Part 1 Total: " + Day12.total);
        
        // Part 2
        String newStr = Day12.input;
        Day12.values.clear();
        Day12.total = 0.0;
        while (newStr.contains("red")) {
            newStr = Day12.removeRed(newStr);
        }
        newStr = newStr.replaceAll("[^-?0-9]+", " ");
        for (String temp : Arrays.asList(newStr.trim().split(" "))) { Day12.values.add(Double.parseDouble(temp)); }
        for (double temp : Day12.values) { Day12.total = Day12.total + temp; }
        System.out.println("Part 2 Total: " + Day12.total);
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.readLine()) != null) Day12.input=read.trim(); } catch (IOException ex) { Logger.getLogger(Day12.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static String removeRed(String newStr) {
        int index = newStr.indexOf("red");
        int start = 0, end = 0, count = 0;
        for (int i = index; i > 0; i--) {
            if (newStr.substring(i,i+1).contains("}") || newStr.substring(i,i+1).contains("]")) {
                count = ++count;
            } else if (newStr.substring(i,i+1).contains("{") && count == 0) {
                start = i;
                break;
            } else if (newStr.substring(i,i+1).contains("[") && count == 0) {
                newStr = newStr.replaceFirst("red", "REPLACED");
                return newStr;
            } else if (newStr.substring(i,i+1).contains("[") || newStr.substring(i,i+1).contains("{")) {
                count = --count;
            }
        }
        count = 0;
        for (int i = start; i < newStr.length(); i++) {
            if (newStr.substring(i,i+1).contains("{") || newStr.substring(i,i+1).contains("[")) {
                count = ++count;
            } else if ((newStr.substring(i,i+1).contains("}") || newStr.substring(i,i+1).contains("]")) && count == 1) {
                end = i;
                break;
            } else if (newStr.substring(i,i+1).contains("}") || newStr.substring(i,i+1).contains("]")) {
                count = --count;
            }
        }
        String firstString = newStr.substring(0,start);
        String secondString = newStr.substring((end+1),newStr.length());
        newStr = firstString.concat(secondString);
        return newStr;
    }
}