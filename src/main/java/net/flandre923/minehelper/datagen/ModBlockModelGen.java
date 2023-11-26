package net.flandre923.minehelper.datagen;

import net.flandre923.minehelper.MineHelper;
import net.flandre923.minehelper.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockModelGen extends BlockStateProvider {

    public ModBlockModelGen(PackOutput output,ExistingFileHelper exFileHelper) {
        super(output, MineHelper.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerSideTopBlockModelAndItem(ModBlocks.MY_ANVIL.get());
    }

    public void  registerBlockModelAndItem(Block block){
        this.simpleBlockWithItem(block,this.cubeAll(block));
        this.blockTexture(block);
    }
    public void  registerSideTopBlockModelAndItem(Block block){
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
        ResourceLocation side = new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_side");
        ResourceLocation top  = new ResourceLocation(name.getNamespace(),ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + "_top");
        this.simpleBlockWithItem(block,this.models().cubeTop(name.getPath(),side,top));
    }



}
