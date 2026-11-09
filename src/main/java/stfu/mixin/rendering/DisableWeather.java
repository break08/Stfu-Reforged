package stfu.mixin.rendering;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.WeatherEffectRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.WeatherRenderState;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.Config;

@Mixin(WeatherEffectRenderer.class)
public class DisableWeather {
    @Inject(method = "tickRainParticles", at = @At("HEAD"), cancellable = true)
    private void addParticlesAndSound(ClientLevel level, Camera camera, int ticks, ParticleStatus particleStatus, int weatherRadius, CallbackInfo ci){
        if (!Config.get().renderWeather) ci.cancel();
    }

    @Inject(method = "render(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/state/level/WeatherRenderState;Lnet/minecraft/client/renderer/state/level/LevelRenderState;)V", at = @At("HEAD"), cancellable = true)
    private void renderPrecipitation(Vec3 cameraPos, WeatherRenderState renderState, @Nullable LevelRenderState levelRenderState, CallbackInfo ci){
        if (!Config.get().renderWeather) ci.cancel();
    }
}