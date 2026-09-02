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
		itemModelGenerators.generateSpear(Items.EMERALD_SPEAR)
		itemModelGenerators.generateSpear(Items.OBSIDIAN_SPEAR)
		itemModelGenerators.generateSpear(Items.QUARTZ_SPEAR)

		//@formatter:off
		itemModelGenerators.generateFlatItem(Items.AMETHYST_SWORD,          ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_PICKAXE,        ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_AXE,            ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_SHOVEL,         ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_HOE,            ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_HELMET,         ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_CHESTPLATE,     ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_LEGGINGS,       ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_BOOTS,          ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_HORSE_ARMOR,    ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_NAUTILUS_ARMOR, ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.AMETHYST_WOLF_ARMOR,     ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_SWORD,           ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_PICKAXE,         ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_AXE,             ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_SHOVEL,          ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_HOE,             ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_HELMET,          ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_CHESTPLATE,      ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_LEGGINGS,        ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_BOOTS,           ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_HORSE_ARMOR,     ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_NAUTILUS_ARMOR,  ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.EMERALD_WOLF_ARMOR,      ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_SWORD,          ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_PICKAXE,        ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_AXE,            ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_SHOVEL,         ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_HOE,            ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_HELMET,         ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_CHESTPLATE,     ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_LEGGINGS,       ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_BOOTS,          ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_HORSE_ARMOR,    ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_NAUTILUS_ARMOR, ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.OBSIDIAN_WOLF_ARMOR,     ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_SWORD,            ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_PICKAXE,          ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_AXE,              ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_SHOVEL,           ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_HOE,              ModelTemplates.FLAT_HANDHELD_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_HELMET,           ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_CHESTPLATE,       ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_LEGGINGS,         ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_BOOTS,            ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_HORSE_ARMOR,      ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_NAUTILUS_ARMOR,   ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.QUARTZ_WOLF_ARMOR,       ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.LEATHER_WOLF_ARMOR,      ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.COPPER_WOLF_ARMOR,       ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.IRON_WOLF_ARMOR,         ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.GOLD_WOLF_ARMOR,         ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.DIAMOND_WOLF_ARMOR,      ModelTemplates.FLAT_ITEM)
		itemModelGenerators.generateFlatItem(Items.NETHERITE_WOLF_ARMOR,    ModelTemplates.FLAT_ITEM)
		//@formatter:on
	}
}