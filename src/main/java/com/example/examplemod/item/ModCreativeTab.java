package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);


    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ModItem.WOOD_BALL.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItem.WOOD_BALL.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(ModItem.STONE_BALL.get());
                output.accept(ModItem.IRON_BALL.get());
                output.accept(ModItem.DIAMOND_BALL.get());
                output.accept(ModItem.END_PEARL_WOOD_BALL.get());
                output.accept(ModItem.END_PEARL_STONE_BALL.get());
                output.accept(ModItem.END_PEARL_IRON_BALL.get());
                output.accept(ModItem.END_PEARL_DIAMOND_BALL.get());
                output.accept(ModItem.DISAPPEAR_WOOD_BALL.get());
                output.accept(ModItem.DISAPPEAR_STONE_BALL.get());
                output.accept(ModItem.DISAPPEAR_IRON_BALL.get());
                output.accept(ModItem.DISAPPEAR_DIAMOND_BALL.get());
                output.accept(ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get());
                output.accept(ModItem.END_PEARL_DISAPPEAR_STONE_BALL.get());
                output.accept(ModItem.END_PEARL_DISAPPEAR_IRON_BALL.get());
                output.accept(ModItem.END_PEARL_DISAPPEAR_DIAMOND_BALL.get());
            }).build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }



}
