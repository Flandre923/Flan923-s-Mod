package com.example.examplemod.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EndPearlBallExplosion extends NormalBallExplosion{
    public EndPearlBallExplosion(Level world, @Nullable Entity entity, @Nullable DamageSource damage, @Nullable ExplosionDamageCalculator context, double x, double y, double z, float size, boolean causesFire, BlockInteraction mode) {
        super(world, entity, damage, context, x, y, z, size, causesFire, mode);
    }


}
