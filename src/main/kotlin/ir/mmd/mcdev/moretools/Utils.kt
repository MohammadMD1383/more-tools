package ir.mmd.mcdev.moretools

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab

fun id(id: String): Identifier = Identifier.fromNamespaceAndPath(Constants.MOD_ID, id)

fun modifyCreativeTab(tab: ResourceKey<CreativeModeTab>, configure: FabricCreativeModeTabOutput.() -> Unit) {
	CreativeModeTabEvents.modifyOutputEvent(tab).register(configure)
}