# Vanilla Enchantment System — Minecraft 26.2

Complete technical inventory of the Java Edition enchantment system, built from the actual
26.2 jars and vanilla datapack files — not the wiki, not old Yarn knowledge.
Written for designing the "Lapis Effect" enchantment (temporarily reproduce another
vanilla enchantment's effect for exactly one interaction).

Source locations: `~/.gradle/caches/fabric-loom/26.2/minecraft-common.jar`
(`data/minecraft/enchantment/*.json`, tags, loot tables),
`minecraft-extracted_server.jar` (classes), and the Mojang-named remapped jar in
`.gradle/loom-cache/minecraftMaven/…` (used for `javap`; recipe in
`minecraft-internals.md` §"Inspecting vanilla code"). Extracted copies of all 43
enchantment JSONs were left at `/tmp/opencode/ench/` during research (ephemeral —
re-extract from `minecraft-common.jar` if needed).

Key classes (all `net.minecraft.world.item.enchantment` unless noted):
`Enchantment` (record: description + `EnchantmentDefinition` + exclusiveSet + effects
`DataComponentMap`), `EnchantmentHelper` (all-static dispatch), `EnchantmentEffectComponents`
(31 registered component keys), `Enchantments` (43 registry keys), `ConditionalEffect<T>`
(effect + optional `requirements` loot condition), `TargetedConditionalEffect<T>`
(+ `enchanted`/`affected`: attacker/victim), `EnchantedItemInUse`.
Effect interfaces in `…/enchantment/effects/`: `EnchantmentValueEffect.process(level, random, base)→float`,
`EnchantmentEntityEffect.apply(ServerLevel, level, EnchantedItemInUse, Entity, Vec3)`,
`EnchantmentLocationBasedEffect.onChangedBlock(…)` (+`onDeactivated`).

## 1. Complete enchantment table (all 43)

Flags: **T** = treasure (`#treasure`: double trade price, excluded from enchanting table);
**C** = curse; **NT** = non-treasure (in enchanting table). Tradeable = NT + binding,
vanishing, frost_walker, mending — so treasure-but-**not**-tradeable: `swift_sneak`,
`soul_speed`, `wind_burst`. `primary_items` shown only where the JSON sets it
(`supported_items` is the real applicability gate).

| ID | Max | Applies to (`supported_items`) | Slot | Effect components | Interaction(s) | Special handling |
| -- | --: | ------------------------------ | ---- | ----------------- | -------------- | ---------------- |
| `protection` | 4 | `#enchantable/armor` | armor | `damage_protection` | damage received | NT, w10, excl `armor` |
| `fire_protection` | 4 | `#enchantable/armor` | armor | `attributes` (burning_time), `damage_protection` | damage received (fire), passive burn-time attr | NT, w5, excl `armor` |
| `feather_falling` | 4 | `#enchantable/foot_armor` | armor | `damage_protection` | damage received (fall) | NT, w5 |
| `blast_protection` | 4 | `#enchantable/armor` | armor | `attributes` (explosion_knockback_resistance), `damage_protection` | damage received (explosion) | NT, w2, excl `armor` |
| `projectile_protection` | 4 | `#enchantable/armor` | armor | `damage_protection` | damage received (projectile) | NT, w5, excl `armor` |
| `respiration` | 3 | `#enchantable/head_armor` | head | `attributes` (oxygen_bonus) | passive attribute | NT, w2 |
| `aqua_affinity` | 1 | `#enchantable/head_armor` | head | `attributes` (submerged_mining_speed) | passive attribute | NT, w2 |
| `thorns` | 3 | `#enchantable/armor` (primary chest) | any | `post_attack` (enchanted=victim, `random_chance` 15%/lvl) | damage received → retaliation | NT, w1 |
| `depth_strider` | 3 | `#enchantable/foot_armor` | feet | `attributes` (water_movement_efficiency) | passive attribute | NT, w2, excl `boots` |
| `frost_walker` | 2 | `#enchantable/foot_armor` | feet | `damage_immunity`, `location_changed` (frosted_ice disk) | movement/location, damage immunity | **T**, tradeable, w2, excl `boots` |
| `binding_curse` | 1 | `#enchantable/equippable` | armor | `prevent_armor_change` | equip/remove block (Java `has()` checks) | **T+C**, tradeable, w1 |
| `soul_speed` | 3 | `#enchantable/foot_armor` | feet | `location_changed` (speed attrs on soul blocks), `tick` (particles) | movement/location + ticking | **T**, NOT tradeable, w1 |
| `swift_sneak` | 3 | `#enchantable/leg_armor` | legs | `attributes` (sneaking_speed) | passive attribute | **T**, NOT tradeable, w1 |
| `sharpness` | 5 | `#enchantable/sharp_weapon` (primary melee) | mainhand | `damage` | attack | NT, w10, excl `damage` |
| `smite` | 5 | `#enchantable/weapon` (primary melee) | mainhand | `damage` (vs `#sensitive_to_smite`) | attack (conditional) | NT, w5, excl `damage` |
| `bane_of_arthropods` | 5 | `#enchantable/weapon` (primary melee) | mainhand | `damage` (vs `#sensitive_to_bane…`), `post_attack` (slowness) | attack + post-attack | NT, w5, excl `damage` |
| `knockback` | 2 | `#enchantable/melee_weapon` | mainhand | `knockback` | attack (knockback stage) | NT, w5 |
| `fire_aspect` | 2 | `#enchantable/fire_aspect` (primary melee) | mainhand | `post_attack` (ignite, direct only); also `#smelts_loot` tag (smelts block loot) | post-attack; loot smelting | NT, w2 |
| `looting` | 3 | `#enchantable/melee_weapon` | mainhand | `equipment_drops` (Mob drop-chance only; main loot bonus is loot-table functions) | kill loot + equipment drops | NT, w2 |
| `sweeping_edge` | 3 | `#enchantable/sweeping` | mainhand | `attributes` (sweeping_damage_ratio) | sweep attack (Java ratio read) | NT, w2 |
| `efficiency` | 5 | `#enchantable/mining` | mainhand | `attributes` (mining_efficiency, levels²+1) | break speed (passive attr) | NT, w10 |
| `silk_touch` | 1 | `#enchantable/mining_loot` | mainhand | `block_experience` (=0); presence read via `match_tool`+`enchantments` in loot tables; 4× `prevents_*` tags | block loot/XP (loot-table + Java tag checks) | NT, w1, excl `mining` |
| `unbreaking` | 3 | `#enchantable/durability` | any | `item_damage` (2× remove_binomial: armor vs non-armor) | durability | NT, w5 |
| `fortune` | 3 | `#enchantable/mining_loot` | mainhand | **NONE** — pure loot-table enchant (`apply_bonus` in ore/crop tables) | block loot (loot tables only) | NT, w2, excl `mining` |
| `power` | 5 | `#enchantable/bow` | mainhand | `damage` (direct_attacker is `#arrows`) | projectile attack | NT, w10 |
| `punch` | 2 | `#enchantable/bow` | mainhand | `knockback` (direct_attacker is `#arrows`) | projectile knockback | NT, w2 |
| `flame` | 1 | `#enchantable/bow` | mainhand | `projectile_spawned` (ignite 100 ticks) | projectile spawn | NT, w2 |
| `infinity` | 1 | `#enchantable/bow` | mainhand | `ammo_use` (=0 for arrows) | ammo consumption | NT, w1, excl `bow` (vs mending) |
| `luck_of_the_sea` | 3 | `#enchantable/fishing` | mainhand | `fishing_luck_bonus` | fishing loot | NT, w2 |
| `lure` | 3 | `#enchantable/fishing` | mainhand | `fishing_time_reduction` | fishing wait time | NT, w2 |
| `loyalty` | 3 | `#enchantable/trident` | mainhand | `trident_return_acceleration` | thrown-trident return (Java level read) | NT, w5 |
| `impaling` | 5 | `#enchantable/trident` | mainhand | `damage` (vs `#sensitive_to_impaling`) | attack (conditional) | NT, w2, excl `damage` |
| `riptide` | 3 | `#enchantable/trident` | hand | `trident_sound`, `trident_spin_attack_strength` (Java water/rain gate) | trident spin attack | NT, w2, excl `riptide` set |
| `channeling` | 1 | `#enchantable/trident` | mainhand | `hit_block` + `post_attack` (lightning, thunder+sky reqs) | projectile hit / post-attack | NT, w1 |
| `multishot` | 1 | `#enchantable/crossbow` | mainhand | `projectile_count` (+2), `projectile_spread` (+10) | shooting | NT, w2, excl `crossbow` |
| `quick_charge` | 3 | `#enchantable/crossbow` | mainhand+offhand | `crossbow_charge_time` (−0.25/lvl), `crossbow_charging_sounds` | charging (Java calc + sounds) | NT, w5 |
| `piercing` | 4 | `#enchantable/crossbow` | mainhand | `projectile_piercing` | projectile pierce count | NT, w10, excl `crossbow` |
| `density` | 5 | `#enchantable/mace` | mainhand | `smash_damage_per_fallen_block` | mace smash (fall-based path) | NT, w5, excl `damage` |
| `breach` | 4 | `#enchantable/mace` | mainhand | `armor_effectiveness` (−15%/lvl) | attack (armor-piercing stage in `CombatRules`) | NT, w2, excl `damage` |
| `wind_burst` | 3 | `#enchantable/mace` | mainhand | `post_attack` (explode/gust, self, fall≥1.5 req) | post smash-attack | **T**, NOT tradeable, w2 |
| `lunge` | 3 | `#enchantable/lunge` (spear) | hand | `post_piercing_attack` (impulse+exhaustion+item damage+sound) | spear pierce attack | NT, w5 |
| `mending` | 1 | `#enchantable/durability` | any | `repair_with_xp` (×2) | XP pickup (`ExperienceOrb.repairPlayerItems`) | **T**, tradeable, w2, excl `bow` (vs infinity) |
| `vanishing_curse` | 1 | `#enchantable/vanishing` | any | `prevent_equipment_drop` | death drops (Java `has()` checks) | **T+C**, tradeable, w1 |

Quirks found in data: `exclusive_set/riptide.json` contains loyalty+channeling
(riptide's own JSON points at `#exclusive_set/riptide`); mending↔infinity share
`exclusive_set/bow`. `fortune` is the only enchantment with **zero** effect components.

## 2. Effect-component table

All list-valued components hold `ConditionalEffect<T>`; `post_attack`/`equipment_drops`
hold `TargetedConditionalEffect<T>`. Dispatch is per-component instance methods on
`Enchantment` (`modify*`, `doPostAttack`, `tick`, …), driven by `EnchantmentHelper` statics.

| Effect component | Java type | Trigger | Vanilla execution method | Target/context | Example enchantments |
| ---------------- | --------- | ------- | ------------------------ | -------------- | -------------------- |
| `damage` | `List<ConditionalEffect<EnchantmentValueEffect>>` | attack (melee `stabAttack`, mob `doHurtTarget`, arrow/trident `onHitEntity`) | `EnchantmentHelper.modifyDamage` → `Enchantment.modifyDamage` | enchanted weapon stack | sharpness, smite, bane, impaling, power |
| `damage_protection` | `List<ConditionalEffect<…ValueEffect>>` | damage received | `EnchantmentHelper.getDamageProtection` ← `LivingEntity.getDamageAfterMagicAbsorb` | victim's equipment | protection ×4, feather_falling |
| `damage_immunity` | `List<ConditionalEffect<DamageImmunity>>` | damage received (tag-filtered) | `EnchantmentHelper.isImmuneToDamage` ← `LivingEntity.isInvulnerableTo` | victim's feet stack | frost_walker |
| `knockback` | `List<ConditionalEffect<…ValueEffect>>` | attack knockback stage | `EnchantmentHelper.modifyKnockback` ← `LivingEntity.getKnockback` / `AbstractArrow.doKnockback` | weapon or arrow stack | knockback, punch |
| `armor_effectiveness` | `List<…ValueEffect>` | armor-reduction stage | `EnchantmentHelper.modifyArmorEffectiveness` ← `CombatRules.getDamageAfterAbsorb` | attacker's weapon | breach |
| `smash_damage_per_fallen_block` | `List<…ValueEffect>` | mace smash | `EnchantmentHelper.modifyFallBasedDamage` ← `MaceItem.getAttackDamageBonus` | mace stack, fall distance | density |
| `post_attack` | `List<TargetedConditionalEffect<EnchantmentEntityEffect>>` | after damage dealt | `EnchantmentHelper.doPostAttackEffects{,WithItemSource{,OnBreak}}` ← `LivingEntity.stabAttack`, `Player.itemAttackInteraction`/`doSweepAttack`, `AbstractArrow/ThrownTrident.onHitEntity`, `Mob.doHurtTarget` | enchanted=attacker/victim per JSON | fire_aspect, thorns, channeling, wind_burst, bane |
| `post_piercing_attack` | `List<ConditionalEffect<EnchantmentEntityEffect>>` | spear pierce attack | `EnchantmentHelper.doPostPiercingAttackEffects` ← `LivingEntity.postPiercingAttack` | pierce-attack weapon | lunge |
| `hit_block` | `List<ConditionalEffect<EnchantmentEntityEffect>>` | projectile hits block / block-break action | `EnchantmentHelper.onHitBlock` ← `AbstractArrow/ThrownTrident.hitBlockEnchantmentEffects`, `ServerPlayerGameMode.handleBlockBreakAction` | projectile/block-hit stack + hit pos | channeling |
| `item_damage` | `List<ConditionalEffect<…ValueEffect>>` | durability loss | `EnchantmentHelper.processDurabilityChange` ← `ItemStack.processDurabilityChange` | damaged stack | unbreaking |
| `ammo_use` | `List<…ValueEffect>` | ammo consumed | `EnchantmentHelper.processAmmoUse` ← `ProjectileWeaponItem.useAmmo` | bow stack | infinity |
| `block_experience` | `List<…ValueEffect>` | block XP drop | `EnchantmentHelper.processBlockExperience` ← `Block.tryDropExperience` | mining tool | silk_touch (=0) |
| `mob_experience` | `List<…ValueEffect>` | mob XP reward | `EnchantmentHelper.processMobExperience` ← `LivingEntity.getExperienceReward` | killer/victim context | **no vanilla user** — free channel for custom use |
| `equipment_drops` | `List<TargetedConditionalEffect<…ValueEffect>>` | mob death drops | `EnchantmentHelper.processEquipmentDropChance` ← `Mob.dropCustomDeathLoot` | killer's weapon (attacker=player req) | looting |
| `repair_with_xp` | `List<…ValueEffect>` | XP orb pickup | `EnchantmentHelper.modifyDurabilityToRepairFromXp` + `getRandomItemWith` ← `ExperienceOrb.repairPlayerItems` | XP orb + random mending item | mending |
| `projectile_count` / `projectile_spread` | `List<…ValueEffect>` | shooting | `EnchantmentHelper.processProjectileCount` ← `ProjectileWeaponItem.draw`; `processProjectileSpread` ← `…shoot` | crossbow | multishot |
| `projectile_piercing` | `List<…ValueEffect>` | arrow created | `EnchantmentHelper.getPiercingCount` ← `AbstractArrow` constructor | crossbow + ammo | piercing |
| `projectile_spawned` | `List<ConditionalEffect<EnchantmentEntityEffect>>` | projectile spawned | `EnchantmentHelper.onProjectileSpawned` ← `Projectile.applyOnProjectileSpawned` | weapon stack; entity=projectile | flame |
| `trident_return_acceleration` | `List<…ValueEffect>` | loyalty return tick | `EnchantmentHelper.getTridentReturnToOwnerAcceleration` ← `ThrownTrident.getLoyaltyFromItem` | trident stack | loyalty |
| `trident_spin_attack_strength` | single `EnchantmentValueEffect` | riptide use | `EnchantmentHelper.getTridentSpinAttackStrength` ← `TridentItem.releaseUsing`/`use` (+ Java water/rain gate) | trident stack | riptide |
| `trident_sound` | `List<Holder<SoundEvent>>` | riptide use | `EnchantmentHelper.pickHighestLevel` ← `TridentItem.releaseUsing` | trident stack | riptide |
| `crossbow_charge_time` | single `EnchantmentValueEffect` | charging | `EnchantmentHelper.modifyCrossbowChargingTime` ← `CrossbowItem.getChargeDuration` | crossbow | quick_charge |
| `crossbow_charging_sounds` | `List<ChargingSounds>` | charging | `EnchantmentHelper.pickHighestLevel` ← `CrossbowItem.getChargingSounds` | crossbow | quick_charge |
| `fishing_luck_bonus` / `fishing_time_reduction` | `List<…ValueEffect>` | cast rod | `EnchantmentHelper.getFishingLuckBonus` / `getFishingTimeReduction` ← `FishingRodItem.use` | rod | luck_of_the_sea / lure |
| `location_changed` | `List<ConditionalEffect<EnchantmentLocationBasedEffect>>` | block change under entity / equipment change | `EnchantmentHelper.runLocationChangedEffects` ← `LivingEntity.onChangedBlock`, `collectEquipmentChanges`; undo via `stopLocationBasedEffects` | boots/feet stack | frost_walker, soul_speed |
| `tick` | `List<ConditionalEffect<EnchantmentEntityEffect>>` | entity tick | `EnchantmentHelper.tickEffects` ← `LivingEntity.baseTick` | equipped stack | soul_speed (particles) |
| `attributes` | `List<EnchantmentAttributeEffect>` | passive (recomputed on equip/modifier query) | `EnchantmentHelper.forEachModifier` ← `ItemStack.forEachModifier` | item + slot | efficiency, sweeping_edge, respiration, aqua_affinity, depth_strider, swift_sneak, fire/blast_protection |
| `prevent_equipment_drop` | `Unit` | death | `EnchantmentHelper.has(stack, PREVENT_EQUIPMENT_DROP)` ← `Player.destroyVanishingCursedItems`, `Mob.attemptToShearEquipment`/`compareArmor`, `AbstractHorse.dropEquipment` | cursed stack | vanishing_curse |
| `prevent_armor_change` | `Unit` | equip/unequip attempt | `EnchantmentHelper.has(stack, PREVENT_ARMOR_CHANGE)` ← `Equippable.swapWithEquipmentSlot`, `ArmorSlot.mayPickup` | bound stack | binding_curse |

Value-effect `type` ids in vanilla JSON (all data-driven, no Java per id): `add`,
`multiply`, `set`, `remove_binomial`, `all_of`, `enchantment_level` (chance provider).
Location/entity `type` ids: `ignite`, `damage_entity`, `apply_mob_effect`, `apply_impulse`,
`apply_exhaustion`, `change_item_damage`, `explode`, `summon_entity`, `play_sound`,
`spawn_particles`, `replace_disk`, `attribute`. Level providers: `linear`, `clamped`,
`fraction`, `levels_squared`, `lookup`, `uniform`. Requirements (`requirements.condition`,
 evaluated per application in `ConditionalEffect.matches`): `random_chance`
(+ `chance: {type: enchantment_level…}`, the thorns precedent), `entity_properties`,
`damage_source_properties`, `match_tool`, `weather_check`, `location_check`,
`enchantment_active_check`, `all_of`/`any_of`/`inverted`.

Loot-only behavior (no component; loot classes gate on enchantment id): fortune →
`ApplyBonusCount` (`ore_drops` etc., e.g. `loot_table/blocks/diamond_ore.json`); looting
(main) → `EnchantedCountIncreaseFunction.lootingMultiplier` +
`random_chance_with_enchanted_bonus` (e.g. `entities/mule.json`, `entities/shulker.json`);
silk_touch presence → `match_tool`+`enchantments` predicate in block loot tables +
`hasTag` checks in `BeehiveBlock.playerDestroy`, `InfestedBlock.spawnAfterBreak`,
`IceBlock.playerDestroy`, `DecoratedPotBlock.playerWillDestroy`; fire_aspect smelting →
`#smelts_loot` tag in the loot path.

## 3. Source execution traces

Mojang names, verified via `javap -c` (caller → `EnchantmentHelper` → component).
Melee family all runs inside `LivingEntity.stabAttack` (`modifyDamage`, then
`doPostAttackEffects`):

- **sharpness**: deal damage → `LivingEntity.stabAttack` → `EnchantmentHelper.modifyDamage(stack…)` → `damage`/`add` linear 1.0+0.5·(lvl−1) → base damage increased.
- **fire_aspect**: damage → `stabAttack` → `Player.itemAttackInteraction`/`doSweepAttack`, `Mob.doHurtTarget` → `doPostAttackEffects[WithItemSource]` → `post_attack` (enchanted=attacker, direct-only req) → `Ignite.apply` → victim ignited.
- **knockback**: damage → `LivingEntity.getKnockback` (arrows: `AbstractArrow.doKnockback`) → `modifyKnockback` → `knockback`/`add`.
- **looting**: kill → entity loot tables (`enchanted_count_increase`, `random_chance_with_enchanted_bonus` read killer-weapon level from `LootContext`); plus `Mob.dropCustomDeathLoot` → `processEquipmentDropChance` → `equipment_drops`/`add` 0.01/lvl (attacker=player only).
- **sweeping_edge**: `Player.doSweepAttack` scales sweep damage by the `sweeping_damage_ratio` attribute, supplied by `attributes`/`fraction` via `ItemStack.forEachModifier` ← `forEachModifier`.
- **efficiency**: mining speed reads the `mining_efficiency` attribute (`levels_squared`+1) via `forEachModifier`; no per-break event.
- **fortune**: no helper call — `apply_bonus {enchantment: fortune, formula: ore_drops}` in ore/crop loot tables; level read from tool by `ApplyBonusCount.run`.
- **silk_touch**: block-loot `alternatives` first child gated by `match_tool`/`enchantments: silk_touch` (drops the block itself); XP zeroed via `Block.tryDropExperience` → `processBlockExperience` → `block_experience`/`set` 0; bee/infest/ice/pot suppression via the `hasTag` checks above.
- **unbreaking**: `ItemStack.processDurabilityChange` → `processDurabilityChange` → `item_damage`/`remove_binomial` (armor branch vs non-armor branch via `match_tool #enchantable/armor`).
- **protection / fire_protection / projectile_protection / feather_falling**: `LivingEntity.getDamageAfterMagicAbsorb` → `getDamageProtection` → `damage_protection`/`add`, filtered by req tags (none / `is_fire` / `is_projectile` / `is_fall`, all excluding `bypasses_invulnerability`); fire_protection also contributes the `burning_time` attribute.
- **thorns**: victim damaged → `doPostAttackEffects` over victim's gear → `post_attack` (enchanted=victim, affected=attacker, `random_chance` 15%/lvl) → `all_of[damage_entity(thorns 1–5), change_item_damage 2]`.
- **respiration / aqua_affinity / depth_strider / swift_sneak**: pure `attributes` passives (`oxygen_bonus`, `submerged_mining_speed`, `water_movement_efficiency`, `sneaking_speed`); no event code.
- **soul_speed**: `LivingEntity.onChangedBlock` → `runLocationChangedEffects` → `location_changed` (soul-block req incl. `enchantment_active_check`) applies speed attrs + `change_item_damage` 1; `LivingEntity.baseTick` → `tickEffects` → `tick` spawns soul particles.
- **flame**: `ProjectileWeaponItem.shoot` → `Projectile.applyOnProjectileSpawned` → `onProjectileSpawned` → `projectile_spawned`/`ignite` 100 → arrow burns.
- **punch**: `AbstractArrow.doKnockback` → `modifyKnockback` → `knockback` (req direct_attacker is `#arrows`).
- **power**: `AbstractArrow.onHitEntity` → `modifyDamage` (arrow's weapon stack) → `damage` (req direct_attacker is `#arrows`).
- **multishot**: `ProjectileWeaponItem.draw` → `processProjectileCount` (+2); `shoot` → `processProjectileSpread` (+10).
- **piercing**: `AbstractArrow` constructor → `getPiercingCount(weapon, ammo)` → `projectile_piercing`.
- **quick_charge**: `CrossbowItem.getChargeDuration` → `modifyCrossbowChargingTime` (−25%/lvl); sounds via `getChargingSounds` → `pickHighestLevel(CROSSBOW_CHARGING_SOUNDS)`.
- **loyalty**: `ThrownTrident.getLoyaltyFromItem` → `getTridentReturnToOwnerAcceleration`; pickup/return gated in Java by loyalty level.
- **channeling**: `ThrownTrident.onHitEntity` → `doPostAttackEffectsWithItemSourceOnBreak` (thunder+sky+direct_attacker=trident req) and `hitBlockEnchantmentEffects` → `onHitBlock` → `all_of[summon_entity lightning, play_sound]`.
- **riptide**: `TridentItem.releaseUsing`/`use` → `getTridentSpinAttackStrength` (+1.5/0.75) + `pickHighestLevel(TRIDENT_SOUND)`; Java gates spin vs throw on water/rain.
- **wind_burst**: mace smash → `doPostAttackEffects` (attacker gear) → `post_attack` (affected=attacker=self, fall_distance≥1.5 req) → `ExplodeEffect` (radius 3.5, `lookup` knockback mult, gust particles, trigger-only block interaction).
- **mending**: `ExperienceOrb.repairPlayerItems` → `getRandomItemWith(REPAIR_WITH_XP…)` picks a damaged mending item → `modifyDurabilityToRepairFromXp` (×2) converts XP to durability.

## 4. Lapis feasibility analysis (source-grounded)

**A. Custom data-driven enchantment executing custom Java without a Mixin? NO.**
The 31 component keys resolve through closed codec registries
(`EnchantmentValueEffect.bootstrap`, `EnchantmentEntityEffect.bootstrap`,
`EnchantmentLocationBasedEffect.bootstrap` → the 31 classes under
`…/enchantment/effects/`). A custom JSON `type` with no registered codec fails
datapack parsing. Pure JSON can only *recombine* vanilla effect types.

**B. Can vanilla `requirements` supply the 20%/50% probability? YES.**
`requirements: {condition: "minecraft:random_chance", chance: …}` is evaluated per
effect application in `ConditionalEffect.matches` / `TargetedConditionalEffect.matches`.
Precedent: `thorns.json` uses exactly this with a level-scaled chance
(`enchantment_level`: linear 0.15/lvl). A Lapis 20% gate = `random_chance` with a
constant 0.2 chance. No Java needed for the roll.

**C–D. Can one custom effect fire an existing vanilla enchantment's effect for one
interaction? Only via Java re-invoking that enchantment's machinery with a synthetic
context — NOT via JSON alone.** Available exact APIs (all `public static` on
`EnchantmentHelper`, server-side): `modifyDamage`, `modifyKnockback`,
`doPostAttackEffectsWithItemSource(ServerLevel, victim, DamageSource, itemStack)`,
`processBlockExperience`, `processDurabilityChange`, `modifyDurabilityToRepairFromXp`,
`processProjectileCount/Spread`, `onProjectileSpawned`, `onHitBlock`,
`getDamageProtection`, `isImmuneToDamage`, `modifyFallBasedDamage`,
`modifyArmorEffectiveness`, `processEquipmentDropChance`, `getFishingLuckBonus` /
`getFishingTimeReduction`, `getTridentReturnToOwnerAcceleration`,
`getTridentSpinAttackStrength`, `modifyCrossbowChargingTime`, `getPiercingCount`,
`runLocationChangedEffects`, `tickEffects`, plus `Enchantment.getEffects(componentType)`
and per-component `Enchantment#modify*/doPostAttack/tick` instance methods.
Pattern (no Mixin): on the interaction event, build a **temporary copy** of the lapis
`ItemStack` with the chosen vanilla enchantment added via
`EnchantmentHelper.updateEnchantments`/`setEnchantments`, pass *that copy* to the
corresponding helper, then discard it — the real item is never modified ("this
interaction only"). `doPostAttackEffectsWithItemSource(level, target, damageSource,
syntheticStack)` is the cleanest single-interaction entry point for the post-attack family.

**E. Where vanilla blocks the pure-datapack version.** (1) Component dispatch iterates
enchantments *present on the triggering stack/equipment* (`runIterationOnItem`,
`runIterationOnEquipment`, `EnchantedItemInUse`) — a lapis enchantment's JSON cannot
reference another enchantment id's effects. (2) Loot-table bonuses (fortune, main
looting, silk_touch presence) read the **tool's** enchantment levels from `LootContext`
(`ApplyBonusCount.run`, `EnchantedCountIncreaseFunction.run`, `match_tool`/`enchantments`
predicate) — Lapis cannot rewrite which id the loot function looks up without Java that
substitutes the context tool or post-processes drops. (3) Passive `attributes` are
recomputed from equipped items, so one-interaction-only attribute borrowing needs a
within-event add/remove window in Java. Minimum custom code, no Mixins: one Fabric event
listener per interaction family that rolls 20% (or relies on JSON `random_chance`), picks
an applicable vanilla enchantment, and invokes the helper with a synthetic enchanted copy.
Only genuinely Java-gated pieces (riptide water check, loyalty return state machine,
soul-speed/frost-walker movement state) additionally need the real gameplay state to hold
at call time — still no Mixin, just call conditions.

**F. Reuse spectrum.** *Almost entirely data-driven* (helper + synthetic stack, or JSON
`requirements`): post-attack family, `damage` family, knockback/punch, protection family,
feather_falling, frost immunity, silk_touch XP-zero, unbreaking, infinity, flame,
multishot/piercing numbers, fishing, mending rate, density/breach numbers. *Needs Java
wrappers around vanilla calls (no Mixin):* fortune/looting/silk_touch loot presence
(loot-context substitution or drop post-processing), sweeping_edge ratio and
efficiency-style speed (attribute must be present during the event), riptide/loyalty
(projectile state machines + environment gates), soul_speed/frost_walker location effects
(movement/location lifecycle + `onDeactivated` cleanup), binding/vanishing (equip/death
lifecycle flags).

## 5. What we learned / read first / unknowns

Learned: 26.2 has exactly **43** enchantments (registry keys ≡ JSON files) and **31**
component keys of which **30** are used — **`mob_experience` is registered but unused**,
a ready-made custom channel. Behavior falls in three classes: component-driven (most),
loot-table-driven (fortune, main looting, silk_touch presence, smelts_loot), Java-flag
(binding/vanishing, riptide/loyalty gates). Probability is already a per-application
data-driven gate (thorns). Every component funnels through a `public static
EnchantmentHelper.*` method — the synthetic-stack pattern can borrow any of them for one
interaction without Mixins.

Read first: the 43 JSONs in `minecraft-common.jar` (`thorns.json` = probability
precedent, `fire_aspect.json` = minimal post-attack, `fortune.json` = empty effects,
`unbreaking.json` = dual-branch values); `EnchantmentHelper` (esp.
`doPostAttackEffectsWithItemSource`), `Enchantment` (`EnchantmentDefinition`,
`getEffects`, per-component methods), `EnchantmentEffectComponents`,
`ConditionalEffect`/`TargetedConditionalEffect`; callers `LivingEntity.stabAttack` /
`getDamageAfterMagicAbsorb` / `baseTick` / `onChangedBlock`, `Player.itemAttackInteraction` /
`doSweepAttack`, `CombatRules.getDamageAfterAbsorb`, `ItemStack.processDurabilityChange` /
`forEachModifier`, `Block.tryDropExperience`, `ExperienceOrb.repairPlayerItems`,
`ProjectileWeaponItem.shoot/draw/useAmmo`, `AbstractArrow.onHitEntity/doKnockback`,
`ThrownTrident.onHitEntity/getLoyaltyFromItem`, `MaceItem.getAttackDamageBonus`,
`Mob.dropCustomDeathLoot/doHurtTarget`; loot seam `ApplyBonusCount`,
`EnchantedCountIncreaseFunction`, `LootItemRandomChanceWithEnchantedBonusCondition`.

Unknowns (need runtime/decompile to close): `EnchantedItemInUse` slot-resolution order
per helper; `getRandomItemWith` weighting with several damaged mending items; whether
`processEquipmentDropChance`'s attacker=player requirement is satisfiable via synthetic
stack; cleanest Fabric interception point for loot-context tool substitution;
attribute-read caching for within-event add/remove windows; `LivingEntity.postPiercingAttack`
(spear/lunge, new 26.2 path) trigger conditions.
