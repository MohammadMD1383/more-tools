# More Tools

Adds 75 items to Minecraft, built from materials the game already has: lapis lazuli, amethyst shards,
emeralds, obsidian and quartz. Every tier has its own durability, mining speed, attack damage,
protection, enchantability, movement speed and repair material, so none of these sets is a reskin of
a vanilla one.

Five complete tiers of 13 items each — sword, spear, pickaxe, axe, shovel, hoe, helmet, chestplate,
leggings, boots, horse armor, wolf armor and nautilus armor. On top of those there are six extra wolf
armors for materials vanilla never gave you (leather, copper, iron, gold, diamond, and netherite via a
smithing upgrade) and a four-piece glass armor set.

Several items do things vanilla has no equivalent for:

- Lapis gear cannot be enchanted, but comes with innate magic: extra damage, extra knockback, a
  chance to ignite attackers, damage absorption, fall protection, thorns and extra item drops.
- Emerald gear grants up to 2.2x experience from all sources while any piece is held or worn.
- Amethyst gear adds knockback to every tool and increases sword sweep damage.
- Obsidian gear trades movement speed for protection, toughness, knockback resistance and 40% less
  fire damage.
- Quartz tools mine specific Nether blocks much faster than their base speed suggests, ancient
  debris and netherite blocks included.
- Wearing the full glass armor set grants real vanilla Invisibility, with no particles and no HUD
  icon. The pieces have no defense and break after a single hit, so treat the set as a costume.

Crafting recipes are included and appear in the crafting table. Each tier repairs at an anvil with its
own material. New items are in the Combat and Tools & Utilities creative tabs.

**Requirements:** Minecraft 26.3, Fabric Loader 0.19.5 or newer, Fabric API 0.161.0+26.3, and Fabric
Language Kotlin. This version does not work on Minecraft 26.2 or earlier.

The tables below are the full reference.

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

- **Lapis** is the mod's cheapest tier. Digging speed (3.0) and damage bonus (0.5) fall between wood
  and stone, so it digs faster and hits harder than a wooden set, but its 50 durability is below
  wood's 59, so it breaks sooner. **Fully unenchantable** — its power comes from built-in lapis magic
  instead (§3). Cheap to make and cheap to repair, given lapis to spare.
