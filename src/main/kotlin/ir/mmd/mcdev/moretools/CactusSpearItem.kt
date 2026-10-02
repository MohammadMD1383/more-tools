package ir.mmd.mcdev.moretools

import net.minecraft.core.Position
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.entity.projectile.arrow.AbstractArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUseAnimation
import net.minecraft.world.item.TridentItem
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Level

/**
 * A trident-handling item (charge to throw, stab otherwise) whose projectile is the mod's own
 * [ThrownCactusSpear] — vanilla's [TridentItem] hardcodes `ThrownTrident::new`, the 8.0f thrown
 * damage, and a refuse-to-throw gate for stacks about to break, none of which fit a durability-1
 * throwaway spear. Hence: `use`/`releaseUsing` are lifted from vanilla with the gate removed and
 * the spawn routed to the custom entity. The riptide branch is dead (the item is non-enchantable).
 *
 * Melee damage is carried entirely by the item's ATTACK_DAMAGE attribute (see [Items.CACTUS_SPEAR]);
 * the thrown entity reads the same attribute, so melee and throw deal equal damage.
 */
class CactusSpearItem(properties: Properties) : TridentItem(properties) {
	override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
		player.startUsingItem(hand)
		return InteractionResult.CONSUME
	}
	
	/** Vanilla returns [ItemUseAnimation.SPEAR]. */
	override fun getUseAnimation(stack: ItemStack) = ItemUseAnimation.SPEAR
	
	override fun releaseUsing(stack: ItemStack, level: Level, entity: LivingEntity, timeLeft: Int): Boolean {
		if (entity !is Player) return false
		if (getUseDuration(stack, entity) - timeLeft < THROW_THRESHOLD_TIME) return false
		if (level !is ServerLevel) return false
		
		// Vanilla order: charge the held stack, then consume one item — the flying stack carries the
		// damage, so a re-picked-up spear is spent (any further use breaks it).
		stack.hurtWithoutBreaking(1, entity)
		val thrownStack = stack.consumeAndReturn(1, entity)
		val sound = EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.TRIDENT_SOUND)
			.orElse(SoundEvents.TRIDENT_THROW)
		
		val spear = Projectile.spawnProjectileFromRotation(::ThrownCactusSpear, level, thrownStack, entity, 0f, PROJECTILE_SHOOT_POWER, 1f)
		if (entity.hasInfiniteMaterials()) spear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY
		level.playSound(null, entity, sound.value(), SoundSource.PLAYERS, 1f, 1f)
		return true
	}
	
	override fun asProjectile(level: Level, position: Position, stack: ItemStack, direction: Direction): Projectile =
		ThrownCactusSpear(level, position.x(), position.y(), position.z(), stack.copyWithCount(1)).apply {
			pickup = AbstractArrow.Pickup.ALLOWED
		}
}
