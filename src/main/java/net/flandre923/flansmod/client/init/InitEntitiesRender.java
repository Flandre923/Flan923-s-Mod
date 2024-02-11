package net.flandre923.flansmod.client.init;

import com.sun.security.auth.callback.TextCallbackHandler;
import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.client.model.TezcatlipocaPistolBulletModel;
import net.flandre923.flansmod.client.renderer.entity.TezcatlipocaPistolBulletRenderer;
import net.flandre923.flansmod.common.entity.ModEntities;
import net.flandre923.flansmod.common.entity.custom.TezcatlipocaPistolBullet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = FlansMod.MOD_ID,value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class InitEntitiesRender {
    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        EntityRenderers.register(ModEntities.TECATLIPOCA_PISTOR_BULLET.get(), TezcatlipocaPistolBulletRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TezcatlipocaPistolBulletModel.LAYER_LOCATION,TezcatlipocaPistolBulletModel::createBodyLayer);
    }
}
