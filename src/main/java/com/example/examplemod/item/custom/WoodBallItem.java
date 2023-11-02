package com.example.examplemod.item.custom;

import com.example.examplemod.entity.custom.WoodBallEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.SleepingLocationCheckEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodBallItem extends SnowballItem {
    public WoodBallItem() {
        super((new Properties()).stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        ItemStack itemStack =playerIn.getItemInHand(handIn);
        if(!playerIn.getAbilities().instabuild){
            itemStack.shrink(1);
        }
        //播放声音
        // TODO: 2023/11/1
        if(!level.isClientSide()){
            // 生成实体
            WoodBallEntity entity = new WoodBallEntity(level,playerIn);
            entity.setItem(itemStack);
            entity.shootFromRotation(playerIn,playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0f);
            level.addFreshEntity(entity);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack,level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
    }
}
