package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object ItemTags {
	private fun create(name: String) = TagKey.create(Registries.ITEM, id(name))
	
	//formatter:off
	@JvmStatic val AMETHYST_TOOL_MATERIALS = create("repairs_amethyst_armor")
	@JvmStatic val EMERALD_TOOL_MATERIALS  = create("repairs_emerald_armor")
	@JvmStatic val OBSIDIAN_TOOL_MATERIALS = create("repairs_obsidian_armor")
	@JvmStatic val QUARTZ_TOOL_MATERIALS   = create("repairs_quartz_armor")
	@JvmStatic val EMERALD_ITEMS_FOR_XP    = create("emerald_items_for_xp")
	@JvmStatic val OBSIDIAN_ARMOR_FOR_FIRE = create("obsidian_armor_for_fire")
	//formatter:on
}
