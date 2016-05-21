//--- Day 18: Like a GIF For Your Yard ---
//
//After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed. You arrange them in a 100x100 grid.
//
//Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few lights, he says, you'll have to resort to animation.
//
//Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a . means "off".
//
//Then, animate your grid in steps, where each step decides the next configuration based on the current one. Each light's next state (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals). Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".
//
//For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8, and the light marked B, which is on an edge, only has the neighbors marked 1 through 5:
//
//1B5...
//234...
//......
//..123.
//..8A4.
//..765.
//The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:
//
//A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
//A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
//All of the lights update simultaneously; they all consider the same current state before moving to the next.
//
//Here's a few steps from an example configuration of another 6x6 grid:
//
//Initial state:
//.#.#.#
//...##.
//#....#
//..#...
//#.#..#
//####..
//
//After 1 step:
//..##..
//..##.#
//...##.
//......
//#.....
//#.##..
//
//After 2 steps:
//..###.
//......
//..###.
//......
//.#....
//.#....
//
//After 3 steps:
//...#..
//......
//...#..
//..##..
//......
//......
//
//After 4 steps:
//......
//......
//..##..
//..##..
//......
//......
//After 4 steps, this example has four lights on.
//
//In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
//
//Your puzzle answer was 768.
//
//--- Part Two ---
//
//You flip the instructions over; Santa goes on to point out that this is all just an implementation of Conway's Game of Life. At least, it was, until you notice that something's wrong with the grid of lights you bought: four lights, one in each corner, are stuck on and can't be turned off. The example above will actually run like this:
//
//Initial state:
//##.#.#
//...##.
//#....#
//..#...
//#.#..#
//####.#
//
//After 1 step:
//#.##.#
//####.#
//...##.
//......
//#...#.
//#.####
//
//After 2 steps:
//#..#.#
//#....#
//.#.##.
//...##.
//.#..##
//##.###
//
//After 3 steps:
//#...##
//####.#
//..##.#
//......
//##....
//####.#
//
//After 4 steps:
//#.####
//#....#
//...#..
//.##...
//#.....
//#.#..#
//
//After 5 steps:
//##.###
//.##..#
//.##...
//.##...
//#.#...
//##...#
//After 5 steps, this example now has 17 lights on.
//
//In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the on state, how many lights are on after 100 steps?
//
//Your puzzle answer was 781.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day18;

import java.io.*;
import java.util.*;
import java.util.logging.*;

public class Day18 {
    private final static String filePath = "day18.txt";
    private final static ArrayList<String> input = new ArrayList<>();
    private final static int row = 100, column = 100;
    private final static int[][] lights = new int[Day18.row][Day18.column];
    private final static int steps = 100;
    
    public static void main(String args[]) {
        Day18.readFile(Day18.filePath);
        
        // Part 1
        Day18.buildLights();
        System.out.println("Part 1");
        for (int i = 0; i < Day18.steps; i++) { Day18.changeLights(false); }
        Day18.countLights();
        
        // Part 2
        Day18.buildLights();
        System.out.println("Part 2");
        for (int i = 0; i < Day18.steps; i++) { Day18.changeLights(true); }
        Day18.countLights();
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.readLine()) != null) Day18.input.add(read.trim()); } catch (IOException ex) { Logger.getLogger(Day18.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void buildLights() {
        char[] tempChars;
        for (int i = 0; i < Day18.input.size(); i++) {
            String line = Day18.input.get(i);
            tempChars = line.toCharArray();
            for (int j = 0; j < tempChars.length; j++) {
                char tempChar = tempChars[j];
                if (tempChar == '#') { lights[i][j] = 1; } else { lights[i][j] = 0;
                }
            }
        }
    }

    private static void changeLights(boolean part) {
        int[][] tempLights = new int[Day18.row][Day18.column];
        int total;
        for (int x = 0; x < Day18.lights.length; x++) {
            for (int y = 0; y < Day18.lights[x].length; y++) {
                // LEFT COLUMN EXCLUDING CORNERS
                if (x == 0 && (y != 0 && y != (Day18.lights[x].length-1))) { total = Day18.lights[x][y-1] + Day18.lights[x+1][y-1] + Day18.lights[x+1][y] + Day18.lights[x+1][y+1] + Day18.lights[x][y+1]; }
                // TOP ROW EXCLUDING CORNERS
                else if (y == 0 && (x != 0 && x != (Day18.lights.length-1))) { total = Day18.lights[x-1][y] + Day18.lights[x-1][y+1] + Day18.lights[x][y+1] + Day18.lights[x+1][y+1] + Day18.lights[x+1][y]; }
                // RIGHT COLUMN EXCLUDING CORNERS
                else if (x == (Day18.lights.length-1) && (y != 0 && (y != (Day18.lights[x].length-1)))) { total = Day18.lights[x][y-1] + Day18.lights[x-1][y-1] + Day18.lights[x-1][y] + Day18.lights[x-1][y+1] + Day18.lights[x][y+1]; }
                // BOTTOM ROW EXCLUDING CORNERS    
                else if (y == (Day18.lights[x].length-1) && (x != 0 && (x != (Day18.lights.length-1)))) { total = Day18.lights[x-1][y] + Day18.lights[x-1][y-1] + Day18.lights[x][y-1] + Day18.lights[x+1][y-1] + Day18.lights[x+1][y]; }
                // TOP LEFT CORNER
                else if (x == 0 && y == 0) { total = Day18.lights[x+1][y] + Day18.lights[x+1][y+1] + Day18.lights[x][y+1]; }
                // TOP RIGHT CORNER
                else if ((x == Day18.lights.length-1) && y == 0) { total = Day18.lights[x-1][y] + Day18.lights[x-1][y+1] + Day18.lights[x][y+1]; }
                // BOTTOM RIGHT CORNER
                else if (x == (Day18.lights.length-1) && y == (Day18.lights[x].length-1)) { total = Day18.lights[x-1][y] + Day18.lights[x-1][y-1] + Day18.lights[x][y-1]; }
                // BOTTOM LEFT CORNER
                else if (x == 0 && y == (Day18.lights[x].length-1)) { total = Day18.lights[x][y-1] + Day18.lights[x+1][y-1] + Day18.lights[x+1][y]; }
                // INSIDE LIGHTS
                else { total = Day18.lights[x-1][y-1] + Day18.lights[x][y-1] + Day18.lights[x+1][y-1] + Day18.lights[x+1][y] + Day18.lights[x+1][y+1] + Day18.lights[x][y+1] + Day18.lights[x-1][y+1] + Day18.lights[x-1][y]; }
                if (part && ((x == 0 && y == 0) || ((x == Day18.lights.length-1) && y == 0) || (x == (Day18.lights.length-1) && y == (Day18.lights[x].length-1) || (x == 0 && y == (Day18.lights[x].length-1))))) { tempLights[x][y] = 1; } else { tempLights[x][y] = Day18.toggleLight(total, Day18.lights[x][y]); }
            }
        }
        for (int i = 0; i < Day18.row; i++) { System.arraycopy(tempLights[i], 0, Day18.lights[i], 0, Day18.column); }
    }

    private static void countLights() {
        int total = 0;
        for (int[] line : Day18.lights) { for (int light : line) { total = total + light; } }
        System.out.println("Total Lights On: " + total);
    }
    
    private static int toggleLight(int total, int tempLight) {
        if (total < 2 || total > 3) { tempLight = 0; } else if (total == 3) {  tempLight = 1; }
        return tempLight;
    }
}
