# Minecraft / Fabric Internals & Gotchas

Non-obvious API constraints hit while working on this mod, plus how to check such things yourself.
For vanilla *stat values* see `minecraft-vanilla-materials.md`.

## Inspecting vanilla code

`~/.gradle/caches/fabric-loom/<version>/` holds **compiled** jars only (`minecraft-common.jar`,
`minecraft-client.jar`, …), no readable sources jar. Two ways in:

**jdx — fastest, no scratch space.** It reads the loom jars directly:

```bash
MC=~/.gradle/caches/fabric-loom/26.3/minecraft-common.jar
jdx members 'net.minecraft.world.entity.LivingEntity' --jars $MC --no-jdk --grep='Visibility'
jdx body   'net.minecraft.world.entity.LivingEntity#getVisibilityPercent' --jars $MC --no-jdk --engine=javap
jdx diff   ~/.gradle/caches/fabric-loom/26.2/minecraft-common.jar $MC --severity breaking
```

`jdx diff` between the old and new version jars is the single most useful command when bumping a
Minecraft version — it names every breaking change with a rule id. 26.2→26.3 reports ~2700
findings, so it must be filtered, or it is noise:

```bash
grep -rhoE '^import net\.minecraft\.[A-Za-z0-9_.$]+' src/ | sed 's/^import //' | sort -u > /tmp/imports.txt
jdx diff OLD NEW --severity breaking --limit 5000 --json > /tmp/breaking.json
```

Keep findings whose `.type` is listed in `/tmp/imports.txt`, then check the remaining ones by
grepping the mod for the *member* name — a finding only matters if the code actually references
that member (e.g. `Block#CODEC` is irrelevant when the matches are your own `CODEC` constants).

**Vineflower into a scratch dir — when you need to read or grep a whole class.** `./gradlew
genSources` populates Loom's *content-addressed* decompile cache
(`~/.gradle/caches/fabric-loom/decompile/v1.zip`), which is not greppable, so decompile directly:

```bash
VF=$(echo ~/.gradle/caches/modules-2/files-2.1/org.vineflower/vineflower/*/*/vineflower-*.jar)
java -jar $VF -dgs=1 ~/.gradle/caches/fabric-loom/26.3/minecraft-common.jar /tmp/mc263/
```

~5000 classes in ~90 s, as normal Mojang-named Java. Vineflower's first positional arg is the
*input*; with no second arg it writes to stdout instead of a directory.

