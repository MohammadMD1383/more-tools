package ir.mmd.mcdev.moretools

import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.equipment.ArmorMaterial
import net.minecraft.world.item.equipment.ArmorMaterials.makeDefense

object ArmorMaterials {
	@JvmStatic val AMETHYST = ArmorMaterial(19, makeDefense(2, 5, 6, 2, 5), 30, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, ItemTags.AMETHYST_TOOL_MATERIALS, EquipmentAssets.AMETHYST)
}