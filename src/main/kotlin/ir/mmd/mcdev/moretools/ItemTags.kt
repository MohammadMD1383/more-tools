package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object ItemTags {
	private fun create(name: String) = TagKey.create(Registries.ITEM, id(name))
	
	@JvmStatic val AMETHYST_TOOL_MATERIALS = create("repairs_amethyst_armor")
	@JvmStatic val EMERALD_TOOL_MATERIALS  = create("repairs_emerald_armor")
}