package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object BlockTags {
	private fun create(name: String) = TagKey.create(Registries.BLOCK, id(name))
	
	@JvmStatic val INCORRECT_FOR_AMETHYST_TOOL = create("incorrect_for_amethyst_tool")
	@JvmStatic val INCORRECT_FOR_EMERALD_TOOL  = create("incorrect_for_emerald_tool")
}