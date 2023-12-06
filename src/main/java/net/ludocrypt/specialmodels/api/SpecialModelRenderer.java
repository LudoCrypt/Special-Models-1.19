package net.ludocrypt.specialmodels.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.ludocrypt.specialmodels.impl.SpecialModels;
import net.ludocrypt.specialmodels.impl.render.MutableQuad;
import net.ludocrypt.specialmodels.impl.render.Vec4b;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.render.chunk.ChunkRenderRegion;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public abstract class SpecialModelRenderer {

	public static final RegistryKey<Registry<SpecialModelRenderer>> SPECIAL_MODEL_RENDERER_KEY = RegistryKey
		.ofRegistry(new Identifier("limlib/special_model_renderer"));
	public static final Registry<SpecialModelRenderer> SPECIAL_MODEL_RENDERER = FabricRegistryBuilder
		.createSimple(SpecialModelRenderer.class, new Identifier("limlib/special_model_renderer"))
		.attribute(RegistryAttribute.SYNCED)
		.buildAndRegister();

	public final boolean performOutside;

	public SpecialModelRenderer() {
		this.performOutside = true;
	}

	public SpecialModelRenderer(boolean performOutside) {
		this.performOutside = performOutside;
	}

	@Environment(EnvType.CLIENT)
	public abstract void setup(MatrixStack matrices, Matrix4f viewMatrix, Matrix4f positionMatrix, float tickDelta,
			ShaderProgram shader, BlockPos chunkOrigin);

	@Environment(EnvType.CLIENT)
	public MutableQuad modifyQuad(ChunkRenderRegion chunkRenderRegion, BlockPos pos, BlockState state, BakedModel model,
			BakedQuad quadIn, long modelSeed, MutableQuad quad) {
		return quad;
	}

	@Environment(EnvType.CLIENT)
	public Matrix4f positionMatrix(Matrix4f in) {
		return in;
	}

	@Environment(EnvType.CLIENT)
	public Matrix4f viewMatrix(Matrix4f in) {
		return in;
	}

	@Environment(EnvType.CLIENT)
	public Vec4b appendState(ChunkRenderRegion chunkRenderRegion, BlockPos pos, BlockState state, BakedModel model,
			long modelSeed) {
		return new Vec4b(0, 0, 0, 0);
	}

	@Environment(EnvType.CLIENT)
	public ShaderProgram getShaderProgram(MatrixStack matrices, float tickDelta) {
		return SpecialModels.LOADED_SHADERS.get(this);
	}

}
