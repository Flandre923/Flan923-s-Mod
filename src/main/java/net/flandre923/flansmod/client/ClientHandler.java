package net.flandre923.flansmod.client;

import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.client.handler.GunRenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = FlansMod.MOD_ID, value = Dist.CLIENT)
public class ClientHandler {
    public static void setup(){
//        NeoForge.EVENT_BUS.register(GunRenderHandler.get());
    }


    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event)
    {
    }

    @SubscribeEvent
    public static void onKeyPressed(InputEvent.Key event)
    {
    }

}
