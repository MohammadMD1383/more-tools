package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.INCORRECT_FOR_AMETHYST
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture

class BlockTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.BlockTagsProvider(output, registryLookupFuture) {
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(INCORRECT_FOR_AMETHYST).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
	}
}