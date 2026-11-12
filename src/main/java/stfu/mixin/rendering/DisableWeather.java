package stfu.mixin.rendering;

import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.Config;

@Mixin(WeatherEffectRenderer.class)
public class DisableWeather {
    @Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
    private void addParticlesAndSound(ClientLevel level, float partialTicks, Vec3 cameraPos, WeatherRenderState renderState, CallbackInfo ci     /*? >= 1.21.11{*/ /*?}*/){
        if (!Config.get().renderWeather) ci.cancel();
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderPrecipitation(CallbackInfo ci){
        if (!Config.get().renderWeather) ci.cancel();
    }
}