package net.flandre923.minehelper.datagen;

import net.flandre923.minehelper.block.ModBlocks;
import net.minecraft.data.loot.packs.VanillaBlockLoot;


public class ModLootTableGen extends VanillaBlockLoot  {
    @Override
    protected void generate() {
        dropSelf(ModBlocks.MY_ANVIL.get());
    }

}
