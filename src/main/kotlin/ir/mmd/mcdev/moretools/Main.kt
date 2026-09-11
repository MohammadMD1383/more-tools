package ir.mmd.mcdev.moretools

import net.fabricmc.api.ModInitializer
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Items
import ir.mmd.mcdev.moretools.Items as MyItems

class Main : ModInitializer {
	override fun onInitialize() {
		addCreativeItems()
	}
	
	fun addCreativeItems() {
		modifyCreativeTab(CreativeModeTabs.COMBAT) {
			insertAfter(
				Items.GOLDEN_SWORD,
				MyItems.LAPIS_SWORD,
				MyItems.AMETHYST_SWORD,
				MyItems.EMERALD_SWORD,
				MyItems.OBSIDIAN_SWORD,
				MyItems.QUARTZ_SWORD
			)
			
			insertAfter(
				Items.GOLDEN_SPEAR,
				MyItems.LAPIS_SPEAR,
				MyItems.AMETHYST_SPEAR,
				MyItems.EMERALD_SPEAR,
				MyItems.OBSIDIAN_SPEAR,
				MyItems.QUARTZ_SPEAR
			)
			
			insertAfter(
				Items.GOLDEN_AXE,
				MyItems.LAPIS_AXE,
				MyItems.AMETHYST_AXE,
				MyItems.EMERALD_AXE,
				MyItems.OBSIDIAN_AXE,
				MyItems.QUARTZ_AXE
			)
			
			insertAfter(
				Items.GOLDEN_BOOTS,
				MyItems.LAPIS_HELMET,
				MyItems.LAPIS_CHESTPLATE,
				MyItems.LAPIS_LEGGINGS,
				MyItems.LAPIS_BOOTS,
				MyItems.AMETHYST_HELMET,
				MyItems.AMETHYST_CHESTPLATE,
				MyItems.AMETHYST_LEGGINGS,
				MyItems.AMETHYST_BOOTS,
				MyItems.EMERALD_HELMET,
				MyItems.EMERALD_CHESTPLATE,
				MyItems.EMERALD_LEGGINGS,
				MyItems.EMERALD_BOOTS,
				MyItems.OBSIDIAN_HELMET,
				MyItems.OBSIDIAN_CHESTPLATE,
				MyItems.OBSIDIAN_LEGGINGS,
				MyItems.OBSIDIAN_BOOTS,
				MyItems.QUARTZ_HELMET,
				MyItems.QUARTZ_CHESTPLATE,
				MyItems.QUARTZ_LEGGINGS,
				MyItems.QUARTZ_BOOTS
			)
			
			insertAfter(
				Items.GOLDEN_HORSE_ARMOR,
				MyItems.LAPIS_HORSE_ARMOR,
				MyItems.AMETHYST_HORSE_ARMOR,
				MyItems.EMERALD_HORSE_ARMOR,
				MyItems.OBSIDIAN_HORSE_ARMOR,
				MyItems.QUARTZ_HORSE_ARMOR
			)
			
			insertAfter(
				Items.GOLDEN_NAUTILUS_ARMOR,
				MyItems.LAPIS_NAUTILUS_ARMOR,
				MyItems.AMETHYST_NAUTILUS_ARMOR,
				MyItems.EMERALD_NAUTILUS_ARMOR,
				MyItems.OBSIDIAN_NAUTILUS_ARMOR,
				MyItems.QUARTZ_NAUTILUS_ARMOR
			)
			
			insertAfter(
				Items.WOLF_ARMOR,
				MyItems.LEATHER_WOLF_ARMOR,
				MyItems.COPPER_WOLF_ARMOR,
				MyItems.IRON_WOLF_ARMOR,
				MyItems.GOLD_WOLF_ARMOR,
				MyItems.LAPIS_WOLF_ARMOR,
				MyItems.AMETHYST_WOLF_ARMOR,
				MyItems.EMERALD_WOLF_ARMOR,
				MyItems.OBSIDIAN_WOLF_ARMOR,
				MyItems.QUARTZ_WOLF_ARMOR,
				MyItems.DIAMOND_WOLF_ARMOR,
				MyItems.NETHERITE_WOLF_ARMOR
			)
		}
		
		modifyCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES) {
			insertAfter(
				Items.GOLDEN_HOE,
				MyItems.LAPIS_SHOVEL,
				MyItems.LAPIS_PICKAXE,
				MyItems.LAPIS_AXE,
				MyItems.LAPIS_HOE,
				MyItems.AMETHYST_SHOVEL,
				MyItems.AMETHYST_PICKAXE,
				MyItems.AMETHYST_AXE,
				MyItems.AMETHYST_HOE,
				MyItems.EMERALD_SHOVEL,
				MyItems.EMERALD_PICKAXE,
				MyItems.EMERALD_AXE,
				MyItems.EMERALD_HOE,
				MyItems.OBSIDIAN_SHOVEL,
				MyItems.OBSIDIAN_PICKAXE,
				MyItems.OBSIDIAN_AXE,
				MyItems.OBSIDIAN_HOE,
				MyItems.QUARTZ_SHOVEL,
				MyItems.QUARTZ_PICKAXE,
				MyItems.QUARTZ_AXE,
				MyItems.QUARTZ_HOE
			)
		}
	}
}
