package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        // TODO: Composite aggregation
        // Return total health of all children (and nested children).
        return 0;
    }

    @Override
    public int getAttackPower() {
        // TODO: Composite aggregation
        // Return total attack of alive children only.
        return 0;
    }

    @Override
    public void takeDamage(int amount) {
        // TODO: Composite distribution
        // Distribute incoming damage across alive children.
        // Suggested baseline:
        // 1) Collect alive children
        // 2) Split amount evenly (or using your own documented rule)
        // 3) Apply damage to each child
    }

    @Override
    public boolean isAlive() {
        for (CombatNode child : children) {
            if (child.isAlive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        // TODO: Tree visualization
        // Print this node and recurse into children with increased indent.
        System.out.println(indent + "+ " + name + " [TODO: compute HP/ATK]");
    }

    private List<CombatNode> getAliveChildren() {
        List<CombatNode> alive = new ArrayList<>();
        for (CombatNode child : children) {
            if (child.isAlive()) {
                alive.add(child);
            }
        }
        return alive;
    }
}
