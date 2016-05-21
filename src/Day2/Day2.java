//--- Day 2: I Was Told There Would Be No Math ---
//
//The elves are running low on wrapping paper, and so they need to submit an order for more. They have a list of the dimensions (length l, width w, and height h) of each present, and only want to order exactly as much as they need.
//
//Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required wrapping paper for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. The elves also need a little extra paper for each present: the area of the smallest side.
//
//For example:
//
//A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, for a total of 58 square feet.
//A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, for a total of 43 square feet.
//All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
//
//Your puzzle answer was 1586300.
//
//--- Part Two ---
//
//The elves are also running low on ribbon. Ribbon is all the same width, so they only have to worry about the length they need to order, which they would again like to be exact.
//
//The ribbon required to wrap a present is the shortest distance around its sides, or the smallest perimeter of any one face. Each present also requires a bow made out of ribbon as well; the feet of ribbon required for the perfect bow is equal to the cubic feet of volume of the present. Don't ask how they tie the bow, though; they'll never tell.
//
//For example:
//
//A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, for a total of 34 feet.
//A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, for a total of 14 feet.
//How many total feet of ribbon should they order?
//
//Your puzzle answer was 3737498.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day2 {

    public static void main(String[] args) {
        
        String input;
        String filePath = "C:\\Users\\co075oh\\Documents\\NetBeansProjects\\byui.allthingsnetwork.org\\AdventOfCode\\day2.txt";
        String[] dimensions;
        int[] intDimensions = new int[3];
        PrintWriter outFile = new PrintWriter(System.out, true);
        double extraPaper = 0.0, paper = 0.0, ribbon = 0.0;
        
        // Part 1
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((input = in.readLine()) != null) {
                dimensions = input.split("x");
                for (int i=0; i < 3; i++) {
                    intDimensions[i] = Integer.parseInt(dimensions[i]);
                }
                Arrays.sort(intDimensions);
                extraPaper = extraPaper + (intDimensions[0] * intDimensions[1]);
                paper = paper + ((intDimensions[0]*intDimensions[1]*2) + (intDimensions[1]*intDimensions[2]*2) + (intDimensions[2]*intDimensions[0]*2));
            }
            
            outFile.println("Extra: " + extraPaper);
            outFile.println("Paper: " + paper);
            outFile.println("Total: " + (extraPaper + paper));
        } catch (IOException ex) { Logger.getLogger(Day2.class.getName()).log(Level.SEVERE, null, ex); }
        
        // Part 2
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) {
            while((input = in.readLine()) != null) {
                dimensions = input.split("x");
                for (int i=0; i < 3; i++) {
                    intDimensions[i] = Integer.parseInt(dimensions[i]);
                }
                Arrays.sort(intDimensions);
                ribbon = ribbon + ((intDimensions[0]*2) + (intDimensions[1]*2));
                ribbon = ribbon + (intDimensions[0] * intDimensions[1]* intDimensions[2]);
            }
            
            outFile.println("Ribbon: " + ribbon);
        } catch (IOException ex) { Logger.getLogger(Day2.class.getName()).log(Level.SEVERE, null, ex); }
    }
}