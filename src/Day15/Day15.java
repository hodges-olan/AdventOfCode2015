//--- Day 15: Science for Hungry People ---
//
//Today, you set out on the task of perfecting your milk-dunking cookie recipe. All you have to do is find the right balance of ingredients.
//
//Your recipe leaves room for exactly 100 teaspoons of ingredients. You make a list of the remaining ingredients you could use to finish the recipe (your puzzle input) and their properties per teaspoon:
//
//capacity (how well it helps the cookie absorb milk)
//durability (how well it keeps the cookie intact when full of milk)
//flavor (how tasty it makes the cookie)
//texture (how it improves the feel of the cookie)
//calories (how many calories it adds to the cookie)
//You can only measure ingredients in whole-teaspoon amounts accurately, and you have to be accurate so you can reproduce your results in the future. The total score of a cookie can be found by adding up each of the properties (negative totals become 0) and then multiplying together everything except calories.
//
//For instance, suppose you have these two ingredients:
//
//Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
//Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
//Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of cinnamon (because the amounts of each ingredient must add up to 100) would result in a cookie with the following properties:
//
//A capacity of 44*-1 + 56*2 = 68
//A durability of 44*-2 + 56*3 = 80
//A flavor of 44*6 + 56*-2 = 152
//A texture of 44*3 + 56*-1 = 76
//Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now) results in a total score of 62842880, which happens to be the best score possible given these ingredients. If any properties had produced a negative total, it would have instead become zero, causing the whole score to multiply to zero.
//
//Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make?
//
//Your puzzle answer was 21367368.
//
//--- Part Two ---
//
//Your cookie recipe becomes wildly popular! Someone asks if you can make another recipe that has exactly 500 calories per cookie (so they can use it as a meal replacement). Keep the rest of your award-winning process the same (100 teaspoons, same ingredients, same scoring system).
//
//For example, given the ingredients above, if you had instead selected 40 teaspoons of butterscotch and 60 teaspoons of cinnamon (which still adds to 100), the total calorie count would be 40*8 + 60*3 = 500. The total score would go down, though: only 57600000, the best you can do in such trying circumstances.
//
//Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make with a calorie total of 500?
//
//Your puzzle answer was 1766400.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day15;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.*;

public class Day15 {
    private final static String filePath = "C:\\Users\\co075oh\\Documents\\NetBeansProjects\\byui.allthingsnetwork.org\\AdventOfCode\\day15.txt";
    private final static ArrayList<String> input = new ArrayList<>();
    private static int[][] ingredients;
    private final static int teaspoons = 100;
    private static int bestRecipe = 0;
    private static int bestCalorieRecipe = 0;
    
    public static void main(String[] args) {
        Day15.readFile(Day15.filePath);
        Day15.calculateRecipe();
        System.out.println("Best Recipe Score: " + Day15.bestRecipe);
        System.out.println("Best Recipe at 500 Calories: " + Day15.bestCalorieRecipe);
    }
    
    private static void readFile(String filePath) {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(filePath))) { while((read = in.readLine()) != null) Day15.input.add(read.trim()); } catch (IOException ex) { Logger.getLogger(Day15.class.getName()).log(Level.SEVERE, null, ex); }
        ingredients = new int[Day15.input.size()][5];
        for (int i = 0; i < Day15.input.size(); i++) {
            String entry = Day15.input.get(i);
            entry = entry.replaceAll("[^-?0-9]+", " ");
            String[] tempEntry = entry.split(" ");
            for (int j = 0; j < (tempEntry.length-1); j++) {
                Day15.ingredients[i][j] = Integer.parseInt(tempEntry[j+1]);
            }
        }
    }

    private static void calculateRecipe() {
        int totalCapacity, totalDurability, totalFlavor, totalTexture, totalCalories, totalScore;
        for (int a = 0; a < Day15.teaspoons; a++) {
            for (int b = 0; b < (Day15.teaspoons-a); b++) {
                for (int c = 0; c < (Day15.teaspoons-a-b); c++) {
                    int d = 100-a-b-c;
                    totalCapacity = ((Day15.ingredients[0][0] * a) + (Day15.ingredients[1][0] * b) + (Day15.ingredients[2][0] * c) + (Day15.ingredients[3][0] * d));
                    totalDurability = ((Day15.ingredients[0][1] * a) + (Day15.ingredients[1][1] * b) + (Day15.ingredients[2][1] * c) + (Day15.ingredients[3][1] * d));
                    totalFlavor = ((Day15.ingredients[0][2] * a) + (Day15.ingredients[1][2] * b) + (Day15.ingredients[2][2] * c) + (Day15.ingredients[3][2] * d));
                    totalTexture = ((Day15.ingredients[0][3] * a) + (Day15.ingredients[1][3] * b) + (Day15.ingredients[2][3] * c) + (Day15.ingredients[3][3] * d));
                    totalCalories = ((Day15.ingredients[0][4] * a) + (Day15.ingredients[1][4] * b) + (Day15.ingredients[2][4] * c) + (Day15.ingredients[3][4] * d));
                    totalScore = totalCapacity * totalDurability * totalFlavor * totalTexture;
                    if (totalCapacity < 0 || totalDurability < 0 || totalFlavor < 0 || totalTexture < 0) { totalScore = 0; }
                    if (totalScore > Day15.bestRecipe) { 
                        Day15.bestRecipe = totalScore;
                    }
                    if (totalScore > Day15.bestCalorieRecipe && totalCalories == 500) {
                        Day15.bestCalorieRecipe = totalScore;
                    }
                }
            }
        }
    }
}