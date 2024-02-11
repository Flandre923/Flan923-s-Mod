package net.flandre923.flansmod.common.sound;

import net.flandre923.flansmod.FlansMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(Registries.SOUND_EVENT, FlansMod.MOD_ID);

    public static final Supplier<SoundEvent> ATTACK1 = registerSound("item.sv371_1_attack1");
    public static final Supplier<SoundEvent> ATTACK2 = registerSound("item.sv371_1_attack2");
    public static final Supplier<SoundEvent> ATTACK3 = registerSound("item.sv371_1_attack3");
    public static final Supplier<SoundEvent> ATTACK7 = registerSound("item.sv371_1_attack7");
    public static final Supplier<SoundEvent> ATTACK11 = registerSound("item.sv371_1_attack11");
    public static final Supplier<SoundEvent> ATTACK13 = registerSound("item.sv371_1_attack13");

    private static Supplier<SoundEvent> registerSound(String name) {//range范围
        return SOUND_EVENT.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(FlansMod.MOD_ID, name), 16.0F));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENT.register(eventBus);
    }

}
