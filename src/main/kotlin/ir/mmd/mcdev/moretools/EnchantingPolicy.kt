package ir.mmd.mcdev.moretools

import net.fabricmc.fabric.api.item.v1.EnchantmentEvents
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries

/**
 * Closes the enchanting paths that ignore the ENCHANTABLE component.
 *
 * [nonEnchantable] removes ENCHANTABLE, which only starves the enchanting table (it rolls offers
 * through enchantability); the anvil book-combine path instead checks the enchantment's
 * supported_items — vanilla elytra behaves the same way (no ENCHANTABLE component, still
 * anvil-enchantable). This hook (wired by Fabric into anvil book-combine, enchanting table,
 * /enchant and loot's enchant_randomly) makes "no ENCHANTABLE component" mean fully
 * unenchantable for this mod's items.
 *
 * Scoped to items from this mod's namespace so vanilla items with the same shape (elytra) keep
 * their behavior. The item's default component set is what matters — an already-enchanted stack
 * (e.g. a Lapis item carrying its built-in lapisMagic) says nothing about external enchanting.
 */
object EnchantingPolicy {
	fun register() {
		EnchantmentEvents.ALLOW_ENCHANTING.register { _, stack, _ ->
			if (BuiltInRegistries.ITEM.getKey(stack.item).namespace != Constants.MOD_ID) {
				TriState.DEFAULT
			} else if (stack.has(DataComponents.ENCHANTABLE)) {
				TriState.DEFAULT
			} else {
				TriState.FALSE
			}
		}
	}
	
}
