//--- Day 9: All in a Single Night ---
//
//Every year, Santa manages to deliver all of his presents in a single night.
//
//This year, however, he has some new locations to visit; his elves have provided him the distances between every pair of locations. He can start and end at any two (different) locations he wants, but he must visit each location exactly once. What is the shortest distance he can travel to achieve this?
//
//For example, given the following distances:
//
//London to Dublin = 464
//London to Belfast = 518
//Dublin to Belfast = 141
//The possible routes are therefore:
//
//Dublin -> London -> Belfast = 982
//London -> Dublin -> Belfast = 605
//London -> Belfast -> Dublin = 659
//Dublin -> Belfast -> London = 659
//Belfast -> Dublin -> London = 605
//Belfast -> London -> Dublin = 982
//The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.
//
//What is the distance of the shortest route?
//
//Your puzzle answer was 207.
//
//--- Part Two ---
//
//The next year, just to show off, Santa decides to take the route with the longest distance instead.
//
//He can still start and end at any two (different) locations he wants, and he still must visit each location exactly once.
//
//For example, given the distances above, the longest route would be 982 via (for example) Dublin -> London -> Belfast.
//
//What is the distance of the longest route?
//
//Your puzzle answer was 804.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day9;

import com.google.common.collect.Collections2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day9 {
    private final static String filePath = "C:\\Users\\co075oh\\Documents\\NetBeansProjects\\byui.allthingsnetwork.org\\AdventOfCode\\day9.txt";
    private final static ArrayList<String> input = new ArrayList<>();
    private final static HashSet<String> cities = new HashSet<>();
    private final static HashSet<String[]> distances = new HashSet<>();
    private static double longest = 0.0, shortest = 100000.0;

    public static void main(String[] args) {
        
        // Part 1 & 2
        Day9.readFile(Day9.filePath);
        Day9.getCities(Day9.input);
        Collection<List<String>> cityPermutations = Collections2.permutations(cities);
        cityPermutations.stream().forEach((permutation) -> { Day9.calculateDistance(permutation); });
        System.out.println("Shortest Distance: " + shortest);
        System.out.println("Longest Distance: " + longest);
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((read = in.readLine()) != null) Day9.input.add(read.trim());
        } catch (IOException ex) { Logger.getLogger(Day9.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void getCities(ArrayList<String> input) {
        for (String directions : input) {
            Day9.cities.addAll(Arrays.asList((directions.split(" = "))[0].split(" to ")));
            Day9.distances.add(directions.split("\\sto\\s|\\s=\\s"));
        }
    }

    private static void calculateDistance(List<String> permutation) {
        double totalDistance = 0.0;
        for (int i = 0; i < permutation.size()-1; i++) {
            for (String[] checkDistance : Day9.distances) {
                if ((permutation.get(i).equals(checkDistance[0]) & permutation.get(i+1).equals(checkDistance[1])) || (permutation.get(i).equals(checkDistance[1]) & permutation.get(i+1).equals(checkDistance[0]))) {
                    totalDistance = totalDistance + Double.parseDouble(checkDistance[2]);
                }
            }
        }
        if (totalDistance < Day9.shortest) Day9.shortest = totalDistance;
        if (totalDistance > Day9.longest) Day9.longest = totalDistance;
    }
}