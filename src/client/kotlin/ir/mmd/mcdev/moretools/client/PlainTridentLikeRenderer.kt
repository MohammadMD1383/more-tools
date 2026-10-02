package ir.mmd.mcdev.moretools.client

import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.model.`object`.projectile.TridentModel
import net.minecraft.client.renderer.SubmitNodeCollector
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.state.ThrownTridentRenderState
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.rendertype.RenderTypes
import net.minecraft.resources.Identifier
import net.minecraft.util.Unit
import net.minecraft.world.entity.projectile.arrow.AbstractArrow
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis

/**
 * The render logic of [net.minecraft.client.renderer.entity.ThrownTridentRenderer] (rotation,
 * [TridentModel], foil pass), parameterized by texture, for custom thrown spears whose entity is
 * the mod's own [ir.mmd.mcdev.moretools.ThrownCactusSpear] (not vanilla ThrownTrident, whose
 * renderer hardcodes the trident texture).
 */
abstract class PlainTridentLikeRenderer(
	context: EntityRendererProvider.Context,
	private val texture: Identifier,
) : EntityRenderer<AbstractArrow, ThrownTridentRenderState>(context) {
	private val model = TridentModel(context.bakeLayer(ModelLayers.TRIDENT))
	
	override fun createRenderState(): ThrownTridentRenderState = ThrownTridentRenderState()
	
	override fun extractRenderState(entity: AbstractArrow, state: ThrownTridentRenderState, partialTick: Float) {
		super.extractRenderState(entity, state, partialTick)
		state.yRot = entity.getYRot(partialTick)
		state.xRot = entity.getXRot(partialTick)
		state.isFoil = entity.pickupItemStackOrigin.hasFoil()
	}
	
	override fun submit(
		state: ThrownTridentRenderState,
		poseStack: PoseStack,
		collectors: SubmitNodeCollector,
		camera: net.minecraft.client.renderer.state.level.CameraRenderState,
	) {
		poseStack.pushPose()
		poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90f))
		poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot + 90f))
		
		collectors.order(0).submitModel(model, Unit.INSTANCE, poseStack, texture, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null)
		if (state.isFoil) {
			collectors.order(1).submitModel(model, Unit.INSTANCE, poseStack, RenderTypes.entityGlint(), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null)
		}
		poseStack.popPose()
	}
}
