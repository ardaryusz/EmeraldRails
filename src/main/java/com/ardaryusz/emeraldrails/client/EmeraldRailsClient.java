package com.ardaryusz.emeraldrails.client;

import com.ardaryusz.emeraldrails.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class EmeraldRailsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EMERALD_RAIL, RenderLayer.getCutout());
    }
}