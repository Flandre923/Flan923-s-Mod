package net.flandre923.flansmod.common.item.cusotm;

import com.mojang.blaze3d.shaders.Effect;
import net.flandre923.flansmod.common.entity.custom.TezcatlipocaPistolBullet;
import net.flandre923.flansmod.common.sound.ModSounds;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TezcatlipocaPistol extends Item {
    private static final RandomSource random = RandomSource.create();
    private int shotsFired;
    private  final float maxDeviation;

    public TezcatlipocaPistol(Properties pProperties) {
        super(pProperties);
        this.shotsFired = 0;
        this.maxDeviation = 0.1f;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        var handItemStack = pPlayer.getItemInHand(pUsedHand);
        this.shoot(pLevel,pPlayer,handItemStack);
        return InteractionResultHolder.consume(handItemStack);

    }

    // 射击，生成子弹效果和散射方向
    public void shoot(Level level,Player playerIn,ItemStack itemStack) {
        if (shotsFired >= 10) {
            playerIn.getCooldowns().addCooldown(this,5*20);
            shotsFired = 0;
        }
        shotsFired++;
        TezcatlipocaPistolBullet.BulletEffect effect = TezcatlipocaPistolBullet.generateEffect(TezcatlipocaPistol.random);
        if (!level.isClientSide){
            TezcatlipocaPistolBullet bullet = new TezcatlipocaPistolBullet(playerIn,level,itemStack,effect);
            bullet.shootFromRotation(playerIn,this.scatter(playerIn.getXRot(),maxDeviation),this.scatter(playerIn.getYRot(),maxDeviation),0f,20f,1f);
            level.addFreshEntity(bullet);
        }
        playerIn.playSound(effect.getSoundEffect().get(),1f,1f);
        playerIn.getCooldowns().addCooldown(this,20);
    }
    public  float scatter(float angle,float maxDeviation){
        return angle + (random.nextFloat() * 2 - 1) * maxDeviation;
    }


}
