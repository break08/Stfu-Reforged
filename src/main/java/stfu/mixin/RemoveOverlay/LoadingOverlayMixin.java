package stfu.mixin.RemoveOverlay;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.LoadingOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stfu.config.StfuConfig;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @Shadow @Final private boolean fadeIn;

    @Inject(method = "render", at = @At("HEAD"))
    private void drawScreenInstead(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        if (StfuConfig.get().disableSplash && fadeIn) {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.screen != null) mc.screen.render(poseStack, i, (int) f, j);
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V"))
    private void skipOverlayScreenRender(net.minecraft.client.gui.screens.Screen instance, PoseStack poseStack, int x, int y, float t, Operation<Void> original) {
        if (!StfuConfig.get().disableSplash || !fadeIn) original.call(instance, poseStack, x, y, t);
    }

    @ModifyConstant(method = {"render"}, constant = @Constant(floatValue = 2.0F))
    private float disableFade(float progress) {
        return StfuConfig.get().disableFade? 1F : progress;
    }

    @Inject(method = "isPauseScreen", at = @At("HEAD"), cancellable = true)
    private void pausesGame(CallbackInfoReturnable<Boolean> cir) {
        if (StfuConfig.get().disableSplash && fadeIn) cir.setReturnValue(false);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;fill(Lcom/mojang/blaze3d/vertex/PoseStack;IIIII)V"))
    private void skipFill(PoseStack poseStack, int i, int j, int k, int l, int m, Operation<Void> original) {
        if (!StfuConfig.get().disableSplash || !fadeIn) original.call(poseStack, i, j, k, l, m);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/screens/LoadingOverlay;blit(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIFFIIII)V"))
    private void skipBlit(PoseStack poseStack, int i, int j, int k, int l, float f, float g, int m, int n, int o, int p, Operation<Void> original) {
        if (!StfuConfig.get().disableSplash || !fadeIn) original.call(poseStack, i, j, k, l, f, g, m, n, o, p);
    }
}
