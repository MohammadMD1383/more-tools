package ir.mmd.mcdev.moretools

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType

object EntityIds {
	@JvmStatic val THROWN_CACTUS_SPEAR: ResourceKey<EntityType<*>> =
		ResourceKey.create(Registries.ENTITY_TYPE, id("thrown_cactus_spear"))
}
