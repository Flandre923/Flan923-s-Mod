package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.custom.DisappearBallItem;
import com.example.examplemod.item.custom.EndPearlBallItem;
import com.example.examplemod.item.custom.EndPearlDisappearBallItem;
import com.example.examplemod.item.custom.NormalBallItem;
import com.example.examplemod.util.MaterialSize;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModItem {
    protected static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);


    public static final RegistryObject<Item> WOOD_BALL =
            ITEMS.register("wood_ball",()->new NormalBallItem(MaterialSize.WOOD.getSize()));
    public static final RegistryObject<Item> STONE_BALL =
            ITEMS.register("stone_ball",()->new NormalBallItem(MaterialSize.STONE.getSize()));
    public static final RegistryObject<Item> IRON_BALL =
            ITEMS.register("iron_ball",()->new NormalBallItem(MaterialSize.IRON.getSize()));
    public static final RegistryObject<Item> DIAMOND_BALL =
            ITEMS.register("diamond_ball",()->new NormalBallItem(MaterialSize.DIAMOND.getSize()));


    public static final RegistryObject<Item> END_PEARL_WOOD_BALL =
            ITEMS.register("end_pearl_wood_ball",()->new EndPearlBallItem(MaterialSize.WOOD.getSize()));
    public static final RegistryObject<Item> END_PEARL_STONE_BALL =
            ITEMS.register("end_pearl_stone_ball",()->new EndPearlBallItem(MaterialSize.STONE.getSize()));
    public static final RegistryObject<Item> END_PEARL_IRON_BALL =
            ITEMS.register("end_pearl_iron_ball",()->new EndPearlBallItem(MaterialSize.IRON.getSize()));
    public static final RegistryObject<Item> END_PEARL_DIAMOND_BALL =
            ITEMS.register("end_pearl_diamond_ball",()->new EndPearlBallItem(MaterialSize.DIAMOND.getSize()));

    public static final RegistryObject<Item> DISAPPEAR_WOOD_BALL =
            ITEMS.register("disappear_wood_ball",()->new DisappearBallItem(MaterialSize.WOOD.getSize()));
    public static final RegistryObject<Item> DISAPPEAR_STONE_BALL =
            ITEMS.register("disappear_stone_ball",()->new DisappearBallItem(MaterialSize.STONE.getSize()));
    public static final RegistryObject<Item> DISAPPEAR_IRON_BALL =
            ITEMS.register("disappear_iron_ball",()->new DisappearBallItem(MaterialSize.IRON.getSize()));
    public static final RegistryObject<Item> DISAPPEAR_DIAMOND_BALL =
            ITEMS.register("disappear_diamond_ball",()->new DisappearBallItem(MaterialSize.DIAMOND.getSize()));


    public static final RegistryObject<Item> END_PEARL_DISAPPEAR_WOOD_BALL =
            ITEMS.register("end_pearl_disappear_wood_ball",()->new EndPearlDisappearBallItem(MaterialSize.WOOD.getSize()));
    public static final RegistryObject<Item> END_PEARL_DISAPPEAR_STONE_BALL =
            ITEMS.register("end_pearl_disappear_stone_ball",()->new EndPearlDisappearBallItem(MaterialSize.STONE.getSize()));
    public static final RegistryObject<Item> END_PEARL_DISAPPEAR_IRON_BALL =
            ITEMS.register("end_pearl_disappear_iron_ball",()->new EndPearlDisappearBallItem(MaterialSize.IRON.getSize()));
    public static final RegistryObject<Item> END_PEARL_DISAPPEAR_DIAMOND_BALL =
            ITEMS.register("end_pearl_disappear_diamond_ball",()->new EndPearlDisappearBallItem(MaterialSize.DIAMOND.getSize()));





    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
