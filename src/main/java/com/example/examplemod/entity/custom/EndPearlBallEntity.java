package com.example.examplemod.entity.custom;

import com.example.examplemod.item.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

public class EndPearlBallEntity extends NormalBallEntity{



    public EndPearlBallEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public EndPearlBallEntity(Level worldIn, LivingEntity throwerIn, int size) {
        super(worldIn, throwerIn, size);
    }

    public EndPearlBallEntity(Level worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItem.END_PEARL_WOOD_BALL.get();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if(!this.level().isClientSide){
            // 爆炸对象
            EndPearlBallExplosion explosion = new EndPearlBallExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(), this.size, false, Explosion.BlockInteraction.KEEP);
            // 检查是否可以爆炸
            if (!EventHooks.onExplosionStart(this.level(), explosion)) {
                // 引爆
                EndPearlExploder.startExplosion(this.level(), explosion, this, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), this.size, 6f,this.playerIn);
            }
        }

        if(!this.level().isClientSide){
            // 广播爆炸声音在服务器端
            this.level().broadcastEntityEvent(this,(byte)3);
            // 销毁实体
            this.discard();
        }
    }




}
