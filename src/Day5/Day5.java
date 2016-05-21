//--- Day 5: Doesn't He Have Intern-Elves For This? ---
//
//Santa needs help figuring out which strings in his text file are naughty or nice.
//
//A nice string is one with all of the following properties:
//
//It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
//It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
//It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
//For example:
//
//ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
//aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
//jchzalrnumimnmhp is naughty because it has no double letter.
//haegwjzuvuyypxyu is naughty because it contains the string xy.
//dvszwmarrgswjxmb is naughty because it contains only one vowel.
//How many strings are nice?
//
//Your puzzle answer was 236.
//
//--- Part Two ---
//
//Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.
//
//Now, a nice string is one with all of the following properties:
//
//It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
//It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.
//For example:
//
//qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
//xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
//uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
//ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
//How many strings are nice under these new rules?
//
//Your puzzle answer was 51.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day5 {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\co075oh\\Documents\\NetBeansProjects\\byui.allthingsnetwork.org\\AdventOfCode\\day5.txt";
        String input, compareOne, compareTwo;
        int vowelCount, stringCount = 0, total = 0;
        boolean invalid, consecutive, repeatingChars;
        
        // Part 1
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((input = in.readLine()) != null) {
                vowelCount = 0;
                invalid = consecutive = false;

                if (input.contains("ab") || input.contains("cd") || input.contains("pq") || input.contains("xy")) { invalid = true; }
                for (int i=0; i < input.length(); i++) {
                    if (i != (input.length() - 1)) { if (input.charAt(i) == input.charAt(i+1)) { consecutive = true; } }
                    if (input.charAt(i) == 'a' || input.charAt(i) == 'e' || input.charAt(i) == 'i' || input.charAt(i) == 'o' || input.charAt(i) == 'u') { vowelCount = ++vowelCount; }
                }
                if (vowelCount > 2 & !invalid & consecutive) { stringCount = ++stringCount; }
                total = ++total;
            }
            
            System.out.println("Total Nice Strings: " + stringCount);
            System.out.println("Total Strings: " + total);
        } catch (IOException ex) { Logger.getLogger(Day5.class.getName()).log(Level.SEVERE, null, ex); }
        
        
        // Part 2
        stringCount = total = 0;       
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((input = in.readLine()) != null) {
                repeatingChars = consecutive = false;
                for (int i=0; i < (input.length() - 2); i++) { if (input.charAt(i) == input.charAt(i+2)) { repeatingChars = true; } }
                for (int i=0; i < (input.length() - 3); i++) {
                    compareOne = input.substring(i,i+2);
                    for (int j=(i+2); j < (input.length() - 1); j++) {
                        compareTwo = input.substring(j,j+2);
                        if (compareOne.equals(compareTwo)) { consecutive = true; }
                    }
                }
                
                if (repeatingChars & consecutive) { stringCount = ++stringCount; }
                total = ++total;
            }
            
            System.out.println("Total Nice Strings: " + stringCount);
            System.out.println("Total Strings: " + total);
        } catch (IOException ex) { Logger.getLogger(Day5.class.getName()).log(Level.SEVERE, null, ex); }
    }   
}