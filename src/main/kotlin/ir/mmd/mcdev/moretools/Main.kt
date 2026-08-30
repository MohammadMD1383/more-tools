package ir.mmd.mcdev.moretools

import ir.mmd.mcdev.moretools.toolsets.AmethystToolSet
import net.fabricmc.api.ModInitializer

class Main : ModInitializer {
	override fun onInitialize() {
		AmethystToolSet.load()
		AmethystToolSet.Items.AMETHYST_SWORD
	}
}
