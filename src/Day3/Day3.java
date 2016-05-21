//--- Day 3: Perfectly Spherical Houses in a Vacuum ---
//
//Santa is delivering presents to an infinite two-dimensional grid of houses.
//
//He begins by delivering a present to the house at his starting location, and then an elf at the North Pole calls him via radio and tells him where to move next. Moves are always exactly one house to the north (^), south (v), east (>), or west (<). After each move, he delivers another present to the house at his new location.
//
//However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, and Santa ends up visiting some houses more than once. How many houses receive at least one present?
//
//For example:
//
//> delivers presents to 2 houses: one at the starting location, and one to the east.
//^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
//^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
//Your puzzle answer was 2565.
//
//--- Part Two ---
//
//The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.
//
//Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.
//
//This year, how many houses receive at least one present?
//
//For example:
//
//^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
//^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
//^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
//Your puzzle answer was 2639.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day3 {

    public static void main(String[] args) {
        String filePath = "day3.txt";
        int[][] houses = new int[10000][10000];
        int santaX = 5000, santaY = 5000, roboX = 5000, roboY = 5000, intInput, total = 0, totalCount = 0;
        char input;
        houses[santaX][santaY] = houses[santaX][santaY] + 2;
        
        // Part 1
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((intInput = in.read()) != -1) {
                input = (char) intInput;
                switch(input) {
                    case '^':
                        santaY = --santaY;
                        break;
                    case '>':
                        santaX = ++santaX;
                        break;
                    case 'v':
                        santaY = ++santaY;
                        break;
                    default:
                        santaX = --santaX;
                        break;
                }
                houses[santaX][santaY] = ++houses[santaX][santaY];
            }
            
            for (int[] houseRows : houses) {
                for (int house : houseRows) {
                    if (house > 0) {
                        total = ++total;
                    } 
                }
            }
            
            System.out.println("Total Houses: " + total);
        } catch (IOException ex) { Logger.getLogger(Day3.class.getName()).log(Level.SEVERE, null, ex); }
        
        // Part 2
        santaX = roboX;
        santaY = roboY;
        total = 0;
        for (int x = 0; x < 10000; x++) {
            for (int y = 0; y < 10000; y++) {
                houses[x][y] = 0;
            }
        }
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((intInput = in.read()) != -1) {
                input = (char) intInput;
                switch(input) {
                    case '^':
                        if (totalCount%2 == 0) {
                            santaY = --santaY;
                        } else {
                            roboY = --roboY;
                        }
                        break;
                    case '>':
                        if (totalCount%2 == 0) {
                            santaX = ++santaX;
                        } else {
                            roboX = ++roboX;
                        }
                        break;
                    case 'v':
                        if (totalCount%2 == 0) {
                            santaY = ++santaY;
                        } else {
                            roboY = ++roboY;
                        }
                        break;
                    default:
                        if (totalCount%2 == 0) {
                            santaX = --santaX;
                        } else {
                            roboX = --roboX;
                        }
                        break;
                }
                if (totalCount%2 == 0) {
                    houses[santaX][santaY] = ++houses[santaX][santaY];
                } else {
                    houses[roboX][roboY] = ++houses[roboX][roboY];
                }
                totalCount = ++totalCount;
            }
            
            for (int[] houseRows : houses) {
                for (int house : houseRows) {
                    if (house > 0) {
                        total = ++total;
                    } 
                }
            }
            
            System.out.println("Total Houses: " + total);
        } catch (IOException ex) { Logger.getLogger(Day3.class.getName()).log(Level.SEVERE, null, ex); }
    }
}