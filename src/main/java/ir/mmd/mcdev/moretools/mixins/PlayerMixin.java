package ir.mmd.mcdev.moretools.mixins;

import ir.mmd.mcdev.moretools.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class PlayerMixin {
	@ModifyVariable(
		method = "giveExperiencePoints",
		at = @At("HEAD"),
		argsOnly = true,
		name = "i"
	)
	private int giveExperiencePoints(int i) {
		final var player = (Player) (Object) this;
		var multiplier = 1.0F;
		
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (player.getItemBySlot(slot).is(ItemTags.getEMERALD_ITEMS_FOR_XP()))
				multiplier += 0.2F;
		}
		
		return Math.min((int) (i * multiplier), Integer.MAX_VALUE);
	}
}
