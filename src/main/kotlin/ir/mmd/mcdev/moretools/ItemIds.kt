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
	@JvmStatic val AMETHYST_WOLF_ARMOR     = create("amethyst_wolf_armor")

	@JvmStatic val EMERALD_SWORD           = create("emerald_sword")
	@JvmStatic val EMERALD_SPEAR           = create("emerald_spear")
	@JvmStatic val EMERALD_PICKAXE         = create("emerald_pickaxe")
	@JvmStatic val EMERALD_AXE             = create("emerald_axe")
	@JvmStatic val EMERALD_SHOVEL          = create("emerald_shovel")
	@JvmStatic val EMERALD_HOE             = create("emerald_hoe")
	@JvmStatic val EMERALD_HELMET          = create("emerald_helmet")
	@JvmStatic val EMERALD_CHESTPLATE      = create("emerald_chestplate")
	@JvmStatic val EMERALD_LEGGINGS        = create("emerald_leggings")
	@JvmStatic val EMERALD_BOOTS           = create("emerald_boots")
	@JvmStatic val EMERALD_HORSE_ARMOR     = create("emerald_horse_armor")
	@JvmStatic val EMERALD_NAUTILUS_ARMOR  = create("emerald_nautilus_armor")
	@JvmStatic val EMERALD_WOLF_ARMOR      = create("emerald_wolf_armor")

	@JvmStatic val LEATHER_WOLF_ARMOR      = create("leather_wolf_armor")
	@JvmStatic val COPPER_WOLF_ARMOR       = create("copper_wolf_armor")
	@JvmStatic val CHAINMAIL_WOLF_ARMOR    = create("chainmail_wolf_armor")
	@JvmStatic val IRON_WOLF_ARMOR         = create("iron_wolf_armor")
	@JvmStatic val GOLD_WOLF_ARMOR         = create("gold_wolf_armor")
	@JvmStatic val DIAMOND_WOLF_ARMOR      = create("diamond_wolf_armor")
	@JvmStatic val NETHERITE_WOLF_ARMOR    = create("netherite_wolf_armor")
	//@formatter:on
}