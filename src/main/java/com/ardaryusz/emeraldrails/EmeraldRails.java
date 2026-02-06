package com.ardaryusz.emeraldrails;

import com.ardaryusz.emeraldrails.registry.ModBlocks;
import net.fabricmc.api.ModInitializer;

public class EmeraldRails implements ModInitializer {
	public static final String MOD_ID = "emeraldrails";

	@Override
	public void onInitialize() {
		ModBlocks.init();

	}
}
