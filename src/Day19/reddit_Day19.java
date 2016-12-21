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
        System.out.println(input);
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