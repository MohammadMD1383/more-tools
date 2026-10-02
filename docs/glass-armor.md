# Glass Armor — set-bonus invisibility

Four armor pieces (helmet, chestplate, leggings, boots) with **durability 1 and zero defense**.
Wearing all four grants **vanilla Invisibility** tuned so mobs cannot see the player; losing any
piece removes it. Files: `effects/GlassInvisibility.kt` (set-bonus helpers),
`effects/GlassArmorInvisibility.kt` (reconciliation), one injection in `mixins/LivingEntityMixin.java`,
`EquipmentAssets.GLASS` + `assets/more-tools/equipment/glass.json`.

## Why vanilla's effect + the armor-cover injection

- **Vanilla Invisibility, not a custom effect** — mob AI targeting has no effect hook to extend.
  `TargetingConditions#test` (used by every `NearestAttackableTargetGoal`) evaluates the invisible
  target through `LivingEntity#getVisibilityPercent`, which reads only the `invisible` flag; the
  target's effect list is never consulted (`MobEffect` has no targeting hook, still true in 26.3). Carrying the
  real vanilla effect means every consumer in the game already understands it — no flag mixin was
  needed (the previous design's `updateInvisibilityStatus` injection is gone).
- **Armor cover is what gave the bug away** — `getVisibilityPercent` multiplies an invisible
  entity's visibility by `0.7 × max(getArmorCoverPercentage(), 0.1)`, and `getArmorCoverPercentage`
  counts *non-empty* armor slots (4/4 = 1.0 for the full set). Armor — even zero-defense glass —
  therefore made mobs detect the invisible player at 70% of follow range instead of the ~2 blocks
  a naked invisible player gets. The single `@ModifyExpressionValue` in `LivingEntityMixin` returns
  the naked-player clamp (0.1) while the full set is worn, reproducing potion-without-armor numbers.
  It replaces the `getArmorCoverPercentage()` **result inside `getVisibilityPercent`** (not the
  method itself, whose return value is also used for the skeleton/zombie/piglin head-disguise
  detection branch).
- `INFINITE_DURATION` + ambient + `visible=false` + `showIcon=false` — the
  `(holder, duration, amplifier, ambient, visible, showIcon)` `MobEffectInstance` constructor covers
  particles (sync filter is `MobEffectInstance::isVisible`) and HUD icon (`showIcon()` gate) with
  zero custom logic. Waypoint transmit-range hiding falls out of vanilla Invisibility's own
  attribute modifier.

## Ownership model

"Glass Armor owns the invisibility" is simply *the player carries the set-bonus Invisibility
instance* — there is no separate state to save/restore/drift. `updateInvisibilityState` adds the
effect when the full set is worn; on set loss it removes it **only if the instance carries the
set-bonus fingerprint** (`infinite + ambient + no icon` — a potion can never look like that: beacon
effects are ambient but show the icon), so a deliberately drunk potion survives. A pre-existing
Invisibility also survives the *grant*: `addEffect` upserts by effect id without touching unrelated
instances, and when the set breaks the deliberately-drunk potion stays while the set-bonus instance
is stripped.

## Event wiring (all in `GlassArmorInvisibility.register`)

| Event | Purpose |
| :--- | :--- |
| `ServerEntityEvents.EQUIPMENT_CHANGE` | equip / unequip / replace / durability mutation / break (break = slot empties: previous=stack, current=EMPTY) → reconcile |
| `ServerMobEffectEvents.AFTER_REMOVE` | self-heal: milk, totem, `/effect clear` strip the effect while the set is still worn → re-add. CME-safe: `removeAllEffects` copies the map before clearing; expiry can't reach it (`INFINITE_DURATION` never ticks out, expiry path bypasses this event) |
| `ServerPlayerEvents.JOIN` / `AFTER_RESPAWN` | idempotent reconcile: the effect persists in saves and tracked-slot events only fire for non-empty slots, so a stale effect would survive a relog |

## The armor-cover mixin (`LivingEntityMixin`)

```java
@ModifyExpressionValue(
    method = "getVisibilityPercent",
    at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;getArmorCoverPercentage()F"),
    require = 1
)
private float moretools$glassSetDoesNotCountAsArmorCover(float original) {
    return GlassInvisibility.armorCoverForGlass(original, (LivingEntity) (Object) this);
}
```

Guarded by `GlassInvisibility.armorCoverForGlass` (spectator and `ArmorStand` excluded — the stand
is a `LivingEntity` whose armor is display state). Exactly one `getArmorCoverPercentage` call
exists in the method, so `require = 1` fails loudly if vanilla changes.

26.3 changed the target's signature to `getVisibilityPercent(ServerLevel, Entity)` but left the
single overload, the `getArmorCoverPercentage()F` call site, and `TargetingConditions#test` as its
only caller — so the name-only `method = "getVisibilityPercent"` and the `INVOKE` target are both
still correct with no edit. Verify by hand after any version bump (see
`minecraft-internals.md`); the compiler will not catch a broken mixin target.

### 26.3 offers a data-driven alternative

26.3 replaced the method's old hardcoded head-item checks (skeleton / zombie / piglin / creeper
heads suppressing detection) with the `DataComponents.MOB_VISIBILITY` component:

```java
public record MobVisibility(HolderSet<EntityType<?>> targetingEntityTypes, float visibility)
```

(JSON: `targeting_entity_types` holder set + `visibility` float clamped to 0.0–10.0.)

`getVisibilityPercent` multiplies its result by `visibility()` for every worn piece whose
`MOB_VISIBILITY.targetingEntityTypes()` contains the observer. That is the same shape of effect as
this mixin (scale detectability by worn gear), done with data instead of bytecode — so the mixin
could eventually be replaced by shipping `mob_visibility` on the Glass pieces. It is not a drop-in
replacement: the component scales *all* observers by a fixed float, while the mixin reproduces the
naked-invisible 0.1 armor-cover clamp, and the component cannot see "is the full set worn". Kept as
a note, not a migration.

## Armor durability = 1

`.humanoidArmor(...)` computes `ArmorType.getDurability(material.durability)` = unit × multiplier
(11–16 per piece even at multiplier 1). Chaining `.durability(1)` after the factory rewrites
`MAX_DAMAGE` directly → literal durability 1 (see `Items.kt` glass entries).

## Textures / assets

- 26.3 equipment textures (paths unchanged from 26.2): `textures/entity/equipment/humanoid(_leggings|_baby)/<material>.png`
  (referenced from `equipment/<material>.json`); item icons at `textures/item/<item>.png`.
- Glass layers are the vanilla diamond layers desaturated and tinted `#D9F0F5`; item icons add
  two translucent white diagonal shine strokes. Regenerate with ImageMagick if lost:
  `magick diamond.png -colorspace Gray -fill "#D9F0F5" -colorize 100 -colorspace sRGB out.png`
- `MobEffect`'s constructor is **protected** — subclass it (`private class Effect : MobEffect(...)`)
  rather than instantiating directly. (Only relevant if a custom effect is ever added again.)
