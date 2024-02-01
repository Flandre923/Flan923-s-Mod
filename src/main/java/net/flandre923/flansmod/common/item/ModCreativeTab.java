package net.flandre923.flansmod.common.item;

import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.common.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FlansMod.MOD_ID);
    public static final String TUTORIAL_TAB_STRING = "creativetab.minehelp_tab";


    public static final Supplier<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable(TUTORIAL_TAB_STRING))
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

                output.accept(ModBlocks.MY_ANVIL.get());
            }).build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }



}
