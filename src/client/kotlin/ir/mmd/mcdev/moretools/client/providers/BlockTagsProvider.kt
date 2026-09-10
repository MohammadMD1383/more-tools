package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.resourceKey
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Blocks
import java.util.concurrent.CompletableFuture
import ir.mmd.mcdev.moretools.BlockTags as MyBlockTags

class BlockTagsProvider(output: FabricPackOutput, registryLookupFuture: CompletableFuture<HolderLookup.Provider>) :
	FabricTagsProvider.BlockTagsProvider(output, registryLookupFuture) {
	
	override fun addTags(registries: HolderLookup.Provider) {
		builder(MyBlockTags.INCORRECT_FOR_AMETHYST_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
		builder(MyBlockTags.INCORRECT_FOR_EMERALD_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
		builder(MyBlockTags.INCORRECT_FOR_OBSIDIAN_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
		builder(MyBlockTags.INCORRECT_FOR_QUARTZ_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
		
		builder(MyBlockTags.QUARTZ_PICKAXE_INSTANT)
			.add(
				Blocks.NETHERRACK.resourceKey,
				Blocks.WARPED_NYLIUM.resourceKey,
				Blocks.CRIMSON_NYLIUM.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_PICKAXE_FAST)
			.add(
				Blocks.BLACKSTONE.resourceKey,
				Blocks.POLISHED_BLACKSTONE.resourceKey,
				Blocks.POLISHED_BLACKSTONE_BRICKS.resourceKey,
				Blocks.BASALT.resourceKey,
				Blocks.POLISHED_BASALT.resourceKey,
				Blocks.SMOOTH_BASALT.resourceKey,
				Blocks.NETHER_BRICKS.resourceKey,
				Blocks.NETHER_BRICK_FENCE.resourceKey,
				Blocks.RED_NETHER_BRICKS.resourceKey,
				Blocks.QUARTZ_BLOCK.resourceKey,
				Blocks.SMOOTH_QUARTZ.resourceKey,
				Blocks.CHISELED_QUARTZ_BLOCK.resourceKey,
				Blocks.QUARTZ_PILLAR.resourceKey,
				Blocks.QUARTZ_BRICKS.resourceKey,
				Blocks.NETHER_QUARTZ_ORE.resourceKey,
				Blocks.NETHER_GOLD_ORE.resourceKey,
				Blocks.MAGMA_BLOCK.resourceKey,
				Blocks.BONE_BLOCK.resourceKey,
				Blocks.ANCIENT_DEBRIS.resourceKey,
				Blocks.NETHERITE_BLOCK.resourceKey,
				Blocks.CRYING_OBSIDIAN.resourceKey,
				Blocks.RESPAWN_ANCHOR.resourceKey,
				Blocks.LODESTONE.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_SHOVEL_INSTANT)
			.add(
				Blocks.SOUL_SAND.resourceKey,
				Blocks.SOUL_SOIL.resourceKey,
			)
		
		builder(MyBlockTags.QUARTZ_SHOVEL_FAST)
			.add(
				Blocks.GRAVEL.resourceKey,
				Blocks.SAND.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_AXE_INSTANT)
			.add(
				Blocks.CRIMSON_STEM.resourceKey,
				Blocks.WARPED_STEM.resourceKey,
				Blocks.STRIPPED_CRIMSON_STEM.resourceKey,
				Blocks.STRIPPED_WARPED_STEM.resourceKey,
				Blocks.CRIMSON_HYPHAE.resourceKey,
				Blocks.WARPED_HYPHAE.resourceKey,
				Blocks.STRIPPED_CRIMSON_HYPHAE.resourceKey,
				Blocks.STRIPPED_WARPED_HYPHAE.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_AXE_FAST)
			.add(
				Blocks.NETHER_WART_BLOCK.resourceKey,
				Blocks.WARPED_WART_BLOCK.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_HOE_INSTANT)
			.add(
				Blocks.NETHER_WART_BLOCK.resourceKey,
				Blocks.WARPED_WART_BLOCK.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_HOE_FAST)
			.add(
				Blocks.NETHER_WART.resourceKey,
				Blocks.NETHER_SPROUTS.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_SWORD_INSTANT)
			.add(
				Blocks.NETHER_WART.resourceKey,
				Blocks.NETHER_SPROUTS.resourceKey
			)
		
		builder(MyBlockTags.QUARTZ_SWORD_FAST)
	}
}