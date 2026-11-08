package stfu.mixin.rendering;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ParticleStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.Config;

@Mixin(WeatherEffectRenderer.class)
public class DisableWeather {
    @Inject(method = "tickRainParticles", at = @At("HEAD"), cancellable = true)
    private void addParticlesAndSound(ClientLevel clientLevel, Camera camera, int ticks, ParticleStatus particleStatus, /*? >= 1.21.11{*/ int j,/*?}*/ CallbackInfo ci){
        if (!Config.get().renderWeather) ci.cancel();
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void renderPrecipitation(CallbackInfo ci){
        if (!Config.get().renderWeather) ci.cancel();
    }
}