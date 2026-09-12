package ir.mmd.mcdev.moretools.effects

import ir.mmd.mcdev.moretools.id
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries


object Effects {
	//@formatter:off
	@JvmStatic val LAPIS_VALUE_EFFECT  = Registry.register(BuiltInRegistries.ENCHANTMENT_VALUE_EFFECT_TYPE,  id("lapis_value"),  LapisValueEffect.CODEC)
	@JvmStatic val LAPIS_ENTITY_EFFECT = Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, id("lapis_entity"), LapisEntityEffect.CODEC)
	//@formatter:on
	
	fun register() {
		GlassInvisibility.register()
	}
}
