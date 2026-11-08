package stfu.mixin.RemoveOverlay;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stfu.config.Config;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @Shadow @Final private boolean fadeIn;

    @Inject(method = "render", at = @At("HEAD"))
    private void drawScreenInstead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (Config.get().disableSplash && fadeIn) {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.screen != null) mc.screen.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void skipOverlayScreenRender(net.minecraft.client.gui.screens.Screen instance, GuiGraphics g, int x, int y, float t, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, g, x, y, t);
    }

    @ModifyConstant(method = {"render"}, constant = @Constant(floatValue = 2.0F))
    private float disableFade(float progress) {
        return Config.get().disableFade? 1F : progress;
    }

    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    private void pausesGame(CallbackInfoReturnable<Boolean> cir) {
        if (Config.get().disableSplash && fadeIn) cir.setReturnValue(false);
    }


    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lnet/minecraft/client/renderer/RenderType;IIIII)V"))
    private void render(GuiGraphics instance, RenderType renderType, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, renderType, i, j, k, l, m);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIFFIIII)V"))
    private void render(GuiGraphics instance, ResourceLocation resourceLocation, int i, int j, int k, int l, float f, float g, int m, int n, int o, int p, Operation<Void> original) {
        if (!Config.get().disableSplash || !fadeIn) original.call(instance, resourceLocation, i, j, k, l, f, g, m, n, o, p);
    }
}
