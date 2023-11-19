package com.example.examplemod.datagen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.ModBlocks;
import com.example.examplemod.item.ModItem;
import com.google.gson.JsonArray;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModRecipesGen extends RecipeProvider {


    public ModRecipesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.MY_ANVIL.get())
                .pattern("###")
                .pattern("#*#")
                .pattern("$$$")
                .define('#', ItemTags.WOODEN_SLABS)
                .define('*', ModItem.WOOD_BALL.get())
                .define('$',Items.COBBLESTONE)
                .group(ExampleMod.MODID)
                .unlockedBy("has_woodball",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItem.WOOD_BALL.get())))
                .save(output);
    }
}
