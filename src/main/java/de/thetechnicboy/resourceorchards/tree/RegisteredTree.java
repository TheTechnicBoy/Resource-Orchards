package de.thetechnicboy.resourceorchards.tree;

import de.thetechnicboy.resourceorchards.ResourceOrchards;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisteredTree {
    private final String name;
    private final int color;

    private int logMin = 1, logMax = 1;
    private double logChance = 1;

    private int acornMin = 1, acornMax = 3;
    private double acornChance = 0.8;

    private int saplingMin = 1, saplingMax = 1;
    private double saplingChance = 0.05;

    private int resinMin = 1, resinMax = 3;
    private double resinChance = 0.8;

    private final List<ExtraDrop> logExtraDrops = new ArrayList<>();
    private final List<ExtraDrop> leavesExtraDrops = new ArrayList<>();

    public RegisteredTree(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public RegisteredTree log(int min, int max, double chance){
        this.logMin = min;
        this.logMax = max;
        this.logChance = chance;
        return this;
    }

    public RegisteredTree acorn(int min, int max, double chance) {
        this.acornMin = min;
        this.acornMax = max;
        this.acornChance = chance;
        return this;
    }

    public RegisteredTree sapling(int min, int max, double chance) {
        this.saplingMin = min;
        this.saplingMax = max;
        this.saplingChance = chance;
        return this;
    }

    public RegisteredTree resin(int min, int max, double chance) {
        this.resinMin = min;
        this.resinMax = max;
        this.resinChance = chance;
        return this;
    }

    public RegisteredTree extraLogDrop(String itemId, int min, int max, double chance) {
        logExtraDrops.add(new ExtraDrop(itemId, min, max, chance));
        return this;
    }

    public RegisteredTree extraLeavesDrop(String itemId, int min, int max, double chance) {
        leavesExtraDrops.add(new ExtraDrop(itemId, min, max, chance));
        return this;
    }

    public String name() { return name; }
    public int color() { return color; }

    public int logMax() { return logMax; }
    public int logMin() { return logMin; }
    public double logChance() { return logChance; }

    public int acornMin() { return acornMin; }
    public int acornMax() { return acornMax; }
    public double acornChance() { return acornChance; }

    public int saplingMin() { return saplingMin; }
    public int saplingMax() { return saplingMax; }
    public double saplingChance() { return saplingChance; }

    public int resinMin() { return resinMin; }
    public int resinMax() { return resinMax; }
    public double resinChance() { return resinChance; }

    public List<ExtraDrop> logExtraDrops() { return Collections.unmodifiableList(logExtraDrops); }
    public List<ExtraDrop> leavesExtraDrops() { return Collections.unmodifiableList(leavesExtraDrops); }

    public Block logBlock() { return BuiltInRegistries.BLOCK.get(id("_log")); }
    public Block leaveBlock() { return BuiltInRegistries.BLOCK.get(id("_leaves")); }
    public Block saplingBlock() { return BuiltInRegistries.BLOCK.get(id("_sapling")); }
    public Block amberBlock() { return BuiltInRegistries.BLOCK.get(id("_amber")); }
    public Block pottedTreeBlock() { return BuiltInRegistries.BLOCK.get(id("_potted_tree")); }

    public Item logItem() { return BuiltInRegistries.ITEM.get(id("_log")); }
    public Item leavesItem() { return BuiltInRegistries.ITEM.get(id("_leaves")); }
    public Item saplingItem() { return BuiltInRegistries.ITEM.get(id("_sapling")); }
    public Item amberItem() { return BuiltInRegistries.ITEM.get(id("_amber")); }

    public Item acornItem() { return BuiltInRegistries.ITEM.get(id("_acorn")); }
    public Item resinItem() { return BuiltInRegistries.ITEM.get(id("_resin")); }
    public Item roastedAcornItem() { return BuiltInRegistries.ITEM.get(id("_roasted_acorn")); }

    private ResourceLocation id(String suffix) {
        return ResourceLocation.fromNamespaceAndPath(ResourceOrchards.MODID, name + suffix);
    }

    public record ExtraDrop(String itemId, int min, int max, double chance) {}
}