Caveat: `jdx body --engine=vineflower` needs its output to parse, and fails with a misleading
`not found` when it does not (jdx#87) — fall back to `--engine=javap` or `jdx source`.

**Always verify mixin targets by hand — the compiler will not.** A mixin whose target changed
name or signature still compiles and only fails when the class is transformed, i.e. at game
start. Check each target against the jar (`jdx members … --grep='<method>'`), and for
`@ModifyVariable(name = "…")` also confirm the LocalVariableTable:

```bash
unzip -oq $MC 'net/minecraft/world/entity/player/Player.class' -d /tmp/chk
javap -v -p -classpath /tmp/chk net.minecraft.world.entity.player.Player   # -v, not -l: Java 25 hides Code
```

Constant arguments show up as `ldc // float -2.4f` / `fconst_1` / `bipush 15` right before the
`invokevirtual` that consumes them, so `grep -B12` around a call site recovers exact values.

Vanilla **datapack** files (tag JSONs etc.) are in the same jar under `data/minecraft/...`.

## Hand-written datapack JSON is validated by nothing

`./gradlew build` and `./gradlew runDatagen` both stay green while a hand-written data file is
**silently unloadable by the game**. Datagen only rewrites `src/main/generated/`; it never parses
`src/main/resources/data/`. The Kotlin compiler never sees JSON. Nothing checks it until the
registry load, so a format change surfaces as a crash the first time someone opens a world:

```
IllegalStateException: No key type in MapLike[{"condition": ...}] ... Failed to load registries due to errors
```

Files at risk are the hand-written ones — here the four `src/main/resources/data/more-tools/enchantment/lapis_*.json`.
Their `effects` / `supported_items` / `requirements` shapes are owned by vanilla codecs and can move
between versions with no compile error anywhere.

So after a version bump, budget a **real load**, not just a build:

```bash
grep -rn '"condition"' src/main/resources/data/     # and any other key a codec may have renamed
```

and diff the mod's files against the vanilla file using the same construct
(`data/minecraft/enchantment/*.json` inside `minecraft-common.jar`). Vanilla ships 43 of them and they
are the ground truth for the current format.

## 26.2 → 26.3 API changes that touched this mod

The `jdx diff` findings this mod actually had to act on — not the other ~2690:

- `LootContext.getOptionalParameter` → **`getOptional`**. The required variant `getParameter` is
  **removed outright**, not moved to a supertype — `LootContext` still extends only `Object`, so
  optional reads are all that remain.
- `RecipeProvider`'s constructor is now `(BootstrapContext<Recipe<?>>, BootstrapContext<Advancement>)`
  instead of `(HolderLookup.Provider, RecipeOutput)`; the `registries` field and
  `RecipeOutput#includeRootAdvancement()` are gone. `RecipeProvider.Runner.createRecipeProvider` gained
  two parameters, and `FabricRecipeProvider.createRecipeProvider` matches that new shape
  (Fabric datagen API 25.5.0 → 27.2.4). Recipes are registered into the bootstrap contexts and
  written by the framework, so a provider that needs a `RecipeOutput` should take the inherited
  `RecipeProvider.output` field instead of constructing one.
- `LivingEntity.getVisibilityPercent(Entity)` → **`getVisibilityPercent(ServerLevel, Entity)`**.
  Still a single overload, so name-only mixin matching and `@ModifyExpressionValue` are unaffected;
  `TargetingConditions#test` remains its only caller. See `glass-armor.md`.
- Advancement criterion key `recipe` → **`recipes`**: `RecipeUnlockedTrigger.TriggerInstance` now holds
  a `HolderSet<Recipe<?>>` rather than a `ResourceKey`. Datagen rewrites all 75 recipe advancements
  accordingly — expected output, not a regression.
- **Loot-condition JSON format changed**, and unlike the above this one is hand-written so *nothing*
  caught it (see the previous section): the condition key inside an enchantment effect's
  `requirements` is now **`type`**, not `condition`, and `damage_source_properties` tag references
  need the **`#`** prefix (`"minecraft:is_fall"` → `"#minecraft:is_fall"`). Only
  `lapis_boots.json` used `requirements`; the other three Lapis enchantments are unaffected.
  Vanilla equivalents to copy the shape from: `feather_falling.json`, `protection.json`.
- **New** `DataComponents.MOB_VISIBILITY` + `MobVisibility` record (`targetingEntityTypes` HolderSet +
  `visibility` float) replaced `getVisibilityPercent`'s hardcoded head-item checks. See `glass-armor.md`.

## Enchantability cannot be 0

`Enchantable`'s constructor throws `IllegalArgumentException: Enchantment value must be positive, but was 0`,
and every tool/armor factory (`sword`, `spear`, `humanoidArmor`, …) calls
`enchantable(material.enchantmentValue())` internally. So a `ToolMaterial`/`ArmorMaterial` with
`enchantmentValue = 0` **crashes at class-load of `Items`** (`ExceptionInInitializerError`), not at runtime.

Also note `Enchantable.CODEC` uses `ExtraCodecs.POSITIVE_INT`, so `0` is out of range on serialization too.

**Vanilla makes an item unenchantable by omitting the `ENCHANTABLE` component, not by setting it to 0.**
`EnchantmentHelper.selectEnchantment` returns an empty list when the component is absent; with a value
present it computes `value / 4 + 1` (no divide-by-zero, just the weakest possible rolls).

The pattern used here: minimum legal value `1` on the material, then strip the component —
`Properties.notEnchantable()` in `Attributes.kt`. Note this blocks the enchanting *table* only; books and
anvils are gated by tags instead (see below).

## Removing a data component

Fabric's `Properties.modifyComponent(type) { … }` calls `DataComponentMap.Builder.set(type, result)`, and
`setUnchecked` treats `null` as `map.remove(type)`. So **returning `null` from the callback removes the
component**. There is no vanilla `Properties` method for this.

Order matters: `modifyComponent` runs after the factory, so `.sword(...).notEnchantable()` strips a component
the factory already set — but it cannot prevent the factory from *constructing* an invalid value first
(see above).

## Tag exclusion ("exclusion after inclusion")

**Vanilla** tags are additive-only: `TagFile` is just `values` + `replace`, and nothing in `TagBuilder` /
`TagAppender` / `TagFile` can subtract an entry.

**Fabric adds load-time removal.** `FabricTagAppender` (injected onto the appender `builder()` returns) provides
`remove` / `removeAll` / `removeTag`, and `fabric-tag-api-v1`'s `TagFileMixin` + `TagLoaderMixin` honour it at
load time. Datagen writes the entries under a **`"fabric:remove"`** key alongside `"values"`.

The important property: for a remove entry, `TagLoaderMixin` calls `TagEntry.build(lookup, set::remove)` — the
entry is resolved and subtracted from the **already-built element set**. So removal works even when the item was
never a direct entry and arrived through a *nested tag reference*.

That is what makes obsidian unenchantable while keeping it in `#minecraft:swords` etc. (those tags also drive
durability, sweeping, mining_loot and lunge, so dropping them would cost real behaviour):

```kotlin
builder(ItemTags.SWORDS).add(MyItemIds.OBSIDIAN_SWORD)              // keep tool behaviour
builder(ItemTags.SWEEPING_ENCHANTABLE).remove(MyItemIds.OBSIDIAN_SWORD)   // but not enchantable
```

Two things to get right:

- The `enchantable/*` tags are **derived transitively** from the tool/armor tags, so an item must be removed from
  every one it would inherit. Map from source tag → `enchantable/*` tags needing a removal:

  | Source tag | Feeds these `enchantable/*` tags |
  | :--- | :--- |
  | `#swords` | `durability`, `melee_weapon`, `sharp_weapon`, `weapon`, `fire_aspect`, `sweeping`, `vanishing` |
  | `#spears` | `durability`, `melee_weapon`, `sharp_weapon`, `weapon`, `fire_aspect`, `lunge`, `vanishing` |
  | `#axes` | `durability`, `mining`, `mining_loot`, `sharp_weapon`, `weapon`, `vanishing` |
  | `#pickaxes` `#shovels` `#hoes` | `durability`, `mining`, `mining_loot`, `vanishing` |
  | `#head_armor` `#chest_armor` `#leg_armor` `#foot_armor` | `durability`, `armor`, `equippable`, matching `enchantable/<slot>`, `vanishing` |

- **Parent tags need their own removal.** `weapon` contains `#enchantable/sharp_weapon`, `vanishing` contains
  `#enchantable/durability`, `armor` contains `#enchantable/<slot>`. Each tag is built independently, so removing
  from a child does not remove from its parent.

Vanilla constants are named `<THING>_ENCHANTABLE` (e.g. `ItemTags.MINING_LOOT_ENCHANTABLE`), not `ENCHANTABLE_*`.

Blocking the enchanting **table** as well is a separate concern — that is gated by the `ENCHANTABLE` *component*,
see above. Obsidian does both: `notEnchantable()` for the table, tag exclusion for books and anvils.

## Ability mechanisms (mixins + custom attributes)

Exact per-item numbers are in `minecraft-vanilla-materials.md` §6; this section records *how* each
ability is wired, since none of it is visible from the item factories.

### Emerald XP bonus — `PlayerMixin`

`@ModifyVariable` on `Player.giveExperiencePoints`, `HEAD`, `argsOnly`, variable `"i"`. Iterates
`EquipmentSlot.VALUES` (mainhand, offhand, feet, legs, chest, head, body); each slot holding an item
in `emerald_items_for_xp` adds `0.2` to a multiplier starting at `1.0`. Returns
`min((int)(i * multiplier), Integer.MAX_VALUE)` — note the float→int truncation.

Tag contents (`ItemTagsProvider.kt`): the 10 held/worn emerald items (sword, spear, axe, pickaxe,
shovel, hoe, helmet, chestplate, leggings, boots). Horse/wolf/nautilus armor are deliberately
excluded, so the practical maximum is 6 slots (both hands + 4 armor) = 2.2×.

### Obsidian fire reduction — `LivingEntityMixin`

`@Inject` into `LivingEntity.getDamageAfterArmorAbsorb` at `RETURN`, cancellable. Ignores damage
sources outside `DamageTypeTags.IS_FIRE`. Counts worn items in `obsidian_armor_for_fire` across
`EquipmentSlot.VALUES` and multiplies the post-armor value by `1 - 0.1 * pieces` (full humanoid
set = ×0.6).

Tag contents: the 4 humanoid pieces **plus** horse, wolf and nautilus obsidian armor — mounts and
pets benefit when wearing theirs.

### Obsidian movement penalty — `Attributes.obsidianMovementSpeed()`

`MOVEMENT_SPEED -0.05 ADD_MULTIPLIED_TOTAL` in `EquipmentSlotGroup.ARMOR`, chained on every obsidian
armor item including horse/wolf/nautilus (each with its own id in `AttributeIds`, e.g.
`OBSIDIAN_MOVEMENT_SPEED_HELMET`). The modifiers sum before multiplying, so a full set is
`1 - 4 × 0.05` = −20% move speed.

### Quartz mining efficiency — `Attributes.quartzMiningEfficiency()`

Prepends two `Tool.Rule`s to the factory-built `TOOL` component and preserves the existing rules
(`addAll(tool.rules)`, same `defaultMiningSpeed`): `instantTag` at speed `100f`, `fastTag` at speed
`16f` (both with `Optional.empty()` for the correct-drop flag, so drops follow the vanilla rules).
One tag pair per tool in `BlockTags` (`QUARTZ_<TOOL>_INSTANT` / `QUARTZ_<TOOL>_FAST`); exact block
lists live in `BlockTagsProvider.kt`. Note `QUARTZ_SWORD_FAST` is registered but empty — the sword
has instant blocks only.

## `ItemIds` vs `BlockItemIds`

`net.minecraft.references.ItemIds` only covers non-block items. A block's item form is in
`net.minecraft.references.BlockItemIds`, and the `ResourceKey<Item>` comes off the record:

```kotlin
builder(MyItemTags.OBSIDIAN_TOOL_MATERIALS).add(BlockItemIds.OBSIDIAN.item())  // no ItemIds.OBSIDIAN
```

## Status effects & equipment events (26.3)

Custom `MobEffect`s, effect instance particle/icon flags and the equipment-change event are covered
in `glass-armor.md` (the Glass Armor implementation is the reference). Short version: `MobEffect`'s
constructor is protected (subclass it); `EQUIPMENT_CHANGE` lives in `ServerEntityEvents`
(lifecycle-events-v1) and fires on mutation, not just slot changes.

**Mob AI targeting has no effect hook.** `TargetingConditions#test` sees an invisible target only
through `LivingEntity#getVisibilityPercent`, which reads the `invisible` flag set by
`updateInvisibilityStatus` — the target's effect list is never consulted, and a custom `MobEffect`
cannot contribute to targeting (verified in the 26.3 bytecode). To make mobs unable to see a
player, grant the vanilla `MobEffects.INVISIBILITY` and neutralize the `getArmorCoverPercentage()`
result *inside `getVisibilityPercent`* (armor makes an invisible player more detectable:
visibility = 0.7 × max(cover, 0.1), cover = non-empty humanoid armor slots / 4). Full reasoning in
`glass-armor.md`.

## Verifying datagen output

Per complete material (13 items) expect exactly:

```
13 assets/more-tools/items/<mat>_*.json
14 assets/more-tools/models/item/<mat>_*.json   # 14: the spear adds an _in_hand model
13 data/more-tools/recipe/<mat>_*.json
13 data/more-tools/advancement/recipes/**/<mat>_*.json
```

```bash
for m in amethyst emerald obsidian quartz; do
  printf "%-9s items=%2d models=%2d recipes=%2d adv=%2d\n" "$m" \
    "$(ls src/main/generated/assets/more-tools/items/${m}_* | wc -l)" \
    "$(ls src/main/generated/assets/more-tools/models/item/${m}_* | wc -l)" \
    "$(ls src/main/generated/data/more-tools/recipe/${m}_* | wc -l)" \
    "$(find src/main/generated/data/more-tools/advancement -name "${m}_*" | wc -l)"
done
```

A material missing a whole category means its provider call was skipped — datagen does **not** warn.
Check these counts rather than assuming `BUILD SUCCESSFUL` means complete output.

On top of the four 13-item materials, expect the wolf-armor extras: 5 shaped recipes
(`leather/copper/iron/gold/diamond_wolf_armor` + advancements) and 1 smithing-transform recipe
`netherite_wolf_armor_smithing` (base = mod diamond wolf armor, addition = netherite ingot,
template = netherite upgrade, category `MISC`, id from `RecipeIds.kt`) with its advancement under
`advancement/recipes/misc/`.

The glass set adds 4 armor-only items (no tools): 4 `items/` + 4 `models/item/` + 4 recipes +
4 combat advancements, plus entries in the four vanilla armor-slot tags and `repairs_glass_armor`.

## Lapis enchantment effects (26.3)

Facts discovered while implementing the Lapis enchantments (code in `src/main/kotlin/ir/mmd/mcdev/moretools/effects/`):

- `RandomSource` has only `nextFloat()` — no range overload; lerp manually (`min + nextFloat() * (max - min)`). `nextInt(min, max)` is exclusive of max.
- `LootContextParams.TOOL` is typed `ItemInstance`; for entity loot there is no tool param — read the killing player's main hand via `LAST_DAMAGE_PLAYER`. `LootContext.hasParameter` distinguishes block loot (`BLOCK_STATE`) from entity loot (`LAST_DAMAGE_PLAYER`).
- `minecraft:enchantable/melee_weapon` contains only swords + spears; axes are NOT included. For a sword/axe/spear set, use an explicit holder-set list `["#minecraft:swords", "#minecraft:axes", "#minecraft:spears"]`.
- Fabric loot-api-v3 `LootTableEvents.MODIFY_DROPS` receives the finished drop list per loot event; duplicate the list copies to add bonus drops.
- `EnchantedItemInUse.owner()` is nullable in Kotlin; `DamageSources.thorns(Entity)` is the vanilla retaliation damage source. Damage is applied via `LivingEntity.hurtServer(ServerLevel, source, amount)` — as of 26.3 `LivingEntity` no longer declares `hurt(DamageSource, float)` at all (client/server split), so `hurtServer` is the only option.
- Kotlin enum implementing `StringRepresentable`: don't declare a `name` constructor property (hides `Enum.name`); use `serialName` + `override fun getSerializedName()`.
- Registry-dependent default item components (e.g. pre-shipped `ENCHANTMENTS`) use `Item.Properties.delayedComponent(type) { registries -> value }`: initializers run during `ReloadableServerResources.loadResources` with the fully reloaded datapack registries, so datapack enchantments are resolvable there (verified via `BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(...)` call site). `supported_items`/`primary_items` as a JSON **array** accepts only plain item IDs — `#tag` entries only work as a single string.
- Removing the `ENCHANTABLE` component (see `nonEnchantable()` in `Attributes.kt`) only blocks the **enchanting table** (offers roll through enchantability). The **anvil book-combine** path checks only the enchantment's `supported_items` (`Enchantment.canEnchant`), and vanilla's `enchantable/foot_armor` etc. resolve through the item tags mod items are added to — so anvil enchanting still worked. Vanilla precedent: elytra has no `ENCHANTABLE` component yet is anvil-enchantable. Fix: `EnchantingPolicy.kt` registers Fabric's `EnchantmentEvents.ALLOW_ENCHANTING` (wired by Fabric into anvil book-combine, enchanting table, `/enchant` and loot `enchant_randomly`) denying items from this mod's namespace that lack `ENCHANTABLE`.
