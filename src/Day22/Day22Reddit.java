//--- Day 22: Wizard Simulator 20XX ---
//
//Little Henry Case decides that defeating bosses with swords and stuff is boring. Now he's playing the game with a wizard. Of course, he gets stuck on another boss and needs your help again.
//
//In this version, combat still proceeds with the player and the boss taking alternating turns. The player still goes first. Now, however, you don't get any equipment; instead, you must choose one of your spells to cast. The first character at or below 0 hit points loses.
//
//Since you're a wizard, you don't get to wear armor, and you can't attack normally. However, since you do magic damage, your opponent's armor is ignored, and so the boss effectively has zero armor as well. As before, if armor (from a spell, in this case) would reduce damage below 1, it becomes 1 instead - that is, the boss' attacks always deal at least 1 damage.
//
//On each of your turns, you must select one of your spells to cast. If you cannot afford to cast any spell, you lose. Spells cost mana; you start with 500 mana, but have no maximum limit. You must have enough mana to cast a spell, and its cost is immediately deducted when you cast it. Your spells are Magic Missile, Drain, Shield, Poison, and Recharge.
//
//Magic Missile costs 53 mana. It instantly does 4 damage.
//Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
//Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
//Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
//Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
//Effects all work the same way. Effects apply at the start of both the player's turns and the boss' turns. Effects are created with a timer (the number of turns they last); at the start of each turn, after they apply any effect they have, their timer is decreased by one. If this decreases the timer to zero, the effect ends. You cannot cast a spell that would start an effect which is already active. However, effects can be started on the same turn they end.
//
//For example, suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage:
//
//-- Player turn --
//- Player has 10 hit points, 0 armor, 250 mana
//- Boss has 13 hit points
//Player casts Poison.
//
//-- Boss turn --
//- Player has 10 hit points, 0 armor, 77 mana
//- Boss has 13 hit points
//Poison deals 3 damage; its timer is now 5.
//Boss attacks for 8 damage.
//
//-- Player turn --
//- Player has 2 hit points, 0 armor, 77 mana
//- Boss has 10 hit points
//Poison deals 3 damage; its timer is now 4.
//Player casts Magic Missile, dealing 4 damage.
//
//-- Boss turn --
//- Player has 2 hit points, 0 armor, 24 mana
//- Boss has 3 hit points
//Poison deals 3 damage. This kills the boss, and the player wins.
//Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
//
//-- Player turn --
//- Player has 10 hit points, 0 armor, 250 mana
//- Boss has 14 hit points
//Player casts Recharge.
//
//-- Boss turn --
//- Player has 10 hit points, 0 armor, 21 mana
//- Boss has 14 hit points
//Recharge provides 101 mana; its timer is now 4.
//Boss attacks for 8 damage!
//
//-- Player turn --
//- Player has 2 hit points, 0 armor, 122 mana
//- Boss has 14 hit points
//Recharge provides 101 mana; its timer is now 3.
//Player casts Shield, increasing armor by 7.
//
//-- Boss turn --
//- Player has 2 hit points, 7 armor, 110 mana
//- Boss has 14 hit points
//Shield's timer is now 5.
//Recharge provides 101 mana; its timer is now 2.
//Boss attacks for 8 - 7 = 1 damage!
//
//-- Player turn --
//- Player has 1 hit point, 7 armor, 211 mana
//- Boss has 14 hit points
//Shield's timer is now 4.
//Recharge provides 101 mana; its timer is now 1.
//Player casts Drain, dealing 2 damage, and healing 2 hit points.
//
//-- Boss turn --
//- Player has 3 hit points, 7 armor, 239 mana
//- Boss has 12 hit points
//Shield's timer is now 3.
//Recharge provides 101 mana; its timer is now 0.
//Recharge wears off.
//Boss attacks for 8 - 7 = 1 damage!
//
//-- Player turn --
//- Player has 2 hit points, 7 armor, 340 mana
//- Boss has 12 hit points
//Shield's timer is now 2.
//Player casts Poison.
//
//-- Boss turn --
//- Player has 2 hit points, 7 armor, 167 mana
//- Boss has 12 hit points
//Shield's timer is now 1.
//Poison deals 3 damage; its timer is now 5.
//Boss attacks for 8 - 7 = 1 damage!
//
//-- Player turn --
//- Player has 1 hit point, 7 armor, 167 mana
//- Boss has 9 hit points
//Shield's timer is now 0.
//Shield wears off, decreasing armor by 7.
//Poison deals 3 damage; its timer is now 4.
//Player casts Magic Missile, dealing 4 damage.
//
//-- Boss turn --
//- Player has 1 hit point, 0 armor, 114 mana
//- Boss has 2 hit points
//Poison deals 3 damage. This kills the boss, and the player wins.
//You start with 50 hit points and 500 mana points. The boss's actual stats are in your puzzle input. What is the least amount of mana you can spend and still win the fight? (Do not include mana recharge effects as "spending" negative mana.)
//
//Your puzzle answer was 953.
//
//--- Part Two ---
//
//On the next run through the game, you increase the difficulty to hard.
//
//At the start of each player turn (before any other effects apply), you lose 1 hit point. If this brings you to or below 0 hit points, you lose.
//
//With the same starting stats for you and the boss, what is the least amount of mana you can spend and still win the fight?
//
//Your puzzle answer was 1289.
//
//Both parts of this puzzle are complete! They provide two gold stars: **
//
//At this point, you should return to your advent calendar and try another puzzle.
//
//If you still want to see it, you can get your puzzle input.
//
//You can also [Share] this puzzle.

