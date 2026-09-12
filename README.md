# More Tools

A Fabric mod for Minecraft 1.21.11 that adds **75 new items**: five complete tool/armor tiers
(**lapis, amethyst, emerald, obsidian, quartz**, 13 items each), **6 wolf armors for vanilla
materials** that vanilla never gave you, and a special **glass armor** set with full-set invisibility.

Each tier is built from an in-game material (lapis lazuli, amethyst shard, emerald, obsidian, quartz)
and has its own stat profile. Nothing here is a reskin: mining power, damage, speed, protection,
enchantability, movement, XP, fire resistance and even per-block mining rules all differ per tier.

- Requirements: Minecraft 1.21.11 with Fabric Loader, Fabric API, and Fabric Language Kotlin
- Adds 75 items total: 5 tiers × 13 items, + 6 extra wolf armors, + 4 glass armor pieces

Every tier contains the same 13-item set:

| Slot | Items |
| :--- | :--- |
| Weapons | Sword, Spear |
| Tools | Pickaxe, Axe, Shovel, Hoe |
| Humanoid armor | Helmet, Chestplate, Leggings, Boots |
| Mount/pet armor | Horse armor, Wolf armor, Nautilus armor |

Plus 6 extra wolf armors: leather, copper, iron, gold, diamond, netherite.

