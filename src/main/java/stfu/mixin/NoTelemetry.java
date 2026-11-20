package stfu.mixin;

import net.minecraft.client.ClientTelemetryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientTelemetryManager.class)
public abstract class NoTelemetry {
    @Inject(method = "sendWorldLoadEvent", at = @At("HEAD"), cancellable = true)
    private void NoopSender(ClientTelemetryManager.PlayerInfo p_193558_, CallbackInfo ci) {
        ci.cancel();
    }
}
