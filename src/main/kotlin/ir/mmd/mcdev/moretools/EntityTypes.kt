package ir.mmd.mcdev.moretools

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

object EntityTypes {
	@JvmStatic val THROWN_CACTUS_SPEAR: EntityType<ThrownCactusSpear> = create()
	
	private fun create(): EntityType<ThrownCactusSpear> =
		Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			EntityIds.THROWN_CACTUS_SPEAR,
			EntityType.Builder.of(::ThrownCactusSpear, MobCategory.MISC)
				.noLootTable()
				.sized(0.5f, 0.5f)
				.clientTrackingRange(4)
				.updateInterval(20)
				.build(EntityIds.THROWN_CACTUS_SPEAR)
		)
	
	/** Called from [Main.onInitialize]; the registration itself happens in [THROWN_CACTUS_SPEAR]. */
	fun register() { /* no-op: fields initialize on first access */ }
}
