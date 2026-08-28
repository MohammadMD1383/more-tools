package ir.mmd.mcdev.moretools.client

import ir.mmd.mcdev.moretools.Items
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.model.ModelTemplates

class ModelProvider(output: FabricPackOutput) : FabricModelProvider(output) {
	override fun generateBlockStateModels(blockModelGenerators: BlockModelGenerators) {
	}
	
	override fun generateItemModels(itemModelGenerators: ItemModelGenerators) {
		itemModelGenerators.generateFlatItem(Items.AMETHYST_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM)
	}
}