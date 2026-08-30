package ir.mmd.mcdev.moretools.client.providers

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.data.recipes.RecipeProvider.getHasName
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
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
	
	private fun RecipeProvider.swordCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.COMBAT, outcome)
			.pattern("X")
			.pattern("X")
			.pattern("S")
			.define('X', material)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.pickaxeCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.TOOLS, outcome)
			.pattern("XXX")
			.pattern(" S ")
			.pattern(" S ")
			.define('X', material)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.axeCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.TOOLS, outcome)
			.pattern("XX")
			.pattern("XS")
			.pattern(" S")
			.define('X', material)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.shovelCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.TOOLS, outcome)
			.pattern("X")
			.pattern("S")
			.pattern("S")
			.define('X', material)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.hoeCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.TOOLS, outcome)
			.pattern("XX")
			.pattern(" S")
			.pattern(" S")
			.define('X', material)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.spearCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.COMBAT, outcome)
			.pattern("  X")
			.pattern(" S ")
			.pattern("S  ")
			.define('X', material)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.allToolsCraftingRecipe(
		material: ItemLike,
		sword: ItemLike,
		pickaxe: ItemLike,
		axe: ItemLike,
		shovel: ItemLike,
		hoe: ItemLike,
		spear: ItemLike,
		output: RecipeOutput
	) {
		swordCraftingRecipe(material, sword, output)
		pickaxeCraftingRecipe(material, pickaxe, output)
		axeCraftingRecipe(material, axe, output)
		shovelCraftingRecipe(material, shovel, output)
		hoeCraftingRecipe(material, hoe, output)
		spearCraftingRecipe(material, spear, output)
	}
	
	private fun RecipeProvider.helmetCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.COMBAT, outcome)
			.pattern("XXX")
			.pattern("X X")
			.define('X', material)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.chestplateCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.COMBAT, outcome)
			.pattern("X X")
			.pattern("XXX")
			.pattern("XXX")
			.define('X', material)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.leggingsCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.COMBAT, outcome)
			.pattern("XXX")
			.pattern("X X")
			.pattern("X X")
			.define('X', material)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.bootsCraftingRecipe(
		material: ItemLike,
		outcome: ItemLike,
		output: RecipeOutput
	) {
		shaped(RecipeCategory.COMBAT, outcome)
			.pattern("X X")
			.pattern("X X")
			.define('X', material)
			.unlockedBy(getHasName(material), has(material))
			.save(output)
	}
	
	private fun RecipeProvider.allArmorCraftingRecipe(
		material: ItemLike,
		helmet: ItemLike,
		chestplate: ItemLike,
		leggings: ItemLike,
		boots: ItemLike,
		output: RecipeOutput
	) {
		helmetCraftingRecipe(material, helmet, output)
		chestplateCraftingRecipe(material, chestplate, output)
		leggingsCraftingRecipe(material, leggings, output)
		bootsCraftingRecipe(material, boots, output)
	}
	
	override fun createRecipeProvider(
		registries: HolderLookup.Provider,
		output: RecipeOutput
	) = recipeProvider(registries, output) {
		allToolsCraftingRecipe(
			Items.AMETHYST_SHARD,
			MyItems.AMETHYST_SWORD,
			MyItems.AMETHYST_PICKAXE,
			MyItems.AMETHYST_AXE,
			MyItems.AMETHYST_SHOVEL,
			MyItems.AMETHYST_HOE,
			MyItems.AMETHYST_SPEAR,
			output
		)
		allArmorCraftingRecipe(
			Items.AMETHYST_SHARD,
			MyItems.AMETHYST_HELMET,
			MyItems.AMETHYST_CHESTPLATE,
			MyItems.AMETHYST_LEGGINGS,
			MyItems.AMETHYST_BOOTS,
			output
		)
	}
	
	override fun getName() = "More Tools Recipes"
}