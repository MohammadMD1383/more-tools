package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object Enchantments {
	private fun create(id: String) = ResourceKey.create(Registries.ENCHANTMENT, id(id))
	
	//@formatter:off
	@JvmStatic val LAPIS_WEAPON = create("lapis_weapon")
	@JvmStatic val LAPIS_TOOL   = create("lapis_tool")
	@JvmStatic val LAPIS_ARMOR  = create("lapis_armor")
	@JvmStatic val LAPIS_BOOTS  = create("lapis_boots")
	//@formatter:on
}