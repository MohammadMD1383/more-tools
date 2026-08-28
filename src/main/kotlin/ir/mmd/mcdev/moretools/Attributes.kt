package ir.mmd.mcdev.moretools

import net.minecraft.resources.Identifier
import net.minecraft.world.entity.ai.attributes.AttributeModifier

object Attributes {
	val AmethystKnockback = AttributeModifier(
		Identifier.fromNamespaceAndPath(Constants.MOD_ID, "amethyst_knockback"),
		2.0,
		AttributeModifier.Operation.ADD_VALUE
	)
	
	val AmethystSweepDamage = AttributeModifier(
		Identifier.fromNamespaceAndPath(Constants.MOD_ID, "amethyst_sweep_damage"),
		0.5,
		AttributeModifier.Operation.ADD_VALUE
	)
}