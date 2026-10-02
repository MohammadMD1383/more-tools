package ir.mmd.mcdev.moretools.client

import ir.mmd.mcdev.moretools.EntityTypes
import ir.mmd.mcdev.moretools.Items
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.entity.EntityRenderers

class MainClient : ClientModInitializer {
	@Suppress("unused")
	private fun load(noop: Any) = Unit
	
	override fun onInitializeClient() {
		load(Items)
		EntityRenderers.register(EntityTypes.THROWN_CACTUS_SPEAR, ::ThrownCactusSpearRenderer)
	}
}
