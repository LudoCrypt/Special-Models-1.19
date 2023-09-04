package net.ludocrypt.specialmodels.impl.mixin.render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.blaze3d.vertex.VertexBuffer;

@Mixin(VertexBuffer.class)
public interface VertexBufferAccessor {

	@Accessor
	int getIndexCount();

}
