# New Tool / Armor / Item Guide

This guide documents exactly how to add a new item to **more-tools**, based on how the amethyst toolset is implemented. Follow it top to bottom for a new *material* (a whole toolset: sword, pickaxe, armor, horse armor, ...), or skip the material-level steps when adding a single item to an existing material.

- Game / mappings: Minecraft 26.2, Fabric, Kotlin (see `gradle.properties`)
- Item count today: 58 — four full materials (amethyst, emerald, obsidian, quartz) of 13 items each, plus 6 wolf armors for vanilla materials. All registered through the same code path.
- Datagen: all models, item definitions, lang, tags and recipes are **generated** — do not hand-edit `src/main/generated/`

## Architecture overview

An item touches up to three layers:

1. **Code (main source set)** — `src/main/kotlin/ir/mmd/mcdev/moretools/`
   Identity, registration, materials, attributes. Loaded on both client and server.
2. **Code (client source set)** — `src/client/kotlin/.../client/providers/`
   Datagen providers. Their *output* ships inside the built jar.
3. **Hand-made assets** — `src/main/resources/assets/more-tools/`
   Textures and the equipment asset JSON. Everything else under `src/main/generated/` is produced by running datagen.

The one data flow to remember: **code → run datagen → generated JSON → game**. If you change code and forget to run datagen, the game runs with stale JSON.

## Step-by-step

### 1. Material (skip if reusing an existing material)

A "material" here is one shared set of stats + textures used by every item of a tier.

**`ItemIds.kt`** — add one `ResourceKey<Item>` per new item:

```kotlin
@JvmStatic val AMETHYST_SWORD = create("amethyst_sword")   // create() prefixes more-tools:
```

> ⚠️ `enchantmentValue` must be **≥ 1** — `0` crashes at class-load. If a material is meant to be unenchantable,
> use `1` and chain `.notEnchantable()` on each item. See `minecraft-internals.md`.

**`ToolMaterials.kt`** — one entry per tool material:

```kotlin
@JvmStatic val AMETHYST = ToolMaterial(
    BlockTags.INCORRECT_FOR_AMETHYST_TOOL,  // blocks this tool CANNOT mine
    350,                                    // durability
    6f,                                     // mining speed bonus
    2f,                                     // attack damage bonus (applied on top of per-item values)
    25,                                     // enchantability
    ItemTags.AMETHYST_TOOL_MATERIALS        // repair ingredient tag
)
```

**`ArmorMaterials.kt`** — one entry per armor material:

```kotlin
@JvmStatic val AMETHYST = ArmorMaterial(
    19,                                   // durability multiplier
    makeDefense(2, 5, 6, 2, 5),           // defense points by slot: boots, leggings, chestplate, helmet, body
    28,                                   // enchantability
    SoundEvents.ARMOR_EQUIP_DIAMOND,       // equip sound
    0f,                                   // toughness
    0f,                                   // knockback resistance
    ItemTags.AMETHYST_TOOL_MATERIALS,      // repair ingredient tag
    EquipmentAssets.AMETHYST              // equipment asset (armor textures)
)
```

**`BlockTags.kt` / `ItemTags.kt`** — declare the mod's own tags:

```kotlin
// BlockTags.kt
@JvmStatic val INCORRECT_FOR_AMETHYST_TOOL = create("incorrect_for_amethyst_tool")
// ItemTags.kt — the repair tag (vanilla names these repairs_<material>_armor)
@JvmStatic val AMETHYST_TOOL_MATERIALS = create("repairs_amethyst_armor")
```

**`EquipmentAssets.kt`** — one key per armor material, used in `assets/more-tools/equipment/<name>.json`:

```kotlin
@JvmStatic val AMETHYST = create("amethyst")
```

### 2. Register the items — `Items.kt`

