//--- Day 1: Not Quite Lisp ---
//
//Santa was hoping for a white Christmas, but his weather machine's "snow" function is powered by stars, and he's fresh out! To save Christmas, he needs you to collect fifty stars by December 25th.
//
//Collect stars by helping Santa solve puzzles. Two puzzles will be made available on each day in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
//
//Here's an easy puzzle to warm you up.
//
//Santa is trying to deliver presents in a large apartment building, but he can't find the right floor - the directions he got are a little confusing. He starts on the ground floor (floor 0) and then follows the instructions one character at a time.
//
//An opening parenthesis, (, means he should go up one floor, and a closing parenthesis, ), means he should go down one floor.
//
//The apartment building is very tall, and the basement is very deep; he will never find the top or bottom floors.
//
//For example:
//
//(()) and ()() both result in floor 0.
//((( and (()(()( both result in floor 3.
//))((((( also results in floor 3.
//()) and ))( both result in floor -1 (the first basement level).
//))) and )())()) both result in floor -3.
//To what floor do the instructions take Santa?
//
//Your puzzle answer was 74.
//
//--- Part Two ---
//
//Now, given the same instructions, find the position of the first character that causes him to enter the basement (floor -1). The first character in the instructions has position 1, the second character has position 2, and so on.
//
//For example:
//
//) causes him to enter the basement at character position 1.
//()()) causes him to enter the basement at character position 5.
//What is the position of the character that causes Santa to first enter the basement?
//
//Your puzzle answer was 1795.
//
//Both parts of this puzzle are complete! They provide two gold stars: **

package Day1;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Day1 {
    private final static String filePath = "day1.txt";
    private final static ArrayList<Character> input = new ArrayList<>();
    
    public static void main(String[] args) {
        int floor = 0, position = 0, basement = 0;
        boolean atBasement = false;
        Day1.readFile(Day1.filePath);

        for (char line : Day1.input) {
            position = ++position;
            if (line == '(') { floor = ++floor; } else { floor = --floor; }
            if (floor == -1 && !atBasement) { basement = position; atBasement = true; }
        }
        System.out.println("Final Floor: " + floor);
        System.out.println("Instruction Position when first reached basement: " + basement);
    }
    private static void readFile(String filePath) {
        int read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.read()) != -1) Day1.input.add((char) read); } catch (IOException ex) { Logger.getLogger(Day1.class.getName()).log(Level.SEVERE, null, ex); }
    }
}