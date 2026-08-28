package ir.mmd.mcdev.moretools.client

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
		translationBuilder.add(Items.AMETHYST_SWORD, "Amethyst Sword")
	}
}