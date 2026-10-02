package ir.mmd.mcdev.moretools.client

import ir.mmd.mcdev.moretools.ThrownCactusSpear
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.Identifier

/**
 * [net.minecraft.client.renderer.entity.ThrownTridentRenderer] with the cactus texture — vanilla
 * hardcodes `textures/entity/trident/trident.png` on its baked [net.minecraft.client.model.object.projectile.TridentModel],
 * so the thrown entity gets its own renderer instead of reusing the trident's.
 *
 * The texture is `assets/more-tools/textures/entity/cactus_spear/cactus_spear.png` (64×32, the
 * trident atlas layout — [net.minecraft.client.model.geom.ModelLayers.TRIDENT] is reused so the
 * geometry matches vanilla exactly).
 */
class ThrownCactusSpearRenderer(context: EntityRendererProvider.Context) :
	PlainTridentLikeRenderer(context, TEXTURE) {
	
	companion object {
		val TEXTURE: Identifier = Identifier.fromNamespaceAndPath("more-tools", "textures/entity/cactus_spear/cactus_spear.png")
	}
}
