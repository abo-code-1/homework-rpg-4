package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.AreaSkill;
import com.narxoz.rpg.bridge.FireEffect;
import com.narxoz.rpg.bridge.IceEffect;
import com.narxoz.rpg.bridge.PhysicalEffect;
import com.narxoz.rpg.bridge.ShadowEffect;
import com.narxoz.rpg.bridge.SingleTargetSkill;
import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.EnemyUnit;
import com.narxoz.rpg.composite.HeroUnit;
import com.narxoz.rpg.composite.PartyComposite;
import com.narxoz.rpg.composite.RaidGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        System.out.println("========== PART 1: BRIDGE PATTERN ==========\n");

        Skill slashPhysical = new SingleTargetSkill("Slash", 20, new PhysicalEffect());
        Skill slashFire = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill slashShadow = new SingleTargetSkill("Slash", 20, new ShadowEffect());

        Skill stormFire = new AreaSkill("Storm", 15, new FireEffect());
        Skill stormIce = new AreaSkill("Storm", 15, new IceEffect());
        Skill stormShadow = new AreaSkill("Storm", 15, new ShadowEffect());

        System.out.println("--- Same Skill, Different Effects ---");
        System.out.println(slashPhysical.getSkillName() + " + " + slashPhysical.getEffectName());
        System.out.println(slashFire.getSkillName() + " + " + slashFire.getEffectName());
        System.out.println(slashIce.getSkillName() + " + " + slashIce.getEffectName());
        System.out.println(slashShadow.getSkillName() + " + " + slashShadow.getEffectName());

        System.out.println("\n--- Different Skills, Same Effect (Fire) ---");
        System.out.println(slashFire.getSkillName() + " + " + slashFire.getEffectName());
        System.out.println(stormFire.getSkillName() + " + " + stormFire.getEffectName());

        EnemyUnit dummy = new EnemyUnit("Dummy", 200, 0);
        System.out.println("\n--- Damage Test on " + dummy.getName() + " (HP=" + dummy.getHealth() + ") ---");
        slashPhysical.cast(dummy);
        System.out.println("After Slash+Physical: HP=" + dummy.getHealth());
        slashFire.cast(dummy);
        System.out.println("After Slash+Fire:     HP=" + dummy.getHealth());
        slashShadow.cast(dummy);
        System.out.println("After Slash+Shadow:   HP=" + dummy.getHealth());

        System.out.println("\n========== PART 2: COMPOSITE PATTERN ==========\n");

        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina", 90, 40);
        HeroUnit ranger = new HeroUnit("Sylvanas", 100, 35);

        PartyComposite heroes = new PartyComposite("Hero Party");
        heroes.add(warrior);
        heroes.add(mage);

        PartyComposite scouts = new PartyComposite("Scout Squad");
        scouts.add(ranger);

        RaidGroup heroRaid = new RaidGroup("Hero Raid");
        heroRaid.add(heroes);
        heroRaid.add(scouts);

        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);
        EnemyUnit troll = new EnemyUnit("Troll", 100, 22);
        EnemyUnit dragon = new EnemyUnit("Dragon", 200, 50);

        PartyComposite frontline = new PartyComposite("Frontline");
        frontline.add(goblin);
        frontline.add(orc);

        PartyComposite backline = new PartyComposite("Backline");
        backline.add(troll);
        backline.add(dragon);

        RaidGroup enemyRaid = new RaidGroup("Enemy Raid");
        enemyRaid.add(frontline);
        enemyRaid.add(backline);

        System.out.println("--- Hero Raid Structure ---");
        heroRaid.printTree("");

        System.out.println("\n--- Enemy Raid Structure ---");
        enemyRaid.printTree("");

        System.out.println("\n--- Composite Stats ---");
        System.out.println("Hero Raid  -> HP=" + heroRaid.getHealth() + ", ATK=" + heroRaid.getAttackPower());
        System.out.println("Enemy Raid -> HP=" + enemyRaid.getHealth() + ", ATK=" + enemyRaid.getAttackPower());

        System.out.println("\n========== PART 3: RAID SIMULATION ==========\n");

        Skill heroSkill = new AreaSkill("Firestorm", 18, new FireEffect());
        Skill enemySkill = new SingleTargetSkill("Shadow Strike", 25, new ShadowEffect());

        System.out.println("Hero Skill:  " + heroSkill.getSkillName() + " (" + heroSkill.getEffectName() + ")");
        System.out.println("Enemy Skill: " + enemySkill.getSkillName() + " (" + enemySkill.getEffectName() + ")");
        System.out.println();

        RaidEngine engine = new RaidEngine().setRandomSeed(42L);
        RaidResult result = engine.runRaid(heroRaid, enemyRaid, heroSkill, enemySkill);

        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        System.out.println();
        for (String line : result.getLog()) {
            System.out.println("  " + line);
        }

        System.out.println("\n--- Final State ---");
        heroRaid.printTree("");
        enemyRaid.printTree("");

        System.out.println("\n=== Demo Complete ===");
    }
}
