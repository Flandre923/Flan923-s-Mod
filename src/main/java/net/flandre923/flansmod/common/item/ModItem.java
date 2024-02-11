package net.flandre923.flansmod.common.item;

import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.common.item.ball.MaterialSize;
import net.flandre923.flansmod.common.item.ball.MaterialType;
import net.flandre923.flansmod.common.item.ball.NormalBallItem;
import net.flandre923.flansmod.common.item.cusotm.TezcatlipocaPistol;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItem {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, FlansMod.MOD_ID);


    public static final DeferredHolder<Item, NormalBallItem> WOOD_BALL =
            ITEMS.register("wood_ball",()->new NormalBallItem(MaterialSize.WOOD.getSize(), MaterialType.COMMON));
    public static final DeferredHolder<Item, NormalBallItem> STONE_BALL =
            ITEMS.register("stone_ball",()->new NormalBallItem(MaterialSize.STONE.getSize(),MaterialType.COMMON));
    public static final DeferredHolder<Item, NormalBallItem> IRON_BALL =
            ITEMS.register("iron_ball",()->new NormalBallItem(MaterialSize.IRON.getSize(),MaterialType.COMMON));
    public static final DeferredHolder<Item, NormalBallItem> DIAMOND_BALL =
            ITEMS.register("diamond_ball",()->new NormalBallItem(MaterialSize.DIAMOND.getSize(),MaterialType.COMMON));


    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_WOOD_BALL =
            ITEMS.register("end_pearl_wood_ball",()->new NormalBallItem(MaterialSize.WOOD.getSize(),MaterialType.END_PEARL));
    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_STONE_BALL =
            ITEMS.register("end_pearl_stone_ball",()->new NormalBallItem(MaterialSize.STONE.getSize(),MaterialType.END_PEARL));
    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_IRON_BALL =
            ITEMS.register("end_pearl_iron_ball",()->new NormalBallItem(MaterialSize.IRON.getSize(),MaterialType.END_PEARL));
    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_DIAMOND_BALL =
            ITEMS.register("end_pearl_diamond_ball",()->new NormalBallItem(MaterialSize.DIAMOND.getSize(),MaterialType.END_PEARL));

    public static final DeferredHolder<Item, NormalBallItem> DISAPPEAR_WOOD_BALL =
            ITEMS.register("disappear_wood_ball",()->new NormalBallItem(MaterialSize.WOOD.getSize(),MaterialType.DISAPPEAR));
    public static final DeferredHolder<Item, NormalBallItem> DISAPPEAR_STONE_BALL =
            ITEMS.register("disappear_stone_ball",()->new NormalBallItem(MaterialSize.STONE.getSize(),MaterialType.DISAPPEAR));
    public static final DeferredHolder<Item, NormalBallItem> DISAPPEAR_IRON_BALL =
            ITEMS.register("disappear_iron_ball",()->new NormalBallItem(MaterialSize.IRON.getSize(),MaterialType.DISAPPEAR));
    public static final DeferredHolder<Item, NormalBallItem> DISAPPEAR_DIAMOND_BALL =
            ITEMS.register("disappear_diamond_ball",()->new NormalBallItem(MaterialSize.DIAMOND.getSize(),MaterialType.DISAPPEAR));


    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_DISAPPEAR_WOOD_BALL =
            ITEMS.register("end_pearl_disappear_wood_ball",()->new NormalBallItem(MaterialSize.WOOD.getSize(),MaterialType.DISAPPEAR_AND_END_PEARL));
    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_DISAPPEAR_STONE_BALL =
            ITEMS.register("end_pearl_disappear_stone_ball",()->new NormalBallItem(MaterialSize.STONE.getSize(),MaterialType.DISAPPEAR_AND_END_PEARL));
    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_DISAPPEAR_IRON_BALL =
            ITEMS.register("end_pearl_disappear_iron_ball",()->new NormalBallItem(MaterialSize.IRON.getSize(),MaterialType.DISAPPEAR_AND_END_PEARL));
    public static final DeferredHolder<Item, NormalBallItem> END_PEARL_DISAPPEAR_DIAMOND_BALL =
            ITEMS.register("end_pearl_disappear_diamond_ball",()->new NormalBallItem(MaterialSize.DIAMOND.getSize(),MaterialType.DISAPPEAR_AND_END_PEARL));


    public static final DeferredHolder<Item, TezcatlipocaPistol> TEZCATLIPOCA_PISTOL =
            ITEMS.register("tezcatlipoca_pistol",()->new TezcatlipocaPistol(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
