package net.ludocrypt.specialmodels.impl.access;

import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public interface WorldRendererAccess {

	public void render(MatrixStack matrices, Matrix4f positionMatrix, float tickDelta, Camera camera, boolean outside);

}
