package ir.mmd.mcdev.moretools.effects

import ir.mmd.mcdev.moretools.Enchantments
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.storage.loot.parameters.LootContextParams

/**
 * Handles the two drop-bonus effects, which cannot ride a vanilla enchantment effect component:
 * vanilla block/mob loot tables only apply `enchanted_count_increase` for hard-coded enchantments.
 *
 * Block drops are identified by the loot context carrying a block state; entity drops by the
 * last-damage player. Both Lapis enchantments are evaluated independently on block loot, because
 * weapons mine blocks too (axe → logs, sword → bamboo): the tool enchantment applies to tools, the
 * weapon enchantment to weapons, each with its own chance and magnitude range. On entity loot only
 * the weapon enchantment applies. One independent roll per enchantment decides whether the whole
 * drop batch is duplicated; the batch is multiplied by a random magnitude within bounds.
 */
object LapisLoot {
	fun register() {
		LootTableEvents.MODIFY_DROPS.register { _, context, drops ->
			if (context.hasParameter(LootContextParams.BLOCK_STATE)) {
				applyBonus(
					context,
					drops,
					Enchantments.LAPIS_TOOL,
					LapisConfig.BLOCK_DROPS_CHANCE,
					LapisConfig.BLOCK_DROPS_MIN,
					LapisConfig.BLOCK_DROPS_MAX
				)
				applyBonus(
					context,
					drops,
					Enchantments.LAPIS_WEAPON,
					LapisConfig.ENTITY_DROPS_CHANCE,
					LapisConfig.ENTITY_DROPS_MIN,
					LapisConfig.ENTITY_DROPS_MAX
				)
			} else if (context.hasParameter(LootContextParams.LAST_DAMAGE_PLAYER)) {
				applyBonus(
					context,
					drops,
					Enchantments.LAPIS_WEAPON,
					LapisConfig.ENTITY_DROPS_CHANCE,
					LapisConfig.ENTITY_DROPS_MIN,
					LapisConfig.ENTITY_DROPS_MAX
				)
			}
		}
	}
	
	private fun applyBonus(
		context: net.minecraft.world.level.storage.loot.LootContext,
		drops: MutableList<ItemStack>,
		enchantment: ResourceKey<Enchantment>,
		chance: Float,
		minBonus: Int,
		maxBonus: Int
	) {
		val tool = context.getOptionalParameter(LootContextParams.TOOL) as? ItemStack
			?: context.getOptionalParameter(LootContextParams.LAST_DAMAGE_PLAYER)?.mainHandItem
			?: return
		if (!hasEnchant(tool, enchantment)) return
		if (context.random.nextFloat() >= chance) return
		
		val bonus = context.random.nextInt(minBonus, maxBonus + 1)
		val original = drops.map { it.copy() }
		repeat(bonus) { original.forEach { drops.add(it.copy()) } }
	}
	
	private fun hasEnchant(stack: ItemStack, enchantment: ResourceKey<Enchantment>): Boolean =
		stack.get(DataComponents.ENCHANTMENTS)?.keySet()?.any { it.unwrapKey().orElse(null) == enchantment } == true
	
}
