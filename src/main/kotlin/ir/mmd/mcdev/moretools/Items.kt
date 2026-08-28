package ir.mmd.mcdev.moretools

import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item
import ir.mmd.mcdev.moretools.Attributes as MyAttributes

object Items {
	private fun register(key: ResourceKey<Item>, factory: (Item.Properties) -> Item, settings: Item.Properties): Item {
		val item = factory(settings.setId(key))
		Registry.register(BuiltInRegistries.ITEM, key, item)
		return item
	}
	
	val AMETHYST_SWORD = register(
		ItemIds.AMETHYST_SWORD, ::Item, Item.Properties().sword(ToolMaterials.AMETHYST, 2f, -2.2f).modifyComponent(
			DataComponents.ATTRIBUTE_MODIFIERS
		) { modifiers, _, _ ->
			modifiers!!.withModifierAdded(
				Attributes.ATTACK_KNOCKBACK,
				MyAttributes.AmethystKnockback,
				EquipmentSlotGroup.MAINHAND
			).withModifierAdded(
				Attributes.SWEEPING_DAMAGE_RATIO,
				MyAttributes.AmethystSweepDamage,
				EquipmentSlotGroup.MAINHAND
			)
		}
	)
	
	fun load() {}
}