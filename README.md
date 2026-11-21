# Resource Orchards
 
Resource Orchards is a small Minecraft mod that lets you define **custom resource trees** with KubeJS.
Every tree gets its own tinted log, leaves, sapling, acorn, resin and more, with fully configurable drops.
The trees work in the world and in **Botany Pots**.
 
This is a port / fork of [Sky Orchards](https://www.curseforge.com/minecraft/mc-mods/sky-orchards) by [vadis365](https://www.curseforge.com/members/vadis365).
 
> **Note for modpack developers:** This mod is intended for modpack makers and mod developers, not as a standalone content mod. It only provides the trees and their drops. It does **not** add recipes that use acorns, resin, amber or the other items for anything (apart from roasting acorns). What these items are used for is up to you, for example through KubeJS recipes.
 
## Features
 
* Register as many trees as you like from a KubeJS startup script. No Java needed.
* Each tree automatically gets:
  * Tinted **log**, **leaves** and **sapling** (colored with the color you choose)
  * **Acorn**, **roasted acorn** (smelting / campfire cooking) and **resin**
  * **Amber** block
  * A Botany Pots **crop**, so the tree can be grown in a pot
  * Worldgen so saplings grow into a tree
* Configurable drops (chance, min and max) for every tree:
  * **Log** and **resin** (and extra drops) drop from the **trunk**
  * **Acorn** and **sapling** (and extra drops) drop from the **leaves**
  * Silk Touch / shears drop the block itself
* **JEI integration**: a "Tree Drops" category shows what a tree gives you. Hover over an output to see the chance, the amount and whether it comes from the trunk or the leaves.
* Own creative tab.
## Requirements
 
* Minecraft 1.21.1 & NeoForge
* [KubeJS](https://www.curseforge.com/minecraft/mc-mods/kubejs) (used to define the trees)
* [Botany Pots](https://www.curseforge.com/minecraft/mc-mods/botany-pots) (optional, for growing the trees in pots)
* [JEI](https://www.curseforge.com/minecraft/mc-mods/jei) (optional, for the drop overview)
## Usage
 
Trees are registered in a KubeJS **startup script**, for example `kubejs/startup_scripts/trees.js`:
 
```js
// registerTree(name, color)  ->  color is an RGB value (0xRRGGBB)
ResourceOrchards.registerTree('iron', 0xD8AF93)
    .log(1, 1, 1.0)          // min, max, chance
    .resin(1, 3, 0.8)
    .acorn(1, 3, 0.8)
    .sapling(1, 1, 0.05)
    .extraLogDrop('minecraft:raw_iron', 1, 2, 0.25)       // itemId, min, max, chance
    .extraLeavesDrop('minecraft:iron_nugget', 1, 3, 0.4)
```
 
All drop methods are optional. If you leave one out, the default is used.
 
| Method | Drops from | Default (min / max / chance) |
|---|---|---|
| `log(min, max, chance)` | Log | 1 / 1 / 100 % |
| `resin(min, max, chance)` | Log | 1 / 3 / 80 % |
| `acorn(min, max, chance)` | Leaves | 1 / 3 / 80 % |
| `sapling(min, max, chance)` | Leaves | 1 / 1 / 5 % |
| `extraLogDrop(itemId, min, max, chance)` | Log | - |
| `extraLeavesDrop(itemId, min, max, chance)` | Leaves | - |
 
Setting a chance or max of `0` removes that drop.
 
**Note:** In a Botany Pot, all drops of a tree come from a single loot table, so log and leaves are not separated there.
 
## License
 
All Rights Reserved.
 
## Credits
 
* Port / fork of [Sky Orchards](https://www.curseforge.com/minecraft/mc-mods/sky-orchards) by [vadis365](https://www.curseforge.com/members/vadis365)
* Author: TheTechnicBoy
 