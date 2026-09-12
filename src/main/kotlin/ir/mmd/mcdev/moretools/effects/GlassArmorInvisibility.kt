package ir.mmd.mcdev.moretools.effects

import ir.mmd.mcdev.moretools.Items as MyItems
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffectInstance
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
 * Ownership: "Glass Armor owns the invisibility" simply means the player carries the
 * [GlassInvisibility.GLASS_INVISIBILITY] effect — no separate state exists to drift out of sync.
 * Vanilla Invisibility and other mods' effects are never inspected, saved or restored; they are
 * independent effect instances, so a pre-existing Invisibility survives the whole cycle untouched
 * (granted/removed here = only ever the Glass effect). Invisibility flag itself is recomputed by
 * vanilla from all present effects (see the mixin in `LivingEntityMixin`).
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
				if (touchedGlass || countWornGlassPieces(entity) == FULL_SET) {
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
			if (instance.`is`(GlassInvisibility.GLASS_INVISIBILITY) && entity is ServerPlayer && hasFullSet(entity)) {
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
			// Removes only the Glass effect; vanilla Invisibility and other mods' effects stay.
			player.removeEffect(GlassInvisibility.GLASS_INVISIBILITY)
		}
	}
	
	private fun grantGlassInvisibility(player: ServerPlayer) {
		if (!player.hasEffect(GlassInvisibility.GLASS_INVISIBILITY)) {
			player.addEffect(
				MobEffectInstance(
					GlassInvisibility.GLASS_INVISIBILITY,
					MobEffectInstance.INFINITE_DURATION,
					0,
					true,   // ambient
					false,  // visible: no particles
					false   // showIcon: no HUD icon
				)
			)
		}
	}
}
