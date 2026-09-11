# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
- **Type**: Fabric Minecraft Mod (Kotlin, JVM)
- **Minecraft Version**: 1.21.11 (26.2)
- **Mappings**: Yarn (1.21.11+build.6)
- **Build System**: Gradle (kts)

## Codebase Architecture
- **Registration**: All items are registered in `src/main/kotlin/ir/mmd/mcdev/moretools/Items.kt`. Use `ItemIds.kt` for managing ResourceKeys.
- **Materials**: Custom tool and armor material properties are defined in `ToolMaterials.kt` and `ArmorMaterials.kt`.
- **Data Generation**: The mod uses Fabric Data Generation. Generated files (JSON, recipes, lang) are located in `src/main/generated/`. When adding new items, ensure the data generation tasks are run.

## Useful Documentation
Read these only when the task calls for them.

- `README.md`: Player-facing overview (gameplay numbers, pros/cons). Keep code internals out of it —
  technical facts belong in `docs/`, not the README.
- `docs/new-item-guide.md`: Mandatory reading when adding new items to the mod.
- `docs/minecraft-vanilla-materials.md`: Vanilla stat tables — material-level stats, per-item `damage`/`attackSpeed`, the attack-damage formula, and the nine `spear()` parameters. §6 holds the same tables for this mod's materials/items. Read when balancing a new material.
- `docs/minecraft-internals.md`: Non-obvious API constraints and gotchas (enchantability cannot be 0, removing a data component, tags are additive-only, inspecting vanilla bytecode, verifying datagen output) plus how each special ability is wired (emerald XP, obsidian fire/slow, quartz mining rules). Read when something behaves unexpectedly or you need to check a vanilla implementation detail.
- `docs/checklist.md`: Broad per-item test matrix.
- `docs/vanilla-enchantment-system-26.2.md`: Complete vanilla enchantment inventory (all 43 enchantments, 31 effect components, execution traces, Lapis-feasibility analysis) from the 26.2 jars. Read when designing any enchantment-related feature.
- `docs/lapis-effect-building-blocks.md`: Per-item enchantment applicability + concrete per-level effect ranges for the Lapis datapack. Read when picking Lapis effects/numbers.

## Development Tasks
- **Build**: `./gradlew build`. Datagen: `./gradlew runDatagen` — always re-run after touching items or providers, and verify the output counts (see `docs/minecraft-internals.md`); datagen does not warn about a provider you forgot.
- **Run the two as separate invocations.** `./gradlew build runDatagen` fails configuration validation: `:sourcesJar` consumes `src/main/generated` (runDatagen's output) without declaring a dependency on it. Do `./gradlew runDatagen` first, then `./gradlew build`.
- **Minecraft's internal code**: `~/.gradle/caches/fabric-loom/26.2/` contains only **compiled** jars, no sources. Use the `javap` recipe in `docs/minecraft-internals.md`.
- **Sandboxed shells**: Gradle needs to write to `~/.gradle`; in a sandboxed shell it fails with `Read-only file system` on a `.lck` file. Re-run the gradle command with the sandbox disabled.

## Documenting your experience
Whatever task you do here, record what you had to *discover* — in `CLAUDE.md` or under `docs/` — so the next agent doesn't have to rediscover it.

Keep this sustainable rather than ever-growing:

1. **Short and precise.** Facts and constraints, not narrative.
2. **No duplication.** If the code or an existing doc already states it, link to that instead of restating it. Correct what is wrong rather than appending next to it.
3. **Split by topic, loaded on demand.** Put it in the file whose subject it matches (or a new one), then reference it from `CLAUDE.md` with a note about *when* to read it. `CLAUDE.md` is the always-loaded root, so it stays an index — details live in `docs/`.
