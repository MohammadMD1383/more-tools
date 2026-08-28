package ir.mmd.mcdev.moretools

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.references.ItemIds
import net.minecraft.resources.Identifier
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import java.util.concurrent.CompletableFuture
import ir.mmd.mcdev.moretools.ItemIds as MyItemIds

class ItemTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.ItemTagsProvider(output, registryLookupFuture) {
	
	companion object {
		val REPAIRS_AMETHYST = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "repairs_amethyst"))
	}
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(REPAIRS_AMETHYST).add(ItemIds.AMETHYST_SHARD)
		builder(ItemTags.SWORDS).add(MyItemIds.AMETHYST_SWORD)
	}
}