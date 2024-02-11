package net.flandre923.flansmod.common.entity.custom;

import com.mojang.blaze3d.shaders.Effect;
import net.flandre923.flansmod.common.entity.ModEntities;
import net.flandre923.flansmod.common.item.ModItem;
import net.flandre923.flansmod.common.sound.ModSounds;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.Random;
import java.util.function.Supplier;

public class TezcatlipocaPistolBullet extends AbstractArrow {
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(ModItem.TEZCATLIPOCA_PISTOL);
    private BulletEffect bulletEffect;

    /**
     *  取消arrow的逻辑
     * @param pResult 三叉戟击中实体的结果
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var hitEntity = pResult.getEntity();
        var owner = this.getOwner();
        var  damageSource = this.damageSources().arrow(this,(Entity)(owner == null ? this : owner));
        if(hitEntity.level().isClientSide){
            return;
        }
        var damage = this.bulletEffect.getDamage();
        if (hitEntity.hurt(damageSource,damage)){
            if(hitEntity.getType() == EntityType.ENDERMAN){
                return;
            }
        }
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
    }



    @Override
    protected boolean tryPickup(Player pPlayer) {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        Vec3 vec3 = pResult.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vec3);
        Vec3 vec31 = vec3.normalize().scale(0.05F);
        this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
        this.inGround = true;
        this.shakeTime = 7;
        this.setCritArrow(false);
        this.setPierceLevel((byte)0);
        this.setShotFromCrossbow(false);
        this.discard();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        int bulletEffect1 = pCompound.getInt("BulletEffect");
        this.bulletEffect = BulletEffect.values()[bulletEffect1];
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("BulletEffect", this.bulletEffect.ordinal());
    }

    @Override
    protected float getWaterInertia() {
        return 0.99f;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        return true;
    }

    public TezcatlipocaPistolBullet(EntityType<? extends TezcatlipocaPistolBullet> pEntityType, Level pLevel) {
        super(pEntityType, pLevel,DEFAULT_ARROW_STACK);
//        this.handItemStack = DEFAULT_ARROW_STACK;
    }

    public TezcatlipocaPistolBullet( double pX, double pY, double pZ, Level pLevel,ItemStack handItemStack,BulletEffect effect) {
        this(ModEntities.TECATLIPOCA_PISTOR_BULLET.get(),pLevel);
        this.setPos(pX, pY, pZ);
//        this.handItemStack = handItemStack.copy();
        this.bulletEffect = effect;
    }

    public TezcatlipocaPistolBullet(LivingEntity pShooter, Level pLevel, ItemStack handItemStack,BulletEffect effect) {
        this(pShooter.getX(), pShooter.getEyeY() - 0.1F, pShooter.getZ(), pLevel,handItemStack,effect);
        this.setOwner(pShooter);
    }


    public static BulletEffect generateEffect(RandomSource random) {
        double roll = random.nextDouble() * 100.0;
        for (BulletEffect effect : BulletEffect.values()) {
            if (roll < effect.getProbability()) {
                return effect;
            }
            roll -= effect.getProbability();
        }
        return BulletEffect.DEFAULT; // 如果没有效果，返回null
    }





    public enum BulletEffect{
        INSTANT_KILL(1.0,99999, ModSounds.ATTACK1),
        DAMAGE_10(9.0,20,  ModSounds.ATTACK2),
        DAMAGE_8(20.0,16,  ModSounds.ATTACK3),
        DAMAGE_5(50.0,10,  ModSounds.ATTACK7),
        DAMAGE_1(10.0,2,  ModSounds.ATTACK11),
        DAMAGE_2(10.0,4,  ModSounds.ATTACK13),
        DEFAULT(0.0, 1,  ModSounds.ATTACK13); // 默认效果，概率设置为0，不会随机选择

        private final double probability;
        private final float damage;
        private final Supplier<SoundEvent>  soundEffect;

        BulletEffect(double probability,float damage,Supplier<SoundEvent> soundEffect){
            this.probability = probability;
            this.damage = damage;
            this.soundEffect = soundEffect;
        }

        public double getProbability() {
            return probability;
        }
        public Supplier<SoundEvent> getSoundEffect() {
            return soundEffect;
        }
        public float getDamage(){return damage;}
    }
}
