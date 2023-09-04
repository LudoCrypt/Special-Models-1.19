package net.ludocrypt.specialmodels.impl.render;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.Viewport;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.util.math.Matrix4f;

public class StoredBuffer {

	private final int id;

	private final int x;
	private final int y;
	private final int width;
	private final int height;

	private final Matrix4f projectionMatrix;

	public StoredBuffer(int id, int x, int y, int width, int height, Matrix4f projectionMatrix) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.projectionMatrix = projectionMatrix;
	}

	public static StoredBuffer store() {
		return new StoredBuffer(GlStateManager.getBoundFramebuffer(), Viewport.getX(), Viewport.getY(), Viewport.getWidth(), Viewport.getHeight(), RenderSystem.getProjectionMatrix());
	}

	public void restore() {
		GlStateManager._glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.id);
		GlStateManager._viewport(this.x, this.y, this.width, this.height);
		RenderSystem.setProjectionMatrix(this.projectionMatrix);
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

}
