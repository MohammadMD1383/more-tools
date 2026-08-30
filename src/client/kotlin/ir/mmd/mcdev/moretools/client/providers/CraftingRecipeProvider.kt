package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.Items.AMETHYST_AXE
import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.Items.AMETHYST_HOE
import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.Items.AMETHYST_PICKAXE
import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.Items.AMETHYST_SHOVEL
import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.Items.AMETHYST_SPEAR
import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet.Items.AMETHYST_SWORD
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory.COMBAT
import net.minecraft.data.recipes.RecipeCategory.TOOLS
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.item.Items.AMETHYST_SHARD
import net.minecraft.world.item.Items.STICK
import java.util.concurrent.CompletableFuture

class CraftingRecipeProvider(
	output: FabricPackOutput,
	registriesFuture: CompletableFuture<HolderLookup.Provider>,
) : FabricRecipeProvider(output, registriesFuture) {
	
	override fun createRecipeProvider(
		registries: HolderLookup.Provider,
		output: RecipeOutput
	): RecipeProvider {
		return object : RecipeProvider(registries, output) {
			override fun buildRecipes() {
				shaped(COMBAT, AMETHYST_SWORD)
					.pattern("A")
					.pattern("A")
					.pattern("S")
					.define('A', AMETHYST_SHARD)
					.define('S', STICK)
					.unlockedBy(getHasName(AMETHYST_SHARD), has(AMETHYST_SHARD))
					.save(output)
				
				shaped(COMBAT, AMETHYST_SPEAR)
					.pattern(" A ")
					.pattern("AAA")
					.pattern(" S ")
					.define('A', AMETHYST_SHARD)
					.define('S', STICK)
					.unlockedBy(getHasName(AMETHYST_SHARD), has(AMETHYST_SHARD))
					.save(output)
				
				shaped(TOOLS, AMETHYST_PICKAXE)
					.pattern("AAA")
					.pattern(" S ")
					.pattern(" S ")
					.define('A', AMETHYST_SHARD)
					.define('S', STICK)
					.unlockedBy(getHasName(AMETHYST_SHARD), has(AMETHYST_SHARD))
					.save(output)
				
				shaped(TOOLS, AMETHYST_AXE)
					.pattern("AA")
					.pattern("AS")
					.pattern(" S")
					.define('A', AMETHYST_SHARD)
					.define('S', STICK)
					.unlockedBy(getHasName(AMETHYST_SHARD), has(AMETHYST_SHARD))
					.save(output)
				
				shaped(TOOLS, AMETHYST_SHOVEL)
					.pattern("A")
					.pattern("S")
					.pattern("S")
					.define('A', AMETHYST_SHARD)
					.define('S', STICK)
					.unlockedBy(getHasName(AMETHYST_SHARD), has(AMETHYST_SHARD))
					.save(output)
				
				shaped(TOOLS, AMETHYST_HOE)
					.pattern("AA")
					.pattern(" S")
					.pattern(" S")
					.define('A', AMETHYST_SHARD)
					.define('S', STICK)
					.unlockedBy(getHasName(AMETHYST_SHARD), has(AMETHYST_SHARD))
					.save(output)
			}
		}
	}
	
	override fun getName() = "More Tools Recipes"
}