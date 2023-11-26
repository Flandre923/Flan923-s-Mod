package net.flandre923.minehelper.datagen;

import net.flandre923.minehelper.MineHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public  class DataGenerators {
    @Mod.EventBusSubscriber(modid = MineHelper.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class DataGen{
        @SubscribeEvent
        public  static void generator(GatherDataEvent event){
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper helper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(event.includeClient(),new ModLanguageGen(output,"en_us"));
            generator.addProvider(event.includeClient(),new ModZHLanguageGen(output,"zh_cn"));

            generator.addProvider(event.includeClient(),new ModItemModelGen(output, helper));
            generator.addProvider(event.includeClient(),new ModBlockModelGen(output,helper));

            ModBlockTagGen blockModelGen = new ModBlockTagGen(output,lookupProvider,event.getExistingFileHelper());
            generator.addProvider(event.includeServer(),blockModelGen);
            generator.addProvider(event.includeServer(),new ModRecipesGen(output,lookupProvider));
            generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(ModLootTableGen::new, LootContextParamSets.BLOCK))));
        }
    }

}
