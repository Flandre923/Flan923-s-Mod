package net.flandre923.flansmod.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.client.model.TezcatlipocaPistolBulletModel;
import net.flandre923.flansmod.common.entity.custom.TezcatlipocaPistolBullet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class TezcatlipocaPistolBulletRenderer <T extends TezcatlipocaPistolBullet> extends EntityRenderer<T> {
    public static final ResourceLocation BULLET_LOCATION = new ResourceLocation(FlansMod.MOD_ID,"textures/entity/tezatilipoca_pistol_bullet.png");
    public  final TezcatlipocaPistolBulletModel model;

    public TezcatlipocaPistolBulletRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new TezcatlipocaPistolBulletModel(pContext.bakeLayer(TezcatlipocaPistolBulletModel.LAYER_LOCATION));
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(
                pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, false
        );
        this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return BULLET_LOCATION;
    }


}
