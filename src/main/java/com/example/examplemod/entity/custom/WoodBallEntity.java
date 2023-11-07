package com.example.examplemod.entity.custom;

import com.example.examplemod.entity.ModEntities;
import com.example.examplemod.item.ModItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.entity.IEntityAdditionalSpawnData;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.NetworkHooks;

public class WoodBallEntity extends ThrowableItemProjectile implements IEntityAdditionalSpawnData {

    public final int size;// 爆炸范围
    public WoodBallEntity(EntityType<? extends ThrowableItemProjectile> entityType,Level level){
        super(entityType,level);
        this.size = 0;
    }

    public WoodBallEntity(Level worldIn, LivingEntity throwerIn,int size) {
        super(ModEntities.WOOD_ENTITY.get(), throwerIn, worldIn);
        this.size = size;
    }

    public WoodBallEntity(Level worldIn, double x, double y, double z) {
        super(ModEntities.WOOD_ENTITY.get(), x, y, z, worldIn);
        this.size = 6;
    }



    @Override
    protected Item getDefaultItem() {
        return ModItem.WOOD_BALL.get();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if(!this.level().isClientSide){
            // 爆炸对象
            WoodBallExplosion explosion = new WoodBallExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(), this.size, false, Explosion.BlockInteraction.KEEP);
            // 检查是否可以爆炸
            if (!EventHooks.onExplosionStart(this.level(), explosion)) {
                // 引爆
                Exploder.startExplosion(this.level(), explosion, this, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), this.size, 6f);
            }
        }

        if(!this.level().isClientSide){
            // 广播爆炸声音在服务器端
            this.level().broadcastEntityEvent(this,(byte)3);
            // 销毁实体
            this.discard();
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        // 创造实体时,将木球的Item写入字节流
        buffer.writeItem(this.getItemRaw());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        // 客户端读取时,从字节流中解析出Item来设置木球的物品
        this.setItem(additionalData.readItem());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        //创建实体时,通过NetworkHooks发送刷新包来在客户端创建实体的逻辑
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
