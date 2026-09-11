package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.Items
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class EnglishLanguageProvider(packOutput: FabricPackOutput, registryLookup: CompletableFuture<HolderLookup.Provider>) :
	FabricLanguageProvider(packOutput, "en_us", registryLookup) {
	
	override fun generateTranslations(
		registryLookup: HolderLookup.Provider,
		translationBuilder: TranslationBuilder
	) {
		//@formatter:off
		translationBuilder.add(Items.LAPIS_SWORD,             "Lapis Sword")
		translationBuilder.add(Items.LAPIS_SPEAR,             "Lapis Spear")
		translationBuilder.add(Items.LAPIS_PICKAXE,           "Lapis Pickaxe")
		translationBuilder.add(Items.LAPIS_AXE,               "Lapis Axe")
		translationBuilder.add(Items.LAPIS_SHOVEL,            "Lapis Shovel")
		translationBuilder.add(Items.LAPIS_HOE,               "Lapis Hoe")
		translationBuilder.add(Items.LAPIS_HELMET,            "Lapis Helmet")
		translationBuilder.add(Items.LAPIS_CHESTPLATE,        "Lapis Chestplate")
		translationBuilder.add(Items.LAPIS_LEGGINGS,          "Lapis Leggings")
		translationBuilder.add(Items.LAPIS_BOOTS,             "Lapis Boots")
		translationBuilder.add(Items.LAPIS_HORSE_ARMOR,       "Lapis Horse Armor")
		translationBuilder.add(Items.LAPIS_NAUTILUS_ARMOR,    "Lapis Nautilus Armor")
		translationBuilder.add(Items.LAPIS_WOLF_ARMOR,        "Lapis Wolf Armor")
		translationBuilder.add(Items.AMETHYST_SWORD,          "Amethyst Sword")
		translationBuilder.add(Items.AMETHYST_SPEAR,          "Amethyst Spear")
		translationBuilder.add(Items.AMETHYST_PICKAXE,        "Amethyst Pickaxe")
		translationBuilder.add(Items.AMETHYST_AXE,            "Amethyst Axe")
		translationBuilder.add(Items.AMETHYST_SHOVEL,         "Amethyst Shovel")
		translationBuilder.add(Items.AMETHYST_HOE,            "Amethyst Hoe")
		translationBuilder.add(Items.AMETHYST_HELMET,         "Amethyst Helmet")
		translationBuilder.add(Items.AMETHYST_CHESTPLATE,     "Amethyst Chestplate")
		translationBuilder.add(Items.AMETHYST_LEGGINGS,       "Amethyst Leggings")
		translationBuilder.add(Items.AMETHYST_BOOTS,          "Amethyst Boots")
		translationBuilder.add(Items.AMETHYST_HORSE_ARMOR,    "Amethyst Horse Armor")
		translationBuilder.add(Items.AMETHYST_NAUTILUS_ARMOR, "Amethyst Nautilus Armor")
		translationBuilder.add(Items.AMETHYST_WOLF_ARMOR,     "Amethyst Wolf Armor")
		translationBuilder.add(Items.EMERALD_SWORD,           "Emerald Sword")
		translationBuilder.add(Items.EMERALD_SPEAR,           "Emerald Spear")
		translationBuilder.add(Items.EMERALD_PICKAXE,         "Emerald Pickaxe")
		translationBuilder.add(Items.EMERALD_AXE,             "Emerald Axe")
		translationBuilder.add(Items.EMERALD_SHOVEL,          "Emerald Shovel")
		translationBuilder.add(Items.EMERALD_HOE,             "Emerald Hoe")
		translationBuilder.add(Items.EMERALD_HELMET,          "Emerald Helmet")
		translationBuilder.add(Items.EMERALD_CHESTPLATE,      "Emerald Chestplate")
		translationBuilder.add(Items.EMERALD_LEGGINGS,        "Emerald Leggings")
		translationBuilder.add(Items.EMERALD_BOOTS,           "Emerald Boots")
		translationBuilder.add(Items.EMERALD_HORSE_ARMOR,     "Emerald Horse Armor")
		translationBuilder.add(Items.EMERALD_NAUTILUS_ARMOR,  "Emerald Nautilus Armor")
		translationBuilder.add(Items.EMERALD_WOLF_ARMOR,      "Emerald Wolf Armor")
		translationBuilder.add(Items.OBSIDIAN_SWORD,          "Obsidian Sword")
		translationBuilder.add(Items.OBSIDIAN_SPEAR,          "Obsidian Spear")
		translationBuilder.add(Items.OBSIDIAN_PICKAXE,        "Obsidian Pickaxe")
		translationBuilder.add(Items.OBSIDIAN_AXE,            "Obsidian Axe")
		translationBuilder.add(Items.OBSIDIAN_SHOVEL,         "Obsidian Shovel")
		translationBuilder.add(Items.OBSIDIAN_HOE,            "Obsidian Hoe")
		translationBuilder.add(Items.OBSIDIAN_HELMET,         "Obsidian Helmet")
		translationBuilder.add(Items.OBSIDIAN_CHESTPLATE,     "Obsidian Chestplate")
		translationBuilder.add(Items.OBSIDIAN_LEGGINGS,       "Obsidian Leggings")
		translationBuilder.add(Items.OBSIDIAN_BOOTS,          "Obsidian Boots")
		translationBuilder.add(Items.OBSIDIAN_HORSE_ARMOR,    "Obsidian Horse Armor")
		translationBuilder.add(Items.OBSIDIAN_NAUTILUS_ARMOR, "Obsidian Nautilus Armor")
		translationBuilder.add(Items.OBSIDIAN_WOLF_ARMOR,     "Obsidian Wolf Armor")
		translationBuilder.add(Items.QUARTZ_SWORD,            "Quartz Sword")
		translationBuilder.add(Items.QUARTZ_SPEAR,            "Quartz Spear")
		translationBuilder.add(Items.QUARTZ_PICKAXE,          "Quartz Pickaxe")
		translationBuilder.add(Items.QUARTZ_AXE,              "Quartz Axe")
		translationBuilder.add(Items.QUARTZ_SHOVEL,           "Quartz Shovel")
		translationBuilder.add(Items.QUARTZ_HOE,              "Quartz Hoe")
		translationBuilder.add(Items.QUARTZ_HELMET,           "Quartz Helmet")
		translationBuilder.add(Items.QUARTZ_CHESTPLATE,       "Quartz Chestplate")
		translationBuilder.add(Items.QUARTZ_LEGGINGS,         "Quartz Leggings")
		translationBuilder.add(Items.QUARTZ_BOOTS,            "Quartz Boots")
		translationBuilder.add(Items.QUARTZ_HORSE_ARMOR,      "Quartz Horse Armor")
		translationBuilder.add(Items.QUARTZ_NAUTILUS_ARMOR,   "Quartz Nautilus Armor")
		translationBuilder.add(Items.QUARTZ_WOLF_ARMOR,       "Quartz Wolf Armor")
		translationBuilder.add(Items.LEATHER_WOLF_ARMOR,      "Leather Wolf Armor")
		translationBuilder.add(Items.COPPER_WOLF_ARMOR,       "Copper Wolf Armor")
		translationBuilder.add(Items.IRON_WOLF_ARMOR,         "Iron Wolf Armor")
		translationBuilder.add(Items.GOLD_WOLF_ARMOR,         "Gold Wolf Armor")
		translationBuilder.add(Items.DIAMOND_WOLF_ARMOR,      "Diamond Wolf Armor")
		translationBuilder.add(Items.NETHERITE_WOLF_ARMOR,    "Netherite Wolf Armor")
		//@formatter:on
	}
}
