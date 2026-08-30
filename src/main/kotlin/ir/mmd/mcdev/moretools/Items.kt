package ir.mmd.mcdev.moretools

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.equipment.ArmorType

object Items {
	private fun register(id: ResourceKey<Item>, properties: Properties) = Item(properties.setId(id)).also { Registry.register(BuiltInRegistries.ITEM, id, it) }
	
	//@formatter:off
	@JvmStatic val AMETHYST_SPEAR = register(ItemIds.AMETHYST_SPEAR, Properties().spear(ToolMaterials.AMETHYST, 0.75f, 0.95f, 0.4f, 3.5f, 9.0f, 7.75f, 4f, 12f, 4.6f).amethystKnockback())
	
	@JvmStatic val AMETHYST_SWORD   = register(ItemIds.AMETHYST_SWORD  , Properties().sword  (ToolMaterials.AMETHYST, 2.0f, -2.2f).amethystKnockback().amethystSweepDamage())
	@JvmStatic val AMETHYST_PICKAXE = register(ItemIds.AMETHYST_PICKAXE, Properties().pickaxe(ToolMaterials.AMETHYST, 1.0f, -2.6f).amethystKnockback())
	@JvmStatic val AMETHYST_AXE     = register(ItemIds.AMETHYST_AXE    , Properties().axe    (ToolMaterials.AMETHYST, 6.0f, -2.9f).amethystKnockback())
	@JvmStatic val AMETHYST_SHOVEL  = register(ItemIds.AMETHYST_SHOVEL , Properties().shovel (ToolMaterials.AMETHYST, 1.5f, -2.8f).amethystKnockback())
	@JvmStatic val AMETHYST_HOE     = register(ItemIds.AMETHYST_HOE    , Properties().hoe    (ToolMaterials.AMETHYST,  -2f,  0.0f).amethystKnockback())
	
	@JvmStatic val AMETHYST_HELMET      = register(ItemIds.AMETHYST_HELMET     , Properties().humanoidArmor(ArmorMaterials.AMETHYST, ArmorType.HELMET))
	@JvmStatic val AMETHYST_CHESTPLATE  = register(ItemIds.AMETHYST_CHESTPLATE , Properties().humanoidArmor(ArmorMaterials.AMETHYST, ArmorType.CHESTPLATE))
	@JvmStatic val AMETHYST_LEGGINGS    = register(ItemIds.AMETHYST_LEGGINGS   , Properties().humanoidArmor(ArmorMaterials.AMETHYST, ArmorType.LEGGINGS))
	@JvmStatic val AMETHYST_BOOTS       = register(ItemIds.AMETHYST_BOOTS      , Properties().humanoidArmor(ArmorMaterials.AMETHYST, ArmorType.BOOTS))
	
	@JvmStatic val AMETHYST_HORSE_ARMOR    = register(ItemIds.AMETHYST_HORSE_ARMOR   , Properties().horseArmor   (ArmorMaterials.AMETHYST))
	@JvmStatic val AMETHYST_NAUTILUS_ARMOR = register(ItemIds.AMETHYST_NAUTILUS_ARMOR, Properties().nautilusArmor(ArmorMaterials.AMETHYST))
	//@formatter:on
}