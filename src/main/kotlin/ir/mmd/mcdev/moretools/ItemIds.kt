package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey

object ItemIds {
	private fun create(id: String) = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, id))
	
	val AMETHYST_SWORD = create("amethyst_sword")
	val AMETHYST_SPEAR = create("amethyst_spear")
	val AMETHYST_PICKAXES = create("amethyst_pickaxe")
	val AMETHYST_AXE = create("amethyst_axe")
	val AMETHYST_SHOVEL = create("amethyst_shovel")
	val AMETHYST_HOE = create("amethyst_hoe")
}