package ir.mmd.mcdev.moretools

import net.minecraft.core.Holder
import net.minecraft.core.component.DataComponents
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.Item.Properties

object AttributeIds {
	//@formatter:off
	@JvmStatic val AMETHYST_KNOCKBACK                 = id("amethyst_knockback")
	@JvmStatic val AMETHYST_SWEEP_DAMAGE              = id("amethyst_sweep_damage")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_HELMET     = id("obsidian_movement_speed_helmet")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_CHESTPLATE = id("obsidian_movement_speed_chestplate")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_LEGGINGS   = id("obsidian_movement_speed_leggings")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_BOOTS      = id("obsidian_movement_speed_boots")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_HORSE      = id("obsidian_movement_speed_horse")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_WOLF       = id("obsidian_movement_speed_wolf")
	@JvmStatic val OBSIDIAN_MOVEMENT_SPEED_NAUTILUS   = id("obsidian_movement_speed_nautilus")
	//@formatter:on
}

fun Properties.addAttributeModifier(
	attribute: Holder<Attribute>,
	modifier: AttributeModifier,
	slot: EquipmentSlotGroup
) = modifyComponent(DataComponents.ATTRIBUTE_MODIFIERS) { modifiers, _, _ ->
	modifiers?.withModifierAdded(attribute, modifier, slot)
}

fun Properties.nonEnchantable() = modifyComponent(DataComponents.ENCHANTABLE) { _, _, _ -> null }

fun Properties.amethystKnockback() = addAttributeModifier(
	Attributes.ATTACK_KNOCKBACK,
	AttributeModifier(AttributeIds.AMETHYST_KNOCKBACK, 1.0, AttributeModifier.Operation.ADD_VALUE),
	EquipmentSlotGroup.MAINHAND
)

fun Properties.amethystSweepDamage() = addAttributeModifier(
	Attributes.SWEEPING_DAMAGE_RATIO,
	AttributeModifier(AttributeIds.AMETHYST_SWEEP_DAMAGE, 0.35, AttributeModifier.Operation.ADD_VALUE),
	EquipmentSlotGroup.MAINHAND
)

fun Properties.obsidianMovementSpeed(id: Identifier) = addAttributeModifier(
	Attributes.MOVEMENT_SPEED,
	AttributeModifier(id, -0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
	EquipmentSlotGroup.ARMOR
)