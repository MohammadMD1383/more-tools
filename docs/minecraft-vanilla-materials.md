# Minecraft Vanilla Materials Reference (v1.21.11 / Yarn `26.2`)

This document contains extracted data regarding Minecraft's native tool and armor tiers, durability values, speed multipliers, attack damages, defense values, and enchantability ratings, as extracted directly from the yarn-named classes (`minecraft-common-043a8b3edf-26.2.jar`).

---

## 1. Tool Materials (`net.minecraft.world.item.ToolMaterial`)

Constructor parameters: `ToolMaterial(TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems)`

| Tier | Durability | Mining Speed | Attack Damage Bonus | Enchantability | Incorrect Block Tag | Repair Items Tag |
| :--- | :---: | :---: | :---: | :---: | :--- | :--- |
| **WOOD** | 59 | 2.0f | 0.0f | 15 | `INCORRECT_FOR_WOODEN_TOOL` | `WOODEN_TOOL_MATERIALS` |
| **STONE** | 131 | 4.0f | 1.0f | 5 | `INCORRECT_FOR_STONE_TOOL` | `STONE_TOOL_MATERIALS` |
| **COPPER** | 190 | 5.0f | 1.0f | 13 | `INCORRECT_FOR_COPPER_TOOL` | `COPPER_TOOL_MATERIALS` |
| **IRON** | 250 | 6.0f | 2.0f | 14 | `INCORRECT_FOR_IRON_TOOL` | `IRON_TOOL_MATERIALS` |
| **DIAMOND** | 1561 | 8.0f | 3.0f | 10 | `INCORRECT_FOR_DIAMOND_TOOL` | `DIAMOND_TOOL_MATERIALS` |
| **GOLD** | 32 | 12.0f | 0.0f | 22 | `INCORRECT_FOR_GOLD_TOOL` | `GOLD_TOOL_MATERIALS` |
| **NETHERITE** | 2031 | 9.0f | 4.0f | 15 | `INCORRECT_FOR_NETHERITE_TOOL` | `NETHERITE_TOOL_MATERIALS` |

---

## 2. Armor Materials (`net.minecraft.world.item.equipment.ArmorMaterials`)

Constructor parameters: `ArmorMaterial(int durability, Map<ArmorType, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, TagKey<Item> repairIngredient, ResourceKey<EquipmentAsset> assetId)`

The `defense` map is provided via `makeDefense(boots, leggings, chestplate, helmet, body)` — this is the `ArmorType` enum order (verified from bytecode), **not** helmet-first. The table below lists the **exact `makeDefense` argument order**, so a row can be copied straight into a call: DIAMOND → `makeDefense(3, 6, 8, 3, 11)`.

`body` is used by wolf / horse / nautilus armor. Note netherite's body value (`19`) is much higher than its humanoid values would suggest.

| Material | Durability Multiplier | `makeDefense(boots, leggings, chestplate, helmet, body)` | Enchantability | Toughness | Knockback Resistance | Equip Sound | Repair Ingredient Tag | Equipment Asset Key |
| :--- | :---: | :---: | :---: | :---: | :---: | :--- | :--- | :--- |
| **LEATHER** | 5 | `(1, 2, 3, 1, 3)` | 15 | 0.0f | 0.0f | `ARMOR_EQUIP_LEATHER` | `REPAIRS_LEATHER_ARMOR` | `LEATHER` |
| **COPPER** | 11 | `(1, 3, 4, 2, 4)` | 8 | 0.0f | 0.0f | `ARMOR_EQUIP_COPPER` | `REPAIRS_COPPER_ARMOR` | `COPPER` |
| **CHAINMAIL** | 15 | `(1, 4, 5, 2, 4)` | 12 | 0.0f | 0.0f | `ARMOR_EQUIP_CHAIN` | `REPAIRS_CHAIN_ARMOR` | `CHAINMAIL` |
| **IRON** | 15 | `(2, 5, 6, 2, 5)` | 9 | 0.0f | 0.0f | `ARMOR_EQUIP_IRON` | `REPAIRS_IRON_ARMOR` | `IRON` |
| **GOLD** | 7 | `(1, 3, 5, 2, 7)` | 25 | 0.0f | 0.0f | `ARMOR_EQUIP_GOLD` | `REPAIRS_GOLD_ARMOR` | `GOLD` |
| **DIAMOND** | 33 | `(3, 6, 8, 3, 11)` | 10 | 2.0f | 0.0f | `ARMOR_EQUIP_DIAMOND` | `REPAIRS_DIAMOND_ARMOR` | `DIAMOND` |
| **TURTLE_SCUTE** | 25 | `(2, 5, 6, 2, 5)` | 9 | 0.0f | 0.0f | `ARMOR_EQUIP_TURTLE` | `REPAIRS_TURTLE_HELMET` | `TURTLE_SCUTE` |
| **NETHERITE** | 37 | `(3, 6, 8, 3, 19)` | 15 | 3.0f | 0.1f | `ARMOR_EQUIP_NETHERITE` | `REPAIRS_NETHERITE_ARMOR` | `NETHERITE` |
| **ARMADILLO_SCUTE** | 4 | `(3, 6, 8, 3, 11)` | 10 | 0.0f | 0.0f | `ARMOR_EQUIP_WOLF` | `REPAIRS_WOLF_ARMOR` | `ARMADILLO_SCUTE` |

