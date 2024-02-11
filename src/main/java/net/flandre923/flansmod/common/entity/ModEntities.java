package net.flandre923.flansmod.common.entity;

import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.common.entity.custom.TezcatlipocaPistolBullet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    protected static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, FlansMod.MOD_ID);


    public static final Supplier<EntityType<NormalBallEntity>> NORMAL_ENTITY =
            ENTITY_TYPES.register("normal_ball_entity",
                    () ->EntityType.Builder.<NormalBallEntity>of(NormalBallEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .setTrackingRange(4)
                            .setUpdateInterval(10)
                            .setShouldReceiveVelocityUpdates(true)
                            .build(new ResourceLocation(FlansMod.MOD_ID,"normal_ball_entity").toString())
            );

    public static final Supplier<EntityType<TezcatlipocaPistolBullet>> TECATLIPOCA_PISTOR_BULLET =
            ENTITY_TYPES.register("tezcatlipoca_pistol_bullet",
                    ()->EntityType.Builder.<TezcatlipocaPistolBullet>of(TezcatlipocaPistolBullet::new,MobCategory.MISC)
                            .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                            .build(new ResourceLocation(FlansMod.MOD_ID,"tezcatlipoca_pistol_bullet").toString()));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
