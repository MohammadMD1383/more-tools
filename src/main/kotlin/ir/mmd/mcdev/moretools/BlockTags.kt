package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey

object BlockTags {
	private fun create(name: String) = TagKey.create(Registries.BLOCK, id(name))
	
	//formatter:off
	@JvmStatic val INCORRECT_FOR_AMETHYST_TOOL = create("incorrect_for_amethyst_tool")
	@JvmStatic val INCORRECT_FOR_EMERALD_TOOL  = create("incorrect_for_emerald_tool")
	@JvmStatic val INCORRECT_FOR_OBSIDIAN_TOOL = create("incorrect_for_obsidian_tool")
	@JvmStatic val INCORRECT_FOR_QUARTZ_TOOL   = create("incorrect_for_quartz_tool")
	@JvmStatic val QUARTZ_PICKAXE_INSTANT      = create("quartz_pickaxe_instant")
	@JvmStatic val QUARTZ_PICKAXE_FAST         = create("quartz_pickaxe_fast")
	@JvmStatic val QUARTZ_SHOVEL_INSTANT       = create("quartz_shovel_instant")
	@JvmStatic val QUARTZ_SHOVEL_FAST          = create("quartz_shovel_fast")
	@JvmStatic val QUARTZ_AXE_INSTANT          = create("quartz_axe_instant")
	@JvmStatic val QUARTZ_AXE_FAST             = create("quartz_axe_fast")
	@JvmStatic val QUARTZ_HOE_INSTANT          = create("quartz_hoe_instant")
	@JvmStatic val QUARTZ_HOE_FAST             = create("quartz_hoe_fast")
	@JvmStatic val QUARTZ_SWORD_INSTANT        = create("quartz_sword_instant")
	@JvmStatic val QUARTZ_SWORD_FAST           = create("quartz_sword_fast")
	//formatter:on
}
