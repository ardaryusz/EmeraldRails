package com.ardaryusz.emeraldrails;

import com.ardaryusz.emeraldrails.registry.ModBlocks;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;

public class EmeraldRailsTooltips {

    public static void init() {
        ItemTooltipCallback.EVENT.register(
                (stack, context, tooltipType, lines) -> {
                    if (stack.isOf(ModBlocks.EMERALD_RAIL.asItem())) {
                        lines.add(
                                Text.translatable("tooltip.emeraldrails.emerald_rail")
                        );
                    }
                }
        );
    }

}

//huge thanks to rafaelpierangeli for suggesting and even helping with this!