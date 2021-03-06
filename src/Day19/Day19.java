//--- Day 19: Medicine for Rudolph ---
//
//Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
//
//Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made medicine. Unfortunately, Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
//
//The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant, capable of constructing any Red-Nosed Reindeer molecule you need. It works by starting with some input molecule and then doing a series of replacements, one per step, until it has the right molecule.
//
//However, the machine has to be calibrated before it can be used. Calibration involves determining the number of molecules that can be generated in one step from a given starting point.
//
//For example, imagine a simpler machine that supports only the following replacements:
//
//H => HO
//H => OH
//O => HH
//Given the replacements above and starting with HOH, the following molecules could be generated:
//
//HOOH (via H => HO on the first H).
//HOHO (via H => HO on the second H).
//OHOH (via H => OH on the first H).
//HOOH (via H => OH on the second H).
//HHHH (via O => HH).
//So, in the example above, there are 4 distinct molecules (not five, because HOOH appears twice) after one replacement from HOH. Santa's favorite molecule, HOHOHO, can become 7 distinct molecules (over nine replacements: six from H, and three from O).
//
//The machine replaces without regard for the surrounding characters. For example, given the string H2O, the transition H => OO would result in OO2O.
//
//Your puzzle input describes all of the possible replacements and, at the bottom, the medicine molecule for which you need to calibrate the machine. How many distinct molecules can be created after all the different ways you can do one replacement on the medicine molecule?
//
//Your puzzle answer was 576.
//
//--- Part Two ---
//
//Now that the machine is calibrated, you're ready to begin molecule fabrication.
//
//Molecule fabrication always begins with just a single electron, e, and applying replacements one at a time, just like the ones during calibration.
//
//For example, suppose you have the following replacements:
//
//e => H
//e => O
//H => HO
//H => OH
//O => HH
//If you'd like to make HOH, you start with e, and then make the following replacements:
//
//e => O to get O
//O => HH to get HH
//H => OH (on the second H) to get HOH
//So, you could make HOH after 3 steps. Santa's favorite molecule, HOHOHO, can be made in 6 steps.
//
//How long will it take to make the medicine? Given the available replacements and the medicine molecule in your puzzle input, what is the fewest number of steps to go from e to the medicine molecule?
//
//Your puzzle answer was 207.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day19;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import org.apache.commons.lang.StringUtils;

public class Day19 {
    private final static String filePath = "day19.txt";
    private final static ArrayList<String[]> input = new ArrayList<>();
    private static String molecule = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";
    private final static HashSet combos = new HashSet();
    private static int reverseCount = 0;
    
    public static void main(String args[]) {
        Day19.readFile();
        
        // Part 1
        for(String[] line : Day19.input) Day19.combos(line);
        System.out.println("Part 1 molecule combinations: " + Day19.combos.size());
        
        // Part 2
        while(!Day19.molecule.equals("e")) {
            for (String[] line : Day19.input) Day19.reverseCombos(line);
        }
        System.out.println("Part 2 molecule combinations: " + Day19.reverseCount);
    }
    
    private static void readFile() {
        String read;
        try (BufferedReader in = new BufferedReader(new FileReader(Day19.filePath))) { while((read = in.readLine()) != null) Day19.input.add(read.trim().split(" => ")); } catch (IOException ex) { Logger.getLogger(Day19.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static void combos(String[] line) {
        if(Day19.molecule.contains(line[0])) {
            int i = 0;
            while((i = Day19.molecule.indexOf(line[0], i)) >= 0) {
                String newMolecule = (Day19.molecule.substring(0, i) + line[1] + Day19.molecule.substring(i+line[0].length(), Day19.molecule.length()));
                Day19.combos.add(newMolecule);
                i = i + line[0].length();
            }
        }
    }

    private static void reverseCombos(String[] line) {
        if(Day19.molecule.contains(line[1])) {
            Day19.reverseCount += StringUtils.countMatches(Day19.molecule, line[1]);
            Day19.molecule = Day19.molecule.replaceAll(line[1], line[0]);
        }
    }
}