package ir.mmd.mcdev.moretools.toolsets

import ir.mmd.mcdev.moretools.Constants
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.item.component.ItemAttributeModifiers


abstract class ToolSet {
	protected fun createResourceKey(id: String) = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, id))
	
	protected fun registerItem(key: ResourceKey<Item>, factory: (Item.Properties) -> Item, settings: Item.Properties): Item {
		val item = factory(settings.setId(key))
		Registry.register(BuiltInRegistries.ITEM, key, item)
		return item
	}
	
	protected fun registerSword(
		key: ResourceKey<Item>,
		damage: Float,
		speed: Float,
		configure: ((ItemAttributeModifiers) -> ItemAttributeModifiers)? = null
	): Item {
		var properties = Item.Properties().sword(material, damage, speed)
		if (configure != null) {
			properties = properties.modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ -> configure(modifiers!!) }
		}
		return registerItem(key, ::Item, properties)
	}
	
	protected fun registerSpear(
		key: ResourceKey<Item>,
		attackDuration: Float,
		damageMultiplier: Float,
		delay: Float,
		dismountTime: Float,
		dismountThreshold: Float,
		knockbackTime: Float,
		knockbackThreshold: Float,
		damageTime: Float,
		damageThreshold: Float,
		configure: ((ItemAttributeModifiers) -> ItemAttributeModifiers)? = null
	): Item {
		var properties = Item.Properties().spear(
			material, attackDuration, damageMultiplier, delay, dismountTime,
			dismountThreshold, knockbackTime, knockbackThreshold, damageTime, damageThreshold
		)
		if (configure != null) {
			properties = properties.modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ -> configure(modifiers!!) }
		}
		return registerItem(key, ::Item, properties)
	}
	
	protected fun registerPickaxe(
		key: ResourceKey<Item>,
		damage: Float,
		speed: Float,
		configure: ((ItemAttributeModifiers) -> ItemAttributeModifiers)? = null
	): Item {
		var properties = Item.Properties().pickaxe(material, damage, speed)
		if (configure != null) {
			properties = properties.modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ -> configure(modifiers!!) }
		}
		return registerItem(key, ::Item, properties)
	}
	
	protected fun registerAxe(
		key: ResourceKey<Item>,
		damage: Float,
		speed: Float,
		configure: ((ItemAttributeModifiers) -> ItemAttributeModifiers)? = null
	): Item {
		var properties = Item.Properties().axe(material, damage, speed)
		if (configure != null) {
			properties = properties.modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ -> configure(modifiers!!) }
		}
		return registerItem(key, ::Item, properties)
	}
	
	protected fun registerShovel(
		key: ResourceKey<Item>,
		damage: Float,
		speed: Float,
		configure: ((ItemAttributeModifiers) -> ItemAttributeModifiers)? = null
	): Item {
		var properties = Item.Properties().shovel(material, damage, speed)
		if (configure != null) {
			properties = properties.modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ -> configure(modifiers!!) }
		}
		return registerItem(key, ::Item, properties)
	}
	
	protected fun registerHoe(
		id: ResourceKey<Item>,
		damage: Float,
		speed: Float,
		configure: ((ItemAttributeModifiers) -> ItemAttributeModifiers)? = null
	): Item {
		var properties = Item.Properties().hoe(material, damage, speed)
		if (configure != null) {
			properties = properties.modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ -> configure(modifiers!!) }
		}
		return registerItem(id, ::Item, properties)
	}
	
	protected fun creative(tab: ResourceKey<CreativeModeTab>, configure: FabricCreativeModeTabOutput.() -> Unit) {
		CreativeModeTabEvents.modifyOutputEvent(tab).register(configure)
	}
	
	protected abstract val material: ToolMaterial
	
	protected open fun addCreativeItems() {}
	protected open fun addLoots() {}
	
	fun load() {
		addCreativeItems()
		addLoots()
	}
}
