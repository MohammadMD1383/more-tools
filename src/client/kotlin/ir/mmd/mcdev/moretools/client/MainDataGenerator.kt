package ir.mmd.mcdev.moretools.client

import ir.mmd.mcdev.moretools.BlockTagsProvider
import ir.mmd.mcdev.moretools.ItemTagsProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class MainDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::BlockTagsProvider)
		pack.addProvider(::ItemTagsProvider)
		pack.addProvider(::ModelProvider)
	}
}
