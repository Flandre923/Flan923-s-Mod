package com.example.examplemod.datagen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.ModBlocks;
import com.example.examplemod.item.ModItem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZHLanguageGen extends LanguageProvider {
    public ModZHLanguageGen(PackOutput output, String locale) {
        super(output, ExampleMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
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
        add(ModItem.END_PEARL_DISAPPEAR_STONE_BALL.get(),"铁传送消失炸弹");
        add(ModItem.END_PEARL_DISAPPEAR_IRON_BALL.get(),"木头传送消失炸弹");
        add(ModItem.END_PEARL_DISAPPEAR_DIAMOND_BALL.get(),"木头传送消失炸弹");
        add(ModBlocks.MY_ANVIL.get(),"炸弹铁砧");


    }
}
