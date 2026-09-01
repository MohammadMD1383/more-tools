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
		translationBuilder.add(Items.LEATHER_WOLF_ARMOR,      "Leather Wolf Armor")
		translationBuilder.add(Items.COPPER_WOLF_ARMOR,       "Copper Wolf Armor")
		translationBuilder.add(Items.IRON_WOLF_ARMOR,         "Iron Wolf Armor")
		translationBuilder.add(Items.GOLD_WOLF_ARMOR,         "Gold Wolf Armor")
		translationBuilder.add(Items.DIAMOND_WOLF_ARMOR,      "Diamond Wolf Armor")
		translationBuilder.add(Items.NETHERITE_WOLF_ARMOR,    "Netherite Wolf Armor")
		//@formatter:on
	}
}