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
			insertAfter(Items.IRON_SWORD, MyItems.AMETHYST_SWORD)
			insertAfter(Items.IRON_SPEAR, MyItems.AMETHYST_SPEAR)
			insertAfter(Items.IRON_AXE, MyItems.AMETHYST_AXE)
			
			insertAfter(
				Items.IRON_BOOTS,
				MyItems.AMETHYST_HELMET,
				MyItems.AMETHYST_CHESTPLATE,
				MyItems.AMETHYST_LEGGINGS,
				MyItems.AMETHYST_BOOTS
			)
			
			insertAfter(Items.IRON_HORSE_ARMOR, MyItems.AMETHYST_HORSE_ARMOR)
			insertAfter(Items.IRON_NAUTILUS_ARMOR, MyItems.AMETHYST_NAUTILUS_ARMOR)
			insertAfter(Items.WOLF_ARMOR, MyItems.AMETHYST_WOLF_ARMOR)
		}
		
		modifyCreativeTab(CreativeModeTabs.TOOLS_AND_UTILITIES) {
			insertAfter(
				Items.IRON_HOE,
				MyItems.AMETHYST_SHOVEL,
				MyItems.AMETHYST_PICKAXE,
				MyItems.AMETHYST_AXE,
				MyItems.AMETHYST_HOE
			)
		}
	}
}
