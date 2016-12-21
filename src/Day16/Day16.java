//--- Day 16: Aunt Sue ---
//
//Your Aunt Sue has given you a wonderful gift, and you'd like to send her a thank you card. However, there's a small problem: she signed it "From, Aunt Sue".
//
//You have 500 Aunts named "Sue".
//
//So, to avoid sending the card to the wrong person, you need to figure out which Aunt Sue (which you conveniently number 1 to 500, for sanity) gave you the gift. You open the present and, as luck would have it, good ol' Aunt Sue got you a My First Crime Scene Analysis Machine! Just what you wanted. Or needed, as the case may be.
//
//The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few specific compounds in a given sample, as well as how many distinct kinds of those compounds there are. According to the instructions, these are what the MFCSAM can detect:
//
//children, by human DNA age analysis.
//cats. It doesn't differentiate individual breeds.
//Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.
//goldfish. No other kinds of fish.
//trees, all in one group.
//cars, presumably by exhaust or gasoline or something.
//perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
//In fact, many of your Aunts Sue have many of these. You put the wrapping from the gift into the MFCSAM. It beeps inquisitively at you a few times and then prints out a message on ticker tape:
//
//children: 3
//cats: 7
//samoyeds: 2
//pomeranians: 3
//akitas: 0
//vizslas: 0
//goldfish: 5
//trees: 3
//cars: 2
//perfumes: 1
//You make a list of the things you can remember about each Aunt Sue. Things missing from your list aren't zero - you simply don't remember the value.
//
//What is the number of the Sue that got you the gift?
//
//Your puzzle answer was 373.
//
//--- Part Two ---
//
//As you're about to send the thank you note, something in the MFCSAM's instructions catches your eye. Apparently, it has an outdated retroencabulator, and so the output from the machine isn't exact values - some of them indicate ranges.
//
//In particular, the cats and trees readings indicates that there are greater than that many (due to the unpredictable nuclear decay of cat dander and tree pollen), while the pomeranians and goldfish readings indicate that there are fewer than that many (due to the modial interaction of magnetoreluctance).
//
//What is the number of the real Aunt Sue?
//
//Your puzzle answer was 260.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day16;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

public class Day16 {
    private final static String filePath = "day16.txt";
    private final static ArrayList<String> input = new ArrayList<>();
    private final static ArrayList<String[]> data = new ArrayList<>();
    private static int finalSue = 0;
    private static int sueScore = 0;
    
    private enum evidence {
        children(3),
        cats(7),
        samoyeds(2),
        pomeranians(3),
        akitas(0),
        vizslas(0),
        goldfish(5),
        trees(3),
        cars(2),
        perfumes(1);
        
        private final int quantity;
        
        evidence(int quantity) {
            this.quantity = quantity;
        }
        
        public int getQuantity() {
            return this.quantity;
        }
    
    }
    
    public static void main(String[] args) {
        Day16.readFile(Day16.filePath);
        Day16.compileData();
        
        // Part 1
        Day16.findSue();
        System.out.println("Part 1 Correct Sue #: " + Day16.finalSue);
        
        // Part 2
        Day16.finalSue = 0;
        Day16.sueScore = 0;
        Day16.findCorrectSue();
        System.out.println("Part 2 Correct Sue #: " + Day16.finalSue);
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.readLine()) != null) Day16.input.add(read.trim()); } catch (IOException ex) { Logger.getLogger(Day16.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void compileData() {
        for (String line : Day16.input) {
            line = line.replaceFirst("Sue \\d+:\\s", "");
            line = line.replaceAll(":", "");
            Day16.data.add(line.split(", "));
        }
    }

    private static void findSue() {
        for (int i = 0; i < Day16.data.size(); i++) {
            String[] currentSue = Day16.data.get(i);
            int sueTotalScore = 0;
            for (String currentEvidence : currentSue) {
                String[] sueEvidence = currentEvidence.split(" ");
                if (sueEvidence[0].equals("children") && evidence.children.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("cats") && evidence.cats.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("samoyeds") && evidence.samoyeds.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("pomeranians") && evidence.pomeranians.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("akitas") && evidence.akitas.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("vizslas") && evidence.vizslas.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("goldfish") && evidence.goldfish.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("trees") && evidence.trees.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("cars") && evidence.cars.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("perfumes") && evidence.perfumes.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
            }
            if (sueTotalScore > Day16.sueScore) {
                Day16.sueScore = sueTotalScore;
                Day16.finalSue = i+1;
            }
        }
    }
    
    private static void findCorrectSue() {
        for (int i = 0; i < Day16.data.size(); i++) {
            String[] currentSue = Day16.data.get(i);
            int sueTotalScore = 0;
            for (String currentEvidence : currentSue) {
                String[] sueEvidence = currentEvidence.split(" ");
                if (sueEvidence[0].equals("children") && evidence.children.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("cats") && evidence.cats.getQuantity() < Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("samoyeds") && evidence.samoyeds.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("pomeranians") && evidence.pomeranians.getQuantity() > Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("akitas") && evidence.akitas.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("vizslas") && evidence.vizslas.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("goldfish") && evidence.goldfish.getQuantity() > Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("trees") && evidence.trees.getQuantity() < Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("cars") && evidence.cars.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
                if (sueEvidence[0].equals("perfumes") && evidence.perfumes.getQuantity() == Integer.parseInt(sueEvidence[1])) { sueTotalScore = ++sueTotalScore; }
            }
            if (sueTotalScore > Day16.sueScore) {
                Day16.sueScore = sueTotalScore;
                Day16.finalSue = i+1;
            }
        }
    }
}