package Day22;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day22Reddit {

    private static int solve(List<String> s, boolean hard) {

        PriorityQueue<Wizard> wizards = new PriorityQueue<Wizard>(
            (a, b) -> Integer.compare(b.mana_spend, a.mana_spend));
        AtomicInteger minMana = new AtomicInteger(Integer.MAX_VALUE);
        wizards.add(new Wizard(50, 500, parseBoss(s)));
        while (wizards.size() > 0) {
            Wizard curr = wizards.poll();
            if (hard) {
                if (--curr.hp <= 0)
                    continue;
            }
            curr.applyEffect();
            for (int spell = 0; spell < Wizard.spells.length; spell++) {
                if (curr.canCast(spell)) {
                    Wizard next = curr.clone();
                    next.castSpell(spell);
                    next.applyEffect();

                    if (next.boss[0] <= 0) {
                        minMana.set(Math.min(minMana.get(), next.mana_spend));
                        wizards.removeIf(w -> w.mana_spend > minMana.get());
                    } else {
                        next.hp -= Math.max(1, next.boss[1] - next.armor);
                        if (next.hp > 0 && next.mana > 0 && next.mana_spend < minMana.get())
                            wizards.add(next);
                    }
                }
            }
        }
        return minMana.get();
    }

    private static int[] parseBoss(List<String> s) {
        int[] boss = new int[2];
        for (int i = 0; i < boss.length; i++)
            boss[i] = Integer.valueOf(s.get(i).split(": ")[1]);
        return boss;
    }

    public static void main(String[] args) throws IOException {
        List<String> s = Files.readAllLines(Paths.get("day22.txt"));
        long start = System.currentTimeMillis();
        System.out.println("Part One = " + solve(s, false));
        System.out.println("Runtime in ms = " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        System.out.println("Part Two = " + solve(s, true));
        System.out.println("Runtime in ms = " + (System.currentTimeMillis() - start));
    }
}

class Wizard implements Cloneable {

    static int[][] spells = { { 53, 0 }, { 73, 0 }, { 113, 6 }, { 173, 6 }, { 229, 5 } };

    int hp, mana, armor, mana_spend;

    int[] active_effects = new int[3];
    int[] boss; // {hp, dmg}

    public Wizard(int hp, int mana, int[] boss) {
        this.hp = hp;
        this.mana = mana;
        this.boss = boss;
    }

    public boolean canCast(int i) {
        return mana >= spells[i][0] && (i < 2 || active_effects[i - 2] == 0);
    }

    public void castSpell(int i) {
        mana -= spells[i][0];
        mana_spend += spells[i][0];
        if (i == 0) { // Magic Missile
            boss[0] -= 4;
        } else if (i == 1) { // Drain
            hp += 2;
            boss[0] -= 2;
        } else { // active effect
            active_effects[i - 2] = spells[i][1];
        }
    }

    public void applyEffect() {
        for (int i = 0; i < active_effects.length; i++) {
            if (active_effects[i] > 0) {
                active_effects[i]--;
                if (i == 0) { // Shield
                    armor = 7;
                } else if (i == 1) { // Poison
                    boss[0] -= 3;
                } else if (i == 2) { // Recharge
                    mana += 101;
                }
            } else if (i == 0)
                armor = 0;
        }
    }

    public Wizard clone() {
        Wizard neu = new Wizard(hp, mana, boss.clone());
        neu.armor = this.armor;
        neu.mana_spend = this.mana_spend;
        neu.active_effects = this.active_effects.clone();
        return neu;
    }
}
