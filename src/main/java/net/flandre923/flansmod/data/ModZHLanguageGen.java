package net.flandre923.flansmod.data;

import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.common.block.ModBlocks;
import net.flandre923.flansmod.common.item.ModCreativeTab;
import net.flandre923.flansmod.common.item.ModItem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZHLanguageGen extends LanguageProvider {
    public ModZHLanguageGen(PackOutput output, String locale) {
        super(output, FlansMod.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add(ModCreativeTab.TUTORIAL_TAB_STRING,"挖矿小帮手物品栏");
        add(ModItem.WOOD_BALL.get(),"木头炸弹");
        add(ModItem.STONE_BALL.get(),"石头炸弹");
        add(ModItem.IRON_BALL.get(),"铁炸弹");
        add(ModItem.DIAMOND_BALL.get(),"钻石炸弹");
        add(ModItem.END_PEARL_WOOD_BALL.get(),"木头传送炸弹");
        add(ModItem.END_PEARL_STONE_BALL.get(),"石头传送炸弹");
        add(ModItem.END_PEARL_IRON_BALL.get(),"铁传送炸弹");
        add(ModItem.END_PEARL_DIAMOND_BALL.get(),"钻石传送炸弹");
        add(ModItem.DISAPPEAR_WOOD_BALL.get(),"木头消失炸弹");
        add(ModItem.DISAPPEAR_STONE_BALL.get(),"石头消失炸弹");
        add(ModItem.DISAPPEAR_IRON_BALL.get(),"铁消失炸弹");
        add(ModItem.DISAPPEAR_DIAMOND_BALL.get(),"钻石消失炸弹");
        add(ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get(),"木头传送消失炸弹");
        add(ModItem.END_PEARL_DISAPPEAR_STONE_BALL.get(),"石头传送消失炸弹");
        add(ModItem.END_PEARL_DISAPPEAR_IRON_BALL.get(),"铁传送消失炸弹");
        add(ModItem.END_PEARL_DISAPPEAR_DIAMOND_BALL.get(),"钻石传送消失炸弹");
        add(ModBlocks.MY_ANVIL.get(),"炸弹铁砧");


    }
}
