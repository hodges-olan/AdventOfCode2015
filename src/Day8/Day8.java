//--- Day 8: Matchsticks ---
//
//Space on the sleigh is limited this year, and so Santa will be bringing his list as a digital copy. He needs to know how much space it will take up when stored.
//
//It is common in many programming languages to provide a way to escape special characters in strings. For example, C, JavaScript, Perl, Python, and even PHP handle special characters in very similar ways.
//
//However, it is important to realize the difference between the number of characters in the code representation of the string literal and the number of characters in the in-memory string itself.
//
//For example:
//
//"" is 2 characters of code (the two double quotes), but the string contains zero characters.
//"abc" is 5 characters of code, but 3 characters in the string data.
//"aaa\"aaa" is 10 characters of code, but the string itself contains six "a" characters and a single, escaped quote character, for a total of 7 characters in the string data.
//"\x27" is 6 characters of code, but the string itself contains just one - an apostrophe ('), escaped using hexadecimal notation.
//Santa's list is a file that contains many double-quoted string literals, one on each line. The only escape sequences used are \\ (which represents a single backslash), \" (which represents a lone double-quote character), and \x plus two hexadecimal characters (which represents a single character with that ASCII code).
//
//Disregarding the whitespace in the file, what is the number of characters of code for string literals minus the number of characters in memory for the values of the strings in total for the entire file?
//
//For example, given the four strings above, the total number of characters of string code (2 + 5 + 10 + 6 = 23) minus the total number of characters in memory for string values (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
//
//Your puzzle answer was 1371.
//
//--- Part Two ---
//
//Now, let's go the other way. In addition to finding the number of characters of code, you should now encode each code representation as a new string and find the number of characters of the new encoded representation, including the surrounding double quotes.
//
//For example:
//
//"" encodes to "\"\"", an increase from 2 characters to 6.
//"abc" encodes to "\"abc\"", an increase from 5 characters to 9.
//"aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10 characters to 16.
//"\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.
//Your task is to find the total number of characters to represent the newly encoded strings minus the number of characters of code in each original string literal. For example, for the strings above, the total encoded length (6 + 9 + 16 + 11 = 42) minus the characters in the original code representation (23, just like in the first part of this puzzle) is 42 - 23 = 19.
//
//Your puzzle answer was 2117.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8 {
    
    public static int totalCharacterCount = 0;
    public static int hexTotal = 0;
    public static int quotesTotal = 0;
    public static int backslashesTotal = 0;
    public static int remainingCharactersTotal = 0;

    public static void main(String[] args) {
        String filePath = "day8.txt";
        ArrayList<String> input = Day8.readFile(filePath);;
        
        // Part 1
        System.out.println("Part One: " + partOne(input, false));
        
        // Part 2
        System.out.println("Part Two: " + partOne(input, true));
        
    }
    
    private static String partOne(ArrayList<String> input, boolean partTwo) {
        Day8.countTotalCharacters(input);
        input = Day8.removeQuotes(input);
        input = Day8.countHex(input);
        input = Day8.countQuotes(input, partTwo);
        input = Day8.countBackslashes(input, partTwo);
        Day8.countRemainingCharacters(input);
        int total = (partTwo) ? (Day8.totalCharacterCount + Day8.backslashesTotal + Day8.hexTotal + Day8.quotesTotal) - Day8.totalCharacterCount : Day8.totalCharacterCount - Day8.backslashesTotal - Day8.hexTotal - Day8.quotesTotal - Day8.remainingCharactersTotal;
        return Integer.toString(total);
    }
    
    private static ArrayList<String> readFile(String filePath) {
        ArrayList<String> input = new ArrayList<>();
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) { input.add(read.trim()); }
        } catch (IOException ex) { Logger.getLogger(Day8.class.getName()).log(Level.SEVERE, null, ex); }
        return input;
    }

    private static ArrayList<String> removeQuotes(ArrayList<String> input) {
        ArrayList<String> newInput = new ArrayList<>();
        input.stream().forEach((line) -> { newInput.add(line.substring(1, line.length()-1)); });
        return newInput;
    }

    private static void countTotalCharacters(ArrayList<String> input) {
        int total = 0;
        for(String line : input) { total = total + line.length(); }
        Day8.totalCharacterCount = total;
    }

    private static ArrayList<String> countHex(ArrayList<String> input) {
        ArrayList<String> newInput = new ArrayList<>();
        int total = 0;
        Pattern pattern = Pattern.compile("\\\\x[0-9a-f]{2}");
        Matcher matcher;
        for(String line : input) {
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                total++;
                line = line.replaceFirst("\\\\x[0-9a-f]{2}", "");
            }
            newInput.add(line);
        }
        Day8.hexTotal = total;
        return newInput;
    }

    private static ArrayList<String> countQuotes(ArrayList<String> input, boolean partTwo) {
        ArrayList<String> newInput = new ArrayList<>();
        int total = 0;
        Pattern pattern = Pattern.compile("\\\\\"");
        Matcher matcher;
        for (String line : input) {
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                total = (partTwo) ? total + 2 : ++total;
                line = line.replaceFirst("\\\\\"", "");
            }
            if(partTwo) total = total + 4;
            newInput.add(line);
        }
        Day8.quotesTotal = total;
        return newInput;
    }

    private static ArrayList<String> countBackslashes(ArrayList<String> input, boolean partTwo) {
        ArrayList<String> newInput = new ArrayList<>();
        int total = 0;
        Pattern pattern = Pattern.compile("\\\\{2}");
        Matcher matcher;
        for (String line : input) {
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                total = (partTwo) ? total + 2 : ++total;
                line = line.replaceFirst("\\\\{2}", "");
            }
            newInput.add(line);
        }
        Day8.backslashesTotal = total;
        return newInput;
    }

    private static void countRemainingCharacters(ArrayList<String> input) {
        int total = 0;
        for (String line : input) { total = total + line.length(); }
        Day8.remainingCharactersTotal = total;
    }

}