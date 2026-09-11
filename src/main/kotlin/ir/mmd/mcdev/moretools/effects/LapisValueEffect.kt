package ir.mmd.mcdev.moretools.effects

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.util.RandomSource
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect

/**
 * Value effect used by the Lapis enchantments for every numeric behavior.
 *
 * The [Kind] selects which independent chance and magnitude range from [LapisConfig] applies.
 * On a successful roll the rolled magnitude is added to the incoming value; on a failed roll the
 * value passes through untouched, so each effect only ever adds its own contribution.
 */
class LapisValueEffect(val kind: Kind) : EnchantmentValueEffect {
	enum class Kind(private val serialName: String) : StringRepresentable {
		DAMAGE("damage"),
		KNOCKBACK("knockback"),
		PROTECTION("protection"),
		FALL_PROTECTION("fall_protection");
		
		override fun getSerializedName() = serialName
	}
	
	companion object {
		@JvmStatic
		val CODEC: MapCodec<LapisValueEffect> = RecordCodecBuilder.mapCodec { instance ->
			instance.group(
				StringRepresentable.fromEnum { Kind.entries.toTypedArray() }.fieldOf("kind").forGetter(LapisValueEffect::kind)
			).apply(instance, ::LapisValueEffect)
		}
	}
	
	override fun process(level: Int, random: RandomSource, value: Float): Float {
		return when (kind) {
			Kind.DAMAGE -> onRoll(random, LapisConfig.DAMAGE_CHANCE, value, level) {
				LapisConfig.DAMAGE_MIN + random.nextFloat() * (LapisConfig.DAMAGE_MAX - LapisConfig.DAMAGE_MIN)
			}
			
			Kind.KNOCKBACK -> onRoll(random, LapisConfig.KNOCKBACK_CHANCE, value, level) {
				LapisConfig.KNOCKBACK_MIN + random.nextFloat() * (LapisConfig.KNOCKBACK_MAX - LapisConfig.KNOCKBACK_MIN)
			}
			
			Kind.PROTECTION -> onRoll(random, LapisConfig.PROTECTION_CHANCE, value, level) {
				random.nextInt(LapisConfig.PROTECTION_MIN, LapisConfig.PROTECTION_MAX + 1).toFloat()
			}
			
			Kind.FALL_PROTECTION -> onRoll(random, LapisConfig.FALL_PROTECTION_CHANCE, value, level) {
				random.nextInt(LapisConfig.FALL_PROTECTION_MIN, LapisConfig.FALL_PROTECTION_MAX + 1).toFloat()
			}
		}
	}
	
	private inline fun onRoll(random: RandomSource, chance: Float, value: Float, level: Int, magnitude: () -> Float) =
		if (random.nextFloat() < chance) value + magnitude() * level else value
	
	override fun codec(): MapCodec<out EnchantmentValueEffect> = CODEC
}
