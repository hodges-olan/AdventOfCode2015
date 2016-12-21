//--- Day 13: Knights of the Dinner Table ---
//
//In years past, the holiday feast with your family hasn't gone so well. Not everyone gets along! This year, you resolve, will be different. You're going to find the optimal seating arrangement and avoid all those awkward conversations.
//
//You start by writing up a list of everyone invited and the amount their happiness would increase or decrease if they were to find themselves sitting next to each other person. You have a circular table that will be just big enough to fit everyone comfortably, and so each person will have exactly two neighbors.
//
//For example, suppose you have only four attendees planned, and you calculate their potential happiness as follows:
//
//Alice would gain 54 happiness units by sitting next to Bob.
//Alice would lose 79 happiness units by sitting next to Carol.
//Alice would lose 2 happiness units by sitting next to David.
//Bob would gain 83 happiness units by sitting next to Alice.
//Bob would lose 7 happiness units by sitting next to Carol.
//Bob would lose 63 happiness units by sitting next to David.
//Carol would lose 62 happiness units by sitting next to Alice.
//Carol would gain 60 happiness units by sitting next to Bob.
//Carol would gain 55 happiness units by sitting next to David.
//David would gain 46 happiness units by sitting next to Alice.
//David would lose 7 happiness units by sitting next to Bob.
//David would gain 41 happiness units by sitting next to Carol.
//Then, if you seat Alice next to David, Alice would lose 2 happiness units (because David talks so much), but David would gain 46 happiness units (because Alice is such a good listener), for a total change of 44.
//
//If you continue around the table, you could then seat Bob next to Alice (Bob gains 83, Alice gains 54). Finally, seat Carol, who sits next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41). The arrangement looks like this:
//
//     +41 +46
//+55   David    -2
//Carol       Alice
//+60    Bob    +54
//     -7  +83
//After trying every other seating arrangement in this hypothetical scenario, you find that this one is the most optimal, with a total change in happiness of 330.
//
//What is the total change in happiness for the optimal seating arrangement of the actual guest list?
//
//Your puzzle answer was 664.
//
//--- Part Two ---
//
//In all the commotion, you realize that you forgot to seat yourself. At this point, you're pretty apathetic toward the whole thing, and your happiness wouldn't really go up or down regardless of who you sit next to. You assume everyone else would be just as ambivalent about sitting next to you, too.
//
//So, add yourself to the list, and give all happiness relationships that involve you a score of 0.
//
//What is the total change in happiness for the optimal seating arrangement that actually includes yourself?
//
//Your puzzle answer was 640.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day13;

import com.google.common.collect.Collections2;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Day13 {
    private final static String filePath = "day13.txt";
    private final static ArrayList<String> input = new ArrayList<>();
    private final static HashSet<String> people = new HashSet<>();
    private final static HashSet<String[]> happinessChart = new HashSet<>();
    private static double happiness = 0.0;

    public static void main(String[] args) {
        Day13.readFile(Day13.filePath);
        Day13.getPeople(Day13.input);
        
        // Part 1
        Collection<List<String>> peoplePermutations = Collections2.permutations(Day13.people);
        peoplePermutations.stream().forEach((permutation) -> { Day13.calculateHappiness(permutation); });
        System.out.println("Part 1 Best Happiness: " + Day13.happiness);
        
        // Part 2
        Day13.people.add("Myself");
        Day13.happiness = 0.0;
        peoplePermutations = Collections2.permutations(Day13.people);
        peoplePermutations.stream().forEach((permutation) -> { Day13.calculateHappiness(permutation); });
        System.out.println("Part 2 Best Happiness: " + Day13.happiness);
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.readLine()) != null) Day13.input.add(read.trim()); } catch (IOException ex) { Logger.getLogger(Day13.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void getPeople(ArrayList<String> input) {
        for (String line : input) {
            line = line.replaceAll("\\.", "");
            Day13.people.add(line.split(" ")[0]);
            Day13.happinessChart.add(line.split(" "));
        }
    }

    private static void calculateHappiness(List<String> permutation) {
        String firstPerson, secondPerson, thirdPerson;
        double tempDouble, tempHappiness = 0.0;
        for (int i = 0; i < permutation.size(); i++) {
            secondPerson = permutation.get(i);
            if (i == 0) {
                firstPerson = permutation.get(permutation.size()-1);
                thirdPerson = permutation.get(i+1);
            } else if (i == permutation.size()-1) {
                firstPerson = permutation.get(i-1);
                thirdPerson = permutation.get(0);
            } else {
                firstPerson = permutation.get(i-1);
                thirdPerson = permutation.get(i+1);
            }
            for (String[] happyChart : Day13.happinessChart) {
                if (happyChart[0].equals(secondPerson) & (happyChart[10].equals(firstPerson) || happyChart[10].equals(thirdPerson))) {
                    tempDouble = Double.parseDouble(happyChart[3]);
                    if (happyChart[2].equals("lose")) { tempDouble *= -1; }
                    tempHappiness = tempHappiness + tempDouble;
                }
            }
        }
        if (tempHappiness > Day13.happiness) { Day13.happiness = tempHappiness; }
    }
}