package de.thetechnicboy.resourceorchards.kubejs;

import de.thetechnicboy.resourceorchards.ResourceOrchards;
import de.thetechnicboy.resourceorchards.tree.RegisteredTree;
import de.thetechnicboy.resourceorchards.tree.TreeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class DynamicTreeAssets {

    // ---------- Client assets (blockstates, item models, lang) ----------

    @Nullable
    public static String generate(String path) {
        for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
            String name = tree.name();

            if (path.equals("blockstates/" + name + "_log.json")) return blockstateAxis(ResourceOrchards.MODID + ":block/log");
            if (path.equals("blockstates/" + name + "_leaves.json")) return blockstateSimple(ResourceOrchards.MODID + ":block/leaves");
            if (path.equals("blockstates/" + name + "_sapling.json")) return blockstateStage(ResourceOrchards.MODID + ":block/sapling");
            if (path.equals("blockstates/" + name + "_amber.json")) return blockstateSimple(ResourceOrchards.MODID + ":block/amber");
            if (path.equals("blockstates/" + name + "_potted_tree.json")) return blockstateSimple(ResourceOrchards.MODID + ":block/potted_tree");

            if (path.equals("models/item/" + name + "_log.json")) return parentOnly(ResourceOrchards.MODID + ":block/log");
            if (path.equals("models/item/" + name + "_leaves.json")) return parentOnly(ResourceOrchards.MODID + ":block/leaves");
            if (path.equals("models/item/" + name + "_sapling.json")) return parentOnly(ResourceOrchards.MODID + ":item/sapling");
            if (path.equals("models/item/" + name + "_amber.json")) return parentOnly(ResourceOrchards.MODID + ":block/amber");
            if (path.equals("models/item/" + name + "_acorn.json")) return parentOnly(ResourceOrchards.MODID + ":item/acorn");
            if (path.equals("models/item/" + name + "_resin.json")) return parentOnly(ResourceOrchards.MODID + ":item/resin");
            if (path.equals("models/item/" + name + "_roasted_acorn.json")) return parentOnly(ResourceOrchards.MODID + ":item/roasted_acorn");
        }

        if (path.equals("lang/en_us.json")) return lang();

        return null;
    }

    public static List<String> allPaths() {
        List<String> paths = new ArrayList<>();
        for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
            String name = tree.name();
            paths.add("blockstates/" + name + "_log.json");
            paths.add("blockstates/" + name + "_leaves.json");
            paths.add("blockstates/" + name + "_sapling.json");
            paths.add("blockstates/" + name + "_amber.json");
            paths.add("blockstates/" + name + "_potted_tree.json");
            paths.add("models/item/" + name + "_log.json");
            paths.add("models/item/" + name + "_leaves.json");
            paths.add("models/item/" + name + "_sapling.json");
            paths.add("models/item/" + name + "_amber.json");
            paths.add("models/item/" + name + "_acorn.json");
            paths.add("models/item/" + name + "_resin.json");
            paths.add("models/item/" + name + "_roasted_acorn.json");
        }
        paths.add("lang/en_us.json");
        return paths;
    }

    // ---------- Server data (loot tables, tags, worldgen) ----------

    @Nullable
    public static String generateData(String namespace, String path) {
        if (namespace.equals(ResourceOrchards.MODID)) {
            for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
                String name = tree.name();
                if (path.equals("loot_table/blocks/" + name + "_log.json")) return lootTableLog(tree);
                if (path.equals("loot_table/blocks/" + name + "_sapling.json")) return lootTableDropSelf(name + "_sapling");
                if (path.equals("loot_table/blocks/" + name + "_leaves.json")) return lootTableLeaves(tree);
                if (path.equals("loot_table/blocks/" + name + "_amber.json")) return lootTableDropSelf(name + "_amber");
                if (path.equals("loot_table/pots/" + name + "_tree.json")) return potHarvestLootTable(tree);
                if (path.equals("worldgen/configured_feature/" + name + "_tree.json")) return configuredFeatureTree(name);
                if (path.equals("recipe/" + name + "_tree_crop.json")) return botanyPotsCrop(tree);
                if(path.equals("recipe/" + name + "_roasted_acorn_smelting.json")) return recipeRoastedAcornSmelting(tree);
                if(path.equals("recipe/" + name + "_roasted_acorn_campfire_cooking.json")) return recipeRoastedAcornCampfireCooking(tree);
            }
        }

        if (namespace.equals("minecraft")) {
            if (path.equals("tags/block/mineable/axe.json")) return tagValues("_log");
            if (path.equals("tags/block/mineable/hoe.json")) return tagValues("_leaves");
            if (path.equals("tags/block/mineable/pickaxe.json")) return tagValues("_amber");
            if (path.equals("tags/block/logs.json")) return tagValues("_log");
            if (path.equals("tags/block/logs_that_burn.json")) return tagValues("_log");
        }

        return null;
    }

    public static List<String> allDataPaths(String namespace) {
        List<String> paths = new ArrayList<>();
        if (namespace.equals(ResourceOrchards.MODID)) {
            for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
                String name = tree.name();
                paths.add("loot_table/blocks/" + name + "_log.json");
                paths.add("loot_table/blocks/" + name + "_sapling.json");
                paths.add("loot_table/blocks/" + name + "_amber.json");
                paths.add("loot_table/blocks/" + name + "_leaves.json");
                paths.add("loot_table/pots/" + name + "_tree.json");
                paths.add("worldgen/configured_feature/" + name + "_tree.json");
                paths.add("recipe/" + name + "_tree_crop.json");
                paths.add("recipe/" + name + "_roasted_acorn_smelting.json");
                paths.add("recipe/" + name + "_roasted_acorn_campfire_cooking.json");
            }
        }
        if (namespace.equals("minecraft")) {
            paths.add("tags/block/mineable/axe.json");
            paths.add("tags/block/mineable/hoe.json");
            paths.add("tags/block/mineable/pickaxe.json");
            paths.add("tags/block/logs.json");
            paths.add("tags/block/logs_that_burn.json");
        }
        return paths;
    }

    // ---------- generators: client assets ----------

    private static String blockstateAxis(String modelPath) {
        return """
                {
                    "variants": {
                        "axis=y": { "model": "%1$s" },
                        "axis=z": { "model": "%1$s", "x": 90 },
                        "axis=x": { "model": "%1$s", "x": 90, "y": 90 }
                    }
                }
                """.formatted(modelPath);
    }

    private static String blockstateSimple(String modelPath) {
        return """
                {
                    "variants": {
                        "": { "model": "%1$s" }
                    }
                }
                """.formatted(modelPath);
    }

    private static String blockstateStage(String modelPath) {
        return """
            {
                "variants": {
                    "stage=0": { "model": "%1$s" },
                    "stage=1": { "model": "%1$s" }
                }
            }
            """.formatted(modelPath);
    }

    private static String parentOnly(String parentModel) {
        return """
                {
                    "parent": "%1$s"
                }
                """.formatted(parentModel);
    }

    private static String lang() {
        StringBuilder sb = new StringBuilder("{\n");
        boolean first = true;
        for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
            String display = Character.toUpperCase(tree.name().charAt(0)) + tree.name().substring(1);
            first = appendLang(sb, first, "block." + ResourceOrchards.MODID + "." + tree.name() + "_log", display + " Log");
            first = appendLang(sb, first, "block." + ResourceOrchards.MODID + "." + tree.name() + "_leaves", display + " Leaves");
            first = appendLang(sb, first, "block." + ResourceOrchards.MODID + "." + tree.name() + "_sapling", display + " Sapling");
            first = appendLang(sb, first, "block." + ResourceOrchards.MODID + "." + tree.name() + "_amber", display + " Amber");
            first = appendLang(sb, first, "item." + ResourceOrchards.MODID + "." + tree.name() + "_acorn", display + " Acorn");
            first = appendLang(sb, first, "item." + ResourceOrchards.MODID + "." + tree.name() + "_resin", display + " Resin");
            first = appendLang(sb, first, "item." + ResourceOrchards.MODID + "." + tree.name() + "_roasted_acorn", "Roasted " + display + " Acorn");
        }
        sb.append("\n}");
        return sb.toString();
    }

    private static boolean appendLang(StringBuilder sb, boolean first, String key, String value) {
        if (!first) sb.append(",\n");
        sb.append("    \"").append(key).append("\": \"").append(value).append("\"");
        return false;
    }

    // ---------- generators: worldgen ----------
    private static String configuredFeatureTree(String treeName) {
        //Same as Oak Tree
        return """
        {
            "type": "minecraft:tree",
            "config": {
                "decorators": [],
                "dirt_provider": {
                    "type": "minecraft:simple_state_provider",
                    "state": {
                        "Name": "minecraft:dirt"
                    }
                },
                "foliage_placer": {
                    "type": "minecraft:blob_foliage_placer",
                    "height": 3,
                    "offset": 0,
                    "radius": 2
                },
                "foliage_provider": {
                    "type": "minecraft:simple_state_provider",
                    "state": {
                        "Name": "%1$s:%2$s_leaves",
                        "Properties": {
                            "distance": "7",
                            "persistent": "false",
                            "waterlogged": "false"
                        }
                    }
                },
                "force_dirt": false,
                "ignore_vines": true,
                "minimum_size": {
                    "type": "minecraft:two_layers_feature_size",
                    "limit": 1,
                    "lower_size": 0,
                    "upper_size": 1
                },
                "trunk_placer": {
                    "type": "minecraft:straight_trunk_placer",
                    "base_height": 4,
                    "height_rand_a": 2,
                    "height_rand_b": 0
                },
                "trunk_provider": {
                    "type": "minecraft:simple_state_provider",
                    "state": {
                        "Name": "%1$s:%2$s_log",
                        "Properties": {
                            "axis": "y"
                        }
                    }
                }
            }
        }
        """.formatted(ResourceOrchards.MODID, treeName);
    }

    // ---------- generators: botany pots ----------

    private static String botanyPotsCrop(RegisteredTree tree) {
        return """
                {
                    "type": "botanypots:crop",
                    "input": { "item": "%1$s:%2$s_sapling" },
                    "categories": ["dirt", "farmland"],
                    "growthTicks": 1200,
                    "display": {
                        "type": "botanypots:simple",
                        "block_state": { "block": "%1$s:%2$s_potted_tree" }
                    },
                    "drops": {
                        "type": "botanypots:loot_table",
                        "table_id": "%1$s:pots/%2$s_tree"
                    }
                }
                """.formatted(ResourceOrchards.MODID, tree.name());
    }

    private static String potHarvestLootTable(RegisteredTree tree) {
        List<String> pools = new ArrayList<>();

        pools.add("""
                {
                    "rolls": 1,
                    "entries": [
                        { "type": "minecraft:item", "name": "minecraft:oak_log" }
                    ]
                }
                """);

        pools.add(potChanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_resin",
                tree.resinChance(), tree.resinMin(), tree.resinMax()));

        pools.add(potChanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_acorn",
                tree.acornChance(), tree.acornMin(), tree.acornMax()));

        pools.add(potChanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_sapling",
                tree.saplingChance(), tree.saplingMin(), tree.saplingMax()));

        for (RegisteredTree.ExtraDrop drop : tree.logExtraDrops()) {
            pools.add(potChanceDropPool(drop.itemId(), drop.chance(), drop.min(), drop.max()));
        }
        for (RegisteredTree.ExtraDrop drop : tree.leavesExtraDrops()) {
            pools.add(potChanceDropPool(drop.itemId(), drop.chance(), drop.min(), drop.max()));
        }

        return wrapPools(pools);
    }

    private static String potChanceDropPool(String itemId, double chance, int min, int max) {
        return """
                {
                    "rolls": 1,
                    "entries": [
                        {
                            "type": "minecraft:item",
                            "name": "%1$s",
                            "conditions": [
                                { "condition": "minecraft:random_chance", "chance": %2$s }
                            ],
                            "functions": [
                                { "function": "minecraft:set_count", "count": { "type": "minecraft:uniform", "min": %3$d, "max": %4$d } }
                            ]
                        }
                    ]
                }
                """.formatted(itemId, chance, min, max);
    }

    // ---------- generators: recipes ----------
    private static String recipeRoastedAcornSmelting(RegisteredTree tree){
        return """
                {
                  "type": "minecraft:smelting",
                  "experience": 0.15,
                  "cookingtime": 200,
                  "ingredient": {
                    "item": "%1$s:%2$s"
                  },
                  "result": {
                    "id": "%1$s:%3$s"
                  }
                }
                """.formatted(ResourceOrchards.MODID, tree.name() + "_acorn", tree.name() + "_roasted_acorn");
    }

    private static String recipeRoastedAcornCampfireCooking(RegisteredTree tree){
        return """
                {
                  "type": "minecraft:campfire_cooking",
                  "experience": 0.15,
                  "cookingtime": 600,
                  "ingredient": {
                    "item": "%1$s:%2$s"
                  },
                  "result": {
                    "id": "%1$s:%3$s"
                  }
                }
                """.formatted(ResourceOrchards.MODID, tree.name() + "_acorn", tree.name() + "_roasted_acorn");
    }

    // ---------- generators: loot tables ----------

    private static String lootTableDropSelf(String blockName) {
        return """
                {
                    "type": "minecraft:block",
                    "pools": [
                        {
                            "rolls": 1,
                            "entries": [
                                { "type": "minecraft:item", "name": "%1$s:%2$s" }
                            ]
                        }
                    ]
                }
                """.formatted(ResourceOrchards.MODID, blockName);
    }

    private static String lootTableLog(RegisteredTree tree) {
        List<String> pools = new ArrayList<>();

        pools.add("""
            {
                "rolls": 1,
                "entries": [
                    {
                        "type": "minecraft:item",
                        "name": "%1$s:%2$s_log",
                        "conditions": [ %3$s ]
                    }
                ]
            }
            """.formatted(
                ResourceOrchards.MODID,
                tree.name(),
                silkTouchCondition()
        ));

        pools.add(chanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_log",
                notSilkTouchCondition(),
                tree.logChance(),
                tree.logMin(),
                tree.logMax()
        ));

        pools.add(chanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_resin",
                notSilkTouchCondition(),
                tree.resinChance(),
                tree.resinMin(),
                tree.resinMax()
        ));

        for (RegisteredTree.ExtraDrop drop : tree.logExtraDrops()) {
            pools.add(chanceDropPool(
                    drop.itemId(),
                    notSilkTouchCondition(),
                    drop.chance(),
                    drop.min(),
                    drop.max()
            ));
        }

        return wrapPools(pools);
    }

    private static String lootTableLeaves(RegisteredTree tree) {
        List<String> pools = new ArrayList<>();

        pools.add("""
                {
                    "rolls": 1,
                    "entries": [
                        {
                            "type": "minecraft:item",
                            "name": "%1$s:%2$s_leaves",
                            "conditions": [ %3$s ]
                        }
                    ]
                }
                """.formatted(ResourceOrchards.MODID, tree.name(), shearsOrSilkTouchCondition()));

        pools.add(chanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_acorn",
                notShearsOrSilkTouchCondition(), tree.acornChance(), tree.acornMin(), tree.acornMax()));

        pools.add(chanceDropPool(
                ResourceOrchards.MODID + ":" + tree.name() + "_sapling",
                notShearsOrSilkTouchCondition(), tree.saplingChance(), tree.saplingMin(), tree.saplingMax()));

        for (RegisteredTree.ExtraDrop drop : tree.leavesExtraDrops()) {
            pools.add(chanceDropPool(drop.itemId(), notShearsOrSilkTouchCondition(), drop.chance(), drop.min(), drop.max()));
        }

        return wrapPools(pools);
    }

    private static String chanceDropPool(String itemId, String extraCondition, double chance, int min, int max) {
        return """
                {
                    "rolls": 1,
                    "entries": [
                        {
                            "type": "minecraft:item",
                            "name": "%1$s",
                            "conditions": [
                                %2$s,
                                { "condition": "minecraft:random_chance", "chance": %3$s }
                            ],
                            "functions": [
                                { "function": "minecraft:set_count", "count": { "type": "minecraft:uniform", "min": %4$d, "max": %5$d } }
                            ]
                        }
                    ]
                }
                """.formatted(itemId, extraCondition, chance, min, max);
    }

    private static String wrapPools(List<String> pools) {
        return """
                {
                    "type": "minecraft:block",
                    "pools": [
                        %1$s
                    ]
                }
                """.formatted(String.join(",\n", pools));
    }

    private static String silkTouchCondition() {
        return """
                {
                    "condition": "minecraft:match_tool",
                    "predicate": {
                        "predicates": {
                            "minecraft:enchantments": [
                                { "enchantment": "minecraft:silk_touch", "levels": { "min": 1 } }
                            ]
                        }
                    }
                }
                """;
    }

    private static String notSilkTouchCondition() {
        return """
                { "condition": "minecraft:inverted", "term": %1$s }
                """.formatted(silkTouchCondition());
    }

    private static String shearsOrSilkTouchCondition() {
        return """
                {
                    "condition": "minecraft:any_of",
                    "terms": [
                        {
                            "condition": "minecraft:match_tool",
                            "predicate": { "items": "minecraft:shears" }
                        },
                        %1$s
                    ]
                }
                """.formatted(silkTouchCondition());
    }

    private static String notShearsOrSilkTouchCondition() {
        return """
                { "condition": "minecraft:inverted", "term": %1$s }
                """.formatted(shearsOrSilkTouchCondition());
    }

    private static String tagValues(String suffix) {
        StringBuilder sb = new StringBuilder("{\n    \"values\": [\n");
        boolean first = true;
        for (RegisteredTree tree : TreeRegistry.getTrees().values()) {
            if (!first) sb.append(",\n");
            sb.append("        \"").append(ResourceOrchards.MODID).append(":").append(tree.name()).append(suffix).append("\"");
            first = false;
        }
        sb.append("\n    ]\n}");
        return sb.toString();
    }

    private DynamicTreeAssets() {}
}