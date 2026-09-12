# Glass Armor — set-bonus invisibility

Four armor pieces (helmet, chestplate, leggings, boots) with **durability 1 and zero defense**.
Wearing all four grants a custom **Glass Invisibility** status effect; losing any piece removes it.
Files: `effects/GlassInvisibility.kt` (the effect), `effects/GlassArmorInvisibility.kt` (reconciliation),
one injection in `mixins/LivingEntityMixin.java`, `EquipmentAssets.GLASS` + `assets/more-tools/equipment/glass.json`.

## Why each piece is where it is

- **Effect instead of vanilla Invisibility** — a separate `MobEffect` (`more-tools:glass_invisibility`)
  keeps Glass Armor invisible apart from vanilla Invisibility and other mods' sources. All effects
  coexist; vanilla Invisibility is never inspected, saved or restored by this code.
- **Equipment-change event instead of polling** — Fabric 26.2 moved `EQUIPMENT_CHANGE` to
  `ServerEntityEvents` (fabric-lifecycle-events-v1). It fires from vanilla's
  `LivingEntity#collectEquipmentChanges` diff (`ItemStack.matches`, so *mutation* counts as change),
  and once per non-empty slot at first tracking — no per-tick handler needed.
- **Invisibility via a one-line argument widen** — vanilla's only invisibility hook is the argument
  of `setInvisible(hasEffect(INVISIBILITY))` inside `LivingEntity#updateInvisibilityStatus`; there is
  no public API to extend it, hence the mixin (below).
- **`INFINITE_DURATION` + ambient + `visible=false` + `showIcon=false`** — the
  `(holder, duration, amplifier, ambient, visible, showIcon)` `MobEffectInstance` constructor covers
  particles (sync filter is `MobEffectInstance::isVisible`) and HUD icon (`showIcon()` gate) with zero
  custom logic. `Player` does **not** override `updateInvisibilityStatus` (only `ServerPlayer` does,
  chaining to `super`), so one mixin on the base method covers players.

## Ownership model

"Glass Armor owns the invisibility" is simply *the player carries the Glass effect* — there is no
separate state to save/restore/drift. `updateInvisibilityState` adds the effect when the full set is
worn, removes it otherwise; nothing else is ever touched. A pre-existing vanilla Invisibility is an
independent effect instance and survives the whole cycle untouched.

## Event wiring (all in `GlassArmorInvisibility.register`)

| Event | Purpose |
| :--- | :--- |
| `ServerEntityEvents.EQUIPMENT_CHANGE` | equip / unequip / replace / durability mutation / break (break = slot empties: previous=stack, current=EMPTY) → reconcile |
| `ServerMobEffectEvents.AFTER_REMOVE` | self-heal: milk, totem, `/effect clear` strip the effect while the set is still worn → re-add. CME-safe: `removeAllEffects` copies the map before clearing; expiry can't reach it (`INFINITE_DURATION` never ticks out, expiry path bypasses this event) |
| `ServerPlayerEvents.JOIN` / `AFTER_RESPAWN` | idempotent reconcile: the effect persists in saves and tracked-slot events only fire for non-empty slots, so a stale effect would survive a relog |

## The invisibility mixin (`LivingEntityMixin`)

```java
@ModifyExpressionValue(
    method = "updateInvisibilityStatus",
    at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z"),
    require = 1
)
private boolean moretools$glassInvisibilityMakesInvisible(boolean original) {
    return original || ((LivingEntity) (Object) this).hasEffect(GlassInvisibility.GLASS_INVISIBILITY);
}
```

Exactly one `hasEffect` call exists in the method, so `require = 1` fails loudly if vanilla changes.
Re-evaluation is event-driven (any effect add/remove/update sets `effectsDirty` →
`updateDirtyEffects` → `updateInvisibilityStatus`), so no mod-side polling and no flag restoration:
coexistence of any invisible-source combination falls out of the flag being recomputed from all
present effects.

## Armor durability = 1

`.humanoidArmor(...)` computes `ArmorType.getDurability(material.durability)` = unit × multiplier
(11–16 per piece even at multiplier 1). Chaining `.durability(1)` after the factory rewrites
`MAX_DAMAGE` directly → literal durability 1 (see `Items.kt` glass entries).

## Textures / assets

- 26.2 equipment textures: `textures/entity/equipment/humanoid(_leggings|_baby)/<material>.png`
  (referenced from `equipment/<material>.json`); item icons at `textures/item/<item>.png`.
- Glass layers are the vanilla diamond layers desaturated and tinted `#D9F0F5`; item icons add
  two translucent white diagonal shine strokes. Regenerate with ImageMagick if lost:
  `magick diamond.png -colorspace Gray -fill "#D9F0F5" -colorize 100 -colorspace sRGB out.png`
- `MobEffect`'s constructor is **protected** — subclass it (`private class Effect : MobEffect(...)`)
  rather than instantiating directly.
