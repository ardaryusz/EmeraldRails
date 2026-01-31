package com.ardaryusz.emeraldrails.registry;

import com.ardaryusz.emeraldrails.EmeraldRails;
import com.ardaryusz.emeraldrails.block.EmeraldRailBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public final class ModBlocks {
    private ModBlocks() {}

    public static final Block EMERALD_RAIL = registerBlock(
            "emerald_rail",
            EmeraldRailBlock::new,
            AbstractBlock.Settings.copy(Blocks.POWERED_RAIL)
    );

    private static Block registerBlock(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Identifier id = Identifier.of(EmeraldRails.MOD_ID, path);
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, id);

        Block block = Blocks.register(key, factory, settings);
        Items.register(block); // registers the BlockItem with the same id

        return block;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE)
                .register(entries -> entries.add(EMERALD_RAIL));
    }
}
