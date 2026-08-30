package ir.mmd.mcdev.moretools.client.providers

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
		itemModelGenerators.generateSpear(Items.AMETHYST_SPEAR)
		
		//@formatter:off
		itemModelGenerators.generateFlatItem(Items.AMETHYST_SWORD          , ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_PICKAXE        , ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_AXE            , ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_SHOVEL         , ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_HOE            , ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_HELMET         , ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_CHESTPLATE     , ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_LEGGINGS       , ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_BOOTS          , ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_HORSE_ARMOR    , ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_NAUTILUS_ARMOR , ModelTemplates.FLAT_ITEM)
		//@formatter:on
	}
}