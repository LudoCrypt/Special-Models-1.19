package net.ludocrypt.specialmodels.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.ludocrypt.specialmodels.impl.render.MutableQuad;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public abstract class SpecialModelRenderer {

	public static final RegistryKey<Registry<SpecialModelRenderer>> SPECIAL_MODEL_RENDERER_KEY = RegistryKey.ofRegistry(new Identifier("limlib/special_model_renderer"));
	public static final Registry<SpecialModelRenderer> SPECIAL_MODEL_RENDERER = FabricRegistryBuilder.createSimple(SpecialModelRenderer.class, new Identifier("limlib/special_model_renderer"))
			.attribute(RegistryAttribute.SYNCED).buildAndRegister();

	@Environment(EnvType.CLIENT)
	public abstract void setup(MatrixStack matrices, Matrix4f viewMatrix, Matrix4f positionMatrix, float tickDelta, ShaderProgram shader);

	@Environment(EnvType.CLIENT)
	public MutableQuad modifyQuad(MutableQuad quad) {
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

}