- **Amethyst** gives iron-grade digging and damage with roughly 40% more durability than iron and the
  highest enchantability in the mod (25 on tools, above gold's 22).
- **Emerald** gives diamond-grade digging and damage at about 38% of diamond's durability, but only
  iron mining power and enchantability 3.
- **Obsidian** gives stone-grade digging and damage at iron mining power, 800 durability, and is
  **fully unenchantable** — no enchanting table, books, or anvil enchantments.
- **Quartz** gives gold-grade speed and damage with diamond mining power, 1050 durability and
  enchantability 10. Fast, but weak per hit.

Mining-power consequence:

- Lapis mines at **stone level**: iron, copper and lapis ore yes; **gold, redstone, diamond and
  emerald ore no**.
- Amethyst, emerald and obsidian mine everything iron can (including diamond ore with the right
  tool), but **cannot** mine obsidian blocks, ancient debris and similar the way diamond can.
- Quartz is the **only** mod tier that mines at diamond level (obsidian blocks included).

**Attack damage per weapon/tool.** Damage depends on the material plus the item type:

| Item | Lapis | Amethyst | Emerald | Obsidian | Quartz |
| :--- | ---: | ---: | ---: | ---: | ---: |
| Sword | **4.5** | **5** | **7** (= diamond sword) | **5** (= stone sword) | **4** (= gold sword) |
| Pickaxe | **2.5** | **4** | **5** (= diamond) | **3** (= stone) | **2** (= gold) |
| Axe | **8** | **9** | **9** (= diamond axe) | **9** (= stone axe) | **7** (= gold axe) |
| Shovel | **3** | **4.5** | **5.5** (= diamond) | **3.5** (= stone) | **2.5** (= gold) |
| Hoe | **1** | **1** | **1** | **1** | **1** (all hoes deal 1) |
| Spear | **1.5** | **3** | **4** (= diamond spear) | **2** (= stone spear) | **1** (= gold spear) |

Emerald hits the same as diamond on every weapon, obsidian the same as stone, quartz the same as
gold, and amethyst the same as iron (sword 5, axe 9, pickaxe 4). Lapis is the only tier that lands on
**half-heart numbers** — its damage bonus is 0.5, halfway between wood and stone, so a lapis sword
deals 4.5 (wood 4, stone 5) and a lapis axe 8 (wood 7, stone 9).

**Attack speed (higher = faster swings):**

| Item | Lapis | Amethyst | Emerald | Obsidian | Quartz | Vanilla reference |
| :--- | ---: | ---: | ---: | ---: | ---: | :--- |
| Sword | 1.6 | **1.8** | 1.6 | **1.2** | 1.6 | iron/diamond/gold 1.6 |
| Pickaxe | 1.2 | **1.4** | 1.2 | **0.9** | 1.2 | diamond 1.2 |
| Axe | 0.8 | **1.1** | 1.0 | **0.6** | 1.0 | diamond/netherite 1.0 |
| Shovel | 1.0 | **1.2** | 1.0 | **0.7** | 1.0 | diamond 1.0 |
| Hoe | 1.5 | **4.0** | 4.0 | **0.7** | **4.0** | diamond/netherite 4.0 |
| Spear (attacks/second) | **~1.43** | **~1.33** | ~0.95 (= diamond) | **~0.8** | ~0.95 (= diamond) | wood ~1.54 … netherite ~0.87 |

- **Amethyst tools swing faster than their iron and diamond equivalents** (sword 1.8 vs 1.6,
  pickaxe 1.4 vs 1.2, axe 1.1 vs 1.0).
- **Obsidian has the lowest attack speed of the mod's tiers** on every weapon and tool, and its spear
  is slower than netherite's.
- **Quartz swings as fast as diamond** but hits like gold.
- **Lapis swings at ordinary speeds** (sword 1.6, like iron/diamond/gold) but its axe (0.8) and hoe
  (1.5) are slower than their vanilla peers.
- **The lapis spear has the fastest attack cycle among the mod's spears** (~1.43/sec vs amethyst's
  ~1.33 and netherite's ~0.87), but hits for only 1.5. Emerald and quartz spears handle like diamond.

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

- **Amethyst armor** gives iron protection, outlasts iron, and has enchantability 28 — the highest
  armor enchantability in the mod (gold is 25).
- **Emerald armor** gives diamond protection at roughly two-thirds of diamond's longevity, with
  enchantability 3.
- **Obsidian armor** gives diamond protection, toughness 1, and knockback resistance 0.15 — above
  netherite's 0.1 — at the price of being fully unenchantable and reducing movement speed (§3).
- **Quartz armor** gives gold protection with near-diamond longevity and enchantability 10.
- **Lapis armor** gives copper protection (10 points total) with slightly shorter-lived pieces than
  copper (durability 9 vs 11). Its pet-armor value of 5 is the same as iron and amethyst, between
  copper's 4 and gold's 7. **Fully unenchantable** — protection comes from its built-in lapis magic
  (§3): damage absorption, thorns and fall softening instead of enchantments.

Horse, wolf and nautilus armor protect according to the material's pet-armor value: lapis 5, amethyst
5 (= iron), emerald and obsidian 11 (= diamond), quartz 7 (= gold).

Glass armor has **0 defense and 1 durability** on every piece, so it protects against nothing and
breaks after one hit. Its value is the set bonus in §3.

---

## 3. Special abilities (the part the stat tables don't show)

**Lapis — built-in magic, still unenchantable.** The one tier that can't use the enchanting table,
enchanted books or anvils. Instead every lapis item ships with its own **lapis magic** pre-applied:
fixed custom enchantments that can't be added, removed or rerolled. Each effect rolls independently
on every interaction:

*Weapons (sword, spear, axe) — on each hit:*
- 25% chance: +1-3 bonus damage (works on any enemy)
- 20% chance: shove the target 1-2 knockback levels farther
- 10% chance: set the target on fire for 4-8 seconds
- 15% chance: duplicate the drops of the mob you killed, x1-3 — and the weapon's drop magic also
  applies to blocks it breaks (axe → logs, sword → bamboo)

*Tools (pickaxe, shovel, hoe) — on each block broken:*
- 20% chance: duplicate the block's drops, x1-4

*Armor (helmet, chestplate, leggings) — while worn:*
- 20% chance per damage event: absorb 1-5 protection points (4% each, any damage type)
- 15% chance per piece: retaliate for 1-4 thorns damage when hit

*Boots — everything armor does, plus:*
- 30% chance per fall: soften it by 3-12 protection points (up to ~48% less fall damage)

The magic is fixed at its tuned strength — no levels, no stacking, no way to lose it.

**Amethyst — knockback on every tool, high enchantability.**

1. **Extra knockback on every weapon and tool.** Swords, spears, axes, pickaxes, shovels and hoes all
   shove enemies farther back than their damage class would suggest.
2. **Stronger sweep attacks on the sword.** Swinging through a crowd hurts nearby mobs more than a
   normal sword's sweep.
3. **High enchantability**: tools 25 (gold is 22), armor 28 (gold armor is 25).

*Disadvantages:* iron-class damage and protection, iron mining power — it will never hit like diamond
or mine obsidian. Durability (350) sits between iron (250) and emerald (600).

**Emerald — experience multiplier.**

- **+20% experience per emerald item equipped, stacking.** Every emerald sword, spear, tool or armor
  piece you hold or wear adds +20%: two pieces = 1.4x, hands full plus full armor = **2.2x XP**.
  (Horse, wolf and nautilus armor don't count — worn player gear only.)
- Raw stats are diamond-class on both tools and armor, with 600 tool durability.

*Disadvantages:* enchantability 3, so getting good enchantments (including on the pieces you need for
the XP bonus) takes a lot of levels. Mining power is only iron despite diamond damage and speed, so it
can't mine diamond-tier blocks. Repairs cost emeralds, which you may rather spend on villager trades.

**Obsidian — unenchantable, slower, fire resistant.**

1. **Fire protection: -10% fire damage per obsidian armor piece.** A full set reduces fire damage by
   **40%**, on top of normal armor. Obsidian horse, wolf and nautilus armor protect their wearers the
   same way.
2. **Knockback resistance 0.15** (above netherite's 0.1), plus toughness 1 and diamond-level
   protection.
3. **Movement-speed penalty: -5% per piece.** A full set reduces speed by about **20%**; obsidian
   horse, wolf and nautilus armor slow their wearers the same way.

*Disadvantages:* **completely unenchantable** (no table, books, or anvil enchantments); the lowest
attack speed of the mod's tiers on every weapon and tool; stone-class damage and digging speed despite
800 durability; iron mining power.

**Quartz — Nether mining specialist.** Each quartz tool mines certain Nether blocks faster than its
base speed suggests, either **near-instantly** or **extra fast**:

| Tool | Near-instant | Extra fast |
| :--- | :--- | :--- |
| Pickaxe | netherrack, warped/crimson nylium | blackstone family, basalt family, nether bricks, quartz blocks, nether quartz/gold ore, magma, bone block, **ancient debris, netherite block**, crying obsidian, respawn anchor, lodestone |
| Shovel | soul sand, soul soil | sand, gravel |
| Axe | crimson/warped stems (incl. stripped) and hyphae | nether/warped wart blocks |
| Hoe | nether/warped wart blocks | nether wart, nether sprouts |
| Sword | nether wart, nether sprouts | — (none) |

Combined with a base mining speed of 12 (the same as gold), diamond mining power and 1050 durability,
quartz tools mine the Nether faster than any other tier in this mod, including blocks like ancient
debris that normally need a top-tier tool.

*Disadvantages:* gold-class damage on everything (sword 4, axe 7) and gold-class armor (11 total
defense). No combat or mobility ability; the advantage is mining and utility, mostly in the Nether.

**Glass — the invisibility set.** The opposite of armor: **each piece has 1 durability and 0
defense**, so it protects against nothing and breaks after a single hit. Wear all four pieces at once
and you become **invisible** with true vanilla invisibility, identical to drinking a potion of
invisibility (no particles, nothing shown in your effect list). Take off, lose, replace or break any
one piece and you become visible again immediately. It does not conflict with invisibility potions —
the two work independently.

*Disadvantages:* you are wearing glass. One hit on any slot ends both that piece and your camouflage,
and until then you have no protection at all.

---

## 4. Per-item verdicts (advantages / disadvantages)

**Swords (sorted by damage):**

| Sword | Damage / speed | Verdict |
| :--- | :--- | :--- |
| Emerald (7 / 1.6) | Hits like diamond | ✅ diamond damage + XP bonus. ❌ enchantability 3, iron mining power, costs emeralds to repair. |
| Amethyst (5 / 1.8) | Hits like iron, swings **faster** than iron | ✅ extra knockback, strong sweep, high enchantability. ❌ iron damage, iron mining power. |
| Obsidian (5 / 1.2) | Hits like stone, slowest sword swing in the mod | ✅ durable (800). ❌ unenchantable, slow, stone damage. |
| Lapis (4.5 / 1.6) | Hits between wood and stone | ✅ cheap, normal swing speed, built-in lapis magic. ❌ **unenchantable**, 50 durability, below stone damage. |
| Quartz (4 / 1.6) | Hits like gold | ✅ fast swing, diamond mining power, shreds wart and sprouts. ❌ lowest sword damage in the mod. |

**Spears.** A throwable and melee hybrid weapon:

| Spear | Damage / rate | Verdict |
| :--- | :--- | :--- |
| Emerald (4 / ~0.95 per sec) | Hits like a diamond spear | ✅ highest spear damage + XP bonus. ❌ low enchantability. |
| Amethyst (3 / ~1.33 per sec) | Iron-tier damage, second-fastest mod spear | ✅ extra knockback, fast attack cycle. |
| Obsidian (2 / ~0.8 per sec) | Hits like a stone spear, slowest spear in the mod | ✅ durable. ❌ unenchantable, sluggish. |
| Lapis (1.5 / **~1.43 per sec**) | Lowest spear damage, fastest attack cycle in the mod | ✅ cheapest spear, fastest attack cycle, built-in lapis magic. ❌ **unenchantable**, weak per hit, 50 durability. |
| Quartz (1 / ~0.95 per sec) | Hits like a gold spear, diamond-like handling | ✅ diamond-tier handling. ❌ 1 damage — a utility piece rather than a weapon. |

**Pickaxes / axes / shovels / hoes:**

- **Pickaxes**: lapis (damage 2.5, speed 3.0, stone mining power) → digs between wood and stone speed
  but harvests like stone, so it can mine iron, copper and lapis ore, with built-in drop magic.
  Amethyst (damage 4, iron speed, faster swing, knockback) → a general early upgrade over iron.
  Emerald (damage 5, diamond speed, iron mining power) → good for everything except diamond-tier
  blocks. Obsidian (damage 3, stone speed, slowest) → durability without enchantments. Quartz
  (damage 2, speed 12, diamond mining power, Nether bonus rules) → mines Nether blocks fastest of the
  mod's tiers, weak anywhere damage matters.
- **Axes**: lapis deals 8, quartz 7, everything else **9**. The amethyst axe (fast, with knockback) is
  the strongest combat axe here; the quartz axe (near-instant on Nether stems) the fastest Nether
  woodcutter.
- **Shovels**: lapis 3 is between wood and stone; emerald 5.5 is diamond-class; amethyst 4.5 has
  knockback and a fast swing; obsidian 3.5 is slowest; quartz 2.5 but digs soul sand and soul soil
  near-instantly.
- **Hoes**: all deal 1 damage. Amethyst, emerald and quartz hoes swing at top speed, lapis at
  1.5/sec, obsidian is the slowest. The quartz hoe breaks wart blocks near-instantly.

**Armor sets (helmet/chestplate/leggings/boots):**

| Set (full-set defense) | Verdict |
| :--- | :--- |
| Emerald (20, diamond-class) | ✅ diamond protection + XP set bonus (up to 2.2x with weapon in hand). ❌ enchantability 3. |
| Obsidian (20, diamond-class) | ✅ diamond protection, toughness, knockback resistance, -40% fire damage full set. ❌ unenchantable, -20% move speed. |
| Amethyst (15, iron-class) | ✅ enchantability 28, much easier to enchant than diamond — enchanted, it can outscale a plain diamond set. ❌ iron protection, no toughness or knockback resistance. |
| Lapis (10, copper-class) | ✅ cheapest full set in the mod, built-in protection/thorns/fall magic (§3). ❌ **unenchantable**, shortest-lived pieces (durability 9), lowest protection of any mod tier. |
| Quartz (11, gold-class) | ✅ long-lasting pieces, enchantability 10. ❌ gold protection — lower than everything except gold and leather. |
| Glass (0) | ✅ full-set **invisibility** — a wearable potion with no particles. ❌ 0 defense, 1 durability per piece — any hit shatters a piece and your cover with it. |

**Horse / wolf / nautilus armor:**

- Full body-armor coverage for all five mod materials (lapis and amethyst 5, emerald and obsidian 11,
  quartz 7 protection), each with its own recipe, look and name.
- Obsidian mount and pet armor keeps both abilities for the wearer: fire reduction **and** the
  movement penalty — armored but slower horses, wolves and nautiluses.
- Emerald mount and pet armor does **not** grant the XP bonus; that comes from your own held and worn
  gear only.
- **Wolf armor for vanilla materials**: leather, copper, iron, gold, diamond and netherite (also
  fire-resistant), so dog armor can progress alongside your own gear. Leather through diamond craft
  directly; netherite upgrades from diamond wolf armor at the smithing table (needs a netherite
  upgrade template and a netherite ingot).

---

## 5. Crafting, repair, creative inventory

- **Ingredients** (same shaped layouts as vanilla tools and armor, with sticks where vanilla uses
  them; learning one recipe unlocks when you pick up the material): **lapis lazuli → lapis set**;
  amethyst shard → amethyst set; emerald → emerald set; **obsidian** → obsidian set; quartz → quartz
  set. **Glass blocks → glass set.** The vanilla-material wolf armors use leather, copper ingot, iron
  ingot, gold ingot and diamond. (74 shaped recipes, plus the netherite wolf armor smithing recipe.)
- **Netherite wolf armor** is the one exception: upgrade a diamond wolf armor at the smithing table
  with a netherite upgrade template and a netherite ingot.
- **Repair**: each tier repairs with its own material at an anvil — lapis lazuli, amethyst shard,
  emerald, obsidian or quartz. (Lapis is unenchantable, so anvils will repair it but never enchant
  it; its magic ships built in and can't be changed.)
- **Creative inventory**: weapons, armor, horse armor, nautilus armor and wolf armor are in the
  Combat tab (after the golden gear); pickaxes, axes, shovels and hoes are in Tools & Utilities (axes
  appear in both, matching vanilla).

---

## 6. Mod vs. Minecraft — comparison table

| Need | Vanilla answer | Mod answer | Trade-off |
| :--- | :--- | :--- | :--- |
| Cheapest full set, early game | wood/leather | **lapis (13 items from lapis lazuli)** | weaker than stone/copper, 50 durability; **unenchantable** but magic is built in (§3) |
| Highest enchantability | gold | **amethyst** | iron-class stats, iron mining power |
| Highest tool damage | netherite | **emerald (diamond-class)** | 600 durability, enchantability 3, iron mining power |
| Fastest mining | gold (12) | **quartz (12 base, faster on the listed Nether blocks, some near-instant)** | gold damage, gold armor |
| Highest protection | netherite | **obsidian (diamond protection, extra toughness, knockback resistance, -40% fire)** | unenchantable, -20% speed |
| XP gain | … | **emerald gear (up to 2.2x)** | low enchantability while grinding |
| Knockback | knockback swords | **amethyst (extra knockback on all tools, stronger sweep)** | iron damage |
| Dog armor progression | one option only | **leather → netherite wolf armor** | netherite needs a smithing table and upgrade template |
| Diamond-tier mining without diamonds | … | **quartz (diamond mining power, 1050 uses)** | gold damage and protection |
| Stealth | invisibility potion | **glass armor full set (real vanilla invisibility, no particles)** | zero defense, each piece breaks after one hit |