`register(id, properties)` returns the `Item` and registers it in `BuiltInRegistries.ITEM` in object-init order, so **`Items` must be class-loaded before any registry use** (the client entrypoint calls `load(Items)` for this; the main entrypoint's registration happens at class-load).

Pick the right factory from `Item.Properties` and pass the material + per-item stats:

| Item kind | Factory call |
|---|---|
| Sword | `.sword(ToolMaterials.X, damage, attackSpeed)` |
| Spear | `.spear(ToolMaterials.X, ...)` — takes several charge/throw stats |
| Pickaxe / Axe / Shovel / Hoe | `.pickaxe / .axe / .shovel / .hoe(ToolMaterials.X, damage, attackSpeed)` |
| Humanoid armor | `.humanoidArmor(ArmorMaterials.X, ArmorType.HELMET / CHESTPLATE / LEGGINGS / BOOTS)` |
| Horse armor | `.horseArmor(ArmorMaterials.X)` |
| Wolf armor | `.wolfArmor(ArmorMaterials.X)` |
| Nautilus armor | `.nautilusArmor(ArmorMaterials.X)` |

```kotlin
@JvmStatic val AMETHYST_SWORD = register(
    ItemIds.AMETHYST_SWORD,
    Properties().sword(ToolMaterials.AMETHYST, 2.0f, -2.2f)
        .amethystKnockback()          // optional custom attribute, see step 3
        .amethystSweepDamage()
)
```

### 3. Custom attribute modifiers — `Attributes.kt`

For stat boosts beyond the built-in tool/armor stats, add an extension on `Properties`:

```kotlin
fun Properties.amethystKnockback() = addAttributeModifier(
    Attributes.ATTACK_KNOCKBACK,
    AttributeModifier(AttributeIds.AMETHYST_KNOCKBACK, 1.0, AttributeModifier.Operation.ADD_VALUE),
    EquipmentSlotGroup.MAINHAND
)
```

`AttributeIds` in the same file holds the modifier-id strings (must be unique per modifier).

⚠️ `addAttributeModifier` uses `withModifierAdded` and **preserves** modifiers already set by `.sword()` etc. — always add via these helpers, never replace the whole `ATTRIBUTE_MODIFIERS` component.

### 4. Creative tab placement — `Main.kt`

Items are inserted into vanilla tabs via Fabric's `modifyOutputEvent`, keyed to a vanilla item to anchor the ordering:

```kotlin
modifyCreativeTab(CreativeModeTabs.COMBAT) {
    insertAfter(Items.IRON_SWORD, MyItems.AMETHYST_SWORD)
    insertAfter(Items.IRON_BOOTS, MyItems.AMETHYST_HELMET, MyItems.AMETHYST_CHESTPLATE, ...)
}
modifyCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES) {
    insertAfter(Items.IRON_HOE, MyItems.AMETHYST_SHOVEL, ...)
}
```

Combat tab for weapons/armor; TOOLS_AND_UTILITIES for pickaxe/shovel/hoe/axe (note the axe appears in both, matching vanilla).

### 5. Textures — `src/main/resources/assets/more-tools/textures/`

**Item icons** — 16×16 PNG at `textures/item/<item_name>.png`. One per item. Name must match the item id path exactly.

Handheld items use `minecraft:item/handheld` parent; armor/flat items use `minecraft:item/generated` — choose in the model provider (step 6). Texture pixel art should match the material's color palette so the set reads as a family.

**Spear extra texture** — spears need a second `_in_hand` texture (`amethyst_spear_in_hand.png`) for the held/thrown model. The datagen call in step 6 expects it to exist.

**Armor textures** — `textures/entity/equipment/<slot>/<material>.png`, one PNG per layer slot the equipment asset references:

```
textures/entity/equipment/
    humanoid/amethyst.png           (helmet, chest, boots, arm)
    humanoid_baby/amethyst.png      (baby humanoid armor)
    humanoid_leggings/amethyst.png  (leggings-only layer)
    horse_body/amethyst.png
    nautilus_body/amethyst.png
    wolf_body/amethyst.png
```

Only create the files for slots your material actually equips. These are 64×32 (or larger, e.g. for wolf/horse bodies) layout PNGs mapping to the entity model — base them on the vanilla iron/diamond equivalents.

### 6. Equipment asset JSON — `src/main/resources/assets/more-tools/equipment/<material>.json`

Hand-written (not datagen). Maps each equipment slot to its texture, referenced from `ArmorMaterials` via `EquipmentAssets.AMETHYST`:

```json
{
  "layers": {
    "humanoid":          [ { "texture": "more-tools:amethyst" } ],
    "humanoid_baby":     [ { "texture": "more-tools:amethyst" } ],
    "humanoid_leggings": [ { "texture": "more-tools:amethyst" } ],
    "horse_body":        [ { "texture": "more-tools:amethyst" } ],
    "nautilus_body":     [ { "texture": "more-tools:amethyst" } ],
    "wolf_body":         [ { "texture": "more-tools:amethyst" } ]
  }
}
```

The texture path resolves to `textures/entity/equipment/<slot>/<name>.png`. Multiple layers/dyed layers are possible per the vanilla equipment asset format.

### 7. Datagen providers — `src/client/kotlin/.../client/providers/`

All five providers must be updated, then datagen run.

**`ModelProvider.kt`** — item models + client item definitions:

```kotlin
itemModelGenerators.generateSpear(Items.AMETHYST_SPEAR)          // generates both spear models + in_hand selection
itemModelGenerators.generateFlatItem(Items.AMETHYST_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM)  // tools/weapons
itemModelGenerators.generateFlatItem(Items.AMETHYST_HELMET, ModelTemplates.FLAT_ITEM)          // armor & flat items
```

- `FLAT_HANDHELD_ITEM` → parent `minecraft:item/handheld` (correct held rotation)
- `FLAT_ITEM` → parent `minecraft:item/generated`

Outputs go to `assets/more-tools/items/<item>.json` and `assets/more-tools/models/item/<item>.json`.

**`EnglishLanguageProvider.kt`**:

```kotlin
translationBuilder.add(Items.AMETHYST_SWORD, "Amethyst Sword")
```

→ `assets/more-tools/lang/en_us.json` (`item.more-tools.<name>`). Add other languages in a new provider if needed.

**`ItemTagsProvider.kt`** — vanilla tags first, then mod tags:

```kotlin
builder(MyItemTags.AMETHYST_TOOL_MATERIALS).add(ItemIds.AMETHYST_SHARD)  // repair ingredient(s)

builder(ItemTags.SWORDS     ).add(MyItemIds.AMETHYST_SWORD)
builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.AMETHYST_HELMET)
builder(ItemTags.CHEST_ARMOR).add(MyItemIds.AMETHYST_CHESTPLATE)
// ... SPEARS, PICKAXES, AXES, SHOVELS, HOES, LEG_ARMOR, FOOT_ARMOR
```

Vanilla tags matter: they gate enchantability, dispenser behavior, piglin interest, etc. Also remember `HEAD_ARMOR/CHEST_ARMOR/LEG_ARMOR/FOOT_ARMOR` for the four humanoid pieces.

**`BlockTagsProvider.kt`** — the material's mining tier. `INCORRECT_FOR_<MATERIAL>_TOOL` should usually chain to the vanilla tier it matches (amethyst sits at iron level):

```kotlin
builder(MyBlockTags.INCORRECT_FOR_AMETHYST_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
```

**`CraftingRecipeProvider.kt`** — the repo's `allCraftingRecipe(...)` builder generates the standard shaped recipe per item slot (sword, spear, pickaxe, axe, shovel, hoe, helmet, chestplate, leggings, boots, nautilus, horse, wolf). Pass the new items via the matching named parameters:

```kotlin
allCraftingRecipe(
    Items.AMETHYST_SHARD, output,   // crafting material = recipe ingredient + unlock criteria
    sword = MyItems.AMETHYST_SWORD,
    helmet = MyItems.AMETHYST_HELMET,
    // ...
)
```

For non-standard recipes, call the lower-level `craftingRecipe(category, material, outcome, pattern, output)` with a custom `pattern` (uses `X` = material, `S` = stick). Outputs land in `data/more-tools/recipe/` plus unlock advancements in `data/more-tools/advancement/recipes/<category>/`.

For smithing upgrades (currently only netherite wolf armor: base = diamond wolf armor, addition =
netherite ingot, template = netherite upgrade), use `SmithingTransformRecipeBuilder.smithing(...)`
directly and pass an explicit id from `RecipeIds.kt` to `.save(output, id)` — the shaped helper
above cannot express smithing.

### 8. Run datagen

```bash
./gradlew runDatagen
```

This regenerates `src/main/generated/` from the providers. Commit the result (it ships in the jar). Check `git status` after running — missing-file or stale-output surprises show up there.

Datagen reports `BUILD SUCCESSFUL` even when a provider was never called, so **count the output** — a whole
missing category is otherwise invisible. Per-material expected counts and a ready-made check script are in
`minecraft-internals.md`.

## Verifying your work

After datagen, a complete single armor item (`amethyst_helmet`) produces all of:

```
assets/more-tools/items/amethyst_helmet.json          (client item definition)
assets/more-tools/models/item/amethyst_helmet.json    (model)
assets/more-tools/lang/en_us.json                     (translation line)
data/minecraft/tags/item/head_armor.json              (vanilla slot tag)
data/more-tools/tags/item/repairs_amethyst_armor.json (repair tag, material-level)
data/more-tools/recipe/amethyst_helmet.json           (recipe)
data/more-tools/advancement/recipes/combat/amethyst_helmet.json
```

A complete single tool item (`amethyst_pickaxe`) additionally:

```
data/minecraft/tags/item/pickaxes.json
data/more-tools/tags/block/incorrect_for_amethyst_tool.json  (material-level)
```

If a file is missing, its provider step was skipped — map the missing file to the provider in step 7.

## Final checklist

**Material (once per material)**

- [ ] `ItemIds.kt` — key for every new item
- [ ] `ToolMaterials.kt` and/or `ArmorMaterials.kt` — stats, durability, enchantability, repair tag
- [ ] `BlockTags.kt` — `incorrect_for_<material>_tool` key
- [ ] `ItemTags.kt` — `repairs_<material>_armor` key
- [ ] `EquipmentAssets.kt` — equipment asset key (armor only)
- [ ] `equipment/<material>.json` — equipment asset with one entry per equippable slot
- [ ] `textures/entity/equipment/<slot>/<material>.png` for every slot listed in the equipment asset

**Every item**

- [ ] `ItemIds.kt` entry
- [ ] `Items.kt` — registered with correct factory (`sword`/`pickaxe`/`humanoidArmor`/…) and stats
- [ ] Custom attributes (if any) — unique id in `AttributeIds`, helper in `Attributes.kt`, chained via `withModifierAdded`
- [ ] `Main.kt` — creative tab placement with a sensible vanilla anchor item
- [ ] `textures/item/<item_name>.png` (16×16, exact id path)
- [ ] Spear only: `textures/item/<item_name>_in_hand.png`
- [ ] `ModelProvider` — `generateFlatItem` (or `generateSpear`) with the right template
- [ ] `EnglishLanguageProvider` — `en_us` translation
- [ ] `ItemTagsProvider` — correct vanilla tag(s) (`SWORDS`, `PICKAXES`, `HEAD_ARMOR`, …)
- [ ] `CraftingRecipeProvider` — recipe wired up
- [ ] `./gradlew runDatagen` run and output committed
- [ ] Per-material output counts verified (see `minecraft-internals.md`)

**Sanity checks after datagen**

- [ ] `items/`, `models/item/`, `lang/`, `recipe/`, advancement JSONs all exist for each new item (see "Verifying your work")
- [ ] Repair-tag JSON lists the right ingredient
- [ ] `git status` clean of unexpected deletions/changes in `src/main/generated/`

**In-game**

- [ ] `/give` the item — name, texture, model correct
- [ ] Attack/mine with it — damage, speed, knockback, block-breaking tier all as intended
- [ ] Wear armor pieces — texture on player (and baby zombie, if relevant), defense points correct
- [ ] Equip horse/wolf/nautilus armor on the matching mob
- [ ] Enchanting table offers sensible enchantments
- [ ] Anvil repair with the material's repair ingredient works
- [ ] Craft it — recipe and unlock trigger work
- [ ] Correct creative-tab position (after the intended vanilla item)

A broader per-item test matrix (smelting, loot, dispenser behavior, survival vs creative, ...) lives in `docs/checklist.md`.
