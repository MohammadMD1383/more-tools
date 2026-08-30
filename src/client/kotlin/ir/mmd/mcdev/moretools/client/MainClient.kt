package ir.mmd.mcdev.moretools.client

import ir.mmd.mcdev.moretools.Items
import net.fabricmc.api.ClientModInitializer

class MainClient : ClientModInitializer {
	@Suppress("unused")
	private fun load(noop: Any) = Unit
	
	override fun onInitializeClient() {
		load(Items)
	}
}
