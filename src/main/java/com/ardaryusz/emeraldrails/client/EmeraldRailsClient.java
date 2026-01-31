package com.ardaryusz.emeraldrails.client;

import com.ardaryusz.emeraldrails.registry.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class EmeraldRailsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlock(ModBlocks.EMERALD_RAIL, BlockRenderLayer.CUTOUT);
    }
}
