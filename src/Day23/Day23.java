//--- Day 23: Opening the Turing Lock ---
//
//Little Jane Marie just got her very first computer for Christmas from some unknown benefactor. It comes with instructions and an example program, but the computer itself seems to be malfunctioning. She's curious what the program does, and would like you to help her run it.
//
//The manual explains that the computer supports two registers and six instructions (truly, it goes on to remind the reader, a state-of-the-art technology). The registers are named a and b, can hold any non-negative integer, and begin with a value of 0. The instructions are as follows:
//
//hlf r sets register r to half its current value, then continues with the next instruction.
//tpl r sets register r to triple its current value, then continues with the next instruction.
//inc r increments register r, adding 1 to it, then continues with the next instruction.
//jmp offset is a jump; it continues with the instruction offset away relative to itself.
//jie r, offset is like jmp, but only jumps if register r is even ("jump if even").
//jio r, offset is like jmp, but only jumps if register r is 1 ("jump if one", not odd).
//All three jump instructions work with an offset relative to that instruction. The offset is always written with a prefix + or - to indicate the direction of the jump (forward or backward, respectively). For example, jmp +1 would simply continue with the next instruction, while jmp +0 would continuously jump back to itself forever.
//
//The program exits when it tries to run an instruction beyond the ones defined.
//
//For example, this program sets a to 2, because the jio instruction causes it to skip the tpl instruction:
//
//inc a
//jio a, +2
//tpl a
//inc a
//What is the value in register b when the program in your puzzle input is finished executing?
//
//Your puzzle answer was 255.
//
//--- Part Two ---
//
//The unknown benefactor is very thankful for releasi-- er, helping little Jane Marie with her computer. Definitely not to distract you, what is the value in register b after the program is finished executing if register a starts as 1 instead?
//
//Your puzzle answer was 334.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day23;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

public class Day23 {
    private final static String filePath = "day23.txt";
    private final static ArrayList<String[]> input = new ArrayList<>();
    private static long registerA = 0, registerB = 0;
    
    public static void main(String args[]) {
        Day23.readFile();
        
        // Part1 
        Day23.compute();
        System.out.println("Part 1 -\nRegister A: " + Day23.registerA + "\tRegister B: " + Day23.registerB);
        
        // Part2
        Day23.registerA = 1;
        Day23.registerB = 0;
        Day23.compute();
        System.out.println("Part 2 -\nRegister A: " + Day23.registerA + "\tRegister B: " + Day23.registerB);
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(Day23.filePath))) { while((read = in.readLine()) != null) Day23.input.add(read.trim().replaceAll(",","").split(" ")); } catch (IOException ex) { Logger.getLogger(Day23.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
    private static void compute() {
        int offset = 0;
        int tempNum;
        while (offset >= 0 && offset < Day23.input.size()) {
            switch(Day23.input.get(offset)[0]) {
                case "hlf":
                    if (Day23.input.get(offset)[1].equals("a")) Day23.registerA /= 2; else Day23.registerB /= 2;
                    break;
                case "tpl":
                    if (Day23.input.get(offset)[1].equals("a")) Day23.registerA *= 3; else Day23.registerB *= 3;
                    break;
                case "inc":
                    if (Day23.input.get(offset)[1].equals("a")) Day23.registerA++; else Day23.registerB++;
                    break;
                case "jmp":
                    offset += Integer.parseInt(Day23.input.get(offset)[1]) - 1;
                    break;
                case "jie":
                    if (Day23.input.get(offset)[1].equals("a") && Day23.registerA%2 == 0) {
                        offset += Integer.parseInt(Day23.input.get(offset)[2]) - 1;
                    } else if (Day23.input.get(offset)[1].equals("b") && Day23.registerB%2 == 0) {
                        offset += Integer.parseInt(Day23.input.get(offset)[2]) - 1;
                    }
                    break;
                default:
                    if (Day23.input.get(offset)[1].equals("a") && Day23.registerA == 1) {
                        offset += Integer.parseInt(Day23.input.get(offset)[2]) - 1;
                    } else if (Day23.input.get(offset)[1].equals("b") && Day23.registerB == 1) {
                        offset += Integer.parseInt(Day23.input.get(offset)[2]) - 1;
                    }
                    break;
            }
            offset++;
        }
    }
}