> *Note: ARMADILLO_SCUTE represents Wolf Armor, which also uses ArmorMaterial.*

---

## 3. Formulas and Mechanics

- **Tool Durability**: Actual number of times an item can be used. Assigned directly to the `ToolMaterial` record.
- **Tool Mining Speed**: Multiplier applied when mining blocks.
- **Tool Attack Damage Bonus**: Base Attack Damage added to the player's 1.0 base damage (i.e., `Total Damage = 1.0 + attackDamageBonus`).
- **Armor Durability Calculation**: Actual durability for a specific armor piece = `Material Durability` × `ArmorType Durability Multiplier` (Helmet = 13, Boots = 13, Leggings = 15, Chestplate = 16).
- **Toughness**: Reduces the effect of high-damage attacks on armor durability reduction.
- **Knockback Resistance**: Reduces knockback taken (Netherite = 10% knockback resistance, others = 0).

---

## 4. Per-item tool stats (`damage`, `attackSpeed`)

`ToolMaterial` carries only the material-level numbers. The **per-item** attack values are passed separately to
each `Item.Properties` factory — `sword(material, damage, attackSpeed)`, `axe(...)`, `hoe(...)`, etc.
These are *not* in the `ToolMaterial` record and differ per tool type (and, for axes and hoes, per tier).

Extracted from `Items.class` / the `AxeItem`, `ShovelItem`, `HoeItem` constructor call sites.

**Uniform across every tier:**

| Tool | `damage` | `attackSpeed` |
| :--- | :---: | :---: |
| Sword | `3.0f` | `-2.4f` |
| Pickaxe | `1.0, -2.8` → **2.5** / 1.2 | Pickaxe | `1.0f` | `-2.8f` |
| Shovel | `1.5, -3.0` → **3** / 1.0 | Shovel | `1.5f` | `-3.0f` |

**Per-tier (axe and hoe only):**

| Tier | Axe `damage` | Axe `attackSpeed` | Hoe `damage` | Hoe `attackSpeed` |
| :--- | :---: | :---: | :---: | :---: |
| **WOOD** | `6.0f` | `-3.2f` | `0.0f` | `-3.0f` |
| **STONE** | `7.0f` | `-3.2f` | `-1.0f` | `-2.0f` |
| **COPPER** | `7.0f` | `-3.2f` | `-1.0f` | `-2.0f` |
| **IRON** | `6.0f` | `-3.1f` | `-2.0f` | `-1.0f` |
| **GOLD** | `6.0f` | `-3.0f` | `0.0f` | `-3.0f` |
| **DIAMOND** | `5.0f` | `-3.0f` | `-3.0f` | `0.0f` |
| **NETHERITE** | `5.0f` | `-3.0f` | `-4.0f` | `0.0f` |

**Slowest `attackSpeed` vanilla uses per tool type** — useful when a material is meant to be "slower than
everything": sword `-2.4f`, pickaxe `-2.8f`, shovel `-3.0f`, axe `-3.2f`, hoe `-3.0f`.

### Attack damage formula

```
displayed attack damage = 1.0 (player base) + per-item damage + ToolMaterial.attackDamageBonus
```

