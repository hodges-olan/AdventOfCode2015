//--- Day 4: The Ideal Stocking Stuffer ---
//
//Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically forward-thinking little girls and boys.
//
//To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal. To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
//
//For example:
//
//If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
//If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
//Your puzzle answer was 282749.
//
//--- Part Two ---
//
//Now find one that starts with six zeroes.
//
//Your puzzle answer was 9962624.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//Your puzzle input was yzbqklnj.
//
//You can also [Share] this puzzle.

package Day4;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day4 {

    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String puzzleInput = "yzbqklnj", decimalString, hashCombo, md5sum;
            StringBuilder sb = new StringBuilder();
            int decimal = 0;
            boolean valid = false;
            byte byteData[];
            
            do {
                decimalString = String.valueOf(decimal);
                hashCombo = puzzleInput + decimalString;
                md.update(hashCombo.getBytes());
                byteData = md.digest();
                for (int i = 0; i < byteData.length; i++) {
                    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                }
                md5sum = sb.toString();
                if (md5sum.substring(0,6).equals("000000")) {
                    valid = true;
                } else {
                    decimal = ++decimal;
                }
                sb.setLength(0);
            } while (!valid);
            System.out.println("Decimal String: " + decimalString);
            System.out.println("MD5SUM: " + md5sum);
            System.out.println("Input: " + hashCombo);
        } catch (NoSuchAlgorithmException ex) { Logger.getLogger(Day4.class.getName()).log(Level.SEVERE, null, ex);}
    } 
}