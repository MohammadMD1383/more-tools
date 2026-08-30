package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey

object ItemIds {
	private fun create(id: String) = ResourceKey.create(Registries.ITEM, id(id))
	
	//@formatter:off
	@JvmStatic val AMETHYST_SWORD          = create("amethyst_sword")
	@JvmStatic val AMETHYST_SPEAR          = create("amethyst_spear")
	@JvmStatic val AMETHYST_PICKAXE        = create("amethyst_pickaxe")
	@JvmStatic val AMETHYST_AXE            = create("amethyst_axe")
	@JvmStatic val AMETHYST_SHOVEL         = create("amethyst_shovel")
	@JvmStatic val AMETHYST_HOE            = create("amethyst_hoe")
	@JvmStatic val AMETHYST_HELMET         = create("amethyst_helmet")
	@JvmStatic val AMETHYST_CHESTPLATE     = create("amethyst_chestplate")
	@JvmStatic val AMETHYST_LEGGINGS       = create("amethyst_leggings")
	@JvmStatic val AMETHYST_BOOTS          = create("amethyst_boots")
	@JvmStatic val AMETHYST_HORSE_ARMOR    = create("amethyst_horse_armor")
	@JvmStatic val AMETHYST_NAUTILUS_ARMOR = create("amethyst_nautilus_armor")
	//@formatter:on
}