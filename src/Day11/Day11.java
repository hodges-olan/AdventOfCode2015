//--- Day 11: Corporate Policy ---
//
//Santa's previous password expired, and he needs help choosing a new one.
//
//To help him remember his new password after the old one expires, Santa has devised a method of coming up with a password based on the previous one. Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons), so he finds his new password by incrementing his old password string repeatedly until it is valid.
//
//Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on. Increase the rightmost letter one step; if it was z, it wraps around to a, and repeat with the next letter to the left until one doesn't wrap around.
//
//Unfortunately for Santa, a new Security-Elf recently started, and he has imposed some additional password requirements:
//
//Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
//Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
//Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
//For example:
//
//hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement requirement (because it contains i and l).
//abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
//abbcegjk fails the third requirement, because it only has one double letter (bb).
//The next password after abcdefgh is abcdffaa.
//The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., since i is not allowed.
//Given Santa's current password (your puzzle input), what should his next password be?
//
//Your puzzle answer was hepxxyzz.
//
//--- Part Two ---
//
//Santa's password expired again. What's the next one?
//
//Your puzzle answer was heqaabcc.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//Your puzzle input was hepxcrrq.
//
//You can also [Share] this puzzle.

package Day11;

public class Day11 {

    public static void main(String[] args) {
        String input = "hepxcrrq";
        String inputTwo = "hepxxyzz";
        
        // Part 1
        System.out.println("Part 1");
        System.out.println("Old Password: " + input);
        while (!Day11.validPassword(input)) { input = incrementPassword(input); }
        System.out.println("New Password: " + input);
        
        // Part 2
        System.out.println("Part 2");
        System.out.println("Old Password: " + inputTwo);
        inputTwo = incrementPassword(inputTwo);
        while (!Day11.validPassword(inputTwo)) { inputTwo = incrementPassword(inputTwo); }
        System.out.println("New Password: " + inputTwo);
    }

    private static String incrementPassword(String input) {
        StringBuilder newPassword = new StringBuilder();
        char[] tempChars;
        int tempCharInt, tempInt = 1;
        
        tempChars = input.toCharArray();
        for (int i = tempChars.length-1; i >= 0; i--) {
            if (tempChars[i] == 'z' & tempInt == 1) {
                tempChars[i] = 'a';
                tempInt = 1;
            } else {
                tempCharInt = (int) tempChars[i];
                if (tempCharInt == 105 || tempCharInt == 108 || tempCharInt == 111) { tempInt = ++tempInt; }
                tempCharInt = tempCharInt + tempInt;
                tempChars[i] = (char) tempCharInt;
                tempInt = 0;
            }
        }
        for (int i = 0; i < tempChars.length; i++) { newPassword.append(tempChars[i]); }
        return newPassword.toString();
    }

    private static boolean validPassword(String input) {
        boolean valid = true;
        StringBuilder patternChars;
        int patternCount = 0;
        
        // Requirement 1
        for (int i = 97; i < 121; i++) {
            if (i < 103 || i > 111) {
                patternChars = new StringBuilder();
                patternChars.append((char) i);
                patternChars.append((char) (i+1));
                patternChars.append((char) (i+2));
                if (input.contains(patternChars.toString())) {
                    patternCount = ++patternCount;
                }
            }
        }
        if (patternCount < 1) { return false; }
        
        // Requirement 2
        patternCount = 0;
        for (int i = 97; i < 123; i++) {
            patternChars = new StringBuilder();
            patternChars.append((char) i);
            patternChars.append((char) i);
            if (input.contains(patternChars.toString())) {
                patternCount = ++patternCount;
            }
        }
        if (patternCount < 2) { return false; }
        
        // Requirement 3
        if (input.contains("i") || input.contains("o") || input.contains("l")) { return false; }
        
        return valid;
    }
}
