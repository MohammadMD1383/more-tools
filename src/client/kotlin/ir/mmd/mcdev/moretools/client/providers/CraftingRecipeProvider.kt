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
	
	private fun RecipeProvider.craftingRecipe(
		category: RecipeCategory,
		material: ItemLike,
		outcome: ItemLike,
		pattern: List<String>,
		output: RecipeOutput
	) {
		shaped(category, outcome).apply {
			pattern.forEach(::pattern)
			define('X', material)
			if (pattern.any { 'S' in it }) define('S', Items.STICK)
			unlockedBy(getHasName(material), has(material))
			save(output)
		}
	}
	
	private fun RecipeProvider.allCraftingRecipe(
		material: ItemLike,
		output: RecipeOutput,
		
		sword: ItemLike? = null,
		spear: ItemLike? = null,
		
		pickaxe: ItemLike? = null,
		axe: ItemLike? = null,
		shovel: ItemLike? = null,
		hoe: ItemLike? = null,
		
		helmet: ItemLike? = null,
		chestplate: ItemLike? = null,
		leggings: ItemLike? = null,
		boots: ItemLike? = null,
		
		nautilus: ItemLike? = null,
		horse: ItemLike? = null,
		wolf: ItemLike? = null,
	) {
		sword?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("X", "X", "S"),
				output
			)
		}
		
		spear?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("  X", " S ", "S  "),
				output
			)
		}
		
		pickaxe?.let {
			craftingRecipe(
				RecipeCategory.TOOLS,
				material,
				it,
				listOf("XXX", " S ", " S "),
				output
			)
		}
		
		axe?.let {
			craftingRecipe(
				RecipeCategory.TOOLS,
				material,
				it,
				listOf("XX", "XS", " S"),
				output
			)
		}
		
		shovel?.let {
			craftingRecipe(
				RecipeCategory.TOOLS,
				material,
				it,
				listOf("X", "S", "S"),
				output
			)
		}
		
		hoe?.let {
			craftingRecipe(
				RecipeCategory.TOOLS,
				material,
				it,
				listOf("XX", " S", " S"),
				output
			)
		}
		
		helmet?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("XXX", "X X"),
				output
			)
		}
		
		chestplate?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("X X", "XXX", "XXX"),
				output
			)
		}
		
		leggings?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("XXX", "X X", "X X"),
				output
			)
		}
		
		boots?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("X X", "X X"),
				output
			)
		}
		
		nautilus?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf(" XX", " XX", "XXX"),
				output
			)
		}
		
		horse?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("X X", "XXX", "X X"),
				output
			)
		}
		
		wolf?.let {
			craftingRecipe(
				RecipeCategory.COMBAT,
				material,
				it,
				listOf("X  ", "XXX", "X X"),
				output
			)
		}
	}
	
	override fun createRecipeProvider(
		registries: HolderLookup.Provider,
		output: RecipeOutput
	) = recipeProvider(registries, output) {
		allCraftingRecipe(
			Items.AMETHYST_SHARD, output,
			sword = MyItems.AMETHYST_SWORD,
			spear = MyItems.AMETHYST_SPEAR,
			pickaxe = MyItems.AMETHYST_PICKAXE,
			axe = MyItems.AMETHYST_AXE,
			shovel = MyItems.AMETHYST_SHOVEL,
			hoe = MyItems.AMETHYST_HOE,
			helmet = MyItems.AMETHYST_HELMET,
			chestplate = MyItems.AMETHYST_CHESTPLATE,
			leggings = MyItems.AMETHYST_LEGGINGS,
			boots = MyItems.AMETHYST_BOOTS,
			nautilus = MyItems.AMETHYST_NAUTILUS_ARMOR,
			horse = MyItems.AMETHYST_HORSE_ARMOR,
			wolf = MyItems.AMETHYST_WOLF_ARMOR
		)

		allCraftingRecipe(
			Items.EMERALD, output,
			sword = MyItems.EMERALD_SWORD,
			spear = MyItems.EMERALD_SPEAR,
			pickaxe = MyItems.EMERALD_PICKAXE,
			axe = MyItems.EMERALD_AXE,
			shovel = MyItems.EMERALD_SHOVEL,
			hoe = MyItems.EMERALD_HOE,
			helmet = MyItems.EMERALD_HELMET,
			chestplate = MyItems.EMERALD_CHESTPLATE,
			leggings = MyItems.EMERALD_LEGGINGS,
			boots = MyItems.EMERALD_BOOTS,
			nautilus = MyItems.EMERALD_NAUTILUS_ARMOR,
			horse = MyItems.EMERALD_HORSE_ARMOR,
			wolf = MyItems.EMERALD_WOLF_ARMOR
		)

		allCraftingRecipe(Items.LEATHER, output, wolf = MyItems.LEATHER_WOLF_ARMOR)
		allCraftingRecipe(Items.COPPER_INGOT, output, wolf = MyItems.COPPER_WOLF_ARMOR)
		allCraftingRecipe(
			Items.IRON_CHAIN, output,
			helmet = Items.CHAINMAIL_HELMET,
			chestplate = Items.CHAINMAIL_CHESTPLATE,
			leggings = Items.CHAINMAIL_LEGGINGS,
			boots = Items.CHAINMAIL_BOOTS,
			wolf = MyItems.CHAINMAIL_WOLF_ARMOR
		)
		allCraftingRecipe(Items.IRON_INGOT, output, wolf = MyItems.IRON_WOLF_ARMOR)
		allCraftingRecipe(Items.GOLD_INGOT, output, wolf = MyItems.GOLD_WOLF_ARMOR)
		allCraftingRecipe(Items.DIAMOND, output, wolf = MyItems.DIAMOND_WOLF_ARMOR)
		// todo: netherite upgrading template
	}
	
	override fun getName() = "More Tools Recipes"
}