package ir.mmd.mcdev.moretools.toolsets

import ir.mmd.mcdev.moretools.Constants
import ir.mmd.mcdev.moretools.id
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.EquipmentSlotGroup.MAINHAND
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE
import net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_KNOCKBACK
import net.minecraft.world.entity.ai.attributes.Attributes.SWEEPING_DAMAGE_RATIO
import net.minecraft.world.item.CreativeModeTabs.COMBAT
import net.minecraft.world.item.CreativeModeTabs.TOOLS_AND_UTILITIES
import net.minecraft.world.item.Items.*
import net.minecraft.world.item.ToolMaterial

object AmethystToolSet : ToolSet() {
	val INCORRECT_FOR_AMETHYST = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "incorrect_for_amethyst"))
	val REPAIRS_AMETHYST = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "repairs_amethyst"))
	val MATERIAL = ToolMaterial(INCORRECT_FOR_AMETHYST, 350, 6f, 3f, 25, REPAIRS_AMETHYST)
	override val material: ToolMaterial get() = MATERIAL
	
	object ItemIds {
		val AMETHYST_SWORD = createResourceKey("amethyst_sword")
		val AMETHYST_SPEAR = createResourceKey("amethyst_spear")
		val AMETHYST_PICKAXE = createResourceKey("amethyst_pickaxe")
		val AMETHYST_AXE = createResourceKey("amethyst_axe")
		val AMETHYST_SHOVEL = createResourceKey("amethyst_shovel")
		val AMETHYST_HOE = createResourceKey("amethyst_hoe")
	}
	
	object Attributes {
		val KNOCKBACK = AttributeModifier(id("amethyst_knockback"), 1.0, ADD_VALUE)
		val SWEEP_DAMAGE = AttributeModifier(id("amethyst_sweep_damage"), 0.5, ADD_VALUE)
	}
	
	object Items {
		val AMETHYST_SWORD = registerSword(ItemIds.AMETHYST_SWORD, 2f, -2.2f) {
			it.withModifierAdded(ATTACK_KNOCKBACK, Attributes.KNOCKBACK, MAINHAND)
				.withModifierAdded(SWEEPING_DAMAGE_RATIO, Attributes.SWEEP_DAMAGE, MAINHAND)
		}
		val AMETHYST_SPEAR = registerSpear(ItemIds.AMETHYST_SPEAR, 0.85F, 0.95F, 0.5F, 2.5F, 11.0F, 5.5F, 3.8F, 11.25F, 4.3F) {
			it.withModifierAdded(ATTACK_KNOCKBACK, Attributes.KNOCKBACK, MAINHAND)
		}
		val AMETHYST_PICKAXE = registerPickaxe(ItemIds.AMETHYST_PICKAXE, 0f, -2.6f) {
			it.withModifierAdded(ATTACK_KNOCKBACK, Attributes.KNOCKBACK, MAINHAND)
		}
		val AMETHYST_AXE = registerAxe(ItemIds.AMETHYST_AXE, 5f, -2.9f) {
			it.withModifierAdded(ATTACK_KNOCKBACK, Attributes.KNOCKBACK, MAINHAND)
		}
		val AMETHYST_SHOVEL = registerShovel(ItemIds.AMETHYST_SHOVEL, 0.5f, -2.8f) {
			it.withModifierAdded(ATTACK_KNOCKBACK, Attributes.KNOCKBACK, MAINHAND)
		}
		val AMETHYST_HOE = registerHoe(ItemIds.AMETHYST_HOE, -3f, 0f) {
			it.withModifierAdded(ATTACK_KNOCKBACK, Attributes.KNOCKBACK, MAINHAND)
		}
	}
	
	override fun addCreativeItems() {
		creative(COMBAT) {
			insertAfter(IRON_SWORD, Items.AMETHYST_SWORD)
			insertAfter(IRON_SPEAR, Items.AMETHYST_SPEAR)
			insertAfter(IRON_AXE, Items.AMETHYST_AXE)
		}
		creative(TOOLS_AND_UTILITIES) {
			insertAfter(IRON_HOE, Items.AMETHYST_SHOVEL)
			insertAfter(Items.AMETHYST_SHOVEL, Items.AMETHYST_PICKAXE)
			insertAfter(Items.AMETHYST_PICKAXE, Items.AMETHYST_AXE)
			insertAfter(Items.AMETHYST_AXE, Items.AMETHYST_HOE)
		}
	}
	
	override fun addLoots() {
	}
}