Verified: diamond sword = `1.0 + 3.0 + 3.0` = **7**; stone axe = `1.0 + 7.0 + 1.0` = **9**; golden hoe = `1.0 + 0.0 + 0.0` = **1**.

So to make a custom material hit exactly like a vanilla tier, copy **both** that tier's `attackDamageBonus`
*and* its per-item `damage` values.

`attackSpeed` is a modifier added to the player's base attack speed of `4.0`.

---

## 5. Spear stats (`spear(material, f1 … f9)`)

`Properties.spear` takes **nine** floats. Their meanings, decompiled from `Item$Properties.spear`:

| Param | Meaning |
| :---: | :--- |
| `f1` | **Seconds per attack.** Drives `SwingAnimation(STAB, f1 * 20)` ticks and `ATTACK_SPEED = (1 / f1) - 4.0`. **Higher = slower.** |
| `f2` | `KineticWeapon` damage-scaling float (paired with a hardcoded `0.38f`) |
| `f3` | Charge-up time in seconds (`f3 * 20` ticks) |
| `f4`, `f5` | `KineticWeapon.Condition.ofAttackerSpeed(f4 * 20, f5)` |
| `f6`, `f7` | `KineticWeapon.Condition.ofAttackerSpeed(f6 * 20, f7)` |
| `f8`, `f9` | `KineticWeapon.Condition.ofRelativeSpeed(f8 * 20, f9)` |

Spear attack damage is `1.0 + 0.0 + material.attackDamageBonus` — the factory hardcodes the per-item damage to `0`.

| Tier | f1 | f2 | f3 | f4 | f5 | f6 | f7 | f8 | f9 |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **WOOD** | 0.65 | 0.70 | 0.75 | 5.0 | 14.0 | 10.00 | 5.1 | 15.00 | 4.6 |
| **STONE** | 0.75 | 0.82 | 0.70 | 4.5 | 13.0 | 9.00 | 5.1 | 13.75 | 4.6 |
| **COPPER** | 0.85 | 0.82 | 0.65 | 4.0 | 12.0 | 8.25 | 5.1 | 12.50 | 4.6 |
| **IRON** | 0.95 | 0.95 | 0.60 | 2.5 | 11.0 | 6.75 | 5.1 | 11.25 | 4.6 |
| **GOLD** | 0.95 | 0.70 | 0.70 | 3.5 | 13.0 | 8.50 | 5.1 | 13.75 | 4.6 |
| **DIAMOND** | 1.05 | 1.075 | 0.50 | 3.0 | 10.0 | 6.50 | 5.1 | 10.00 | 4.6 |
| **NETHERITE** | 1.15 | 1.20 | 0.40 | 2.5 | 9.0 | 5.50 | 5.1 | 8.75 | 4.6 |

Vanilla's slowest-attacking spear is netherite at `f1 = 1.15`.

---

## 6. This mod's custom materials

Stats live in `ToolMaterials.kt` / `ArmorMaterials.kt` — the tables below are copies for balancing
convenience; the code is authoritative. Durability progression:
`gold 32 < lapis 50 < wood 59 < stone 131 < copper 190 < iron 250 < amethyst 350 < emerald 600 < obsidian 800 < quartz 1050 < diamond 1561`.

### 6a. Mod tool materials

`ToolMaterial(incorrectBlocksTag, durability, speed, attackDamageBonus, enchantmentValue, repairTag)`.
Mining tier is set by chaining: lapis `addOptionalTag(INCORRECT_FOR_COPPER_TOOL)`,
amethyst/emerald/obsidian `addOptionalTag(INCORRECT_FOR_IRON_TOOL)`,
quartz `addOptionalTag(INCORRECT_FOR_DIAMOND_TOOL)` (see `BlockTagsProvider.kt`).

