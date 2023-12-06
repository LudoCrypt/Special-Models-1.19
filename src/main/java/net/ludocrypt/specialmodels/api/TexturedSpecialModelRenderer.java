package net.ludocrypt.specialmodels.api;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.registry.Registry;

public class TexturedSpecialModelRenderer extends SpecialModelRenderer {

	public static final SpecialModelRenderer TEXTURED = Registry
		.register(SpecialModelRenderer.SPECIAL_MODEL_RENDERER, new Identifier("specialmodels", "textured"),
			new TexturedSpecialModelRenderer());

	public TexturedSpecialModelRenderer() {
		super();
	}

	public TexturedSpecialModelRenderer(boolean performOutside) {
		super(performOutside);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void setup(MatrixStack matrices, Matrix4f viewMatrix, Matrix4f positionMatrix, float tickDelta,
			ShaderProgram shader, BlockPos origin) {
		RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
	}

	public static void init() {
	}

}
