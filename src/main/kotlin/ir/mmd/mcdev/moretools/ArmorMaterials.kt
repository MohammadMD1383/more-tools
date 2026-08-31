package ir.mmd.mcdev.moretools

import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorMaterials.makeDefense

object ArmorMaterials {
	@JvmStatic val AMETHYST = ArmorMaterial(19, makeDefense(2, 5, 6, 2, 5), 28, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, ItemTags.AMETHYST_TOOL_MATERIALS, EquipmentAssets.AMETHYST)
	@JvmStatic val EMERALD  = ArmorMaterial(21, makeDefense(3, 6, 8, 3, 11), 3, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, ItemTags.EMERALD_TOOL_MATERIALS, EquipmentAssets.EMERALD)
}