package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import net.minecraft.client.data.models.model.ItemModelUtils
import net.minecraft.client.data.models.model.ModelTemplates
import net.minecraft.client.renderer.item.properties.select.DisplayContext
import net.minecraft.world.item.ItemDisplayContext

class ModelProvider(output: FabricPackOutput) : FabricModelProvider(output) {
	override fun generateBlockStateModels(blockModelGenerators: BlockModelGenerators) {
	}
	
	override fun generateItemModels(itemModelGenerators: ItemModelGenerators) {
		// Generate the normal inventory model:
		// amethyst_spear.png
		val spearInventory = itemModelGenerators.createFlatItemModel(AmethystToolSet.Items.AMETHYST_SPEAR, ModelTemplates.FLAT_ITEM)
		
		// Generate the in-hand model:
		// amethyst_spear_in_hand.png
		val spearInHand = itemModelGenerators.createFlatItemModel(
			AmethystToolSet.Items.AMETHYST_SPEAR,
			"_in_hand",
			ModelTemplates.SPEAR_IN_HAND
		)
		
		// Create the client item model:
		itemModelGenerators.itemModelOutput.accept(
			AmethystToolSet.Items.AMETHYST_SPEAR,
			ItemModelUtils.select(
				DisplayContext(),
				ItemModelUtils.plainModel(spearInHand),
				ItemModelUtils.`when`(
					listOf(
						ItemDisplayContext.GUI,
						ItemDisplayContext.GROUND,
						ItemDisplayContext.FIXED,
						ItemDisplayContext.ON_SHELF
					),
					ItemModelUtils.plainModel(spearInventory)
				)
			)
		)
		
		itemModelGenerators.generateFlatItem(AmethystToolSet.Items.AMETHYST_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(AmethystToolSet.Items.AMETHYST_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(AmethystToolSet.Items.AMETHYST_AXE, ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(AmethystToolSet.Items.AMETHYST_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(AmethystToolSet.Items.AMETHYST_HOE, ModelTemplates.FLAT_HANDHELD_ITEM)
	}
}