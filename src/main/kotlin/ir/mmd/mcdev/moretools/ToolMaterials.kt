package ir.mmd.mcdev.moretools

import net.minecraft.world.item.ToolMaterial

object ToolMaterials {
	@JvmStatic val AMETHYST = ToolMaterial(BlockTags.INCORRECT_FOR_AMETHYST_TOOL, 350, 6f, 2f, 25, ItemTags.AMETHYST_TOOL_MATERIALS)
	@JvmStatic val EMERALD  = ToolMaterial(BlockTags.INCORRECT_FOR_EMERALD_TOOL , 600, 8f, 3f,  3, ItemTags.EMERALD_TOOL_MATERIALS)
}