Tiers run from **lapis** (a cheap, fragile starter tier that can't be enchanted) up to
**quartz** (1050 durability, diamond mining power).

---

## 1. Tool tiers: mod vs. vanilla

Sorted by durability ascending, so each mod tier sits next to its vanilla neighbours.
(Higher mining speed = faster digging; higher damage bonus = harder hits.)

| Tier | Durability | Mining speed | Damage bonus | Enchantability | Mines like | Repaired with |
| :--- | ---: | ---: | ---: | ---: | :--- | :--- |
| Gold | 32 | 12.0 | 0.0 | 22 | gold | gold ingot |
| **Lapis** | **50** | **3.0** | **0.5** | **— (can't be enchanted)** | **stone** | **lapis lazuli** |
| Wood | 59 | 2.0 | 0.0 | 15 | wood | planks etc. |
| Stone | 131 | 4.0 | 1.0 | 5 | stone | cobblestone etc. |
| Copper | 190 | 5.0 | 1.0 | 13 | copper | copper ingot |
| Iron | 250 | 6.0 | 2.0 | 14 | iron | iron ingot |
| **Amethyst** | **350** | **6.0** | **2.0** | **25** | **iron** | **amethyst shard** |
| **Emerald** | **600** | **8.0** | **3.0** | **3** | **iron** | **emerald** |
| **Obsidian** | **800** | **4.0** | **1.0** | **— (can't be enchanted)** | **iron** | **obsidian** |
| **Quartz** | **1050** | **12.0** | **0.0** | **10** | **diamond** | **quartz** |
| Diamond | 1561 | 8.0 | 3.0 | 10 | diamond | diamond |
| Netherite | 2031 | 9.0 | 4.0 | 15 | netherite | netherite ingot |

Durability progression:

```
gold 32 < lapis 50 < wood 59 < stone 131 < copper 190 < iron 250
  < amethyst 350 < emerald 600 < obsidian 800 < quartz 1050
  < diamond 1561 < netherite 2031
```

What this means in practice:

- **Lapis** = the mod's bottom tier. Digging speed (3.0) and damage bonus (0.5) land between wood and
  stone, making it a strict upgrade over a wooden set — but its 50 durability is below wood's 59, so it
  breaks sooner. **Fully unenchantable** — its power comes from built-in lapis magic instead (§3).
  Cheap to make and cheap to repair if you have lapis to spare; a starter or throwaway set.
- **Amethyst** = iron-grade digging and damage with ~40% more durability and the best
  enchantability in the game (25, above gold's 22).
- **Emerald** = diamond-grade digging and damage at ~38% of diamond's durability and iron
  mining power, with terrible enchantability (3).
- **Obsidian** = stone-grade digging and damage at iron mining power, good durability (800),
  and **fully unenchantable** — it can't use the enchanting table, enchanted books, or anvil
  enchantments at all.
- **Quartz** = gold-grade speed and damage with diamond mining power, near-diamond
  durability (1050), and diamond-level enchantability (10). Fast but weak per hit.

Mining-power consequence:

- Lapis mines at **stone level**: iron, copper and lapis ore yes; **gold, redstone, diamond and
  emerald ore no**.
- Amethyst / emerald / obsidian mine everything iron can (including diamond ore with the right
  tool), but **cannot** mine obsidian blocks, ancient debris, etc. the way diamond can.
- Quartz is the **only** mod tier that mines at diamond level (obsidian blocks included).

### Attack damage per weapon/tool

Damage depends on the material plus the item type. Final hit damage, compared to vanilla:

| Item | Lapis | Amethyst | Emerald | Obsidian | Quartz |
| :--- | ---: | ---: | ---: | ---: | ---: |
| Sword | **4.5** | **5** | **7** (= diamond sword) | **5** (= stone sword) | **4** (= gold sword) |
| Pickaxe | **2.5** | **4** | **5** (= diamond) | **3** (= stone) | **2** (= gold) |
| Axe | **8** | **9** | **9** (= diamond axe) | **9** (= stone axe) | **7** (= gold axe) |
| Shovel | **3** | **4.5** | **5.5** (= diamond) | **3.5** (= stone) | **2.5** (= gold) |
| Hoe | **1** | **1** | **1** | **1** | **1** (all hoes deal 1) |
| Spear | **1.5** | **3** | **4** (= diamond spear) | **2** (= stone spear) | **1** (= gold spear) |

Takeaway: emerald hits exactly like diamond on every weapon; obsidian hits exactly like stone;
quartz hits exactly like gold; amethyst hits like iron (sword 5, axe 9, pick 4). Lapis is the only
tier that lands on **half-heart numbers** — its damage bonus is 0.5, halfway between wood and stone,
so a lapis sword deals 4.5 (wood 4, stone 5) and a lapis axe 8 (wood 7, stone 9).

### Attack speed (higher = faster swings)

| Item | Lapis | Amethyst | Emerald | Obsidian | Quartz | Vanilla reference |
| :--- | ---: | ---: | ---: | ---: | ---: | :--- |
| Sword | 1.6 | **1.8** | 1.6 | **1.2** | 1.6 | iron/diamond/gold 1.6 |
| Pickaxe | 1.2 | **1.4** | 1.2 | **0.9** | 1.2 | diamond 1.2 |
| Axe | 0.8 | **1.1** | 1.0 | **0.6** | 1.0 | diamond/netherite 1.0 |
| Shovel | 1.0 | **1.2** | 1.0 | **0.7** | 1.0 | diamond 1.0 |
| Hoe | 1.5 | **4.0** | 4.0 | **0.7** | **4.0** | diamond/netherite 4.0 |
| Spear (attacks/second) | **~1.43** | **~1.33** | ~0.95 (= diamond) | **~0.8** | ~0.95 (= diamond) | wood ~1.54 … netherite ~0.87 |

- **Amethyst tools swing faster than their iron/diamond equivalents** (sword 1.8 vs 1.6,
  pick 1.4 vs 1.2, axe 1.1 vs 1.0).
- **Obsidian is deliberately the slowest-swinging material in the game** on every weapon and tool,
  and its spear is slower than netherite's (the slowest vanilla spear).
- **Quartz swings as fast as diamond** but hits like gold: fast swings, weak hits.
- **Lapis swings at ordinary speeds** (sword 1.6, like iron/diamond/gold) but its axe (0.8) and hoe
  (1.5) are slower than their vanilla peers.
- **Lapis spear has the quickest attack cycle of any spear** (~1.43/sec vs amethyst's ~1.33 and
  netherite's ~0.87), but hits for only 1.5 — a poke-and-retreat weapon. Amethyst's is the fastest
  *useful* spear with iron-tier damage; emerald/quartz spears handle like diamond.

---

## 2. Armor tiers: mod vs. vanilla

Sorted by full-set protection descending, so each mod armor sits next to the vanilla armor it
protects like. (Durability score: higher = each piece lasts longer. Full-set defense: total armor
points wearing all four pieces.)

| Material | Durability | Defense (boots, leggings, chestplate, helmet, pet armor) | Full-set defense | Enchantability | Toughness | Knockback resist |
| :--- | ---: | :--- | ---: | ---: | ---: | ---: |
| Diamond | 33 | (3, 6, 8, 3, 11) | 20 | 10 | 2 | 0 |
| Netherite | 37 | (3, 6, 8, 3, 19) | 20 | 15 | 3 | 0.1 |
| **Emerald** | **21** | **(3, 6, 8, 3, 11)** | **20 (= diamond)** | **3** | 0 | 0 |
| **Obsidian** | **25** | **(3, 6, 8, 3, 11)** | **20 (= diamond)** | **— (can't be enchanted)** | **1** | **0.15** |
| Iron | 15 | (2, 5, 6, 2, 5) | 15 | 9 | 0 | 0 |
| **Amethyst** | **19** | **(2, 5, 6, 2, 5)** | **15 (= iron)** | **28** | 0 | 0 |
| Chainmail | 15 | (1, 4, 5, 2, 4) | 12 | 12 | 0 | 0 |
| Gold | 7 | (1, 3, 5, 2, 7) | 11 | 25 | 0 | 0 |
| **Quartz** | **29** | **(1, 3, 5, 2, 7)** | **11 (= gold)** | **10** | 0 | 0 |
| Copper | 11 | (1, 3, 4, 2, 4) | 10 | 8 | 0 | 0 |
| **Lapis** | **9** | **(1, 3, 4, 2, 5)** | **10 (= copper)** | **— (can't be enchanted)** | 0 | 0 |
| Leather | 5 | (1, 2, 3, 1, 3) | 7 | 15 | 0 | 0 |
| **Glass** | **1** | **(0, 0, 0, 0, 0)** | **0** | **— (can't be enchanted)** | 0 | 0 |

- **Amethyst armor** = iron protection, longer-lasting than iron, and enchantability
  **28 — the highest armor enchantability in the game** (gold is 25). Best candidate for
  heavily enchanted iron-class armor.
- **Emerald armor** = diamond protection at roughly two-thirds of diamond's longevity,
  with miserable enchantability (3). Tanky but hard to enchant.
- **Obsidian armor** = diamond protection, extra toughness, and **knockback resistance 0.15 —
  higher than netherite's 0.1, the best in the game** — at the price of being fully
  unenchantable and slowing you down (see §3).
- **Quartz armor** = gold protection with near-diamond longevity and diamond-level
  enchantability (10). Lasts long, protects little.
- **Lapis armor** = copper protection (10 points total) with slightly shorter-lived pieces than copper
  (durability 9 vs 11). Its pet-armor value of 5 is the same as iron/amethyst, sitting between copper's
  4 and gold's 7. **Fully unenchantable** — protection comes from its built-in lapis magic (§3):
  damage absorption, thorns and fall softening instead of enchants. A cheap set for the early game.

Horse / wolf / nautilus armor protect as well as the material's pet-armor value:
lapis 5, amethyst 5 (= iron), emerald/obsidian 11 (= diamond), quartz 7 (= gold).

Glass armor protects nothing at all — every piece has **0 defense and 1 durability** (one hit per
piece and it's gone). Its power is the set bonus in §3.

---

## 3. Special abilities (the part the stat tables don't show)

### Lapis — built-in magic, still unenchantable

The one tier that can't use the enchanting table, enchanted books or anvils — instead, every lapis item
ships with its own **lapis magic** pre-applied: fixed custom enchantments that can't be added, removed
or rerolled. Each effect rolls independently on every interaction:

**Weapons (sword, spear, axe) — on each hit:**
- 25% chance: +1–3 bonus damage (works on any enemy)
- 20% chance: shove the target 1–2 knockback levels farther
- 10% chance: set the target on fire for 4–8 seconds
- 15% chance: duplicate the drops of the mob you killed, ×1–3 — and the weapon's drop magic also
  applies to blocks it breaks (axe → logs, sword → bamboo)

**Tools (pickaxe, shovel, hoe) — on each block broken:**
- 20% chance: duplicate the block's drops, ×1–4

**Armor (helmet, chestplate, leggings) — while worn:**
- 20% chance per damage event: absorb 1–5 protection points (4% each, any damage type)
- 15% chance per piece: retaliate for 1–4 thorns damage when hit

**Boots — everything armor does, plus:**
- 30% chance per fall: soften it by 3–12 protection points (up to ~48% less fall damage)

The magic is fixed at its tuned strength — no levels, no stacking, no way to lose it. Cheap to make,
cheap to repair if you have lapis to spare: a starter or throwaway set that fights back while it lasts.

### Amethyst — crowd-control brawler + enchantment king

1. **Extra knockback on every weapon and tool.** Swords, spears, axes, pickaxes, shovels and hoes
   all shove enemies noticeably farther back.
2. **Stronger sweep attacks on the sword.** Swinging through a crowd hurts nearby mobs much more
   than a normal sword's sweep.
3. **Best-in-game enchantability**: tools 25 (beats gold's 22), armor 28 (beats gold armor's 25).
   If you want high-level enchants cheaply, this is the tier.

*Disadvantages:* iron-class damage/protection and iron mining power — it will never hit like
diamond or mine obsidian. Durability (350) sits between iron (250) and emerald (600).

### Emerald — XP farmer

- **+20% experience per emerald item equipped, stacking.** Every emerald sword, spear, tool or
  armor piece you hold or wear adds +20%: two pieces = 1.4×, hands full plus full armor =
  **2.2× XP**. (Horse/wolf/nautilus armor doesn't count — worn player gear only.)
- Raw stats are diamond-class on both tools and armor, with 600 tool durability.

*Disadvantages:* enchantability 3 — among the worst in the game, so getting good enchants (including
on the pieces you need for the XP bonus) is painful. Mining power is only iron despite diamond
damage/speed, so it can't mine diamond-tier blocks. Repairs cost emeralds, which you may rather
spend on villager trades.

### Obsidian — slow, unenchantable tank

1. **Fire protection: −10% fire damage per obsidian armor piece.** A full set cuts fire damage by
   **40%**, on top of normal armor. Obsidian horse, wolf and nautilus armor protect their wearers
   the same way.
2. **Highest knockback resistance in the game (0.15, above netherite's 0.1)** plus extra toughness
   and diamond-level protection — the tankiest armor per hit in the mod.
3. **Movement-speed penalty: −5% per piece.** A full set slows you by about **20%**; obsidian
   horse/wolf/nautilus armor slows its wearer the same way.

*Disadvantages:* **completely unenchantable** (no table, books, or anvil enchantments);
**slowest attack speed of any material** on every weapon/tool (see table); stone-class damage and
digging speed despite 800 durability; iron mining power. You trade all scaling and speed for
raw survivability.

### Quartz — Nether specialist

Each quartz tool mines certain Nether blocks far faster than its base speed suggests:
some **near-instantly**, others **extra fast** (faster than its already-fast base digging).

| Tool | Near-instant | Extra fast |
| :--- | :--- | :--- |
| Pickaxe | netherrack, warped/crimson nylium | blackstone family, basalt family, nether bricks, quartz blocks, nether quartz/gold ore, magma, bone block, **ancient debris, netherite block**, crying obsidian, respawn anchor, lodestone |
| Shovel | soul sand, soul soil | sand, gravel |
| Axe | crimson/warped stems (incl. stripped) and hyphae | nether/warped wart blocks |
| Hoe | nether/warped wart blocks | nether wart, nether sprouts |
| Sword | nether wart, nether sprouts | — (none) |

Combined with the fastest base digging speed in the game (12, tied with gold), diamond mining
power and 1050 durability, quartz tools carve through the Nether faster than anything else —
including blocks like ancient debris that normally demand top-tier tools.

*Disadvantages:* gold-class damage on everything (sword 4, axe 7) and gold-class armor (11 total
defense) — terrible for combat and protection despite the high durability numbers. No combat
or mobility ability; the advantage is purely mining/utility, mostly Nether-biased.

### Glass — the invisibility costume

The opposite of armor: **each piece has 1 durability and 0 defense**, so it protects you from
absolutely nothing and shatters after a single hit. Wear all four pieces at once, though, and you
turn **invisible** — true vanilla invisibility, identical to drinking a potion of invisibility
(no particles, nothing to show in your effect list). Take off, lose, replace or break any one
piece and you become visible again instantly. It doesn't clash with invisibility potions either:
they work independently of each other.

*Disadvantages:* you are wearing glass. One hit on any slot ends both that piece and your
camouflage, and until then you have zero protection from anything. Sneaking past mobs is exactly
as effective as with a potion; sneaking past **damage** is not a thing this set does.

---

## 4. Per-item verdicts (advantages / disadvantages)

### Swords (sorted strongest → weakest)

| Sword | Damage / speed | Verdict |
| :--- | :--- | :--- |
| Emerald (7 / 1.6) | Hits like diamond | ✅ diamond damage + XP bonus. ❌ enchantability 3, iron mining power, costs emeralds to repair. |
| Amethyst (5 / 1.8) | Hits like iron, swings **faster** than iron | ✅ extra knockback, strong sweep, god enchantability. ❌ iron damage, iron mining power. Best crowd-control early/mid sword. |
| Obsidian (5 / 1.2) | Hits like stone, slowest sword swing | ✅ durable (800). ❌ unenchantable, slow, stone damage. Only pick it for flavor/tank roleplay. |
| Lapis (4.5 / 1.6) | Hits between wood and stone | ✅ cheap, fastest renewable starter sword, normal swing speed, built-in lapis magic. ❌ **unenchantable**, 50 durability, below stone damage. |
| Quartz (4 / 1.6) | Hits like gold | ✅ fast swing, diamond mining power, shreds wart/sprouts. ❌ weakest sword damage in the mod. |

### Spears (sorted strongest → weakest)

Spears are a throwable/melee hybrid weapon. Damage and swing rate by tier:

| Spear | Damage / rate | Verdict |
| :--- | :--- | :--- |
| Emerald (4 / ~0.95 per sec) | Hits like a diamond spear | ✅ hardest-hitting spear + XP bonus. ❌ bad enchantability. |
| Amethyst (3 / ~1.33 per sec) | Iron-tier damage, second-fastest mod spear | ✅ extra knockback, fast attack cycle. Best spear for knockback play. |
| Obsidian (2 / ~0.8 per sec) | Hits like a stone spear, slowest spear in the game | ✅ durable. ❌ unenchantable, sluggish. |
| Lapis (1.5 / **~1.43 per sec**) | Lowest damage of any spear, quickest attack cycle | ✅ cheapest spear, fastest attack cycle, built-in lapis magic — a spam-jab starter. ❌ **unenchantable**, weak per hit, 50 durability. |
| Quartz (1 / ~0.95 per sec) | Hits like a gold spear, diamond-like handling | ✅ diamond-tier handling. ❌ 1 damage — a utility/throwing toy, not a weapon. |

### Pickaxes / axes / shovels / hoes

- **Pickaxes**: lapis (damage 2.5, speed 3.0, stone mining power) → digs between wood and stone speed
  but harvests like stone, so it can mine iron, copper and lapis ore; built-in drop magic. A workable
  early pickaxe.
  Amethyst (damage 4, iron speed, faster swing, knockback) → general early upgrade
  over iron. Emerald (damage 5, diamond speed, iron mining power) → good for everything except
  diamond-tier blocks. Obsidian (damage 3, stone speed, slowest) → skip unless you want durability
  without enchants. Quartz (damage 2, speed 12, diamond mining power, Nether bonus rules) →
  **the best Nether miner**; weak anywhere damage matters.
- **Axes**: lapis deals 8, quartz 7, everything else **9**. Amethyst axe (fast + knockback) is
  the best combat axe of the set; quartz axe (near-instant Nether stems) the best Nether woodcutter.
- **Shovels**: lapis 3 is between wood and stone; emerald 5.5 is diamond-class; amethyst 4.5 has knockback and a fast swing; obsidian
  3.5 is slowest; quartz 2.5 but digs soul sand/soil near-instantly.
- **Hoes**: all deal 1 damage. Amethyst/emerald/quartz hoes swing at top speed, lapis at 1.5/sec;
  obsidian hoe is the slowest hoe. Quartz hoe near-instantly breaks wart blocks — the Nether-farm hoe.

### Armor sets (helmet/chestplate/leggings/boots; sorted best protection → worst)

| Set (full-set defense) | Verdict |
| :--- | :--- |
| Emerald (20, diamond-class) | ✅ diamond protection + XP set bonus (up to 2.2× with weapon in hand). ❌ enchantability 3 — expect many levels for mediocre offers. |
| Obsidian (20, diamond-class) | ✅ diamond protection, extra toughness, best knockback resistance, −40% fire damage full set. ❌ unenchantable, −20% move speed, slowest weapons to pair with it. The stand-in-fire tank set. |
| Amethyst (15, iron-class) | ✅ enchantability 28 — the easiest top-tier enchanted armor to roll. ❌ iron protection, no toughness/knockback resist. Enchant it and it outscales plain diamond. |
| Lapis (10, copper-class) | ✅ cheapest full set in the mod, built-in protection/thorns/fall magic (§3). ❌ **unenchantable**, shortest-lived pieces (durability 9), weakest protection of any mod tier. |
| Quartz (11, gold-class) | ✅ long-lasting pieces, enchantability 10. ❌ gold protection — outclassed defensively by everything except gold/leather. Wear it for looks or spare sets, not danger. |
| Glass (0) | ✅ full-set **invisibility** — a wearable potion with no particles. ❌ 0 defense, 1 durability per piece — any hit shatters a piece and your cover with it. Pure stealth, zero tank. |

### Horse / wolf / nautilus armor

- Full body-armor coverage for all five mod materials (lapis/amethyst 5, emerald/obsidian 11, quartz 7
  protection), each with its own recipe, look and name.
- Obsidian mount/pet armor keeps both its abilities for the wearer: fire reduction **and** the
  movement penalty — armored but slower horses/wolves/nautiluses.
- Emerald mount/pet armor does **not** grant the XP bonus — the bonus comes from your own held and
  worn gear only.
- **Bonus vanilla gap-fillers**: leather, copper, iron, gold, diamond and netherite (fire-resistant)
  **wolf armor**, so your dog scales with your own progression instead of being stuck with one
  option. Leather through diamond craft directly; netherite upgrades from diamond wolf armor at the
  smithing table (needs a netherite upgrade template and a netherite ingot).

---

## 5. Crafting, repair, creative inventory

- **Ingredients** (same shaped layouts as vanilla tools/armor, with sticks where vanilla uses them;
  learning one recipe unlocks when you pick up the material): **lapis lazuli → lapis set**;
  amethyst shard → amethyst set; emerald → emerald set;  **obsidian** → obsidian set; quartz → quartz set. **Glass blocks → glass set.** Wolf gap-fillers use
  leather / copper ingot / iron ingot / gold ingot / diamond.
  (74 shaped recipes total, plus the netherite wolf armor smithing recipe.)
- **Netherite wolf armor** is the one exception: upgrade a diamond wolf armor at the smithing table
  with a netherite upgrade template + netherite ingot.
- **Repair**: each tier repairs with its own material at an anvil: lapis lazuli / shard / emerald /
  obsidian / quartz. (Lapis is unenchantable, so anvils will repair it but never enchant it — its
  magic ships built in and can't be changed.)
- **Creative inventory**: weapons, armor, horse armor, nautilus armor and wolf armor are in the
  Combat tab (after the golden gear); pickaxes, axes, shovels and hoes are in Tools & Utilities
  (axes appear in both, matching vanilla).

---

## 6. Mod vs. Minecraft — cheat sheet

| Need | Vanilla answer | Mod answer | Trade-off |
| :--- | :--- | :--- | :--- |
| Cheapest full set, early game | wood/leather | **lapis (13 items from lapis lazuli)** | weaker than stone/copper, 50 durability; **unenchantable** but magic is built in (§3) |
| Best enchants | gold | **amethyst** | iron-class stats, iron mining power |
| Hardest tool hits | netherite | **emerald (diamond-class)** | 600 durability, enchantability 3, iron mining power |
| Fastest mining | gold (12) | **quartz (12 base, faster on listed Nether blocks, some near-instant)** | gold damage, gold armor |
| Tankiest armor | netherite | **obsidian (diamond protection, extra toughness, best knockback resist, −40% fire)** | unenchantable, −20% speed, slowest weapons |
| XP grinding | … | **emerald gear (up to 2.2× XP)** | weak enchantability while grinding |
| Crowd control | knockback swords | **amethyst (extra knockback on all tools, stronger sweep)** | iron damage |
| Dog armor progression | one option only | **leather → netherite wolf armor** | netherite needs smithing table + upgrade template |
| Diamond-tier mining without diamonds | … | **quartz (diamond mining power, 1050 uses)** | gold damage/protection |
| Stealth | invisibility potion | **glass armor full set (real vanilla invisibility, no particles)** | zero defense, each piece breaks after one hit |
