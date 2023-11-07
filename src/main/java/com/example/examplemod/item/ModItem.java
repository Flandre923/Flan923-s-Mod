package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.custom.WoodBallItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModItem {
    protected static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);


    public static final RegistryObject<Item> WOOD_BALL =
            ITEMS.register("wood_ball",()->new WoodBallItem(2));

    public static final RegistryObject<Item> STONE_BALL =
            ITEMS.register("stone_ball",()->new WoodBallItem(4));
    public static final RegistryObject<Item> IRON_BALL =
            ITEMS.register("iron_ball",()->new WoodBallItem(6));

    public static final RegistryObject<Item> DIAMOND_BALL =
            ITEMS.register("diamond_ball",()->new WoodBallItem(8));




    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
