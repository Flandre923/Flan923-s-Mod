package com.example.examplemod.item.custom;

import com.example.examplemod.entity.custom.NormalBallEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EndPearlBallItem extends NormalBallItem{
    public EndPearlBallItem(int size) {
        super(size);
    }

    @Override
    public void generateEntity(Level level, Player playerIn, ItemStack itemStack) {
        super.generateEntity(level, playerIn, itemStack);
    }
}
