# Cactus Spear

A single special weapon (not part of a material tier): a trident-class spear made from cactus.

## Player-facing facts

- Crafted like every other spear (the standard diagonal pattern) with **cactus blocks**; repaired with cactus on an anvil.
- **Trident handling**: hold right-click to charge, release to throw; melee stab otherwise. Charging and release mirror vanilla trident input exactly.
- **Damage 50, melee and thrown alike** — the thrown projectile reads the weapon's own ATTACK_DAMAGE attribute, so a throw is exactly as strong as a stab.
- **Poison (10 seconds) on every hit** — melee stab *and* thrown hit.
- **Durability 1** — a true one-use weapon: throwing it spends it (a re-picked-up spear is already spent; any further use breaks it). A melee kill also breaks it.
- Ships with its built-in **Cactus Poison** enchantment pre-applied (like lapis magic — it is part of the item's default components).

## How it is wired

Two mod classes replace the vanilla trident pair; everything else is vanilla:

- **`CactusSpearItem`** (`extends TridentItem`): `use`/`getUseAnimation`/`releaseUsing` are lifted from vanilla `TridentItem` because vanilla (a) refuses to charge/throw a stack with `nextDamageWillBreak()` — always true at durability 1 — and (b) spawns the projectile through a hardcoded `ThrownTrident::new` factory. The override removes the gate and routes the spawn to the mod's own entity. The vanilla durability accounting is preserved: `hurtWithoutBreaking(1)` on the held stack, then `consumeAndReturn(1)` — the flying stack carries the throw's damage.
- **`ThrownCactusSpear`** (`extends AbstractArrow`): a `ThrownTrident` clone whose hit damage comes from the weapon stack's `ATTRIBUTE_MODIFIERS` (`compute(ATTACK_DAMAGE, base=1, MAINHAND)`) instead of vanilla's hardcoded `8.0f`. The loyalty/return machinery is dropped (the item is non-enchantable); after a hit the projectile deflects and lands exactly like a loyalty-less vanilla trident. The stack is never damaged further in flight; pickup/despawn follow the normal arrow rules.
- **`EntityTypes.THROWN_CACTUS_SPEAR`** (`EntityTypes.kt` + `EntityIds.kt`): registered with the vanilla TRIDENT parameters (`MISC`, no loot table, 0.5×0.5 box, tracking range 4, update interval 20).
- **Renderer** (client): `ThrownCactusSpearRenderer` + `PlainTridentLikeRenderer` — the vanilla `ThrownTridentRenderer` transform/model/foil logic parameterized by texture, because vanilla hardcodes `textures/entity/trident/trident.png`. Texture: `assets/more-tools/textures/entity/cactus_spear/cactus_spear.png` (64×32, trident atlas layout; `ModelLayers.TRIDENT` is baked so geometry matches vanilla). Entity renderer registered via vanilla `EntityRenderers.register` in `MainClient`.

## Damage & poison details

- **Melee 50** = player base 1 + item attribute `49` set by `Properties.attackDamageTotal(50.0)`
  (`Attributes.kt`). The helper builds a fresh `ItemAttributeModifiers` whose only ATTACK_DAMAGE entry
  uses `Item.BASE_ATTACK_DAMAGE_ID` — the same modifier id `TridentItem.createAttributes()` used — so
  `withModifierAdded` *replaces* the trident's 8.0 instead of stacking. The 50 in `Items.kt` is the
  single source of truth; the helper subtracts the player's base 1 internally (the game adds that
  base itself, it is not part of the item).
- **Thrown 50**: `ThrownCactusSpear.onHitEntity` computes the same attribute value (49 + 1 base) and
  still passes it through `EnchantmentHelper.modifyDamage`, so enchantment damage modifiers keep working.
- **Poison 10s on every hit**: the `post_attack → apply_mob_effect` component of `cactus_poison`
  (the vanilla `bane_of_arthropods` pattern, no condition). The melee stab path and the thrown hit
  (via `doPostAttackEffectsWithItemSourceOnBreak`) both dispatch `post_attack` with the weapon stack,
  so one enchantment covers both. `cactus_poison.json` contains **only** the poison effect — thrown
  damage is handled by the entity, not data.
- **Pre-attached enchantment**: `lapisMagic(Enchantments.CACTUS_POISON)` (the same delayed-component
  helper the lapis items use).

## Durability accounting (durability 1, verified against vanilla behavior)

- **Throw**: `hurtWithoutBreaking(1)` on the held stack → the flying stack is at damage 1/1. The
  projectile itself never damages the stack after the throw (mirroring vanilla, where neither
  `AbstractArrow` nor `ThrownTrident` touches the stack on impact).
- **Pickup after a throw**: returns a spent spear (damage 1/1). Any further use breaks it — the
  throw already consumed the item's one life.
- **Melee**: `WEAPON(1)` component → each stab costs 1, so the first stab breaks it.
- Creative-mode throws set `pickup = CREATIVE_ONLY`, as vanilla does.

## History / rejected alternatives (do not re-suggest)

- **Pure vanilla item (plain `TridentItem`)**: at durability 1 vanilla refuses to charge *and* throw
  (`nextDamageWillBreak()` gate), thrown damage was hardcoded 8, and the thrown entity rendered with
  the trident texture — this is exactly what the first in-game test showed. Durability 2 was only
  ever a workaround for that gate.
- **`+42` damage entry in `cactus_poison.json`**: worked for thrown damage but put weapon damage in
  a poison enchantment; replaced by the entity reading the item's own attribute.
- **A `ThrownTrident`/`TridentItem` subclass without overrides**: impossible — `TridentItem.releaseUsing`
  spawns the projectile through a hardcoded `ThrownTrident::new` `invokedynamic` factory.

## Datagen / assets

- `ModelProvider.generateSpear` handles the client item definition + models (expects
  `textures/item/cactus_spear.png` and `cactus_spear_in_hand.png` — currently lapis stubs, replace later).
- Entity translation key `entity.more-tools.thrown_cactus_spear` ("Thrown Cactus Spear") is generated
  in `EnglishLanguageProvider`.
- `ItemTagsProvider`: added to `minecraft:spears` (melee stab behavior + creative-tab grouping parity
  with the other spears) and to the mod's `enchantable/cactus_spear` tag (the enchantment's
  `supported_items`/`primary_items`).
- Recipe: standard spear pattern with `minecraft:cactus`, generated through
  `CraftingRecipeProvider.allCraftingRecipe(Items.CACTUS, output, spear = MyItems.CACTUS_SPEAR)` —
  the same path every other spear uses.
