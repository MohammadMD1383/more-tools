package ir.mmd.mcdev.moretools

import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorMaterials.makeDefense

object ArmorMaterials {
	//@formatter:off
	@JvmStatic val AMETHYST = ArmorMaterial(19, makeDefense(2, 5, 6, 2,  5), 28, SoundEvents.ARMOR_EQUIP_DIAMOND,   0f, 0.00f, ItemTags.AMETHYST_TOOL_MATERIALS, EquipmentAssets.AMETHYST)
	@JvmStatic val EMERALD  = ArmorMaterial(21, makeDefense(3, 6, 8, 3, 11),  3, SoundEvents.ARMOR_EQUIP_DIAMOND,   0f, 0.00f, ItemTags.EMERALD_TOOL_MATERIALS,  EquipmentAssets.EMERALD)
	@JvmStatic val OBSIDIAN = ArmorMaterial(25, makeDefense(3, 6, 8, 3, 11),  1, SoundEvents.ARMOR_EQUIP_NETHERITE, 1f, 0.15f, ItemTags.OBSIDIAN_TOOL_MATERIALS, EquipmentAssets.OBSIDIAN)
	@JvmStatic val QUARTZ   = ArmorMaterial(29, makeDefense(1, 3, 5, 2,  7), 10, SoundEvents.ARMOR_EQUIP_DIAMOND,   0f, 0.00f, ItemTags.QUARTZ_TOOL_MATERIALS,   EquipmentAssets.QUARTZ)
	//@formatter:on
}
