package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet
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
		translationBuilder.add(AmethystToolSet.Items.AMETHYST_SWORD, "Amethyst Sword")
		translationBuilder.add(AmethystToolSet.Items.AMETHYST_SPEAR, "Amethyst Spear")
		translationBuilder.add(AmethystToolSet.Items.AMETHYST_PICKAXE, "Amethyst Pickaxe")
		translationBuilder.add(AmethystToolSet.Items.AMETHYST_AXE, "Amethyst Axe")
		translationBuilder.add(AmethystToolSet.Items.AMETHYST_SHOVEL, "Amethyst Shovel")
		translationBuilder.add(AmethystToolSet.Items.AMETHYST_HOE, "Amethyst Hoe")
	}
}