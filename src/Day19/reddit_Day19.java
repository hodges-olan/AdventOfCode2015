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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reddit_Day19 {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String filePath = "day19.txt";
        String input = readFile(filePath);
        String originalMolecule = "ORnPBPMgArCaCaCaSiThCaCaSiThCaCaPBSiRnFArRnFArCaCaSiThCaCaSiThCaCaCaCaCaCaSiRnFYFArSiRnMgArCaSiRnPTiTiBFYPBFArSiRnCaSiRnTiRnFArSiAlArPTiBPTiRnCaSiAlArCaPTiTiBPMgYFArPTiRnFArSiRnCaCaFArRnCaFArCaSiRnSiRnMgArFYCaSiRnMgArCaCaSiThPRnFArPBCaSiRnMgArCaCaSiThCaSiRnTiMgArFArSiThSiThCaCaSiRnMgArCaCaSiRnFArTiBPTiRnCaSiAlArCaPTiRnFArPBPBCaCaSiThCaPBSiThPRnFArSiThCaSiThCaSiThCaPTiBSiRnFYFArCaCaPRnFArPBCaCaPBSiRnTiRnFArCaPRnFArSiRnCaCaCaSiThCaRnCaFArYCaSiRnFArBCaCaCaSiThFArPBFArCaSiRnFArRnCaCaCaFArSiRnFArTiRnPMgArF";
        Map<String, List<String>> replacements = new HashMap<>();
        Map<String, String> reverseReplacements = new HashMap<>();
        parseInput(input, replacements, reverseReplacements);
        solvePartOne(originalMolecule, replacements);
        solvePartTwo(originalMolecule, reverseReplacements);
    }

    private static void parseInput(String input,
                                   Map<String, List<String>> replacements,
                                   Map<String, String> reverseReplacements) {
        Matcher matcher = Pattern.compile("(\\w+) => (\\w+)").matcher(input.trim());
        while (matcher.find()) {
            if (!replacements.containsKey(matcher.group(1))) {
                replacements.put(matcher.group(1), new LinkedList<>());
            }
            replacements.get(matcher.group(1)).add(matcher.group(2));
            reverseReplacements.put(matcher.group(2), matcher.group(1));
        }
    }

    private static void solvePartOne(String originalMolecule,
                                     Map<String, List<String>> replacements) {
        Set<String> distinctMolecules = new HashSet<>();
        for (String toReplace : replacements.keySet()) {
            for (String replacement : replacements.get(toReplace)) {
                Matcher matcher = Pattern.compile(toReplace).matcher(originalMolecule);
                while (matcher.find()) {
                    distinctMolecules.add(replace(originalMolecule, replacement, matcher));
                }
            }
        }
        System.out.println("Part One: " + distinctMolecules.size());
    }

    private static String replace(String original, String replacement, Matcher matcher) {
        int begin = matcher.start(0);
        int end = matcher.end(0);
        StringBuilder newMolecule = new StringBuilder();
        newMolecule.append(original.substring(0, begin));
        newMolecule.append(replacement);
        newMolecule.append(original.substring(end));
        return newMolecule.toString();
    }

    private static void solvePartTwo(String originalMolecule,
                                     Map<String, String> reverseReplacements) {
        int result = -1;
        while (result == -1) result = findMolecule(0, originalMolecule, reverseReplacements);
        System.out.println("Part Two: " + result);
    }

    private static int findMolecule(int depth,
                                    String molecule,
                                    Map<String, String> reverseReplacements) {
        if (molecule.equals("e")) {
            return depth;
        } else {
            List<String> keys = new ArrayList<>(reverseReplacements.keySet());
            boolean replaced = false;
            while (!replaced) {
                String toReplace = keys.remove(new Random().nextInt(keys.size()));
                Matcher matcher = Pattern.compile(toReplace).matcher(molecule);
                if (matcher.find()) {
                    molecule = replace(molecule, reverseReplacements.get(toReplace), matcher);
                    replaced = true;
                }
                if (keys.isEmpty()) {
                    return -1;
                }
            }
            return findMolecule(depth + 1, molecule, reverseReplacements);
        }
    }

    private static String readFile(String filePath) {
        FileReader fileIn = null;
        try {
            fileIn = new FileReader(filePath);
            BufferedReader buffIn = new BufferedReader(fileIn);
            StringBuilder everything = new StringBuilder();
            String line;
            while( (line = buffIn.readLine()) != null) {
                everything.append(line + "\n");
            }   return everything.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(reddit_Day19.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(reddit_Day19.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileIn.close();
            } catch (IOException ex) {
                Logger.getLogger(reddit_Day19.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "null";
    }
}