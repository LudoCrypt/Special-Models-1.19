package net.ludocrypt.specialmodels.impl;

import java.util.Map;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.ludocrypt.specialmodels.api.TexturedSpecialModelRenderer;
import net.minecraft.client.render.ShaderProgram;

public class SpecialModels implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Special-Models");

	@Environment(EnvType.CLIENT)
	public static final Map<SpecialModelRenderer, ShaderProgram> LOADED_SHADERS = Maps.newHashMap();

	@Override
	public void onInitialize(ModContainer mod) {
		TexturedSpecialModelRenderer.init();
	}

}
