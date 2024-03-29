package net.flandre923.flansmod.common.entity;

import net.flandre923.flansmod.common.item.ModItem;
import net.flandre923.flansmod.common.item.ball.NormalBallItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.event.EventHooks;

import java.util.function.Consumer;

public class NormalBallEntity extends ThrowableItemProjectile implements IEntityWithComplexSpawn {
    public final Player playerIn;
    public final ItemStack itemStack;

    public NormalBallEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level){
        super(entityType,level);
        this.playerIn = null;
        this.itemStack = ItemStack.EMPTY;
    }

    public NormalBallEntity(Level worldIn, LivingEntity throwerIn,ItemStack itemStack) {
        super(ModEntities.NORMAL_ENTITY.get(), throwerIn, worldIn);
        if(throwerIn instanceof Player player){
            this.playerIn = player;
        }else{
            this.playerIn = null;
        }
        this.itemStack = itemStack;
    }

    public NormalBallEntity(Level worldIn, double x, double y, double z) {
        super(ModEntities.NORMAL_ENTITY.get(), x, y, z, worldIn);
        this.playerIn = null;
        this.itemStack = ItemStack.EMPTY;
    }


    @Override
    protected Item getDefaultItem() {
        Item item = itemStack.getItem();
        if(item.equals(ModItem.END_PEARL_WOOD_BALL.get())){
            return ModItem.END_PEARL_WOOD_BALL.get();
        }else if(item.equals(ModItem.DISAPPEAR_WOOD_BALL.get())){
            return ModItem.DISAPPEAR_WOOD_BALL.get();
        }else if(item.equals(ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get())){
            return ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get();
        }else{
            return ModItem.WOOD_BALL.get();
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        if(!this.level().isClientSide){
            ItemStack handItemStack =this.itemStack;
            if(!(handItemStack.getItem() instanceof NormalBallItem)){
                return;
            }
            NormalBallItem normalBallItem = (NormalBallItem) handItemStack.getItem();
            //爆炸对象
            NormalBallExplosion explosion = new NormalBallExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(),normalBallItem.size,false, Explosion.BlockInteraction.KEEP);
            // 检查是否可以爆炸
            if (!EventHooks.onExplosionStart(this.level(), explosion)) {
                // 引爆
                NormalExploder.startExplosion(this.level(), explosion, this, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()),normalBallItem.size, 6f,handItemStack,playerIn);
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

//    @Override
//    public Packet<ClientGamePacketListener> getAddEntityPacket() {
//        //创建实体时,通过NetworkHooks发送刷新包来在客户端创建实体的逻辑
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }

    @Override
    public void sendPairingData(ServerPlayer serverPlayer, Consumer<CustomPacketPayload> bundleBuilder) {
        super.sendPairingData(serverPlayer, bundleBuilder);
    }
}
