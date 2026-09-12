package ir.mmd.mcdev.moretools.client.providers

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.references.BlockItemIds
import net.minecraft.references.ItemIds
import ir.mmd.mcdev.moretools.ItemIds as MyItemIds
import net.minecraft.tags.ItemTags
import java.util.concurrent.CompletableFuture
import ir.mmd.mcdev.moretools.ItemTags as MyItemTags

class ItemTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.ItemTagsProvider(output, registryLookupFuture) {
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(MyItemTags.LAPIS_WEAPON_ENCHANTABLE)
			.addTag(ItemTags.SWORDS)
			.addTag(ItemTags.AXES)
			.addTag(ItemTags.SPEARS)
		
		builder(MyItemTags.LAPIS_ARMOR_ENCHANTABLE)
			.addTag(ItemTags.HEAD_ARMOR)
			.addTag(ItemTags.CHEST_ARMOR)
			.addTag(ItemTags.LEG_ARMOR)
		
		builder(MyItemTags.LAPIS_TOOL_MATERIALS).add(ItemIds.LAPIS_LAZULI)
		builder(MyItemTags.AMETHYST_TOOL_MATERIALS).add(ItemIds.AMETHYST_SHARD)
		builder(MyItemTags.EMERALD_TOOL_MATERIALS).add(ItemIds.EMERALD)
		builder(MyItemTags.OBSIDIAN_TOOL_MATERIALS).add(BlockItemIds.OBSIDIAN.item())
		builder(MyItemTags.QUARTZ_TOOL_MATERIALS).add(ItemIds.QUARTZ)
		builder(MyItemTags.GLASS_TOOL_MATERIALS).add(BlockItemIds.GLASS.item())
		
