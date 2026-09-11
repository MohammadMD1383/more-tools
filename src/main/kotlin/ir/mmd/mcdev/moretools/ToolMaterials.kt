package ir.mmd.mcdev.moretools

import net.minecraft.world.item.ToolMaterial

object ToolMaterials {
	//@formatter:off
	@JvmStatic val LAPIS    = ToolMaterial(BlockTags.INCORRECT_FOR_LAPIS_TOOL,      50,  3f, 0.5f,  1, ItemTags.LAPIS_TOOL_MATERIALS)
	@JvmStatic val AMETHYST = ToolMaterial(BlockTags.INCORRECT_FOR_AMETHYST_TOOL,  350,  6f,   2f, 25, ItemTags.AMETHYST_TOOL_MATERIALS)
	@JvmStatic val EMERALD  = ToolMaterial(BlockTags.INCORRECT_FOR_EMERALD_TOOL,   600,  8f,   3f,  3, ItemTags.EMERALD_TOOL_MATERIALS)
	@JvmStatic val OBSIDIAN = ToolMaterial(BlockTags.INCORRECT_FOR_OBSIDIAN_TOOL,  800,  4f,   1f,  1, ItemTags.OBSIDIAN_TOOL_MATERIALS)
	@JvmStatic val QUARTZ   = ToolMaterial(BlockTags.INCORRECT_FOR_QUARTZ_TOOL,   1050, 12f,   0f, 10, ItemTags.QUARTZ_TOOL_MATERIALS)
	//@formatter:on
}
