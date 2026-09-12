package ir.mmd.mcdev.moretools.effects

import ir.mmd.mcdev.moretools.id
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes

/**
 * Glass Invisibility: conceptually a copy of vanilla Invisibility, but as its own effect so it
 * can coexist with vanilla Invisibility and other mods' invisibility sources.
 *
 * Invisibility itself is not part of [MobEffect] — vanilla flips the entity's `invisible` flag in
 * `LivingEntity#updateInvisibilityStatus` when the INVISIBILITY effect is present; our mixin there
 * treats this effect the same way. Like vanilla's, the only attribute modifier is the waypoint
 * transmission-range hiding (26.2 waypoint system).
 *
 * Particles and the HUD icon are per-instance flags of [net.minecraft.world.effect.MobEffectInstance]
 * (`visible` / `showIcon`), not effect properties: instances are constructed with both false in
 * [GlassArmorInvisibility].
 *
 * The instance also uses `INFINITE_DURATION` (-1), which never ticks down.
 */
object GlassInvisibility {
	/** [MobEffect]'s constructor is protected; this is the smallest legal subclass (no overrides). */
	private class Effect : MobEffect(MobEffectCategory.BENEFICIAL, 0xF0FFFF) // same color family as vanilla invisibility
	
	@JvmStatic
	val GLASS_INVISIBILITY: Holder<MobEffect> = registerEffect(
		"glass_invisibility",
		Effect().addAttributeModifier(
			Attributes.WAYPOINT_TRANSMIT_RANGE,
			id("effect.glass_invisibility_waypoint_transmit_range_hide"),
			-1.0,
			AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
		)
	)
	
	/**
	 * Called from the invisibility mixin. Kept here (instead of inlining the check in Java) so the
	 * mixin body stays a plain delegation — the IDE cannot fold `original` into a compile-time
	 * constant through a function boundary, avoiding a false "condition is always true" warning.
	 */
	@JvmStatic
	fun isInvisibilitySource(original: Boolean, entity: LivingEntity): Boolean =
		original || entity.hasEffect(GLASS_INVISIBILITY)
	
	private fun registerEffect(name: String, effect: MobEffect): Holder<MobEffect> =
		Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, id(name), effect)
	
	/** Called from `Main.onInitialize`; static init above performs the registration. */
	fun register() {}
}
