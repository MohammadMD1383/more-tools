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

The `defense` map is provided via `makeDefense(boots, leggings, chestplate, helmet, body)` — this is the `ArmorType` enum order (verified from bytecode), **not** helmet-first. The table below lists values helmet-first for readability, so converting a table row to a call means reversing the first four: e.g. DIAMOND `[3, 8, 6, 3]` → `makeDefense(3, 6, 8, 3, 11)`. Body defense (used by wolf/horse/nautilus armor) is omitted from the table; diamond's is `11`.

| Material | Durability Multiplier | Defense (Helmet, Chestplate, Leggings, Boots) | Enchantability | Toughness | Knockback Resistance | Equip Sound | Repair Ingredient Tag | Equipment Asset Key |
| :--- | :---: | :---: | :---: | :---: | :---: | :--- | :--- | :--- |
| **LEATHER** | 5 | [1, 3, 2, 1] | 15 | 0.0f | 0.0f | `ARMOR_EQUIP_LEATHER` | `REPAIRS_LEATHER_ARMOR` | `LEATHER` |
| **COPPER** | 11 | [2, 4, 3, 1] | 8 | 0.0f | 0.0f | `ARMOR_EQUIP_COPPER` | `REPAIRS_COPPER_ARMOR` | `COPPER` |
| **CHAINMAIL** | 15 | [2, 5, 4, 1] | 12 | 0.0f | 0.0f | `ARMOR_EQUIP_CHAIN` | `REPAIRS_CHAIN_ARMOR` | `CHAINMAIL` |
| **IRON** | 15 | [2, 6, 5, 2] | 9 | 0.0f | 0.0f | `ARMOR_EQUIP_IRON` | `REPAIRS_IRON_ARMOR` | `IRON` |
| **GOLD** | 7 | [2, 5, 3, 1] | 25 | 0.0f | 0.0f | `ARMOR_EQUIP_GOLD` | `REPAIRS_GOLD_ARMOR` | `GOLD` |
| **DIAMOND** | 33 | [3, 8, 6, 3] | 10 | 2.0f | 0.0f | `ARMOR_EQUIP_DIAMOND` | `REPAIRS_DIAMOND_ARMOR` | `DIAMOND` |
| **TURTLE_SCUTE** | 25 | [2, 6, 5, 2] | 9 | 0.0f | 0.0f | `ARMOR_EQUIP_TURTLE` | `REPAIRS_TURTLE_HELMET` | `TURTLE_SCUTE` |
| **NETHERITE** | 37 | [3, 8, 6, 3] | 15 | 3.0f | 0.1f | `ARMOR_EQUIP_NETHERITE` | `REPAIRS_NETHERITE_ARMOR` | `NETHERITE` |
| **ARMADILLO_SCUTE** | 4 | [3, 8, 6, 3] | 10 | 0.0f | 0.0f | `ARMOR_EQUIP_WOLF` | `REPAIRS_WOLF_ARMOR` | `ARMADILLO_SCUTE` |

> *Note: ARMADILLO_SCUTE represents Wolf Armor, which also uses ArmorMaterial.*

---

## 3. Formulas and Mechanics

- **Tool Durability**: Actual number of times an item can be used. Assigned directly to the `ToolMaterial` record.
- **Tool Mining Speed**: Multiplier applied when mining blocks.
- **Tool Attack Damage Bonus**: Base Attack Damage added to the player's 1.0 base damage (i.e., `Total Damage = 1.0 + attackDamageBonus`).
- **Armor Durability Calculation**: Actual durability for a specific armor piece = `Material Durability` × `ArmorType Durability Multiplier` (Helmet = 13, Boots = 13, Leggings = 15, Chestplate = 16).
- **Toughness**: Reduces the effect of high-damage attacks on armor durability reduction.
- **Knockback Resistance**: Reduces knockback taken (Netherite = 10% knockback resistance, others = 0).
