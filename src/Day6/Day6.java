//--- Day 6: Probably a Fire Hazard ---
//
//Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy one million lights in a 1000x1000 grid.
//
//Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display the ideal lighting configuration.
//
//Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0. The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs. Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore refers to 9 lights in a 3x3 square. The lights all start turned off.
//
//To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
//
//For example:
//
//turn on 0,0 through 999,999 would turn on (or leave on) every light.
//toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and turning on the ones that were off.
//turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
//After following the instructions, how many lights are lit?
//
//Your puzzle answer was 377891.
//
//--- Part Two ---
//
//You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from Ancient Nordic Elvish.
//
//The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. The lights all start at zero.
//
//The phrase turn on actually means that you should increase the brightness of those lights by 1.
//
//The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.
//
//The phrase toggle actually means that you should increase the brightness of those lights by 2.
//
//What is the total brightness of all lights combined after following Santa's instructions?
//
//For example:
//
//turn on 0,0 through 0,0 would increase the total brightness by 1.
//toggle 0,0 through 999,999 would increase the total brightness by 2000000.
//Your puzzle answer was 14110788.
//
//Both parts of this puzzle are complete! They provide two gold stars: **

package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day6 {

    public static void main(String[] args) {
        String filePath = "day6.txt";
        String input, action, startCoordinateString, trash, endCoordinateString;
        String[] startCoordinateStringArray, endCoordinateStringArray;
        int[] startCoordinate = new int[2];
        int[] endCoordinate = new int[2];
        int total = 0;
        int[][] map = new int[1000][1000];
        Scanner scan;
        
        for (int[] columns : map) { Arrays.fill(columns, 0); }
        
        // Part 1
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((input = in.readLine()) != null) {
                input = input.replaceAll("\\s", "");
                scan = new Scanner(input);
                action = scan.findInLine("[a-zA-Z]+");
                startCoordinateString = scan.findInLine("\\d+,\\d+");
                startCoordinateStringArray = startCoordinateString.split(",");
                startCoordinate[0] = Integer.parseInt(startCoordinateStringArray[0]);
                startCoordinate[1] = Integer.parseInt(startCoordinateStringArray[1]);
                trash = scan.findInLine("[a-zA-Z]+");
                endCoordinateString = scan.findInLine("\\d+,\\d+");
                endCoordinateStringArray = endCoordinateString.split(",");
                endCoordinate[0] = Integer.parseInt(endCoordinateStringArray[0]);
                endCoordinate[1] = Integer.parseInt(endCoordinateStringArray[1]);
                for (int i = startCoordinate[0]; i <= endCoordinate[0]; i++) {
                    for (int j = startCoordinate[1]; j <= endCoordinate[1]; j++) {
                        switch(action) {
                            case "turnon":
                                map[i][j] = 1;
                                break;
                            case "turnoff":
                                map[i][j] = 0;
                                break;
                            default:
                                if (map[i][j] == 1) {
                                    map[i][j] = 0;
                                } else {
                                    map[i][j] = 1;
                                }
                                break;
                        }
                    }
                }
            }
            
            for (int[] column : map) { for (int location : column) { if(location == 1) { total = ++total; } } }
            
            System.out.println("Total: " + total);
        } catch (IOException ex) { Logger.getLogger(Day6.class.getName()).log(Level.SEVERE, null, ex); }
        
        
        // Part 2
        for (int[] columns : map) { Arrays.fill(columns, 0); }
        total = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((input = in.readLine()) != null) {
                input = input.replaceAll("\\s", "");
                scan = new Scanner(input);
                action = scan.findInLine("[a-zA-Z]+");
                startCoordinateString = scan.findInLine("\\d+,\\d+");
                startCoordinateStringArray = startCoordinateString.split(",");
                startCoordinate[0] = Integer.parseInt(startCoordinateStringArray[0]);
                startCoordinate[1] = Integer.parseInt(startCoordinateStringArray[1]);
                trash = scan.findInLine("[a-zA-Z]+");
                endCoordinateString = scan.findInLine("\\d+,\\d+");
                endCoordinateStringArray = endCoordinateString.split(",");
                endCoordinate[0] = Integer.parseInt(endCoordinateStringArray[0]);
                endCoordinate[1] = Integer.parseInt(endCoordinateStringArray[1]);
                for (int i = startCoordinate[0]; i <= endCoordinate[0]; i++) {
                    for (int j = startCoordinate[1]; j <= endCoordinate[1]; j++) {
                        switch(action) {
                            case "turnon":
                                map[i][j] = ++map[i][j];
                                break;
                            case "turnoff":
                                if (map[i][j] != 0) {
                                    map[i][j] = --map[i][j];
                                }
                                break;
                            default:
                                map[i][j] = map[i][j] + 2;
                                break;
                        }
                    }
                }
            }
            
            for (int[] column : map) { for (int location : column) { total = total + location; } }
            
            System.out.println("Total: " + total);
        } catch (IOException ex) { Logger.getLogger(Day6.class.getName()).log(Level.SEVERE, null, ex); }
    }
}