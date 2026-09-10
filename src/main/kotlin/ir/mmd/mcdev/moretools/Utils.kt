package ir.mmd.mcdev.moretools

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.level.block.Block

fun id(id: String): Identifier = Identifier.fromNamespaceAndPath(Constants.MOD_ID, id)

fun modifyCreativeTab(tab: ResourceKey<CreativeModeTab>, configure: FabricCreativeModeTabOutput.() -> Unit) {
	CreativeModeTabEvents.modifyOutputEvent(tab).register(configure)
}

val Block.resourceKey get() = BuiltInRegistries.BLOCK.getResourceKey(this).get()