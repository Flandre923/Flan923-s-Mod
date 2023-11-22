package com.example.examplemod.datagen;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.ModBlocks;
import com.example.examplemod.item.ModItem;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

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
                .define('#', ItemTags.PLANKS)
                .define('*', ModItem.WOOD_BALL.get())
                .define('$',Items.COBBLESTONE)
                .group(ExampleMod.MODID)
                .unlockedBy("has_wood_ball",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.GUNPOWDER)))
                .save(output);

        this.buildBallRecipes(output,ItemTags.PLANKS,ModItem.WOOD_BALL.get());
        this.buildBallRecipes(output,ItemTags.STONE_TOOL_MATERIALS,ModItem.STONE_BALL.get());
        this.buildBallRecipes(output,Items.IRON_INGOT,ModItem.IRON_BALL.get());
        this.buildBallRecipes(output,Items.DIAMOND,ModItem.DIAMOND_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.WOOD_BALL.get(),Items.ENDER_PEARL,ModItem.END_PEARL_WOOD_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.STONE_BALL.get(),Items.ENDER_PEARL,ModItem.END_PEARL_STONE_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.IRON_BALL.get(),Items.ENDER_PEARL,ModItem.END_PEARL_IRON_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.DIAMOND_BALL.get(),Items.ENDER_PEARL,ModItem.END_PEARL_DIAMOND_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.WOOD_BALL.get(),Items.COBBLESTONE,ModItem.DISAPPEAR_WOOD_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.STONE_BALL.get(),Items.COBBLESTONE,ModItem.DISAPPEAR_STONE_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.IRON_BALL.get(),Items.COBBLESTONE,ModItem.DISAPPEAR_IRON_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.DIAMOND_BALL.get(),Items.COBBLESTONE,ModItem.DISAPPEAR_DIAMOND_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.WOOD_BALL.get(),Items.COBBLESTONE,Items.ENDER_PEARL,ModItem.END_PEARL_DISAPPEAR_WOOD_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.STONE_BALL.get(),Items.COBBLESTONE,Items.ENDER_PEARL,ModItem.END_PEARL_DISAPPEAR_STONE_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.IRON_BALL.get(),Items.COBBLESTONE,Items.ENDER_PEARL,ModItem.END_PEARL_DISAPPEAR_IRON_BALL.get());
        this.buildEndAndDisappearBallRecipes(output,ModItem.DIAMOND_BALL.get(),Items.COBBLESTONE,Items.ENDER_PEARL,ModItem.END_PEARL_DISAPPEAR_DIAMOND_BALL.get());
    }
    private void buildEndAndDisappearBallRecipes(RecipeOutput output, Item item,Item item2, Item resultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItem,16)
                .pattern("A#A")
                .pattern("*$*")
                .pattern(" * ")
                .define('#',item)
                .define('*', Items.SAND)
                .define('$',Items.GUNPOWDER)
                .define('A',item2)
                .group(ExampleMod.MODID)
                .unlockedBy("has_wood_ball",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItem.WOOD_BALL.get())))
                .save(output);
    }

    private void buildEndAndDisappearBallRecipes(RecipeOutput output, Item item,Item item2,Item item3, Item resultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItem,16)
                .pattern("A#A")
                .pattern("*$*")
                .pattern("B*B")
                .define('#',item)
                .define('*', Items.SAND)
                .define('$',Items.GUNPOWDER)
                .define('A',item2)
                .define('B',item3)
                .group(ExampleMod.MODID)
                .unlockedBy("has_wood_ball",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItem.WOOD_BALL.get())))
                .save(output);
    }


    private void buildBallRecipes(RecipeOutput output, Item item,Item resultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItem,16)
                .pattern(" # ")
                .pattern("*$*")
                .pattern(" * ")
                .define('#',item)
                .define('*', Items.SAND)
                .define('$',Items.GUNPOWDER)
                .group(ExampleMod.MODID)
                .unlockedBy("has_wood_ball",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItem.WOOD_BALL.get())))
                .save(output);
    }

    private void buildBallRecipes(RecipeOutput output, TagKey itemTag,Item resultItem){
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,resultItem,16)
                .pattern(" # ")
                .pattern("*$*")
                .pattern(" * ")
                .define('#',itemTag)
                .define('*', Items.SAND)
                .define('$',Items.GUNPOWDER)
                .group(ExampleMod.MODID)
                .unlockedBy("has_wood_ball",InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(Items.GUNPOWDER)))
                .save(output);
    }

}
