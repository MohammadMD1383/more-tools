package ir.mmd.mcdev.moretools.effects

/**
 * Central balancing values for all Lapis enchantment effects.
 *
 * Each effect has its own independent probability and magnitude range; there is no shared roll.
 * Chances are `0..1` and evaluated with [net.minecraft.util.RandomSource.nextFloat].
 *
 * Magnitude ranges are referenced against the corresponding vanilla enchantment at level 1:
 * - DAMAGE          → Sharpness extra damage (~1.0 for I, ~3.25 for V → capped at 3.0)
 * - KNOCKBACK       → Knockback I..II (1..2 knockback levels)
 * - IGNITE          → Fire Aspect I..II (4s..8s burn)
 * - ENTITY_DROPS    → Looting I..III (extra mob-drop rolls)
 * - BLOCK_DROPS     → Fortune III (extra block drops)
 * - PROTECTION      → Protection I..V (1..5 armor points, 4% each)
 * - FALL_PROTECTION → Feather Falling I..IV (3..12 armor points)
 * - THORNS          → Thorns (1..4 retaliation damage, vanilla per-piece chance 15%)
 */
object LapisConfig {
	//@formatter:off
	const val DAMAGE_CHANCE          = 0.25F
	const val DAMAGE_MIN             = 1.0F
	const val DAMAGE_MAX             = 3.0F
	
	const val KNOCKBACK_CHANCE       = 0.20F
	const val KNOCKBACK_MIN          = 1.0F
	const val KNOCKBACK_MAX          = 2.0F
	
	const val IGNITE_CHANCE          = 0.10F
	const val IGNITE_MIN_SECONDS     = 4F
	const val IGNITE_MAX_SECONDS     = 8F
	
	const val ENTITY_DROPS_CHANCE    = 0.15F
	const val ENTITY_DROPS_MIN       = 1
	const val ENTITY_DROPS_MAX       = 3
	
	const val BLOCK_DROPS_CHANCE     = 0.20F
	const val BLOCK_DROPS_MIN        = 1
	const val BLOCK_DROPS_MAX        = 4
	
	const val PROTECTION_CHANCE      = 0.20F
	const val PROTECTION_MIN         = 1
	const val PROTECTION_MAX         = 5
	
	const val FALL_PROTECTION_CHANCE = 0.30F
	const val FALL_PROTECTION_MIN    = 3
	const val FALL_PROTECTION_MAX    = 12
	
	const val THORNS_CHANCE          = 0.15F
	const val THORNS_MIN             = 1
	const val THORNS_MAX             = 4
	//@formatter:on
}
