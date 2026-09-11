# Lapis Enchantments

The implemented Lapis magic system. Balancing lives in one file:
`src/main/kotlin/ir/mmd/mcdev/moretools/effects/LapisConfig.kt` — change numbers there only.
Reference bounds for every value come from vanilla enchantments (see
`lapis-effect-building-blocks.md` for the per-item vanilla tables they were derived from).

## The four enchantments

Data-driven definitions in `src/main/resources/data/more-tools/enchantment/`, all `max_level: 1`
(the custom effects scale by level anyway, so raising max_level later works without redesign):

| ID | supported/primary items | effects used |
| --- | --- | --- |
| `more-tools:lapis_weapon` | `#more-tools:enchantable/lapis_weapon` (= swords + axes + spears, see Tags) | `minecraft:damage`, `minecraft:knockback`, `minecraft:post_attack` (ignite) |
| `more-tools:lapis_tool` | `#minecraft:enchantable/mining` | none — drops handled by `LapisLoot` |
| `more-tools:lapis_armor` | `#more-tools:enchantable/lapis_armor` (= head/chest/leg armor) | `minecraft:damage_protection`, `minecraft:post_attack` (thorns) |
| `more-tools:lapis_boots` | `#minecraft:enchantable/foot_armor` | `minecraft:damage_protection` ×2 (general + fall-gated), `minecraft:post_attack` (thorns) |

Every effect is its own `ConditionalEffect` entry with its own probability roll — vanilla applies
each matching entry independently, so multiple effects can trigger in the same interaction and
there is no shared/global roll anywhere.

## Custom effect types

Registered in `effects/Effects.kt` under `more-tools:lapis_value` (a `EnchantmentValueEffect`,
dispatched by `damage`/`knockback`/`damage_protection`) and `more-tools:lapis_entity`
(an `EnchantmentEntityEffect`, dispatched by `post_attack`). Each takes a `kind` field selecting
the behavior; probabilities and magnitudes come from `LapisConfig`:

| Effect | Trigger | Chance | Magnitude | Vanilla reference |
| --- | --- | ---: | --- | --- |
| weapon damage | `damage` | 25% | +1.0–3.0 (float) | Sharpness I–V |
| weapon knockback | `knockback` | 20% | +1.0–2.0 | Knockback I–II |
| weapon ignite | `post_attack` attacker→victim | 10% | 4–8 s | Fire Aspect I–II |
| weapon entity drops | `LapisLoot` | 15% | batch ×1–3 | Looting I–III |
| tool block drops | `LapisLoot` | 20% | batch ×1–4 | Fortune III |
| armor/boots protection | `damage_protection` | 20% | 1–5 points (4% each) | Protection I–V |
| boots fall protection | `damage_protection` + `is_fall` gate | 30% | 3–12 points | Feather Falling I–IV |
| thorns retaliation | `post_attack` victim→attacker | 15% per piece | 1–4 damage | Thorns I–III (15%/piece) |

Continuous values use a uniform float across the bounds; integer values use
`nextInt(min, max + 1)`. Magnitudes scale by enchantment level (currently always 1).

## Drop bonuses (`LapisLoot`)

The two drop bonuses cannot use vanilla components: loot tables' `enchanted_count_increase` only
fires for hard-coded vanilla enchantments. They are implemented on Fabric loot-api-v3's
`LootTableEvents.MODIFY_DROPS`:

- Block loot (context has `BLOCK_STATE`): both enchantments evaluated independently on the held
  tool — `lapis_tool` for pickaxe/shovel/hoe, `lapis_weapon` for sword/axe/spear (weapons mine
  blocks too: axe → logs, sword → bamboo).
- Entity loot (context has `LAST_DAMAGE_PLAYER`): `lapis_weapon` on the killer's main hand.

One independent roll decides whether the whole finished drop batch is duplicated; the batch is
multiplied by a random magnitude within bounds. No Mixins.

## Pre-shipped magic

All 11 Lapis items are `.nonEnchantable()` **and** carry their enchantment via
`Items.lapisMagic(...)` → `Item.Properties.delayedComponent(ENCHANTMENTS)`. The delayed form is
required because the component stores registry-resolved `Holder<Enchantment>`s and enchantments
load from the datapack after item registration; initializers run in
`ReloadableServerResources.loadResources` with the fully reloaded registries (verified in the 26.2
bytecode, see `minecraft-internals.md`). The magic is therefore innate: present from first craft,
immune to `/enchant` and anvil stacking, and unremovable in normal play.

## Testing

```
/enchant @s more-tools:lapis_weapon 1   # on a vanilla sword/axe/spear
/enchant @s more-tools:lapis_tool 1     # on a vanilla pickaxe/shovel/hoe
/enchant @s more-tools:lapis_armor 1    # on a vanilla helmet/chestplate/leggings (boots rejected)
/enchant @s more-tools:lapis_boots 1    # on vanilla boots
```

Lapis items themselves ship enchanted — give one and hit/mine/wear it. Each proc is independent;
log-style verification: effects print nothing by design, observe damage numbers, fire ticks,
knockback distance, doubled drops, damage flashes (protection), thorns pings and reduced fall
damage.
