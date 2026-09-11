# Lapis Effect — Building Blocks

Which vanilla enchantments can apply to each item type, and the concrete per-level
effect numbers (ranges) those enchantments produce. Source: 26.2 vanilla
`data/minecraft/enchantment/*.json` + `data/minecraft/tags/item/enchantable/*.json`
in `minecraft-common.jar` (see `vanilla-enchantment-system-26.2.md` for the full system map).

Method: an enchantment applies to an item iff the item is in the enchantment's
`supported_items` tag (tags resolved transitively). Resolved expansions used below:

- `melee_weapon` = swords + spears; `sharp_weapon` = melee + axes;
  `weapon` = sharp + mace; `fire_aspect` tag = melee + mace; `sweeping` = swords;
  `lunge` = spears.
- `mining` / `mining_loot` = axes + pickaxes + shovels + hoes (+shears for `mining` only).
- `durability` = humanoid armor + elytra + shield + swords/axes/pickaxes/shovels/hoes +
  bow/crossbow/trident/fishing_rod/mace/spears + misc tools.
- `armor` = 4 humanoid slots; `equippable` = humanoid armor + elytra + skulls + pumpkin;
  `vanishing` = durability + compass + pumpkin + skulls.
- **Wolf armor, horse armors, nautilus armor are in NO `enchantable/*` tag**
  (wolf_armor only in `cauldron_can_remove_dye`; horse/nautilus in none) → **zero
  vanilla enchantments apply**; there is nothing to borrow for them.

## Part A — Enchantments per item

| Item | Applicable enchantments |
| ---- | ----------------------- |
| sword | sharpness 5, smite 5, bane_of_arthropods 5, knockback 2, fire_aspect 2, looting 3, sweeping_edge 3, unbreaking 3, mending 1, vanishing_curse 1 |
| spear | sharpness 5, smite 5, bane_of_arthropods 5, knockback 2, fire_aspect 2, looting 3, lunge 3, unbreaking 3, mending 1, vanishing_curse 1 |
| axe | sharpness 5, smite 5, bane_of_arthropods 5, efficiency 5, fortune 3, silk_touch 1, unbreaking 3, mending 1, vanishing_curse 1 |
| pickaxe | efficiency 5, fortune 3, silk_touch 1, unbreaking 3, mending 1, vanishing_curse 1 |
| hoe | efficiency 5, fortune 3, silk_touch 1, unbreaking 3, mending 1, vanishing_curse 1 |
| shovel | efficiency 5, fortune 3, silk_touch 1, unbreaking 3, mending 1, vanishing_curse 1 |
| helmet | protection 4, fire_protection 4, blast_protection 4, projectile_protection 4, respiration 3, aqua_affinity 1, thorns 3, unbreaking 3, mending 1, binding_curse 1, vanishing_curse 1 |
| chestplate | protection 4, fire_protection 4, blast_protection 4, projectile_protection 4, thorns 3, unbreaking 3, mending 1, binding_curse 1, vanishing_curse 1 |
| leggings | protection 4, fire_protection 4, blast_protection 4, projectile_protection 4, thorns 3, swift_sneak 3, unbreaking 3, mending 1, binding_curse 1, vanishing_curse 1 |
| boots | protection 4, fire_protection 4, blast_protection 4, projectile_protection 4, feather_falling 4, thorns 3, depth_strider 3, frost_walker 2, soul_speed 3, unbreaking 3, mending 1, binding_curse 1, vanishing_curse 1 |
| wolf_armor | — (none) |
| horse_armor | — (none) |
| nautilus_armor | — (none) |

Notes: mace-only enchantments (density 5, breach 4, wind_burst 3) and
bow/crossbow/trident/fishing enchantments fit no listed item. Thorns (slot `any`)
fits all four armor pieces. Binding (slot `armor`) fits the four humanoid pieces.

## Part B — Effect blocks per item (level 1 → max level)

Ranges computed from each JSON's level providers (`linear base+per`, `fraction`,
`levels_squared`, fixed values). Durations in seconds (20 ticks/s).

### SWORD

- sharpness damage bonus: +1.0 → +3.0 per hit (levels 1–5, +0.5/lvl)
- smite damage bonus vs undead (`#sensitive_to_smite`): +2.5 → +12.5 (1–5)
- bane damage bonus vs arthropods: +2.5 → +12.5 (1–5)
- bane slowness on hit: 1.5s → 3.5s, amplifier 3 fixed (1–5)
- knockback strength: +1 → +2 (levels 1–2)
- fire_aspect ignite: 4s (80 ticks) → 8s (160 ticks) (levels 1–2; direct hits only)
- looting equipment-drop chance (player kills): +1% → +3% (levels 1–3; main loot bonus is loot-table-driven)
- sweeping_edge sweep ratio: 0.50 → 0.75 (levels 1–3)
- unbreaking ignore-chance per durability point: 50% → 75% (levels 1–3, non-armor branch)
- mending XP→durability rate: ×2.0 fixed (level 1)
- vanishing_curse: item destroyed on death (no levels)

### SPEAR

