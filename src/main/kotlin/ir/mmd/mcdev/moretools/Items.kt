package ir.mmd.mcdev.moretools

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.equipment.ArmorMaterials
import net.minecraft.world.item.equipment.ArmorType
import ir.mmd.mcdev.moretools.ArmorMaterials as MyArmorMaterials

object Items {
	private fun register(id: ResourceKey<Item>, properties: Properties) = Item(properties.setId(id)).also { Registry.register(BuiltInRegistries.ITEM, id, it) }
	
	//@formatter:off
	@JvmStatic val AMETHYST_SPEAR = register(ItemIds.AMETHYST_SPEAR, Properties().spear(ToolMaterials.AMETHYST, 0.75f, 0.95f, 0.4f, 3.5f, 9.0f, 7.75f, 4f, 12f, 4.6f).amethystKnockback())
	
	@JvmStatic val AMETHYST_SWORD   = register(ItemIds.AMETHYST_SWORD  , Properties().sword  (ToolMaterials.AMETHYST, 2.0f, -2.2f).amethystKnockback().amethystSweepDamage())
	@JvmStatic val AMETHYST_PICKAXE = register(ItemIds.AMETHYST_PICKAXE, Properties().pickaxe(ToolMaterials.AMETHYST, 1.0f, -2.6f).amethystKnockback())
	@JvmStatic val AMETHYST_AXE     = register(ItemIds.AMETHYST_AXE    , Properties().axe    (ToolMaterials.AMETHYST, 6.0f, -2.9f).amethystKnockback())
	@JvmStatic val AMETHYST_SHOVEL  = register(ItemIds.AMETHYST_SHOVEL , Properties().shovel (ToolMaterials.AMETHYST, 1.5f, -2.8f).amethystKnockback())
	@JvmStatic val AMETHYST_HOE     = register(ItemIds.AMETHYST_HOE    , Properties().hoe    (ToolMaterials.AMETHYST,  -2f,  0.0f).amethystKnockback())
	
	@JvmStatic val AMETHYST_HELMET      = register(ItemIds.AMETHYST_HELMET     , Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.HELMET))
	@JvmStatic val AMETHYST_CHESTPLATE  = register(ItemIds.AMETHYST_CHESTPLATE , Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.CHESTPLATE))
	@JvmStatic val AMETHYST_LEGGINGS    = register(ItemIds.AMETHYST_LEGGINGS   , Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.LEGGINGS))
	@JvmStatic val AMETHYST_BOOTS       = register(ItemIds.AMETHYST_BOOTS      , Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.BOOTS))
	
	@JvmStatic val AMETHYST_HORSE_ARMOR    = register(ItemIds.AMETHYST_HORSE_ARMOR   , Properties().horseArmor   (MyArmorMaterials.AMETHYST))
	@JvmStatic val AMETHYST_NAUTILUS_ARMOR = register(ItemIds.AMETHYST_NAUTILUS_ARMOR, Properties().nautilusArmor(MyArmorMaterials.AMETHYST))
	@JvmStatic val AMETHYST_WOLF_ARMOR     = register(ItemIds.AMETHYST_WOLF_ARMOR    , Properties().wolfArmor    (MyArmorMaterials.AMETHYST))

	@JvmStatic val EMERALD_SPEAR = register(ItemIds.EMERALD_SPEAR, Properties().spear(ToolMaterials.EMERALD, 1.05f, 1.075f, 0.5f, 3.0f, 10.0f, 6.5f, 5.1f, 10.0f, 4.6f))

	@JvmStatic val EMERALD_SWORD   = register(ItemIds.EMERALD_SWORD  , Properties().sword  (ToolMaterials.EMERALD, 3.0f, -2.4f))
	@JvmStatic val EMERALD_PICKAXE = register(ItemIds.EMERALD_PICKAXE, Properties().pickaxe(ToolMaterials.EMERALD, 1.0f, -2.8f))
	@JvmStatic val EMERALD_AXE     = register(ItemIds.EMERALD_AXE    , Properties().axe    (ToolMaterials.EMERALD, 5.0f, -3.0f))
	@JvmStatic val EMERALD_SHOVEL  = register(ItemIds.EMERALD_SHOVEL , Properties().shovel (ToolMaterials.EMERALD, 1.5f, -3.0f))
	@JvmStatic val EMERALD_HOE     = register(ItemIds.EMERALD_HOE    , Properties().hoe    (ToolMaterials.EMERALD, -3.0f,  0.0f))

	@JvmStatic val EMERALD_HELMET      = register(ItemIds.EMERALD_HELMET     , Properties().humanoidArmor(MyArmorMaterials.EMERALD, ArmorType.HELMET))
	@JvmStatic val EMERALD_CHESTPLATE  = register(ItemIds.EMERALD_CHESTPLATE , Properties().humanoidArmor(MyArmorMaterials.EMERALD, ArmorType.CHESTPLATE))
	@JvmStatic val EMERALD_LEGGINGS    = register(ItemIds.EMERALD_LEGGINGS   , Properties().humanoidArmor(MyArmorMaterials.EMERALD, ArmorType.LEGGINGS))
	@JvmStatic val EMERALD_BOOTS       = register(ItemIds.EMERALD_BOOTS      , Properties().humanoidArmor(MyArmorMaterials.EMERALD, ArmorType.BOOTS))

	@JvmStatic val EMERALD_HORSE_ARMOR    = register(ItemIds.EMERALD_HORSE_ARMOR   , Properties().horseArmor   (MyArmorMaterials.EMERALD))
	@JvmStatic val EMERALD_NAUTILUS_ARMOR = register(ItemIds.EMERALD_NAUTILUS_ARMOR, Properties().nautilusArmor(MyArmorMaterials.EMERALD))
	@JvmStatic val EMERALD_WOLF_ARMOR     = register(ItemIds.EMERALD_WOLF_ARMOR    , Properties().wolfArmor    (MyArmorMaterials.EMERALD))

	@JvmStatic val LEATHER_WOLF_ARMOR    = register(ItemIds.LEATHER_WOLF_ARMOR   , Properties().wolfArmor(ArmorMaterials.LEATHER))
	@JvmStatic val COPPER_WOLF_ARMOR     = register(ItemIds.COPPER_WOLF_ARMOR    , Properties().wolfArmor(ArmorMaterials.COPPER))
	@JvmStatic val CHAINMAIL_WOLF_ARMOR  = register(ItemIds.CHAINMAIL_WOLF_ARMOR , Properties().wolfArmor(ArmorMaterials.CHAINMAIL))
	@JvmStatic val IRON_WOLF_ARMOR       = register(ItemIds.IRON_WOLF_ARMOR      , Properties().wolfArmor(ArmorMaterials.IRON))
	@JvmStatic val GOLD_WOLF_ARMOR       = register(ItemIds.GOLD_WOLF_ARMOR      , Properties().wolfArmor(ArmorMaterials.GOLD))
	@JvmStatic val DIAMOND_WOLF_ARMOR    = register(ItemIds.DIAMOND_WOLF_ARMOR   , Properties().wolfArmor(ArmorMaterials.DIAMOND))
	@JvmStatic val NETHERITE_WOLF_ARMOR  = register(ItemIds.NETHERITE_WOLF_ARMOR , Properties().wolfArmor(ArmorMaterials.NETHERITE).fireResistant())
	//@formatter:on
}