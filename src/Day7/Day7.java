//--- Day 7: Some Assembly Required ---
//
//This year, Santa brought little Bobby Tables a set of wires and bitwise logic gates! Unfortunately, little Bobby is a little under the recommended age range, and he needs help assembling the circuit.
//
//Each wire has an identifier (some lowercase letters) and can carry a 16-bit signal (a number from 0 to 65535). A signal is provided to each wire by a gate, another wire, or some specific value. Each wire can only get a signal from one source, but can provide its signal to multiple destinations. A gate provides no signal until all of its inputs have a signal.
//
//The included instructions booklet describes how to connect the parts together: x AND y -> z means to connect wires x and y to an AND gate, and then connect its output to wire z.
//
//For example:
//
//123 -> x means that the signal 123 is provided to wire x.
//x AND y -> z means that the bitwise AND of wire x and wire y is provided to wire z.
//p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2 and then provided to wire q.
//NOT e -> f means that the bitwise complement of the value from wire e is provided to wire f.
//Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If, for some reason, you'd like to emulate the circuit instead, almost all programming languages (for example, C, JavaScript, or Python) provide operators for these gates.
//
//For example, here is a simple circuit:
//
//123 -> x
//456 -> y
//x AND y -> d
//x OR y -> e
//x LSHIFT 2 -> f
//y RSHIFT 2 -> g
//NOT x -> h
//NOT y -> i
//After it is run, these are the signals on the wires:
//
//d: 72
//e: 507
//f: 492
//g: 114
//h: 65412
//i: 65079
//x: 123
//y: 456
//In little Bobby's kit's instructions booklet (provided as your puzzle input), what signal is ultimately provided to wire a?
//
//Your puzzle answer was 956.
//
//--- Part Two ---
//
//Now, take the signal you got on wire a, override wire b to that signal, and reset the other wires (including wire a). What new signal is ultimately provided to wire a?
//
//Your puzzle answer was 40149.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day7 {
    
    public static ArrayList<String> ops, notOps;
    public static Map<String, Integer> values = new HashMap<>();

    public static void main(String[] args) {
        String filePath = "day7.txt";
        ArrayList<String> input = readFile(filePath);
        int aValue = 0;
        
        Day7.ops = getValues(input, 0);
        Day7.notOps = getValues(input, 1);
        Day7.initialValues();
        
        // Part 1
        while (aValue == 0) { aValue = iterateValues(); }
        
        System.out.println("Part 1 Value of a: " + aValue);
        
        // Part 2
        values.clear();
        Day7.initialValues();
        values.put("b", aValue);
        aValue = 0;
        
        // Part 1
        while (aValue == 0) { aValue = iterateValues(); }
        
        System.out.println("Part 2 Value of a: " + aValue);
        
    }
    
    private static ArrayList<String> readFile(String filePath) {
        ArrayList<String> input = new ArrayList<>();
        String read;
        
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) { input.add(read.trim()); }
        } catch (IOException ex) { Logger.getLogger(Day7.class.getName()).log(Level.SEVERE, null, ex); }
        
        return input;
    }

    private static ArrayList<String> getValues(ArrayList<String> input, int i) {
        ArrayList<String> valuesLocal = new ArrayList<>();
        String[] value;
        for (String line : input) {
            value = line.split(" -> ");
            valuesLocal.add(value[i]);
        }
        return valuesLocal;
    }

    private static void initialValues() {
        int i = 0;
        for (String op : Day7.ops) {
            if (op.matches("\\d+")) { Day7.values.put(Day7.notOps.get(i), Integer.parseInt(op)); }
            i = ++i;
        }
    }
    
    private static int iterateValues() {
        String[] split;
        int var1, var2, equals, returnInt, i = 0;
        for (String op : Day7.ops) {
            split = op.split(" ");
            if (Arrays.asList(split).contains("AND")) {
                if ((Day7.values.containsKey(split[0]) || split[0].matches("\\d+")) & (Day7.values.containsKey(split[2]) || split[2].matches("\\d+")) & !Day7.values.containsKey(Day7.notOps.get(i))) {
                    if (split[0].matches("\\d+")) { var1 = Integer.parseInt(split[0]); } else { var1 = Day7.values.get(split[0]); }
                    if (split[2].matches("\\d+")) { var2 = Integer.parseInt(split[2]); } else { var2 = Day7.values.get(split[2]); }
                    equals = var1 & var2;
                    Day7.values.put(Day7.notOps.get(i), equals);
                }
            } else if (Arrays.asList(split).contains("OR")) {
                if ((Day7.values.containsKey(split[0]) || split[0].matches("\\d+")) & (Day7.values.containsKey(split[2]) || split[2].matches("\\d+")) & !Day7.values.containsKey(Day7.notOps.get(i))) {
                    var1 = Day7.values.get(split[0]);
                    var2 = Day7.values.get(split[2]);
                    equals = var1 | var2;
                    Day7.values.put(Day7.notOps.get(i), equals);
                }
            } else if (Arrays.asList(split).contains("NOT")) {
                if ((Day7.values.containsKey(split[1]) || split[1].matches("\\d+")) & !Day7.values.containsKey(Day7.notOps.get(i))) {
                    var1 = Day7.values.get(split[1]);
                    equals = 65535 - var1;
                    Day7.values.put(notOps.get(i), equals);
                }
            } else if (Arrays.asList(split).contains("LSHIFT")) {
                if ((Day7.values.containsKey(split[0]) || split[0].matches("\\d+")) & !Day7.values.containsKey(Day7.notOps.get(i))) {
                    var1 = Day7.values.get(split[0]);
                    var2 = Integer.parseInt(split[2]);
                    equals = var1 << var2;
                    Day7.values.put(Day7.notOps.get(i), equals);
                }
            } else if (Arrays.asList(split).contains("RSHIFT")) {
                if ((Day7.values.containsKey(split[0]) || split[0].matches("\\d+")) & !Day7.values.containsKey(Day7.notOps.get(i))) {
                    var1 = Day7.values.get(split[0]);
                    var2 = Integer.parseInt(split[2]);
                    equals = var1 >>> var2;
                    Day7.values.put(Day7.notOps.get(i), equals);
                }
            } else {
                if (!split[0].matches("\\d+") & Day7.values.containsKey(split[0]) & !Day7.values.containsKey(Day7.notOps.get(i))) {
                    var1 = Day7.values.get(split[0]);
                    Day7.values.put(Day7.notOps.get(i), var1);
                }
            }
            i = ++i;
        }
        if (Day7.values.get("a") == null) { returnInt = 0; } else { returnInt = Day7.values.get("a"); } 
        return returnInt;
    }
}