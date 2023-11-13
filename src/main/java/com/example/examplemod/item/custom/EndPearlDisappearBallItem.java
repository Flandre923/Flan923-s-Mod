package com.example.examplemod.item.custom;

import com.example.examplemod.entity.custom.DisappearBallEntity;
import com.example.examplemod.entity.custom.EndPearlDisappearBallEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EndPearlDisappearBallItem extends NormalBallItem{
    public EndPearlDisappearBallItem(int size) {
        super(size);
    }

    @Override
    public void generateEntity(Level level,Player playerIn,ItemStack itemStack){
        EndPearlDisappearBallEntity entity = new EndPearlDisappearBallEntity(level,playerIn);
        entity.setItem(itemStack);
        entity.shootFromRotation(playerIn,playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0f);
        level.addFreshEntity(entity);
    }
}
