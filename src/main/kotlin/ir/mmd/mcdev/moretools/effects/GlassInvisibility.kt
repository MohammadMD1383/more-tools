package ir.mmd.mcdev.moretools.effects

import ir.mmd.mcdev.moretools.Items as MyItems
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.decoration.ArmorStand
import net.minecraft.world.item.Item

/**
 * Helpers for the Glass Armor set bonus, which is **vanilla [MobEffects.INVISIBILITY]** carried
 * while the full set is worn.
 *
 * Why vanilla's effect and not a custom one: mob AI targeting has no effect hook to extend.
 * `TargetingConditions#test` (every `NearestAttackableTargetGoal`) keys the invisible target's
 * detectability off `LivingEntity#getVisibilityPercent`, which reads only the `invisible` flag —
 * the target's own effects are never consulted. Carrying the real vanilla effect means every
 * consumer in the game (mob targeting, waypoint transmit range, other mods) already understands
 * it, and the `updateInvisibilityStatus` mixin from the previous custom-effect design became
 * unnecessary.
 *
 * The reconcile loop in [GlassArmorInvisibility] must not remove a *deliberate* potion, so when
 * the set is incomplete the effect is only stripped if it was applied by this set bonus. There is
 * no state to persist: [MobEffectInstance.isInfiniteDuration] + `ambient` + `showIcon = false`
 * is the fingerprint — no player-brewed potion has those flags (ambient comes from a beacon
 * [net.minecraft.world.effect.MobEffect] and shows the icon; `/effect give` defaults have both
 * flags off).
 */
object GlassInvisibility {
	/** The instance granted by the set bonus: infinite, ambient, no particles, no HUD icon. */
	@JvmStatic
	val SET_BONUS_INSTANCE = MobEffectInstance(
		MobEffects.INVISIBILITY,
		MobEffectInstance.INFINITE_DURATION,
		0,      // amplifier
		true,   // ambient
		false,  // visible: no particles
		false   // showIcon: no HUD icon
	)
	
	@JvmStatic
	private val GLASS_ARMOR_ITEMS: Set<Item> = setOf(
		MyItems.GLASS_HELMET, MyItems.GLASS_CHESTPLATE, MyItems.GLASS_LEGGINGS, MyItems.GLASS_BOOTS
	)
	
	@JvmStatic
	private val ARMOR_SLOTS = arrayOf(
		EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
	)
	
	/**
	 * [LivingEntity.getVisibilityPercent] clamps an invisible entity's armor cover to 0.1 **only below that
	 * value**, so armor normally hides an invisible player from mobs at up to 70% of follow range
	 * instead of ~2 blocks. Zero-defense Glass pieces still count as cover, which is exactly the
	 * reported bug: mobs see the invisible player at 70% range. Returning the naked-player clamp
	 * (0.1) while the full set is worn reproduces the potion-without-armor numbers; any other
	 * state delegates to vanilla (an empty slot keeps the clamp low anyway).
	 *
	 * Called from the mixin on `LivingEntity#getVisibilityPercent` — the single call site where
	 * mob targeting turns armor into detection range for invisible entities. `ArmorStand` is
	 * excluded: it is a `LivingEntity` whose armor is display state, not worn gear.
	 */
	@JvmStatic
	fun armorCoverForGlass(original: Float, entity: LivingEntity): Float =
		if (!entity.isSpectator && entity !is ArmorStand && isFullSet(entity)) 0.1f else original
	
	/**
	 * The set-bonus fingerprint: the granted instance is infinite + ambient + icon-less, a
	 * combination no vanilla dispenser produces (beacon effects are ambient but show the icon),
	 * so stripping an instance matching this never removes a deliberate potion.
	 */
	@JvmStatic
	fun isSetBonusFingerprint(instance: MobEffectInstance): Boolean =
		instance.isInfiniteDuration && instance.isAmbient && !instance.showIcon()
	
	/** Full glass set = all four humanoid armor slots hold a Glass piece. */
	@JvmStatic
	fun isFullSet(entity: LivingEntity): Boolean =
		ARMOR_SLOTS.all { entity.getItemBySlot(it).item in GLASS_ARMOR_ITEMS }
}
