package net.flandre923.flansmod.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.client.ModRenderType;
import net.flandre923.flansmod.common.item.ModItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderItemInFrameEvent;
import org.joml.Matrix4f;
import org.joml.Random;

@Mod.EventBusSubscriber(modid = FlansMod.MOD_ID,value = Dist.CLIENT)
public class GunRenderHandler {

    public static final ResourceLocation MUZZLE_FLASH_TEXTURE = new ResourceLocation(FlansMod.MOD_ID, "textures/effect/muzzle_flash.png");;

    @SubscribeEvent
    public static void onRenderItemFrame(RenderItemInFrameEvent event){
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.getMainHandItem().getItem().equals(ModItem.TEZCATLIPOCA_PISTOL.get())){
            GunRenderHandler.drawMuzzleFlash(mc.player.getMainHandItem(),new Random().nextFloat(),true, event.getPoseStack(),event.getMultiBufferSource(),0.6600021f);
        }
    }
    @SubscribeEvent
    public static void onRenderOverlay(RenderHandEvent event){
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.getMainHandItem().getItem().equals(ModItem.TEZCATLIPOCA_PISTOL.get())){
            GunRenderHandler.drawMuzzleFlash(mc.player.getMainHandItem(),new Random().nextFloat(),true, event.getPoseStack(),event.getMultiBufferSource(),0.6600021f);
        }
    }

    public static double displayX = 0;
    public static double displayY = 0;
    public static double displayZ = 0;
    public static double sizeZ = 0;

    public double adjustedTrailZ = 0;
    private static void drawMuzzleFlash(ItemStack weapon, float random, boolean flip, PoseStack poseStack, MultiBufferSource buffer, float partialTicks)
    {
//        if(!PropertyHelper.hasMuzzleFlash(weapon, modifiedGun))
//            return;

        poseStack.pushPose();

        // Translate to the position where the muzzle flash should spawn
        Vec3 weaponOrigin = new Vec3(8.0, 0.0, 8.0);
        Vec3 flashPosition = new Vec3(0.0, 3.3, 2.6400000000000006);
        poseStack.translate(weaponOrigin.x * 0.0625, weaponOrigin.y * 0.0625, weaponOrigin.z * 0.0625);
        poseStack.translate(flashPosition.x * 0.0625, flashPosition.y * 0.0625, flashPosition.z * 0.0625);
        poseStack.translate(-0.5, -0.5, -0.5);

        // Legacy method to move muzzle flash to be at the end of the barrel attachment
//        ItemStack barrelStack = Gun.getAttachment(IAttachment.Type.BARREL, weapon);
//        if(!barrelStack.isEmpty() && barrelStack.getItem() instanceof IBarrel barrel && !PropertyHelper.isUsingBarrelMuzzleFlash(barrelStack))
//        {
//            Vec3 scale = PropertyHelper.getAttachmentScale(weapon, modifiedGun, IAttachment.Type.BARREL);
//            double length = barrel.getProperties().getLength();
//            poseStack.translate(0, 0, -length * 0.0625 * scale.z);
//        }

        poseStack.mulPose(Axis.ZP.rotationDegrees(360F * random));
        poseStack.mulPose(Axis.XP.rotationDegrees(flip ? 180F : 0F));

        Vec3 flashScale = new Vec3(0.5, 0.5, 0.5);
        float scaleX = ((float) flashScale.x / 2F) - ((float) flashScale.x / 2F) * (1.0F - partialTicks);
        float scaleY = ((float) flashScale.y / 2F) - ((float) flashScale.y / 2F) * (1.0F - partialTicks);
        poseStack.scale(scaleX, scaleY, 1.0F);

        float scaleModifier = 1.0f;
        poseStack.scale(scaleModifier, scaleModifier, 1.0F);

        // Center the texture
        poseStack.translate(-0.5, -0.5, 0);

        float minU = weapon.isEnchanted() ? 0.5F : 0.0F;
        float maxU = weapon.isEnchanted() ? 1.0F : 0.5F;
        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer builder = buffer.getBuffer(ModRenderType.getMuzzleFlash());
        builder.vertex(matrix, 0, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(maxU, 1.0F).uv2(15728880).endVertex();
        builder.vertex(matrix, 1, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(minU, 1.0F).uv2(15728880).endVertex();
        builder.vertex(matrix, 1, 1, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(minU, 0).uv2(15728880).endVertex();
        builder.vertex(matrix, 0, 1, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(maxU, 0).uv2(15728880).endVertex();

        poseStack.popPose();
    }
}
