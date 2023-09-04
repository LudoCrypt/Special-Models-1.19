package net.ludocrypt.specialmodels.impl.chunk;

import java.util.Map;
import java.util.stream.Collectors;

import com.mojang.blaze3d.vertex.BufferBuilder;

import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.minecraft.client.render.RenderLayer;

public class SpecialBufferBuilderStorage {
	private final Map<SpecialModelRenderer, BufferBuilder> specialModelBuffers = SpecialModelRenderer.SPECIAL_MODEL_RENDERER.getEntries().stream()
			.collect(Collectors.toMap(entry -> entry.getValue(), entry -> new BufferBuilder(RenderLayer.getSolid().getExpectedBufferSize())));

	public BufferBuilder get(SpecialModelRenderer renderer) {
		return this.specialModelBuffers.get(renderer);
	}

	public void clear() {
		this.specialModelBuffers.values().forEach(BufferBuilder::clear);
	}

	public void reset() {
		this.specialModelBuffers.values().forEach(BufferBuilder::discard);
	}

	public Map<SpecialModelRenderer, BufferBuilder> getSpecialModelBuffers() {
		return specialModelBuffers;
	}

}
