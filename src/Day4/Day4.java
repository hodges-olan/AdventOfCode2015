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
//Your puzzle answer was 346386.
//
//--- Part Two ---
//
//Now find one that starts with six zeroes.
//
//Your puzzle answer was 9958218.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//Your puzzle input was iwrupvqb.
//
//You can also [Share] this puzzle.

package Day4;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Day4 {

    public static void main(String[] args) {
        
        String input = "iwrupvqb", md5sum;
        
        // Part 1
        System.out.println("Part 1: " + findHash(input, "00000"));
        
        // Part 2
        System.out.println("Part 2: " + findHash(input, "000000"));
        
    } 
    
    private static String findHash(String input, String search) {
        HashFunction md5 = Hashing.md5();
        String md5sum;
        int decimal = 0;
        boolean valid = false;

        do {
            md5sum = md5.hashString(input + String.valueOf(decimal), Charsets.US_ASCII).toString();
            if (md5sum.substring(0,search.length()).equals(search)) valid = true; else decimal++;
        } while (!valid);
        return String.valueOf(decimal);
    }
}
