package net.ludocrypt.specialmodels.impl.mixin.render;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.ludocrypt.specialmodels.impl.access.WorldChunkBuilderAccess;
import net.ludocrypt.specialmodels.impl.access.WorldRendererAccess;
import net.ludocrypt.specialmodels.impl.bridge.IrisBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

@Mixin(value = WorldRenderer.class, priority = 1050)
public abstract class WorldRendererAfterMixin implements WorldRendererAccess, WorldChunkBuilderAccess {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private Frustum capturedFrustum;

	@Shadow
	@Final
	private Vector3d capturedFrustumPosition;

	@Shadow
	private Frustum frustum;

	@Shadow
	private boolean shouldCaptureFrustum;

	@Inject(method = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private void specialModels$render$clear(MatrixStack matrices, float tickDelta, long limitTime,
			boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
			LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {

		if (IrisBridge.IRIS_LOADED) {

			if (IrisBridge.areShadersInUse()) {

				Frustum frustum;

				if (this.capturedFrustum != null) {
					frustum = this.capturedFrustum;
					frustum
						.setPosition(this.capturedFrustumPosition.x, this.capturedFrustumPosition.y,
							this.capturedFrustumPosition.z);
				} else {
					frustum = this.frustum;
				}

				if (this.shouldCaptureFrustum) {
					Matrix4f matrix4f2 = matrices.peek().getPosition();
					Vec3d vec3d = camera.getPos();
					this
						.captureFrustum(matrix4f2, positionMatrix, vec3d.x, vec3d.y, vec3d.z,
							this.capturedFrustum != null ? new Frustum(matrix4f2, positionMatrix) : frustum);
					this.shouldCaptureFrustum = false;
				}

				this.setupSpecialTerrain(camera, frustum, this.capturedFrustum != null, this.client.player.isSpectator());
				this.findSpecialChunksToRebuild(camera);

				this.render(matrices, positionMatrix, tickDelta, camera, true);
			}

		}

	}

	@Shadow
	abstract void captureFrustum(Matrix4f matrix4f, Matrix4f matrix4f2, double d, double e, double f, Frustum frustum);

}
