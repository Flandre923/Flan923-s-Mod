package net.flandre923.flansmod.common.util;

import net.flandre923.flansmod.common.item.ball.MaterialType;
import net.flandre923.flansmod.common.tag.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.EnumMap;
import java.util.Map;

public class NormalMaterialBallHelper {
    public static final Map<MaterialType, MaterialHandler> materialHandlers = new EnumMap<>(MaterialType.class);

    public static void init(){
        NormalMaterialBallHelper.add(MaterialType.COMMON, (world, spawnPos, dropItemstack, player) ->
                Block.popResource(world, spawnPos, dropItemstack)
        );

        NormalMaterialBallHelper.add(MaterialType.DISAPPEAR, (world, spawnPos, dropItemstack, player) -> {
            RuleTest ruleTest = new TagMatchTest(ModTags.Blocks.CAN_BALL_REPLACED);
            if (!ruleTest.test(Block.byItem(dropItemstack.getItem()).defaultBlockState(), player.getRandom())) {
                Block.popResource(world, spawnPos, dropItemstack);
            }
        });

        NormalMaterialBallHelper.add(MaterialType.END_PEARL, (world, spawnPos, dropItemstack, player) -> {
            spawnPos = player.blockPosition();
            Block.popResource(world, spawnPos, dropItemstack);
        });

        NormalMaterialBallHelper.add(MaterialType.DISAPPEAR_AND_END_PEARL, (world, spawnPos, dropItemstack, player) -> {
            spawnPos = player.blockPosition();
            RuleTest ruleTest = new TagMatchTest(ModTags.Blocks.CAN_BALL_REPLACED);
            if(!ruleTest.test(Block.byItem(dropItemstack.getItem()).defaultBlockState(),player.getRandom())) {
                Block.popResource(world, spawnPos, dropItemstack);
            }
        });

    }

    public static void add(MaterialType materialType,MaterialHandler handler){
        NormalMaterialBallHelper.materialHandlers.put(materialType,handler);
    }

    public static Map<MaterialType, MaterialHandler> getMaterialHandlers(){
        return NormalMaterialBallHelper.materialHandlers;
    }

    @FunctionalInterface
    public interface MaterialHandler{
        void handle(Level level, BlockPos spawnPos, ItemStack dropItemStack, Player player);
    }
}
