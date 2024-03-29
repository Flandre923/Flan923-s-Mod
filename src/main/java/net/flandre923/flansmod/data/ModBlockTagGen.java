package net.flandre923.flansmod.data;

import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.common.block.ModBlocks;
import net.flandre923.flansmod.common.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagGen extends BlockTagsProvider {
    public ModBlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FlansMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.MY_ANVIL.get());
        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.MY_ANVIL.get());

        tag(ModTags.Blocks.CAN_BALL_REPLACED)
                .add(Blocks.COBBLESTONE)
                .add(Blocks.COBBLED_DEEPSLATE)
                .add(Blocks.DIORITE)
                .add(Blocks.GRANITE)
                .add(Blocks.DRIPSTONE_BLOCK)
                .add(Blocks.ANDESITE)
                .add(Blocks.TUFF);

    }
}
