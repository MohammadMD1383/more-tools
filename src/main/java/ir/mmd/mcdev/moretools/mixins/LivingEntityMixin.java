package ir.mmd.mcdev.moretools.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import ir.mmd.mcdev.moretools.ItemTags;
import ir.mmd.mcdev.moretools.effects.GlassInvisibility;
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
	/**
	 * The Glass Invisibility effect must behave exactly like vanilla Invisibility. Vanilla's only
	 * invisibility hook is the argument of `setInvisible(hasEffect(MobEffects.INVISIBILITY))` inside
	 * `updateInvisibilityStatus` (there is no public API for extending it), so we widen that single
	 * argument. Nothing else in vanilla invisibility is touched: coexistence with vanilla
	 * Invisibility and any other source falls out naturally because the flag is re-evaluated
	 * from all effects whenever any effect is added/removed/updated.
	 */
	@ModifyExpressionValue(
		method = "updateInvisibilityStatus",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z"),
		require = 1
	)
	private boolean moretools$glassInvisibilityMakesInvisible(boolean original) {
		return GlassInvisibility.isInvisibilitySource(original, (LivingEntity) (Object) this);
	}
	
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
