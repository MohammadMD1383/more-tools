package ir.mmd.mcdev.moretools

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.item.enchantment.LevelBasedValue
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition

class Main : ModInitializer {
	override fun onInitialize() {
		Items.load()
		
		LootTableEvents.MODIFY.register { key, builder, source, provider ->
			if (source.isBuiltin && key == BuiltInLootTables.ANCIENT_CITY) {
				builder.withPool(
					LootPool.lootPool().add(
						LootItem.lootTableItem(Items.AMETHYST_SWORD)
							.`when` {
								LootItemRandomChanceWithEnchantedBonusCondition(
									0.17f, LevelBasedValue.Linear(0.05f, 0.07f),
									provider.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE)
								)
							}
					)
				)
			}
		}
		
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
			.register { it.accept(Items.AMETHYST_SWORD) }
	}
}
