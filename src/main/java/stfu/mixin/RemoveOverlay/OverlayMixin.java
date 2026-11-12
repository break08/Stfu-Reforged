package stfu.mixin.RemoveOverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Overlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import stfu.config.Config;
import stfu.mixin.accessor.LoadingOverlayAccessor;

@Mixin(Overlay.class)
public class OverlayMixin {
    @Inject(method = "isPausing", at = @At("HEAD"), cancellable = true)
    private void pausesGame(CallbackInfoReturnable<Boolean> cir) {
        Overlay over = Minecraft.getInstance().gui.overlay();
        if (Config.get().disableSplash && over instanceof LoadingOverlay loadingOverlay && ((LoadingOverlayAccessor) loadingOverlay).fadeIn())
            cir.setReturnValue(false);
    }
}
