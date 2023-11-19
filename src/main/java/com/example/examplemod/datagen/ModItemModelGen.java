package com.example.examplemod.datagen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.ModItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.ForgeRegistries;

public class ModItemModelGen extends ItemModelProvider {
    public static final String GENERATED = "item/generated";

    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExampleMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemGeneratedModel(ModItem.WOOD_BALL.get(), resourceItem(itemName(ModItem.WOOD_BALL.get())));
        itemGeneratedModel(ModItem.STONE_BALL.get(),resourceItem(itemName(ModItem.STONE_BALL.get())));
        itemGeneratedModel(ModItem.IRON_BALL.get(),resourceItem(itemName(ModItem.IRON_BALL.get())));
        itemGeneratedModel(ModItem.DIAMOND_BALL.get(),resourceItem(itemName(ModItem.DIAMOND_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_WOOD_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_WOOD_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_STONE_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_STONE_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_IRON_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_IRON_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_DIAMOND_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_DIAMOND_BALL.get())));
        itemGeneratedModel(ModItem.DISAPPEAR_WOOD_BALL.get(),resourceItem(itemName(ModItem.DISAPPEAR_WOOD_BALL.get())));
        itemGeneratedModel(ModItem.DISAPPEAR_STONE_BALL.get(),resourceItem(itemName(ModItem.DISAPPEAR_STONE_BALL.get())));
        itemGeneratedModel(ModItem.DISAPPEAR_IRON_BALL.get(),resourceItem(itemName(ModItem.DISAPPEAR_IRON_BALL.get())));
        itemGeneratedModel(ModItem.DISAPPEAR_DIAMOND_BALL.get(),resourceItem(itemName(ModItem.DISAPPEAR_DIAMOND_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_DISAPPEAR_STONE_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_DISAPPEAR_STONE_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_DISAPPEAR_IRON_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_DISAPPEAR_IRON_BALL.get())));
        itemGeneratedModel(ModItem.END_PEARL_DISAPPEAR_DIAMOND_BALL.get(),resourceItem(itemName(ModItem.END_PEARL_DISAPPEAR_DIAMOND_BALL.get())));
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }


    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(ExampleMod.MODID, "item/" + path);
    }


}
