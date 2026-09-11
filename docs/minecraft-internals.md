# Minecraft / Fabric Internals & Gotchas

Non-obvious API constraints hit while working on this mod, plus how to check such things yourself.
For vanilla *stat values* see `minecraft-vanilla-materials.md`.

## Inspecting vanilla code

**There is no sources jar in the loom cache** — `~/.gradle/caches/fabric-loom/26.2/` holds only compiled jars
(`minecraft-common.jar`, `minecraft-client.jar`, …). Don't hunt for decompiled sources; read the bytecode:

```bash
cd $TMPDIR
unzip -o -q ~/.gradle/caches/fabric-loom/26.2/minecraft-common.jar 'net/minecraft/world/item/Items.class' -d mc
javap -p -c mc/net/minecraft/world/item/Items.class > items.txt
```

Constant arguments show up as `ldc // float -2.4f` / `fconst_1` / `bipush 15` right before the
`invokevirtual` that consumes them, so `grep -B12` around a call site recovers exact values.
The jar is already Yarn-mapped, so names match what the Kotlin sees.

Vanilla **datapack** files (tag JSONs etc.) are in the same jar under `data/minecraft/...`.

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

## Lapis enchantment effects (26.2)

Facts discovered while implementing the Lapis enchantments (code in `src/main/kotlin/ir/mmd/mcdev/moretools/effects/`):

- `RandomSource` in 26.2 has only `nextFloat()` — no range overload; lerp manually (`min + nextFloat() * (max - min)`). `nextInt(min, max)` is exclusive of max.
- `LootContextParams.TOOL` is typed `ItemInstance`; for entity loot there is no tool param — read the killing player's main hand via `LAST_DAMAGE_PLAYER`. `LootContext.hasParameter` distinguishes block loot (`BLOCK_STATE`) from entity loot (`LAST_DAMAGE_PLAYER`).
- `minecraft:enchantable/melee_weapon` contains only swords + spears; axes are NOT included. For a sword/axe/spear set, use an explicit holder-set list `["#minecraft:swords", "#minecraft:axes", "#minecraft:spears"]`.
- Fabric loot-api-v3 `LootTableEvents.MODIFY_DROPS` receives the finished drop list per loot event; duplicate the list copies to add bonus drops.
- `EnchantedItemInUse.owner()` is nullable in Kotlin; `DamageSources.thorns(Entity)` is the vanilla retaliation damage source. Damage is applied via `LivingEntity.hurtServer(ServerLevel, source, amount)` — `hurt(source, amount)` is deprecated in 26.2 (client/server split).
- Kotlin enum implementing `StringRepresentable`: don't declare a `name` constructor property (hides `Enum.name`); use `serialName` + `override fun getSerializedName()`.
- Registry-dependent default item components (e.g. pre-shipped `ENCHANTMENTS`) use `Item.Properties.delayedComponent(type) { registries -> value }`: initializers run during `ReloadableServerResources.loadResources` with the fully reloaded datapack registries, so datapack enchantments are resolvable there (verified via `BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(...)` call site). `supported_items`/`primary_items` as a JSON **array** accepts only plain item IDs — `#tag` entries only work as a single string.
