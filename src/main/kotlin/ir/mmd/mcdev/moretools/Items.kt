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
	@JvmStatic val AMETHYST_SPEAR = register(ItemIds.AMETHYST_SPEAR, Properties().spear(ToolMaterials.AMETHYST, 0.75f, 0.950f, 0.4f, 3.5f, 9.00f, 7.75f, 4.0f, 12.00f, 4.6f).amethystKnockback())
	@JvmStatic val EMERALD_SPEAR = register (ItemIds.EMERALD_SPEAR,  Properties().spear(ToolMaterials.EMERALD,  1.05f, 1.075f, 0.5f, 3.0f, 10.0f, 6.50f, 5.1f, 10.00f, 4.6f))
	@JvmStatic val OBSIDIAN_SPEAR = register(ItemIds.OBSIDIAN_SPEAR, Properties().spear(ToolMaterials.OBSIDIAN, 1.25f, 0.820f, 0.7f, 4.5f, 13.0f, 9.00f, 5.1f, 13.75f, 4.6f).nonEnchantable())
	@JvmStatic val QUARTZ_SPEAR = register  (ItemIds.QUARTZ_SPEAR,   Properties().spear(ToolMaterials.QUARTZ,   1.05f, 0.700f, 0.7f, 3.5f, 13.0f, 8.50f, 5.1f, 13.75f, 4.6f))
	
	@JvmStatic val AMETHYST_SWORD   = register(ItemIds.AMETHYST_SWORD,   Properties().sword  (ToolMaterials.AMETHYST,  2.0f, -2.2f).amethystKnockback().amethystSweepDamage())
	@JvmStatic val AMETHYST_PICKAXE = register(ItemIds.AMETHYST_PICKAXE, Properties().pickaxe(ToolMaterials.AMETHYST,  1.0f, -2.6f).amethystKnockback())
	@JvmStatic val AMETHYST_AXE     = register(ItemIds.AMETHYST_AXE,     Properties().axe    (ToolMaterials.AMETHYST,  6.0f, -2.9f).amethystKnockback())
	@JvmStatic val AMETHYST_SHOVEL  = register(ItemIds.AMETHYST_SHOVEL,  Properties().shovel (ToolMaterials.AMETHYST,  1.5f, -2.8f).amethystKnockback())
	@JvmStatic val AMETHYST_HOE     = register(ItemIds.AMETHYST_HOE,     Properties().hoe    (ToolMaterials.AMETHYST, -2.0f,  0.0f).amethystKnockback())
	@JvmStatic val EMERALD_SWORD    = register(ItemIds.EMERALD_SWORD,    Properties().sword  (ToolMaterials.EMERALD,   3.0f, -2.4f))
	@JvmStatic val EMERALD_PICKAXE  = register(ItemIds.EMERALD_PICKAXE,  Properties().pickaxe(ToolMaterials.EMERALD,   1.0f, -2.8f))
	@JvmStatic val EMERALD_AXE      = register(ItemIds.EMERALD_AXE,      Properties().axe    (ToolMaterials.EMERALD,   5.0f, -3.0f))
	@JvmStatic val EMERALD_SHOVEL   = register(ItemIds.EMERALD_SHOVEL,   Properties().shovel (ToolMaterials.EMERALD,   1.5f, -3.0f))
	@JvmStatic val EMERALD_HOE      = register(ItemIds.EMERALD_HOE,      Properties().hoe    (ToolMaterials.EMERALD,  -3.0f,  0.0f))
	@JvmStatic val OBSIDIAN_SWORD   = register(ItemIds.OBSIDIAN_SWORD,   Properties().sword  (ToolMaterials.OBSIDIAN,  3.0f, -2.8f).nonEnchantable())
	@JvmStatic val OBSIDIAN_PICKAXE = register(ItemIds.OBSIDIAN_PICKAXE, Properties().pickaxe(ToolMaterials.OBSIDIAN,  1.0f, -3.1f).nonEnchantable())
	@JvmStatic val OBSIDIAN_AXE     = register(ItemIds.OBSIDIAN_AXE,     Properties().axe    (ToolMaterials.OBSIDIAN,  7.0f, -3.4f).nonEnchantable())
	@JvmStatic val OBSIDIAN_SHOVEL  = register(ItemIds.OBSIDIAN_SHOVEL,  Properties().shovel (ToolMaterials.OBSIDIAN,  1.5f, -3.3f).nonEnchantable())
	@JvmStatic val OBSIDIAN_HOE     = register(ItemIds.OBSIDIAN_HOE,     Properties().hoe    (ToolMaterials.OBSIDIAN, -1.0f, -3.3f).nonEnchantable())
	@JvmStatic val QUARTZ_SWORD     = register(ItemIds.QUARTZ_SWORD,     Properties().sword  (ToolMaterials.QUARTZ,    3.0f, -2.4f))
	@JvmStatic val QUARTZ_PICKAXE   = register(ItemIds.QUARTZ_PICKAXE,   Properties().pickaxe(ToolMaterials.QUARTZ,    1.0f, -2.8f))
	@JvmStatic val QUARTZ_AXE       = register(ItemIds.QUARTZ_AXE,       Properties().axe    (ToolMaterials.QUARTZ,    6.0f, -3.0f))
	@JvmStatic val QUARTZ_SHOVEL    = register(ItemIds.QUARTZ_SHOVEL,    Properties().shovel (ToolMaterials.QUARTZ,    1.5f, -3.0f))
	@JvmStatic val QUARTZ_HOE       = register(ItemIds.QUARTZ_HOE,       Properties().hoe    (ToolMaterials.QUARTZ,    0.0f,  0.0f))
	
	@JvmStatic val AMETHYST_HELMET         = register(ItemIds.AMETHYST_HELMET,     Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.HELMET))
	@JvmStatic val AMETHYST_CHESTPLATE     = register(ItemIds.AMETHYST_CHESTPLATE, Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.CHESTPLATE))
	@JvmStatic val AMETHYST_LEGGINGS       = register(ItemIds.AMETHYST_LEGGINGS,   Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.LEGGINGS))
	@JvmStatic val AMETHYST_BOOTS          = register(ItemIds.AMETHYST_BOOTS,      Properties().humanoidArmor(MyArmorMaterials.AMETHYST, ArmorType.BOOTS))
	@JvmStatic val EMERALD_HELMET          = register(ItemIds.EMERALD_HELMET,      Properties().humanoidArmor(MyArmorMaterials.EMERALD,  ArmorType.HELMET))
	@JvmStatic val EMERALD_CHESTPLATE      = register(ItemIds.EMERALD_CHESTPLATE,  Properties().humanoidArmor(MyArmorMaterials.EMERALD,  ArmorType.CHESTPLATE))
	@JvmStatic val EMERALD_LEGGINGS        = register(ItemIds.EMERALD_LEGGINGS,    Properties().humanoidArmor(MyArmorMaterials.EMERALD,  ArmorType.LEGGINGS))
	@JvmStatic val EMERALD_BOOTS           = register(ItemIds.EMERALD_BOOTS,       Properties().humanoidArmor(MyArmorMaterials.EMERALD,  ArmorType.BOOTS))
	@JvmStatic val OBSIDIAN_HELMET         = register(ItemIds.OBSIDIAN_HELMET,     Properties().humanoidArmor(MyArmorMaterials.OBSIDIAN, ArmorType.HELMET).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_HELMET))
	@JvmStatic val OBSIDIAN_CHESTPLATE     = register(ItemIds.OBSIDIAN_CHESTPLATE, Properties().humanoidArmor(MyArmorMaterials.OBSIDIAN, ArmorType.CHESTPLATE).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_CHESTPLATE))
	@JvmStatic val OBSIDIAN_LEGGINGS       = register(ItemIds.OBSIDIAN_LEGGINGS,   Properties().humanoidArmor(MyArmorMaterials.OBSIDIAN, ArmorType.LEGGINGS).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_LEGGINGS))
	@JvmStatic val OBSIDIAN_BOOTS          = register(ItemIds.OBSIDIAN_BOOTS,      Properties().humanoidArmor(MyArmorMaterials.OBSIDIAN, ArmorType.BOOTS).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_BOOTS))
	@JvmStatic val QUARTZ_HELMET           = register(ItemIds.QUARTZ_HELMET,       Properties().humanoidArmor(MyArmorMaterials.QUARTZ,   ArmorType.HELMET))
	@JvmStatic val QUARTZ_CHESTPLATE       = register(ItemIds.QUARTZ_CHESTPLATE,   Properties().humanoidArmor(MyArmorMaterials.QUARTZ,   ArmorType.CHESTPLATE))
	@JvmStatic val QUARTZ_LEGGINGS         = register(ItemIds.QUARTZ_LEGGINGS,     Properties().humanoidArmor(MyArmorMaterials.QUARTZ,   ArmorType.LEGGINGS))
	@JvmStatic val QUARTZ_BOOTS            = register(ItemIds.QUARTZ_BOOTS,        Properties().humanoidArmor(MyArmorMaterials.QUARTZ,   ArmorType.BOOTS))
	
	@JvmStatic val AMETHYST_HORSE_ARMOR    = register(ItemIds.AMETHYST_HORSE_ARMOR,    Properties().horseArmor   (MyArmorMaterials.AMETHYST))
	@JvmStatic val AMETHYST_NAUTILUS_ARMOR = register(ItemIds.AMETHYST_NAUTILUS_ARMOR, Properties().nautilusArmor(MyArmorMaterials.AMETHYST))
	@JvmStatic val AMETHYST_WOLF_ARMOR     = register(ItemIds.AMETHYST_WOLF_ARMOR,     Properties().wolfArmor    (MyArmorMaterials.AMETHYST))
	@JvmStatic val EMERALD_HORSE_ARMOR     = register(ItemIds.EMERALD_HORSE_ARMOR,     Properties().horseArmor   (MyArmorMaterials.EMERALD))
	@JvmStatic val EMERALD_NAUTILUS_ARMOR  = register(ItemIds.EMERALD_NAUTILUS_ARMOR,  Properties().nautilusArmor(MyArmorMaterials.EMERALD))
	@JvmStatic val EMERALD_WOLF_ARMOR      = register(ItemIds.EMERALD_WOLF_ARMOR,      Properties().wolfArmor    (MyArmorMaterials.EMERALD))
	@JvmStatic val OBSIDIAN_HORSE_ARMOR    = register(ItemIds.OBSIDIAN_HORSE_ARMOR,    Properties().horseArmor   (MyArmorMaterials.OBSIDIAN).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_HORSE))
	@JvmStatic val OBSIDIAN_NAUTILUS_ARMOR = register(ItemIds.OBSIDIAN_NAUTILUS_ARMOR, Properties().nautilusArmor(MyArmorMaterials.OBSIDIAN).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_NAUTILUS))
	@JvmStatic val OBSIDIAN_WOLF_ARMOR     = register(ItemIds.OBSIDIAN_WOLF_ARMOR,     Properties().wolfArmor    (MyArmorMaterials.OBSIDIAN).nonEnchantable().obsidianMovementSpeed(AttributeIds.OBSIDIAN_MOVEMENT_SPEED_WOLF))
	@JvmStatic val QUARTZ_HORSE_ARMOR      = register(ItemIds.QUARTZ_HORSE_ARMOR,      Properties().horseArmor   (MyArmorMaterials.QUARTZ))
	@JvmStatic val QUARTZ_NAUTILUS_ARMOR   = register(ItemIds.QUARTZ_NAUTILUS_ARMOR,   Properties().nautilusArmor(MyArmorMaterials.QUARTZ))
	@JvmStatic val QUARTZ_WOLF_ARMOR       = register(ItemIds.QUARTZ_WOLF_ARMOR,       Properties().wolfArmor    (MyArmorMaterials.QUARTZ))
	@JvmStatic val LEATHER_WOLF_ARMOR      = register(ItemIds.LEATHER_WOLF_ARMOR,      Properties().wolfArmor    (ArmorMaterials.LEATHER))
	@JvmStatic val COPPER_WOLF_ARMOR       = register(ItemIds.COPPER_WOLF_ARMOR,       Properties().wolfArmor    (ArmorMaterials.COPPER))
	@JvmStatic val IRON_WOLF_ARMOR         = register(ItemIds.IRON_WOLF_ARMOR,         Properties().wolfArmor    (ArmorMaterials.IRON))
	@JvmStatic val GOLD_WOLF_ARMOR         = register(ItemIds.GOLD_WOLF_ARMOR,         Properties().wolfArmor    (ArmorMaterials.GOLD))
	@JvmStatic val DIAMOND_WOLF_ARMOR      = register(ItemIds.DIAMOND_WOLF_ARMOR,      Properties().wolfArmor    (ArmorMaterials.DIAMOND))
	@JvmStatic val NETHERITE_WOLF_ARMOR    = register(ItemIds.NETHERITE_WOLF_ARMOR,    Properties().wolfArmor    (ArmorMaterials.NETHERITE).fireResistant())
	//@formatter:on
}