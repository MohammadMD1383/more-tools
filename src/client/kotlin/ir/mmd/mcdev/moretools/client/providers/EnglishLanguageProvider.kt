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
		//@formatter:on
	}
}