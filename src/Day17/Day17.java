//--- Day 17: No Such Thing as Too Much ---
//
//The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to move it into smaller containers. You take an inventory of the capacities of the available containers.
//
//For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters. If you need to store 25 liters, there are four ways to do it:
//
//15 and 10
//20 and 5 (the first 5)
//20 and 5 (the second 5)
//15, 5, and 5
//Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?
//
//Your puzzle answer was 1304.
//
//--- Part Two ---
//
//While playing with all the containers in the kitchen, another load of eggnog arrives! The shipping and receiving department is requesting as many containers as you can spare.
//
//Find the minimum number of containers that can exactly fit all 150 liters of eggnog. How many different ways can you fill that number of containers and still hold exactly 150 litres?
//
//In the example above, the minimum number of containers was two. There were three ways to use that many containers, and so the answer there would be 3.
//
//Your puzzle answer was 18.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day17;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day17Reddit {

    public static void main(String[] args) throws IOException {
        List<String> s = Files.readAllLines(Paths.get("C:\\Users\\co075oh\\Documents\\NetBeansProjects\\byui.allthingsnetwork.org\\AdventOfCode\\day17.txt"));
        List<Integer> solutions = partOne(s);
        System.out.println("Part Two = " + partTwo(solutions));
    }
    
    public static List<Integer> partOne(List<String> list) {
        List<Integer> containers = list.stream().mapToInt(Integer::valueOf).boxed().collect(Collectors.toList());
        List<Integer> solutionLength = new ArrayList<>();
        System.out.println("Part One = " + solve(150, 0, containers, solutionLength));
        return solutionLength;
    }

    public static long partTwo(List<Integer> solutions) {
        int min = solutions.stream().min(Integer::compareTo).get();
        return solutions.stream().filter(x -> x.equals(min)).count();
    }

    private static int solve(int liters, int used, List<Integer> unused, List<Integer> sLength) {
        if (liters == 0) {
            sLength.add(used);
            return 1;
        } else if (liters < 0 || unused.isEmpty())
            return 0;
        else {
            List<Integer> tail = unused.subList(1, unused.size());
            return solve(liters - unused.get(0), used + 1, tail, sLength) + solve(liters, used, tail, sLength);
        }
    }
}
