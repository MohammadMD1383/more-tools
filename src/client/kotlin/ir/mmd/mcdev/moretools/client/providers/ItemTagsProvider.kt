package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet
import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.REPAIRS_AMETHYST
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.references.ItemIds
import net.minecraft.tags.ItemTags
import java.util.concurrent.CompletableFuture

class ItemTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.ItemTagsProvider(output, registryLookupFuture) {
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(REPAIRS_AMETHYST).add(ItemIds.AMETHYST_SHARD)
		builder(ItemTags.SWORDS).add(AmethystToolSet.ItemIds.AMETHYST_SWORD)
		builder(ItemTags.SPEARS).add(AmethystToolSet.ItemIds.AMETHYST_SPEAR)
		builder(ItemTags.PICKAXES).add(AmethystToolSet.ItemIds.AMETHYST_PICKAXE)
		builder(ItemTags.AXES).add(AmethystToolSet.ItemIds.AMETHYST_AXE)
		builder(ItemTags.SHOVELS).add(AmethystToolSet.ItemIds.AMETHYST_SHOVEL)
		builder(ItemTags.HOES).add(AmethystToolSet.ItemIds.AMETHYST_HOE)
	}
}