| Material | Incorrect-blocks tag | Durability | Speed | Dmg bonus | Ench. | Repair tag → ingredient |
| :--- | :--- | ---: | ---: | ---: | ---: | :--- |
| **LAPIS** | `incorrect_for_lapis_tool` (→ copper) | 50 | 3.0 | 0.5 | 1 + `nonEnchantable()` | `LAPIS_TOOL_MATERIALS` (`repairs_lapis_armor`) → `LAPIS_LAZULI` |
| **AMETHYST** | `incorrect_for_amethyst_tool` (→ iron) | 350 | 6.0 | 2.0 | 25 | `AMETHYST_TOOL_MATERIALS` (`repairs_amethyst_armor`) → `AMETHYST_SHARD` |
| **EMERALD** | `incorrect_for_emerald_tool` (→ iron) | 600 | 8.0 | 3.0 | 3 | `EMERALD_TOOL_MATERIALS` (`repairs_emerald_armor`) → `EMERALD` |
| **OBSIDIAN** | `incorrect_for_obsidian_tool` (→ iron) | 800 | 4.0 | 1.0 | 1 + `nonEnchantable()` | `OBSIDIAN_TOOL_MATERIALS` (`repairs_obsidian_armor`) → `BlockItemIds.OBSIDIAN.item()` (block item, no `ItemIds.OBSIDIAN`) |
| **QUARTZ** | `incorrect_for_quartz_tool` (→ diamond) | 1050 | 12.0 | 0.0 | 10 | `QUARTZ_TOOL_MATERIALS` (`repairs_quartz_armor`) → `QUARTZ` |

Note the naming quirk: the constants are called `*_TOOL_MATERIALS` but their tag paths are
`repairs_*_armor` (vanilla convention), and both tools and armor materials reference them.

### 6b. Mod armor materials

`ArmorMaterial(durabilityMult, makeDefense(boots, leggings, chestplate, helmet, body), enchantmentValue,
equipSound, toughness, knockbackResistance, repairTag, equipmentAsset)`.

| Material | Dur. mult. | `makeDefense(...)` | Ench. | Sound | Tough. | KB resist | Asset |
| :--- | ---: | :--- | ---: | :--- | ---: | ---: | :--- |
| **LAPIS** | 9 | (1, 3, 4, 2, 5) — copper defense, body 5 (copper 4 / gold 7) | 1 + `nonEnchantable()` | `ARMOR_EQUIP_GOLD` | 0.0 | 0.0 | `LAPIS` |
| **AMETHYST** | 19 | (2, 5, 6, 2, 5) = iron | 28 | `ARMOR_EQUIP_DIAMOND` | 0.0 | 0.0 | `AMETHYST` |
| **EMERALD** | 21 | (3, 6, 8, 3, 11) = diamond | 3 | `ARMOR_EQUIP_DIAMOND` | 0.0 | 0.0 | `EMERALD` |
| **OBSIDIAN** | 25 | (3, 6, 8, 3, 11) = diamond | 1 + `nonEnchantable()` | `ARMOR_EQUIP_NETHERITE` | 1.0 | 0.15 | `OBSIDIAN` |
| **QUARTZ** | 29 | (1, 3, 5, 2, 7) = gold | 10 | `ARMOR_EQUIP_DIAMOND` | 0.0 | 0.0 | `QUARTZ` |

### 6c. Mod per-item stats (from `Items.kt`)

Formulae are §4's: damage = `1.0 + damage + bonus`; speed = `4.0 + attackSpeed`.

| Item | Lapis (+0.5) `damage` / speed → dmg / rate | Amethyst (+2) `damage` / speed → dmg / rate | Emerald (+3) | Obsidian (+1) | Quartz (+0) |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Sword | `3.0, -2.4` → **4.5** / 1.6 | `2.0, -2.2` → **5** / 1.8 (+KB, +sweep) | `3.0, -2.4` → **7** / 1.6 | `3.0, -2.8` → **5** / 1.2 | `3.0, -2.4` → **4** / 1.6 |
| Pickaxe | `1.0, -2.8` → **2.5** / 1.2 | `1.0, -2.6` → **4** / 1.4 (+KB) | `1.0, -2.8` → **5** / 1.2 | `1.0, -3.1` → **3** / 0.9 | `1.0, -2.8` → **2** / 1.2 |
| Axe | `6.5, -3.2` → **8** / 0.8 | `6.0, -2.9` → **9** / 1.1 (+KB) | `5.0, -3.0` → **9** / 1.0 | `7.0, -3.4` → **9** / 0.6 | `6.0, -3.0` → **7** / 1.0 |
| Shovel | `1.5, -3.0` → **3** / 1.0 | `1.5, -2.8` → **4.5** / 1.2 (+KB) | `1.5, -3.0` → **5.5** / 1.0 | `1.5, -3.3` → **3.5** / 0.7 | `1.5, -3.0` → **2.5** / 1.0 |
| Hoe | `-0.5, -2.5` → **1** / 1.5 | `-2.0, 0.0` → **1** / 4.0 (+KB) | `-3.0, 0.0` → **1** / 4.0 | `-1.0, -3.3` → **1** / 0.7 | `0.0, 0.0` → **1** / 4.0 |

