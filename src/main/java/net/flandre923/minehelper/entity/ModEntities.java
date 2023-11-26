package net.flandre923.minehelper.entity;

import net.flandre923.minehelper.MineHelper;
import net.flandre923.minehelper.entity.custom.NormalBallEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    protected static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MineHelper.MODID);


    public static final Supplier<EntityType<NormalBallEntity>> NORMAL_ENTITY =
            ENTITY_TYPES.register("normal_ball_entity",
                    () ->EntityType.Builder.<NormalBallEntity>of(NormalBallEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .setTrackingRange(4)
                            .setUpdateInterval(10)
                            .setCustomClientFactory((spawnEntity, world) -> new NormalBallEntity(ModEntities.NORMAL_ENTITY.get(), world))
                            .setShouldReceiveVelocityUpdates(true)
                            .build(new ResourceLocation(MineHelper.MODID,"normal_ball_entity").toString())
            );


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
