package ir.mmd.mcdev.moretools.client.providers

import ir.mmd.mcdev.moretools.Items as MyItems
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.world.item.Items
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
				shaped(RecipeCategory.COMBAT, MyItems.AMETHYST_SWORD)
					.pattern("A")
					.pattern("A")
					.pattern("S")
					.define('A', Items.AMETHYST_SHARD)
					.define('S', Items.STICK)
					.unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
					.save(output)
			}
		}
	}
	
	override fun getName() = "More Tools Recipes"
}