All lapis and obsidian items chain `.nonEnchantable()`; all quartz tools chain `.quartzMiningEfficiency(...)`
with their per-tool tag pair. Amethyst custom modifiers: `ATTACK_KNOCKBACK +1.0 ADD_VALUE MAINHAND`
on every tool + spear (`amethystKnockback()`), `SWEEPING_DAMAGE_RATIO +0.35 MAINHAND` on the sword
only (`amethystSweepDamage()`). Obsidian armor pieces chain `.obsidianMovementSpeed(...)` with a
unique id per slot (`AttributeIds.OBSIDIAN_MOVEMENT_SPEED_*`).

### 6d. Mod spear params (`spear(material, f1 … f9)` — see §5 for meanings)

| Material | f1 (s/attack) | f2 | f3 | f4 | f5 | f6 | f7 | f8 | f9 | Extra |
| :--- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: | :--- |
| **LAPIS** | 0.70 | 0.760 | 0.72 | 4.75 | 13.5 | 9.50 | 5.1 | 14.375 | 4.6 | wood↔stone midpoint, `nonEnchantable()` |
| **AMETHYST** | 0.75 | 0.950 | 0.4 | 3.5 | 9.0 | 7.75 | 4.0 | 12.0 | 4.6 | +KB |
| **EMERALD** | 1.05 | 1.075 | 0.5 | 3.0 | 10.0 | 6.50 | 5.1 | 10.0 | 4.6 | = diamond row |
| **OBSIDIAN** | 1.25 | 0.820 | 0.7 | 4.5 | 13.0 | 9.00 | 5.1 | 13.75 | 4.6 | = stone row, `nonEnchantable()` |
| **QUARTZ** | 1.05 | 0.700 | 0.7 | 3.5 | 13.0 | 8.50 | 5.1 | 13.75 | 4.6 | = gold row except f1 |

Spear damage = `1.0 + bonus`: lapis 1.5, amethyst 3, emerald 4, obsidian 2, quartz 1.

### 6e. Design intent that the numbers alone don't convey

- **Lapis** — the weakest tier in the mod, positioned between *gold and wood*: gold durability (32) is
  the only thing below lapis's 50, while its mining speed (3.0) and damage bonus (0.5) sit between wood
  and stone. Repairing material is plain `minecraft:lapis_lazuli`. Fully unenchantable
  (enchantability is *intended* to be 0 but is `1` in code — see `minecraft-internals.md`).
  The odd `0.5` damage bonus makes every tool display a half-heart damage value (sword 4.5, axe 8).
  ⚠️ `incorrect_for_copper_tool` and `incorrect_for_stone_tool` are **byte-identical** in 26.2 (both are
  just `#needs_diamond_tool` + `#needs_iron_tool`), so the copper chain gives lapis exactly the stone
  harvest level: it mines iron/lapis/copper ore but **not** gold, redstone, diamond or emerald ore
  (those are `#needs_iron_tool`).
- **Obsidian** — pinned to *stone* for damage and mining speed, *iron* mining tier, *diamond* armor defense.
  Its per-item `attackSpeed` values sit below every vanilla floor listed in §4 (and its spear `f1 = 1.25` above
  netherite's `1.15`), making it deliberately the slowest-swinging material in the game. Fully unenchantable (table, books and anvils);
  enchantability is *intended* to be 0 but is `1` in code — see `minecraft-internals.md`.
- **Quartz** — *gold* damage and mining speed with *diamond* `attackSpeed`, enchantability and mining tier.
  Its extra tool rules mine `*_instant` blocks at speed **100** and `*_fast` blocks at speed **16**
  (above its own base 12) — see `minecraft-internals.md` for the rule/tag mechanism.
