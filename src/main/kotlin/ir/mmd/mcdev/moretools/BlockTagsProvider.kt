package ir.mmd.mcdev.moretools

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import java.util.concurrent.CompletableFuture

class BlockTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.BlockTagsProvider(output, registryLookupFuture) {
	
	companion object {
		val INCORRECT_FOR_AMETHYST = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "incorrect_for_amethyst"))
	}
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(INCORRECT_FOR_AMETHYST).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
	}
}