- sharpness damage bonus: +1.0 → +3.0 (1–5)
- smite damage bonus vs undead: +2.5 → +12.5 (1–5)
- bane damage bonus vs arthropods: +2.5 → +12.5 (1–5)
- bane slowness on hit: 1.5s → 3.5s, amplifier 3 fixed (1–5)
- knockback strength: +1 → +2 (1–2)
- fire_aspect ignite: 4s → 8s (1–2)
- looting equipment-drop chance: +1% → +3% (1–3)
- lunge impulse magnitude: 0.458 → 1.374 (levels 1–3)
- lunge exhaustion cost: 4 → 12 (1–3; +1 item damage and sound, fixed)
- unbreaking ignore-chance: 50% → 75% (1–3)
- mending rate: ×2.0 fixed
- vanishing_curse: destroyed on death

### AXE

- sharpness damage bonus: +1.0 → +3.0 (1–5)
- smite damage bonus vs undead: +2.5 → +12.5 (1–5)
- bane damage bonus vs arthropods: +2.5 → +12.5 (1–5)
- bane slowness: 1.5s → 3.5s, amplifier 3 (1–5)
- efficiency mining speed (`mining_efficiency` attr): +2 → +26 (levels 1–5, lvl²+1)
- fortune loot bonus: loot-table-driven, no numeric component (levels 1–3)
- silk_touch: block XP set to 0; drops block itself via loot tables (level 1)
- unbreaking ignore-chance: 50% → 75% (1–3)
- mending rate: ×2.0 fixed
- vanishing_curse: destroyed on death

### PICKAXE / HOE / SHOVEL (identical sets)

- efficiency mining speed: +2 → +26 (1–5)
- fortune loot bonus: loot-table-driven (1–3)
- silk_touch: block XP 0; self-drop via loot tables (level 1)
- unbreaking ignore-chance: 50% → 75% (1–3)
- mending rate: ×2.0 fixed
- vanishing_curse: destroyed on death

### HELMET

- protection (all damage): +1 → +4 EPF contribution (levels 1–4)
- fire_protection: +2 → +8 (1–4); burning-time ×(1 − 0.15 → 0.60) reduction
- blast_protection: +2 → +8 (1–4); explosion-knockback resistance +0.15 → +0.60
- projectile_protection: +2 → +8 (1–4)
- respiration oxygen bonus: +1 → +3 (levels 1–3)
- aqua_affinity underwater mining speed: +4.0 fixed (level 1)
- thorns retaliation chance: 15% → 45% (levels 1–3); thorns damage 1–5 fixed; costs 2 durability when it triggers
- unbreaking ignore-chance (armor branch): 20% → 30% (levels 1–3: 2/10 → 6/20)
- mending rate: ×2.0 fixed
- binding_curse: cannot be removed once worn (no levels)
- vanishing_curse: destroyed on death

### CHESTPLATE

- protection: +1 → +4 (1–4)
- fire_protection: +2 → +8 (1–4); burning-time reduction as helmet
- blast_protection: +2 → +8 (1–4); knockback resistance as helmet
- projectile_protection: +2 → +8 (1–4)
- thorns: 15% → 45% chance; 1–5 damage; 2 durability per trigger (1–3)
- unbreaking (armor branch): 20% → 30% (1–3)
- mending rate: ×2.0 fixed
- binding_curse: cannot be removed
- vanishing_curse: destroyed on death

### LEGGINGS

- protection: +1 → +4 (1–4)
- fire_protection: +2 → +8 (1–4); burning-time reduction
- blast_protection: +2 → +8 (1–4); knockback resistance
- projectile_protection: +2 → +8 (1–4)
- thorns: 15% → 45%; 1–5 damage; 2 durability per trigger (1–3)
- swift_sneak sneaking speed: +0.15 → +0.45 (levels 1–3)
- unbreaking (armor branch): 20% → 30% (1–3)
- mending rate: ×2.0 fixed
- binding_curse: cannot be removed
- vanishing_curse: destroyed on death

### BOOTS

- protection: +1 → +4 (1–4)
- fire_protection: +2 → +8 (1–4); burning-time reduction
- blast_protection: +2 → +8 (1–4); knockback resistance
- projectile_protection: +2 → +8 (1–4)
- feather_falling (fall damage): +3 → +12 (levels 1–4)
- thorns: 15% → 45%; 1–5 damage; 2 durability per trigger (1–3)
- depth_strider water movement: +0.33 → +1.00 (levels 1–3)
- frost_walker ice radius: 3 → 4 blocks (levels 1–2); immunity to stepping-burn damage
- soul_speed movement speed on soul blocks: +0.0405 → +0.0615 (levels 1–3); movement_efficiency +1.0; 1 durability per use (gated)
- unbreaking (armor branch): 20% → 30% (1–3)
- mending rate: ×2.0 fixed
- binding_curse: cannot be removed
- vanishing_curse: destroyed on death

### WOLF_ARMOR / HORSE_ARMOR / NAUTILUS_ARMOR

No vanilla enchantments apply (verified: absent from every `enchantable/*` tag),
so there are no borrowable effects. Lapis effects for these must be hand-designed
in the datapack (custom values, not reproductions).
