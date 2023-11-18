package com.example.examplemod.block.custom;

import com.example.examplemod.screen.MyAnvilBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class MyAnvilBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("mycontainer.repair");

    public MyAnvilBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_48781_) {
        return super.getStateForPlacement(p_48781_);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            // 打开GUI
//            player.openMenu(blockState.getMenuProvider(level, pos));
            NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider(
                    (i, inventory, player1) -> new MyAnvilBlockMenu(i, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE
            ),pos);

            player.awardStat(Stats.INTERACT_WITH_ANVIL);
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (p_48785_, p_48786_, p_48787_) -> new MyAnvilBlockMenu(p_48785_, p_48786_, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE
        );
    }

}
