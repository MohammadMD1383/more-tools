package ir.mmd.mcdev.moretools.effects

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.StringRepresentable
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.enchantment.EnchantedItemInUse
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect
import net.minecraft.world.phys.Vec3

/**
 * Entity effect used by the Lapis enchantments for ignite and thorns retaliation.
 *
 * The [Kind] selects which independent chance and magnitude range from [LapisConfig] applies.
 * Thorns-style entries use `enchanted: victim` in the enchantment JSON, so every armor piece rolls
 * its own independent chance, matching vanilla per-piece Thorns behavior.
 */
class LapisEntityEffect(val kind: Kind) : EnchantmentEntityEffect {
	enum class Kind(private val serialName: String) : StringRepresentable {
		IGNITE("ignite"),
		THORNS("thorns");
		
		override fun getSerializedName() = serialName
	}
	
	companion object {
		@JvmStatic
		val CODEC: MapCodec<LapisEntityEffect> = RecordCodecBuilder.mapCodec { instance ->
			instance.group(
				StringRepresentable.fromEnum { Kind.entries.toTypedArray() }.fieldOf("kind").forGetter(LapisEntityEffect::kind)
			).apply(instance, ::LapisEntityEffect)
		}
	}
	
	override fun apply(
		level: ServerLevel,
		enchantmentLevel: Int,
		item: EnchantedItemInUse,
		entity: Entity,
		position: Vec3
	) {
		val random = level.random
		when (kind) {
			Kind.IGNITE -> {
				if (random.nextFloat() >= LapisConfig.IGNITE_CHANCE) return
				entity.igniteForSeconds(
					LapisConfig.IGNITE_MIN_SECONDS + random.nextFloat() * (LapisConfig.IGNITE_MAX_SECONDS - LapisConfig.IGNITE_MIN_SECONDS)
				)
			}
			
			Kind.THORNS -> {
				if (random.nextFloat() >= LapisConfig.THORNS_CHANCE) return
				val damage = random.nextInt(LapisConfig.THORNS_MIN, LapisConfig.THORNS_MAX + 1).toFloat()
				item.owner()?.let { owner ->
					if (entity is LivingEntity) entity.hurtServer(level, level.damageSources().thorns(owner), damage)
				}
			}
		}
	}
	
	override fun codec(): MapCodec<out EnchantmentEntityEffect> = CODEC
	
}
