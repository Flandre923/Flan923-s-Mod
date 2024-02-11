package net.flandre923.flansmod.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.flandre923.flansmod.FlansMod;
import net.flandre923.flansmod.client.handler.GunRenderHandler;
import net.flandre923.flansmod.common.mixin.MixinRenderStateShard;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;

public class ModRenderType extends RenderType{

    private static final RenderType MUZZLE_FLASH = RenderType.create(FlansMod.MOD_ID + ":muzzle_flash", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, true, false, RenderType.CompositeState.builder().setShaderState(RenderStateShard.POSITION_COLOR_TEX_LIGHTMAP_SHADER).setTextureState(new RenderStateShard.TextureStateShard(GunRenderHandler.MUZZLE_FLASH_TEXTURE, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).createCompositeState(true));

    public ModRenderType(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }


    public static RenderType getMuzzleFlash() {
        return MUZZLE_FLASH;
    }
}
