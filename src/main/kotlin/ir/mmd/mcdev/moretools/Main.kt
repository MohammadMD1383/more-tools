package ir.mmd.mcdev.moretools

import net.fabricmc.api.ModInitializer

class Main : ModInitializer {
	override fun onInitialize() {
		Items.load()
	}
}
