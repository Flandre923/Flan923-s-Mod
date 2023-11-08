package com.example.examplemod.item.custom;

import com.example.examplemod.entity.custom.DisappearBallEntity;
import com.example.examplemod.entity.custom.EndPearlBallEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DisappearBallItem extends NormalBallItem{
    public DisappearBallItem(int size) {
        super(size);
    }

    @Override
    public void generateEntity(Level level,Player playerIn,ItemStack itemStack){
        DisappearBallEntity entity = new DisappearBallEntity(level,playerIn,this.size);
        entity.setItem(itemStack);
        entity.shootFromRotation(playerIn,playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0f);
        level.addFreshEntity(entity);
    }
}
