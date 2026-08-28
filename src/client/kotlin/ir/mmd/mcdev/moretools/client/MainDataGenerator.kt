package ir.mmd.mcdev.moretools.client

import ir.mmd.mcdev.moretools.providers.BlockTagsProvider
import ir.mmd.mcdev.moretools.providers.ItemTagsProvider
import ir.mmd.mcdev.moretools.client.providers.EnglishLanguageProvider
import ir.mmd.mcdev.moretools.client.providers.ModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class MainDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::BlockTagsProvider)
		pack.addProvider(::ItemTagsProvider)
		pack.addProvider(::ModelProvider)
		pack.addProvider(::EnglishLanguageProvider)
	}
}
