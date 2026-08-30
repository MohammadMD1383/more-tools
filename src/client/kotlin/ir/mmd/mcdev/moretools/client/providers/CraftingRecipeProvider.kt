package ir.mmd.mcdev.moretools.client.providers

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.RecipeProvider.getHasName
import net.minecraft.world.item.Items
import java.util.concurrent.CompletableFuture
import ir.mmd.mcdev.moretools.Items as MyItems

class CraftingRecipeProvider(
	output: FabricPackOutput,
	registriesFuture: CompletableFuture<HolderLookup.Provider>,
) : FabricRecipeProvider(output, registriesFuture) {
	private inline fun recipeProvider(
		registries: HolderLookup.Provider,
		output: RecipeOutput,
		crossinline block: RecipeProvider.() -> Unit
	): RecipeProvider {
		return object : RecipeProvider(registries, output) {
			override fun buildRecipes() = block()
		}
	}
	
	override fun createRecipeProvider(
		registries: HolderLookup.Provider,
		output: RecipeOutput
	) = recipeProvider(registries, output) {
		val amethystName = getHasName(Items.AMETHYST_SHARD)
		val amethystItem = has(Items.AMETHYST_SHARD)
		
		shaped(RecipeCategory.COMBAT, MyItems.AMETHYST_SWORD)
			.pattern("A")
			.pattern("A")
			.pattern("S")
			.define('A', Items.AMETHYST_SHARD)
			.define('S', Items.STICK)
			.unlockedBy(amethystName, amethystItem)
			.save(output)
		
		shaped(RecipeCategory.COMBAT, MyItems.AMETHYST_SPEAR)
			.pattern("  A")
			.pattern(" S ")
			.pattern("S  ")
			.define('A', Items.AMETHYST_SHARD)
			.define('S', Items.STICK)
			.unlockedBy(amethystName, amethystItem)
			.save(output)
		
		shaped(RecipeCategory.TOOLS, MyItems.AMETHYST_PICKAXE)
			.pattern("AAA")
			.pattern(" S ")
			.pattern(" S ")
			.define('A', Items.AMETHYST_SHARD)
			.define('S', Items.STICK)
			.unlockedBy(amethystName, amethystItem)
			.save(output)
		
		shaped(RecipeCategory.TOOLS, MyItems.AMETHYST_AXE)
			.pattern("AA")
			.pattern("AS")
			.pattern(" S")
			.define('A', Items.AMETHYST_SHARD)
			.define('S', Items.STICK)
			.unlockedBy(amethystName, amethystItem)
			.save(output)
		
		shaped(RecipeCategory.TOOLS, MyItems.AMETHYST_SHOVEL)
			.pattern("A")
			.pattern("S")
			.pattern("S")
			.define('A', Items.AMETHYST_SHARD)
			.define('S', Items.STICK)
			.unlockedBy(amethystName, amethystItem)
			.save(output)
		
		shaped(RecipeCategory.TOOLS, MyItems.AMETHYST_HOE)
			.pattern("AA")
			.pattern(" S")
			.pattern(" S")
			.define('A', Items.AMETHYST_SHARD)
			.define('S', Items.STICK)
			.unlockedBy(amethystName, amethystItem)
			.save(output)
	}
	
	override fun getName() = "More Tools Recipes"
}