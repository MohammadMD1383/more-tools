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
	 * Glass Armor set bonus: while the full set is worn, armor cover must not make the (vanilla)
	 * Invisibility carrier easier to spot. Vanilla's `getVisibilityPercent` multiplies an invisible
	 * entity's visibility by `0.7 * max(getArmorCoverPercentage(), 0.1)`, so four worn pieces —
	 * even zero-defense glass — let mobs detect the player at 70% of follow range instead of the
	 * ~2 blocks a naked invisible player gets. Returning the same 0.1 clamp for the set reproduces
	 * the potion-without-armor behaviour; every other state delegates to vanilla.
	 */
	@ModifyExpressionValue(
		method = "getVisibilityPercent",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getArmorCoverPercentage()F"),
		require = 1
	)
	private float moretools$glassSetDoesNotCountAsArmorCover(float original) {
		return GlassInvisibility.armorCoverForGlass(original, (LivingEntity) (Object) this);
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
