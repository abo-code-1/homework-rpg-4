package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            result.setRounds(0);
            result.setWinner("INVALID");
            result.addLine("Error: null input detected.");
            return result;
        }

        if (!teamA.isAlive() || !teamB.isAlive()) {
            result.setRounds(0);
            String winner = teamA.isAlive() ? teamA.getName() : teamB.getName();
            result.setWinner(winner);
            result.addLine("Raid skipped: one team is already defeated.");
            return result;
        }

        int maxRounds = 100;
        int round = 0;

        result.addLine("=== Raid Start: " + teamA.getName() + " vs " + teamB.getName() + " ===");

        while (teamA.isAlive() && teamB.isAlive() && round < maxRounds) {
            round++;
            result.addLine("--- Round " + round + " ---");

            boolean critA = random.nextInt(100) < 10;
            result.addLine(teamA.getName() + " uses " + teamASkill.getSkillName()
                    + " (" + teamASkill.getEffectName() + ")" + (critA ? " *CRITICAL*" : ""));
            if (critA) {
                teamASkill.cast(teamB);
                teamASkill.cast(teamB);
            } else {
                teamASkill.cast(teamB);
            }
            result.addLine("  " + teamB.getName() + " HP: " + teamB.getHealth());

            if (!teamB.isAlive()) {
                result.addLine(teamB.getName() + " is defeated!");
                break;
            }

            boolean critB = random.nextInt(100) < 10;
            result.addLine(teamB.getName() + " uses " + teamBSkill.getSkillName()
                    + " (" + teamBSkill.getEffectName() + ")" + (critB ? " *CRITICAL*" : ""));
            if (critB) {
                teamBSkill.cast(teamA);
                teamBSkill.cast(teamA);
            } else {
                teamBSkill.cast(teamA);
            }
            result.addLine("  " + teamA.getName() + " HP: " + teamA.getHealth());

            if (!teamA.isAlive()) {
                result.addLine(teamA.getName() + " is defeated!");
                break;
            }
        }

        if (!teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else if (!teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else {
            result.setWinner("DRAW (max rounds reached)");
        }

        result.setRounds(round);
        result.addLine("=== Raid Over: Winner is " + result.getWinner() + " in " + round + " round(s) ===");
        return result;
    }
}
