package net.flandre923.flansmod.common.mixin;

import net.minecraft.client.renderer.RenderStateShard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderStateShard.class)
public interface  MixinRenderStateShard {
    @Accessor("TRANSLUCENT_TRANSPARENCY")
    static RenderStateShard.TransparencyStateShard getTranslucentTransparency() {
        throw new AssertionError();
    }

    @Accessor("POSITION_COLOR_TEX_LIGHTMAP_SHADER")
    static RenderStateShard.ShaderStateShard getPositionColorTexLightmapShader()  {
        throw new AssertionError();
    }

    @Accessor("NO_CULL")
    static RenderStateShard.CullStateShard getCullStateShard() {
        throw new AssertionError();
    }

}
