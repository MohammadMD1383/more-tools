package ir.mmd.mcdev.moretools.mixins;

import ir.mmd.mcdev.moretools.ItemTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(
		method = "getDamageAfterArmorAbsorb",
		at = @At("RETURN"),
		cancellable = true
	)
	private void reduceObsidianFireDamage(
		DamageSource damageSource,
		float damage,
		CallbackInfoReturnable<Float> cir
	) {
		if (!damageSource.is(DamageTypeTags.IS_FIRE)) {
			return;
		}
		
		final var entity = (LivingEntity) (Object) this;
		var obsidianPieces = 0;
		
		for (EquipmentSlot slot : EquipmentSlot.VALUES) {
			if (entity.getItemBySlot(slot).is(ItemTags.getOBSIDIAN_ARMOR_FOR_FIRE())) {
				obsidianPieces++;
			}
		}
		
		if (obsidianPieces == 0) {
			return;
		}
		
		var ratio = 1F - obsidianPieces * 0.1F;
		var reducedDamage = cir.getReturnValue() * ratio;
		cir.setReturnValue(reducedDamage);
	}
}
