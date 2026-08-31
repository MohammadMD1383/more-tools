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
- `docs/new-item-guide.md`: Mandatory reading when adding new items to the mod.
- `docs/minecraft-vanilla-materials.md`: Reference containing stats (durability, attack damage, armor values) for all vanilla Minecraft tools and armor for balancing your custom additions.

## Development Tasks
- **Build**: Use `./gradlew build` to compile the project.
- **Decompiled Sources**: If you need to reference Minecraft's internal code, look in `~/.gradle/caches/fabric-loom/` or the local project's `.gradle/loom-cache/`.
