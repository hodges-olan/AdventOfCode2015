package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8Part2 {
    
    public static int totalCharacterCount = 0;
    public static int hexTotal = 0;
    public static int quotesTotal = 0;
    public static int backslashesTotal = 0;
    public static int remainingCharactersTotal = 0;

    public static void main(String[] args) {
        String filePath = "day8.txt";
        ArrayList<String> input;
        
        // Part 1
        input = Day8Part2.readFile(filePath);
        Day8Part2.countTotalCharacters(input);
        input = Day8Part2.removeQuotes(input);
        input = Day8Part2.countHex(input);
        input = Day8Part2.countQuotes(input);
        input = Day8Part2.countBackslashes(input);
        Day8Part2.countRemainingCharacters(input);
        Day8Part2.displayResults(input);
        
    }
    
    private static ArrayList<String> readFile(String filePath) {
        ArrayList<String> input = new ArrayList<>();
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) { input.add(read.trim()); }
        } catch (IOException ex) { Logger.getLogger(Day8.class.getName()).log(Level.SEVERE, null, ex); }
        return input;
    }

    private static void displayResults(ArrayList<String> input) {
        input.stream().forEach((output) -> { System.out.println(output); });
        System.out.println(Day8Part2.totalCharacterCount);
        System.out.println(Day8Part2.hexTotal);
        System.out.println(Day8Part2.quotesTotal);
        System.out.println(Day8Part2.backslashesTotal);
        System.out.println(Day8Part2.remainingCharactersTotal);        
        int total = (Day8Part2.totalCharacterCount + Day8Part2.backslashesTotal + Day8Part2.hexTotal + Day8Part2.quotesTotal) - Day8Part2.totalCharacterCount;
        System.out.println("Total: " + total);
    }

    private static ArrayList<String> removeQuotes(ArrayList<String> input) {
        ArrayList<String> newInput = new ArrayList<>();
        input.stream().forEach((line) -> { newInput.add(line.substring(1, line.length()-1)); });
        return newInput;
    }

    private static void countTotalCharacters(ArrayList<String> input) {
        int total = 0;
        for(String line : input) { total = total + line.length(); }
        Day8Part2.totalCharacterCount = total;
    }

    private static ArrayList<String> countHex(ArrayList<String> input) {
        ArrayList<String> newInput = new ArrayList<>();
        int total = 0;
        Pattern pattern = Pattern.compile("\\\\x[0-9a-f]{2}");
        Matcher matcher;
        for(String line : input) {
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                total = ++total;
                line = line.replaceFirst("\\\\x[0-9a-f]{2}", "");
            }
            newInput.add(line);
        }
        Day8Part2.hexTotal = total;
        return newInput;
    }

    private static ArrayList<String> countQuotes(ArrayList<String> input) {
        ArrayList<String> newInput = new ArrayList<>();
        int total = 0;
        Pattern pattern = Pattern.compile("\\\\\"");
        Matcher matcher;
        for (String line : input) {
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                total = total + 2;
                line = line.replaceFirst("\\\\\"", "");
            }
            total = total + 4;
            newInput.add(line);
        }
        Day8Part2.quotesTotal = total;
        return newInput;
    }

    private static ArrayList<String> countBackslashes(ArrayList<String> input) {
        ArrayList<String> newInput = new ArrayList<>();
        int total = 0;
        Pattern pattern = Pattern.compile("\\\\{2}");
        Matcher matcher;
        for (String line : input) {
            matcher = pattern.matcher(line);
            while (matcher.find()) {
                total = total + 2;
                line = line.replaceFirst("\\\\{2}", "");
            }
            newInput.add(line);
        }
        Day8Part2.backslashesTotal = total;
        return newInput;
    }

    private static void countRemainingCharacters(ArrayList<String> input) {
        int total = 0;
        for (String line : input) { total = total + line.length(); }
        Day8Part2.remainingCharactersTotal = total;
    }
}