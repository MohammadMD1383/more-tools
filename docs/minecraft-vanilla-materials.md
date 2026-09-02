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
| Pickaxe | `1.0f` | `-2.8f` |
| Shovel | `1.5f` | `-3.0f` |

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

Stats live in `ToolMaterials.kt` / `ArmorMaterials.kt` — read those, not a copy here. Durability progression:
`iron 250 < amethyst 350 < emerald 600 < obsidian 800 < quartz 1050 < diamond 1561`.

Design intent that the numbers alone don't convey:

- **Obsidian** — pinned to *stone* for damage and mining speed, *iron* mining tier, *diamond* armor defense.
  Its per-item `attackSpeed` values sit below every vanilla floor listed in §4 (and its spear `f1 = 1.25` above
  netherite's `1.15`), making it deliberately the slowest-swinging material in the game. Fully unenchantable (table, books and anvils);
  enchantability is *intended* to be 0 but is `1` in code — see `minecraft-internals.md`.
- **Quartz** — *gold* damage and mining speed with *diamond* `attackSpeed`, enchantability and mining tier.
