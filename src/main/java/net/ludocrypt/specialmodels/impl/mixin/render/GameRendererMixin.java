package net.ludocrypt.specialmodels.impl.mixin.render;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.mojang.blaze3d.shader.ShaderStage;
import com.mojang.blaze3d.vertex.VertexFormats;
import com.mojang.datafixers.util.Pair;

import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.ludocrypt.specialmodels.impl.SpecialModels;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.registry.RegistryKey;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(method = "loadShaders", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 53, shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void specialModels$loadShaders(ResourceManager manager, CallbackInfo ci, List<ShaderStage> list, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> list2) {
		SpecialModels.LOADED_SHADERS.clear();
		SpecialModelRenderer.SPECIAL_MODEL_RENDERER.getEntries().stream().map(Entry::getKey).map(RegistryKey::getValue).forEach((id) -> {

			try {
				list2.add(Pair.of(new ShaderProgram(manager, "rendertype_" + id.getNamespace() + "_" + id.getPath(), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL),
						(shader) -> SpecialModels.LOADED_SHADERS.put(SpecialModelRenderer.SPECIAL_MODEL_RENDERER.get(id), shader)));
			} catch (IOException e) {
				list2.forEach((pair) -> pair.getFirst().close());
				SpecialModels.LOGGER.error("Could not reload shader: {}", id);
				e.printStackTrace();
				throw new RuntimeException();
			}
		});
	}

}
