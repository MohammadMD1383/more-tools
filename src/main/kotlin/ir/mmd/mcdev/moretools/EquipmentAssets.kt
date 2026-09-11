package ir.mmd.mcdev.moretools

import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.equipment.EquipmentAssets.ROOT_ID

object EquipmentAssets {
	private fun create(name: String) = ResourceKey.create(ROOT_ID, id(name))
	
	//@formatter:off
	@JvmStatic val LAPIS    = create("lapis")
	@JvmStatic val AMETHYST = create("amethyst")
	@JvmStatic val EMERALD  = create("emerald")
	@JvmStatic val OBSIDIAN = create("obsidian")
	@JvmStatic val QUARTZ   = create("quartz")
	//@formatter:on
}
