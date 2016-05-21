//--- Day 14: Reindeer Olympics ---
//
//This year is the Reindeer Olympics! Reindeer can fly at high speeds, but must rest occasionally to recover their energy. Santa would like to know which of his reindeer is fastest, and so he has them race.
//
//Reindeer can only either be flying (always at their top speed) or resting (not moving at all), and always spend whole seconds in either state.
//
//For example, suppose you have the following Reindeer:
//
//Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
//Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
//After one second, Comet has gone 14 km, while Dancer has gone 16 km. After ten seconds, Comet has gone 140 km, while Dancer has gone 160 km. On the eleventh second, Comet begins resting (staying at 140 km), and Dancer continues on for a total distance of 176 km. On the 12th second, both reindeer are resting. They continue to rest until the 138th second, when Comet flies for another ten seconds. On the 174th second, Dancer flies for another 11 seconds.
//
//In this example, after the 1000th second, both reindeer are resting, and Comet is in the lead at 1120 km (poor Dancer has only gotten 1056 km by that point). So, in this situation, Comet would win (if the race ended at 1000 seconds).
//
//Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the winning reindeer traveled?
//
//Your puzzle answer was 2660.
//
//--- Part Two ---
//
//Seeing how reindeer move in bursts, Santa decides he's not pleased with the old scoring system.
//
//Instead, at the end of each second, he awards one point to the reindeer currently in the lead. (If there are multiple reindeer tied for the lead, they each get one point.) He keeps the traditional 2503 second time limit, of course, as doing otherwise would be entirely ridiculous.
//
//Given the example reindeer from above, after the first second, Dancer is in the lead and gets one point. He stays in the lead until several seconds into Comet's second burst: after the 140th second, Comet pulls into the lead and gets his first point. Of course, since Dancer had been in the lead for the 139 seconds before that, he has accumulated 139 points by the 140th second.
//
//After the 1000th second, Dancer has accumulated 689 points, while poor Comet, our old champion, only has 312. So, with the new scoring system, Dancer would win (if the race ended at 1000 seconds).
//
//Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, how many points does the winning reindeer have?
//
//Your puzzle answer was 1256.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day14;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

public class Day14 {
    private final static String filePath = "C:\\Users\\co075oh\\Documents\\NetBeansProjects\\byui.allthingsnetwork.org\\AdventOfCode\\day14.txt";
    private static final ArrayList<String> input = new ArrayList<>();
    private final static int raceTime = 2503;
    private static Integer[][] reindeerDistance;
    private static int[] reindeerPoints;
    private static int longestDistance = 0;
    private static int mostPoints = 0;
    
    public static void main(String[] args) {
        Day14.readFile(filePath);
        Day14.reindeerDistance = new Integer[Day14.input.size()][Day14.raceTime];
        Day14.reindeerPoints = new int[Day14.input.size()];
        
        // Part 1
        Day14.calculateDistance();
        System.out.println("Longest Distance: " + Day14.longestDistance);
        
        // Part 2
        Day14.awardPoints();
        System.out.println("Most Points: " + Day14.mostPoints);
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.readLine()) != null) Day14.input.add(read.trim()); } catch (IOException ex) { Logger.getLogger(Day14.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void calculateDistance() {
        int r = 0;
        for (String eachReindeer : Day14.input) {
            String[] tempReindeer = eachReindeer.split(" ");
            int raceSpeed = Integer.parseInt(tempReindeer[3]);
            int raceLength = Integer.parseInt(tempReindeer[6]);
            int restLength = Integer.parseInt(tempReindeer[13]);
            int i = 0;
            while (i < Day14.raceTime) {
                for (int race = 0; race < raceLength; race++) {
                    if (i == Day14.raceTime) { break; }
                    if (i == 0) { Day14.reindeerDistance[r][i] = raceSpeed; } else { Day14.reindeerDistance[r][i] = Day14.reindeerDistance[r][i-1] + raceSpeed; }
                    i = ++i;
                }
                for (int rest = 0; rest < restLength; rest++) {
                    if (i == Day14.raceTime) { break; }
                    Day14.reindeerDistance[r][i] = Day14.reindeerDistance[r][i-1];
                    i = ++i;
                }
            }
            if (Day14.reindeerDistance[r][i-1] > Day14.longestDistance) { Day14.longestDistance = Day14.reindeerDistance[r][i-1]; }
            r = ++r;
        }
    }

    private static void awardPoints() {
        for (int i = 0; i < Day14.raceTime; i++) {
            int currentLongestDistance = 0;
            for (int j = 0; j < Day14.input.size(); j++) {
                if (Day14.reindeerDistance[j][i] > currentLongestDistance) {
                    currentLongestDistance = Day14.reindeerDistance[j][i];
                }
            }
            for (int j = 0; j < Day14.input.size(); j++) {
                if (Day14.reindeerDistance[j][i] == currentLongestDistance) {
                    Day14.reindeerPoints[j] = Day14.reindeerPoints[j] + 1;
                }
            }
        }
        for (int i = 0; i < Day14.input.size(); i++) {
            if (Day14.reindeerPoints[i] > Day14.mostPoints) {
                Day14.mostPoints = Day14.reindeerPoints[i];
            }
        }
    }
}