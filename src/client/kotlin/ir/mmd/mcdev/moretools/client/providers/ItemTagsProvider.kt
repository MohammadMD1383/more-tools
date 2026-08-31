package ir.mmd.mcdev.moretools.client.providers

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.references.ItemIds
import ir.mmd.mcdev.moretools.ItemIds as MyItemIds
import net.minecraft.tags.ItemTags
import java.util.concurrent.CompletableFuture
import ir.mmd.mcdev.moretools.ItemTags as MyItemTags

class ItemTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.ItemTagsProvider(output, registryLookupFuture) {
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(MyItemTags.AMETHYST_TOOL_MATERIALS).add(ItemIds.AMETHYST_SHARD)
		builder(MyItemTags.EMERALD_TOOL_MATERIALS).add(ItemIds.EMERALD)

		//@formatter:off
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
		//@formatter:on
	}
}