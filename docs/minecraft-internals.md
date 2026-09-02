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
