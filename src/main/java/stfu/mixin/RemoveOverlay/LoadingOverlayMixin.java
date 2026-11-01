package stfu.mixin.RemoveOverlay;

import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stfu.config.Config;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @Shadow @Final private boolean fadeIn;

    /*
    @ModifyConstant(method = {"render", "extractRenderState"}, constant = @Constant(floatValue = 2.0F))
    private float disableFade(float progress) {
        return Config.get().disableFade? 1F : progress;
    }

     */

    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    private void pausesGame(CallbackInfoReturnable<Boolean> cir) {
        if (Config.get().disableSplash && fadeIn) cir.setReturnValue(false);
    }

    /*
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V"))
    private void render(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int srcWidth, int srcHeight, int textureWidth, int textureHeight, int color, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight, color);
    }
    @WrapOperation(method = {"render", "extractRenderState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(IIIII)V"))
    private void render(GuiGraphicsExtractor instance, int x1, int y1, int x2, int y2, int color, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, x1, y1, x2, y2, color);
    }

    @WrapOperation(method = {"render", "extractRenderState"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIIII)V"))
    private void render(GuiGraphicsExtractor instance, RenderPipeline pipeline, Identifier sprite, int x, int y, float u, float v, int width, int height, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int color, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, pipeline, sprite, x, y, u, v, width, height, regionWidth, regionHeight, textureWidth, textureHeight, color);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fill(Lnet/minecraft/client/renderer/RenderType;IIIII)V"))
    private void render(GuiGraphicsExtractor instance, RenderType renderType, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, renderType, i, j, k, l, m);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lnet/minecraft/resources/Identifier;IIIIFFIIII)V"))
    private void render(GuiGraphicsExtractor instance, Identifier resourceLocation, int i, int j, int k, int l, float f, float g, int m, int n, int o, int p, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, resourceLocation, i, j, k, l, f, g, m, n, o, p);
    }

     */
}
