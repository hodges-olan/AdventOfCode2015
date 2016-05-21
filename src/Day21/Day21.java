//--- Day 21: RPG Simulator 20XX ---
//
//Little Henry Case got a new video game for Christmas. It's an RPG, and he's stuck on a boss. He needs to know what equipment to buy at the shop. He hands you the controller.
//
//In this game, the player (you) and the enemy (the boss) take turns attacking. The player always goes first. Each attack reduces the opponent's hit points by at least 1. The first character at or below 0 hit points loses.
//
//Damage dealt by an attacker each turn is equal to the attacker's damage score minus the defender's armor score. An attacker always does at least 1 damage. So, if the attacker has a damage score of 8, and the defender has an armor score of 3, the defender loses 5 hit points. If the defender had an armor score of 300, the defender would still lose 1 hit point.
//
//Your damage score and armor score both start at zero. They can be increased by buying items in exchange for gold. You start with no items and have as much gold as you need. Your total damage or armor is equal to the sum of those stats from all of your items. You have 100 hit points.
//
//Here is what the item shop is selling:
//
//Weapons:    Cost  Damage  Armor
//Dagger        8     4       0
//Shortsword   10     5       0
//Warhammer    25     6       0
//Longsword    40     7       0
//Greataxe     74     8       0
//
//Armor:      Cost  Damage  Armor
//Leather      13     0       1
//Chainmail    31     0       2
//Splintmail   53     0       3
//Bandedmail   75     0       4
//Platemail   102     0       5
//
//Rings:      Cost  Damage  Armor
//Damage +1    25     1       0
//Damage +2    50     2       0
//Damage +3   100     3       0
//Defense +1   20     0       1
//Defense +2   40     0       2
//Defense +3   80     0       3
//You must buy exactly one weapon; no dual-wielding. Armor is optional, but you can't use more than one. You can buy 0-2 rings (at most one for each hand). You must use any items you buy. The shop only has one of each item, so you can't buy, for example, two rings of Damage +3.
//
//For example, suppose you have 8 hit points, 5 damage, and 5 armor, and that the boss has 12 hit points, 7 damage, and 2 armor:
//
//The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
//The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
//The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
//The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
//The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
//The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
//The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.
//In this scenario, the player wins! (Barely.)
//
//You have 100 hit points. The boss's actual stats are in your puzzle input. What is the least amount of gold you can spend and still win the fight?
//
//Your puzzle answer was 121.
//
//--- Part Two ---
//
//Turns out the shopkeeper is working with the boss, and can persuade you to buy whatever items he wants. The other rules still apply, and he still only has one of each item.
//
//What is the most amount of gold you can spend and still lose the fight?
//
//Your puzzle answer was 201.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day21;

public class Day21 {
    public static void main (String args[]) {
        int smallestCost = 10000;
        int largestCost = 0;
        String[][] weapons = {{"Dagger", "8", "4", "0"}, {"Shortsword", "10", "5", "0"}, {"Warhammer", "25", "6", "0"}, {"Longsword", "40", "7", "0"}, {"Greataxe", "74", "8", "0"}};
        String[][] armors = {{"Leather", "13", "0", "1"}, {"Chainmail", "31", "0", "2"}, {"Splintmail", "53", "0", "3"}, {"Bandedmail", "75", "0", "4"}, {"Platemail", "102", "0", "5"}};
        String[][] rings = {{"Damage +1", "25", "1", "0"}, {"Damage +2", "50", "2", "0"}, {"Damage +3", "100", "3", "0"}, {"Defense +1", "20", "0", "1"}, {"Defense +2", "40", "0", "2"}, {"Defense +3", "80", "0", "3"}};
        
        for (int weapon = 0; weapon < weapons.length; weapon++) {
            for (int armor = 0; armor <= armors.length; armor++) {
                for (int ring1 = 0; ring1 <= rings.length; ring1++) {
                    for (int ring2 = 0; ring2 <= rings.length; ring2++) {
                        int bossHitPoints = 103, bossDamage = 9, bossArmor = 2, playerHitPoints = 100;
                        int playerDamage = Integer.parseInt(weapons[weapon][2]); 
                        playerDamage += ring1 != 0 ? Integer.parseInt(rings[ring1-1][2]) : 0;
                        playerDamage += ring2 != 0 && ring1 != ring2 ? Integer.parseInt(rings[ring2-1][2]) : 0;
                        int playerArmor = armor != 0 ? Integer.parseInt(armors[armor-1][3]) : 0;
                        playerArmor += ring1 != 0 ? Integer.parseInt(rings[ring1-1][3]) : 0;
                        playerArmor += ring2 != 0 && ring1 != ring2 ? Integer.parseInt(rings[ring2-1][3]) : 0;
                        for (int turns = 0; playerHitPoints > 0 && bossHitPoints > 0; turns++) {
                            if (turns%2 == 0) bossHitPoints -= playerDamage - bossArmor > 1 ? playerDamage - bossArmor : 1; else playerHitPoints -= bossDamage - playerArmor > 1 ? bossDamage - playerArmor : 1;
                        }
                        int totalCost = Integer.parseInt(weapons[weapon][1]);
                        totalCost += armor > 0 ? Integer.parseInt(armors[armor-1][1]) : 0;
                        totalCost += ring1 > 0 ? Integer.parseInt(rings[ring1-1][1]) : 0;
                        totalCost += ring2 > 0 && ring1 != ring2 ? Integer.parseInt(rings[ring2-1][1]) : 0;
                        if (playerHitPoints > bossHitPoints) smallestCost = totalCost < smallestCost ? totalCost : smallestCost; else largestCost = totalCost > largestCost ? totalCost : largestCost;
                    }
                }
            }
        }
        System.out.println("Smallest Cost: " + smallestCost);
        System.out.println("Largest Cost: " + largestCost);
    }
}