package ir.mmd.mcdev.moretools

import ir.mmd.mcdev.moretools.providers.BlockTagsProvider
import ir.mmd.mcdev.moretools.providers.ItemTagsProvider
import net.minecraft.world.item.ToolMaterial

object ToolMaterials {
	val AMETHYST = ToolMaterial(BlockTagsProvider.INCORRECT_FOR_AMETHYST, 350, 6f, 3f, 25, ItemTagsProvider.REPAIRS_AMETHYST)
}