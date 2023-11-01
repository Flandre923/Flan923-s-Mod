package com.example.examplemod.entity;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.custom.WoodBallEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModEntities {
    protected static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ExampleMod.MODID);


    public static final RegistryObject<EntityType<WoodBallEntity>> WOOD_ENTITY =
            ENTITY_TYPES.register("wood_ball_entity",
                    () -> EntityType.Builder.<WoodBallEntity>of(WoodBallEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .setTrackingRange(4)
                    .setUpdateInterval(10)
                    .setCustomClientFactory((spawnEntity, world) -> new WoodBallEntity(ModEntities.WOOD_ENTITY.get(), world))
                    .setShouldReceiveVelocityUpdates(true)
                            .build(new ResourceLocation(ExampleMod.MODID,"wood_ball_entity").toString())
    );
}
