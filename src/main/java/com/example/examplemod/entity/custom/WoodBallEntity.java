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

    public WoodBallEntity(EntityType<? extends ThrowableItemProjectile> entityType,Level level){
        super(entityType,level);
    }

    public WoodBallEntity(Level worldIn, LivingEntity throwerIn) {
        super(ModEntities.WOOD_ENTITY.get(), throwerIn, worldIn);
    }

    public WoodBallEntity(Level worldIn, double x, double y, double z) {
        super(ModEntities.WOOD_ENTITY.get(), x, y, z, worldIn);
    }



    @Override
    protected Item getDefaultItem() {
        return ModItem.WOOD_BALL.get();
    }

    @Override
    protected void onHit(HitResult p_37260_) {
        if(!this.level().isClientSide){
            WoodBallExplosion explosion = new WoodBallExplosion(this.level(), this, null, null, this.getX(), this.getY(), this.getZ(), 6f, false, Explosion.BlockInteraction.KEEP);
            if (!EventHooks.onExplosionStart(this.level(), explosion)) {
                Exploder.startExplosion(this.level(), explosion, this, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), 6f, 6f);
            }
        }

        //  ?
        if(!this.level().isClientSide){
            this.level().broadcastEntityEvent(this,(byte)3);
            this.discard();
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeItem(this.getItemRaw());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.setItem(additionalData.readItem());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
