package com.example.examplemod.entity.custom;

import com.example.examplemod.entity.ModEntities;
import com.example.examplemod.item.ModItem;
import com.example.examplemod.item.custom.NormalBallItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.entity.IEntityAdditionalSpawnData;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.NetworkHooks;

public class NormalBallEntity extends ThrowableItemProjectile implements IEntityAdditionalSpawnData {
    public final Player playerIn;

    public NormalBallEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level){
        super(entityType,level);
        this.playerIn = null;
    }

    public NormalBallEntity(Level worldIn, LivingEntity throwerIn) {
        super(ModEntities.NORMAL_ENTITY.get(), throwerIn, worldIn);
        if(throwerIn instanceof Player player){
            this.playerIn = player;
        }else{
            this.playerIn = null;
        }
    }

    public NormalBallEntity(Level worldIn, double x, double y, double z) {
        super(ModEntities.NORMAL_ENTITY.get(), x, y, z, worldIn);
        this.playerIn = null;
    }



    @Override
    protected Item getDefaultItem() {
        return ModItem.WOOD_BALL.get();
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if(!this.level().isClientSide){
            ItemStack handItemStack =playerIn.getItemInHand(InteractionHand.MAIN_HAND);
            if(!(handItemStack.getItem() instanceof NormalBallItem)){
                handItemStack = playerIn.getItemInHand(InteractionHand.OFF_HAND);
                if(!(handItemStack.getItem() instanceof NormalBallItem)){
                    return;
                }
            }

            NormalBallItem normalBallItem = (NormalBallItem) handItemStack.getItem();
            //爆炸对象
            NormalBallExplosion explosion = new NormalBallExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(),normalBallItem.size,false, Explosion.BlockInteraction.KEEP);
            // 检查是否可以爆炸
            if (!EventHooks.onExplosionStart(this.level(), explosion)) {
                // 引爆
                NormalExploder.startExplosion(this.level(), explosion, this, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()),normalBallItem.size, 6f,handItemStack);
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
