package com.example.examplemod.item.custom;

import com.example.examplemod.entity.custom.NormalBallEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NormalBallItem extends SnowballItem {
    public  final int size; // 爆炸范围

    public NormalBallItem(int size) {
        super((new Properties()).stacksTo(16));
        this.size = size;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        ItemStack itemStack =playerIn.getItemInHand(handIn);
        if(!playerIn.getAbilities().instabuild){
            itemStack.shrink(1);
        }
        //播放声音
        if(!level.isClientSide()){
            // 生成实体
            this.generateEntity(level,playerIn,itemStack);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack,level.isClientSide());
    }

    public void generateEntity(Level level,Player playerIn,ItemStack itemStack){
        NormalBallEntity entity = new NormalBallEntity(level,playerIn);
        entity.setItem(itemStack);
        entity.shootFromRotation(playerIn,playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0f);
        level.addFreshEntity(entity);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }
}
