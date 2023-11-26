package net.flandre923.minehelper.tag;

import net.flandre923.minehelper.MineHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CAN_BALL_REPLACED =
            tag("can_ball_replaced");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(MineHelper.MODID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

}
