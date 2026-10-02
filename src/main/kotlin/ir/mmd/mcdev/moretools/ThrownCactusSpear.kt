package ir.mmd.mcdev.moretools

import net.minecraft.core.component.DataComponents
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityReference
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.ProjectileDeflection
import net.minecraft.world.entity.projectile.arrow.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Level
import net.minecraft.world.level.storage.ValueInput
import net.minecraft.world.level.storage.ValueOutput
import net.minecraft.world.phys.EntityHitResult
import net.minecraft.world.phys.Vec3

/**
 * A thrown-trident clone whose hit damage comes from the spear's own ATTACK_DAMAGE attribute
 * modifier instead of vanilla's hardcoded 8.0f, so a thrown cactus spear deals the same damage as
 * a melee stab. Loyalty cannot exist here (the item is non-enchantable), so the whole
 * return/loyalty machinery is dropped; after a hit the projectile deflects like a loyalty-less
 * vanilla trident, lands, and is picked up (or despawns) by the normal arrow rules.
 *
 * The thrown [ItemStack] is never damaged after the throw — mirroring vanilla, where neither
 * [AbstractArrow] nor ThrownTrident touches the stack on impact. [CactusSpearItem.releaseUsing]
 * already consumed the charge.
 */
class ThrownCactusSpear : AbstractArrow {
	/** Factory for [EntityType.Builder.of] (entity loading). */
	constructor(type: EntityType<out ThrownCactusSpear>, level: Level) : super(type, level)
	
	/** The (pickupStack, firedFromWeapon) super constructor also sets the owner. */
	constructor(level: Level, owner: LivingEntity, stack: ItemStack) :
			super(EntityTypes.THROWN_CACTUS_SPEAR, owner, level, stack, stack)
	
	/** Dispenser path; the dispenser sets the owner afterwards (vanilla [net.minecraft.world.item.ProjectileItem] contract). */
	constructor(level: Level, x: Double, y: Double, z: Double, stack: ItemStack) :
			super(EntityTypes.THROWN_CACTUS_SPEAR, x, y, z, level, stack, stack)
	
	override fun findHitEntity(currentHitPoint: Vec3, startPoint: Vec3): EntityHitResult? {
		return if (dealtDamage) null else super.findHitEntity(currentHitPoint, startPoint)
	}
	
	override fun onHitEntity(result: EntityHitResult) {
		val target = result.entity
		val weapon = pickupItemStackOrigin
		// The weapon's own attribute modifier (49.0) + the player base (1.0) = the melee damage.
		var damage = weapon.get(DataComponents.ATTRIBUTE_MODIFIERS)
			?.compute(Attributes.ATTACK_DAMAGE, 1.0, EquipmentSlot.MAINHAND)
			?.toFloat() ?: 1f
		val ownerEntity: Entity? = getOwner()
		val source: DamageSource = damageSources().trident(this, ownerEntity ?: this)
		val serverLevel = level() as? ServerLevel
		if (serverLevel != null) {
			damage = EnchantmentHelper.modifyDamage(serverLevel, weapon, target, source, damage)
		}
		
		dealtDamage = true
		if (target.hurtOrSimulate(source, damage) && target.type !== net.minecraft.world.entity.EntityTypes.ENDERMAN) {
			if (serverLevel != null) {
				EnchantmentHelper.doPostAttackEffectsWithItemSourceOnBreak(
					serverLevel, target, source, weapon
				) { kill(serverLevel) }
			}
			if (target is LivingEntity) {
				doKnockback(target, source)
				doPostHurtEffects(target)
			}
		}
		
		val ownerRef: EntityReference<Entity>? = ownerEntity?.let { EntityReference.of(it) }
		deflect(ProjectileDeflection.REVERSE, target, ownerRef, false)
		deltaMovement = deltaMovement.multiply(0.02, 0.2, 0.02)
		playSound(SoundEvents.TRIDENT_HIT, 1f, 1f)
	}
	
	override fun tick() {
		if (inGroundTime > 4) dealtDamage = true
		super.tick()
	}
	
	public override fun getDefaultHitGroundSoundEvent(): SoundEvent = SoundEvents.TRIDENT_HIT_GROUND
	
	/** Only used when spawned through the entity type factory, which never happens for projectiles. */
	override fun getDefaultPickupItem(): ItemStack = ItemStack(Items.CACTUS_SPEAR)
	
	override fun readAdditionalSaveData(input: ValueInput) {
		super.readAdditionalSaveData(input)
		dealtDamage = input.getBooleanOr("DealtDamage", false)
	}
	
	override fun addAdditionalSaveData(output: ValueOutput) {
		super.addAdditionalSaveData(output)
		output.putBoolean("DealtDamage", dealtDamage)
	}
	
	override fun getWaterInertia(): Float = WATER_INERTIA
	
	override fun tickDespawn() {
		if (pickup != Pickup.ALLOWED) super.tickDespawn()
	}
	
	override fun playerTouch(player: Player) {
		if (ownedBy(player) || getOwner() == null) super.playerTouch(player)
	}
	
	private var dealtDamage = false
	
	companion object {
		private const val WATER_INERTIA = 0.99f
	}
}
