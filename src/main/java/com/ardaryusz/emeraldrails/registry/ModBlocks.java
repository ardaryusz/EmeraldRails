package com.ardaryusz.emeraldrails.registry;

import com.ardaryusz.emeraldrails.EmeraldRails;
import com.ardaryusz.emeraldrails.block.EmeraldRailBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
        Identifier id = new Identifier(EmeraldRails.MOD_ID, path);

        Block block = factory.apply(settings);

        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));

        return block;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE)
                .register(entries -> entries.add(EMERALD_RAIL));
    }
}