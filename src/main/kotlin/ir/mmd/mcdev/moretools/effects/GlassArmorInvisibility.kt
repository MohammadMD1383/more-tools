package ir.mmd.mcdev.moretools.effects

import ir.mmd.mcdev.moretools.Items as MyItems
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

/**
 * Event-driven Glass Armor invisibility reconciliation. No tick polling: the trigger is Fabric's
 * `ServerEntityEvents.EQUIPMENT_CHANGE` (fabric-lifecycle-events-v1), which fires from vanilla's
 * `LivingEntity#collectEquipmentChanges` — the same diff vanilla itself uses for equip sounds,
 * attribute updates and item-break events — whenever an equipment slot's stack changes *or mutates*
 * (compared with `ItemStack.matches`), and once per non-empty slot when the entity is first tracked.
 *
 * This single hook therefore covers: equipping, unequipping, replacing, durability mutation
 * (DAMAGE/MAX_DAMAGE are data components, so damaging the item changes the stack), and the break
 * itself (when the piece hits 0 durability vanilla empties the slot, reported here as
 * previous = stack, current = EMPTY).
 *
 * Ownership: "Glass Armor owns the invisibility" simply means the player carries the vanilla
 * [net.minecraft.world.effect.MobEffects.INVISIBILITY] instance from
 * [GlassInvisibility.SET_BONUS_INSTANCE] — no separate state exists to drift out of sync. The
 * armour-cover mixin in `LivingEntityMixin` reproduces the naked-invisible-player detection range
 * while the set is worn, which is what makes mobs actually unable to see the player (see
 * [GlassInvisibility.armorCoverForGlass]).
 *
 * A *deliberate* Invisibility (potion etc.) is never touched: it is removed on set loss only when
 * it carries the set-bonus fingerprint (see [GlassInvisibility]), and pre-existing effects survive
 * the whole cycle untouched — addEffect upserts by effect id only.
 */
object GlassArmorInvisibility {
	private const val FULL_SET = 4
	
	private val ARMOR_SLOTS = listOf(
		EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
	)
	
	fun register() {
		ServerEntityEvents.EQUIPMENT_CHANGE.register { entity, _, previous, current ->
			if (entity is Player) {
				val touchedGlass = previous.isGlassArmor() || current.isGlassArmor()
				// Reconcile when a glass piece changed (fast path) OR whenever the full set is worn
				// (defensive: also re-establishes the effect after a join if bookkeeping ever drifts).
				if (touchedGlass || GlassInvisibility.isFullSet(entity)) {
					updateInvisibilityState(entity)
				}
			}
		}
		
		// Milk, totems of undying and /effect clear strip every effect including ours. When the player
		// still wears the full set, immediately re-apply it (event-driven self-heal, no polling).
		// No loop: the removal path below only removes when the set is incomplete. Re-adding here is
		// safe — vanilla's removeAllEffects copies the effects map before clearing (no CME), and the
		// effect's INFINITE_DURATION means it can never re-enter via the expiry path.
		ServerMobEffectEvents.AFTER_REMOVE.register { instance, entity, _ ->
			if (instance.effect == GlassInvisibility.SET_BONUS_INSTANCE.effect &&
				entity is ServerPlayer && hasFullSet(entity)
			) {
				grantGlassInvisibility(entity)
			}
		}
		
		// Join/respawn reconciliation. The effect persists in player saves, and the equipment-change
		// event only fires for non-empty slots when an entity is first tracked, so a stale effect
		// (e.g. armor removed by another mod while offline) would survive a relog. Reconciling on
		// JOIN/AFTER_RESPAWN is idempotent no matter whether the tracked-slot events ran first.
		ServerPlayerEvents.JOIN.register { player ->
			updateInvisibilityState(player)
		}
		
		// Respawn (incl. end-portal return) can keep armor equipped (keepInventory): the new player
		// entity must be reconciled once. The old player entity is discarded, so nothing to clean up.
		ServerPlayerEvents.AFTER_RESPAWN.register { _, newPlayer, _ ->
			updateInvisibilityState(newPlayer)
		}
	}
	
	private fun countWornGlassPieces(player: Player): Int =
		ARMOR_SLOTS.count { player.getItemBySlot(it).isGlassArmor() }
	
	private fun hasFullSet(player: Player): Boolean = countWornGlassPieces(player) == FULL_SET
	
	private fun ItemStack.isGlassArmor(): Boolean = item == MyItems.GLASS_HELMET ||
		item == MyItems.GLASS_CHESTPLATE || item == MyItems.GLASS_LEGGINGS || item == MyItems.GLASS_BOOTS
	
	private fun updateInvisibilityState(player: Player) {
		if (player !is ServerPlayer) return
		
		if (hasFullSet(player)) {
			grantGlassInvisibility(player)
		} else {
			// Strips the set-bonus instance only; a deliberate potion (different fingerprint) and
			// other mods' effects stay. See GlassInvisibility for why the fingerprint is safe.
			val current = player.getEffect(GlassInvisibility.SET_BONUS_INSTANCE.effect)
			if (current != null && GlassInvisibility.isSetBonusFingerprint(current)) {
				player.removeEffect(GlassInvisibility.SET_BONUS_INSTANCE.effect)
			}
		}
	}
	
	private fun grantGlassInvisibility(player: ServerPlayer) {
		if (!player.hasEffect(GlassInvisibility.SET_BONUS_INSTANCE.effect)) {
			// addEffect copies the instance, so handing out the shared SET_BONUS_INSTANCE is safe.
			player.addEffect(GlassInvisibility.SET_BONUS_INSTANCE)
		}
	}
}