		//@formatter:off
		builder(ItemTags.SWORDS     ).add(MyItemIds.LAPIS_SWORD)
		builder(ItemTags.SPEARS     ).add(MyItemIds.LAPIS_SPEAR)
		builder(ItemTags.PICKAXES   ).add(MyItemIds.LAPIS_PICKAXE)
		builder(ItemTags.AXES       ).add(MyItemIds.LAPIS_AXE)
		builder(ItemTags.SHOVELS    ).add(MyItemIds.LAPIS_SHOVEL)
		builder(ItemTags.HOES       ).add(MyItemIds.LAPIS_HOE)
		builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.LAPIS_HELMET)
		builder(ItemTags.CHEST_ARMOR).add(MyItemIds.LAPIS_CHESTPLATE)
		builder(ItemTags.LEG_ARMOR  ).add(MyItemIds.LAPIS_LEGGINGS)
		builder(ItemTags.FOOT_ARMOR ).add(MyItemIds.LAPIS_BOOTS)
		builder(ItemTags.SWORDS     ).add(MyItemIds.AMETHYST_SWORD)
		builder(ItemTags.SPEARS     ).add(MyItemIds.AMETHYST_SPEAR)
		builder(ItemTags.PICKAXES   ).add(MyItemIds.AMETHYST_PICKAXE)
		builder(ItemTags.AXES       ).add(MyItemIds.AMETHYST_AXE)
		builder(ItemTags.SHOVELS    ).add(MyItemIds.AMETHYST_SHOVEL)
		builder(ItemTags.HOES       ).add(MyItemIds.AMETHYST_HOE)
		builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.AMETHYST_HELMET)
		builder(ItemTags.CHEST_ARMOR).add(MyItemIds.AMETHYST_CHESTPLATE)
		builder(ItemTags.LEG_ARMOR  ).add(MyItemIds.AMETHYST_LEGGINGS)
		builder(ItemTags.FOOT_ARMOR ).add(MyItemIds.AMETHYST_BOOTS)
		builder(ItemTags.SWORDS     ).add(MyItemIds.EMERALD_SWORD)
		builder(ItemTags.SPEARS     ).add(MyItemIds.EMERALD_SPEAR)
		builder(ItemTags.PICKAXES   ).add(MyItemIds.EMERALD_PICKAXE)
		builder(ItemTags.AXES       ).add(MyItemIds.EMERALD_AXE)
		builder(ItemTags.SHOVELS    ).add(MyItemIds.EMERALD_SHOVEL)
		builder(ItemTags.HOES       ).add(MyItemIds.EMERALD_HOE)
		builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.EMERALD_HELMET)
		builder(ItemTags.CHEST_ARMOR).add(MyItemIds.EMERALD_CHESTPLATE)
		builder(ItemTags.LEG_ARMOR  ).add(MyItemIds.EMERALD_LEGGINGS)
		builder(ItemTags.FOOT_ARMOR ).add(MyItemIds.EMERALD_BOOTS)
		builder(ItemTags.SWORDS     ).add(MyItemIds.OBSIDIAN_SWORD)
		builder(ItemTags.SPEARS     ).add(MyItemIds.OBSIDIAN_SPEAR)
		builder(ItemTags.PICKAXES   ).add(MyItemIds.OBSIDIAN_PICKAXE)
		builder(ItemTags.AXES       ).add(MyItemIds.OBSIDIAN_AXE)
		builder(ItemTags.SHOVELS    ).add(MyItemIds.OBSIDIAN_SHOVEL)
		builder(ItemTags.HOES       ).add(MyItemIds.OBSIDIAN_HOE)
		builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.OBSIDIAN_HELMET)
		builder(ItemTags.CHEST_ARMOR).add(MyItemIds.OBSIDIAN_CHESTPLATE)
		builder(ItemTags.LEG_ARMOR  ).add(MyItemIds.OBSIDIAN_LEGGINGS)
		builder(ItemTags.FOOT_ARMOR ).add(MyItemIds.OBSIDIAN_BOOTS)
		builder(ItemTags.SWORDS     ).add(MyItemIds.QUARTZ_SWORD)
		builder(ItemTags.SPEARS     ).add(MyItemIds.QUARTZ_SPEAR)
		builder(ItemTags.PICKAXES   ).add(MyItemIds.QUARTZ_PICKAXE)
		builder(ItemTags.AXES       ).add(MyItemIds.QUARTZ_AXE)
		builder(ItemTags.SHOVELS    ).add(MyItemIds.QUARTZ_SHOVEL)
		builder(ItemTags.HOES       ).add(MyItemIds.QUARTZ_HOE)
		builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.QUARTZ_HELMET)
		builder(ItemTags.CHEST_ARMOR).add(MyItemIds.QUARTZ_CHESTPLATE)
		builder(ItemTags.LEG_ARMOR  ).add(MyItemIds.QUARTZ_LEGGINGS)
		builder(ItemTags.FOOT_ARMOR ).add(MyItemIds.QUARTZ_BOOTS)
		builder(ItemTags.HEAD_ARMOR ).add(MyItemIds.GLASS_HELMET)
		builder(ItemTags.CHEST_ARMOR).add(MyItemIds.GLASS_CHESTPLATE)
		builder(ItemTags.LEG_ARMOR  ).add(MyItemIds.GLASS_LEGGINGS)
		builder(ItemTags.FOOT_ARMOR ).add(MyItemIds.GLASS_BOOTS)
		//@formatter:on
		
		builder(MyItemTags.EMERALD_ITEMS_FOR_XP)
			.add(MyItemIds.EMERALD_SWORD)
			.add(MyItemIds.EMERALD_SPEAR)
			.add(MyItemIds.EMERALD_AXE)
			.add(MyItemIds.EMERALD_PICKAXE)
			.add(MyItemIds.EMERALD_SHOVEL)
			.add(MyItemIds.EMERALD_HOE)
			.add(MyItemIds.EMERALD_HELMET)
			.add(MyItemIds.EMERALD_CHESTPLATE)
			.add(MyItemIds.EMERALD_LEGGINGS)
			.add(MyItemIds.EMERALD_BOOTS)
		
		builder(MyItemTags.OBSIDIAN_ARMOR_FOR_FIRE)
			.add(MyItemIds.OBSIDIAN_HELMET)
			.add(MyItemIds.OBSIDIAN_CHESTPLATE)
			.add(MyItemIds.OBSIDIAN_LEGGINGS)
			.add(MyItemIds.OBSIDIAN_BOOTS)
			.add(MyItemIds.OBSIDIAN_HORSE_ARMOR)
			.add(MyItemIds.OBSIDIAN_WOLF_ARMOR)
			.add(MyItemIds.OBSIDIAN_NAUTILUS_ARMOR)
	}
}