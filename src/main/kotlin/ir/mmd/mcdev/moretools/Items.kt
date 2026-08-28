package ir.mmd.mcdev.moretools

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item

object Items {
	private fun register(key: ResourceKey<Item>, factory: (Item.Properties) -> Item, settings: Item.Properties): Item {
		val item = factory(settings.setId(key))
		Registry.register(BuiltInRegistries.ITEM, key, item)
		return item
	}
	
	val AMETHYST_SWORD = register(ItemIds.AMETHYST_SWORD, ::Item, Item.Properties().sword(ToolMaterials.AMETHYST, 3f, -2.2f))
	
	fun load() {}
}