package com.example.examplemod.datagen;

import com.example.examplemod.block.ModBlocks;
import net.minecraft.data.loot.packs.VanillaBlockLoot;


public class ModLootTableGen extends VanillaBlockLoot  {
    @Override
    protected void generate() {
        dropSelf(ModBlocks.MY_ANVIL.get());
    }

}
