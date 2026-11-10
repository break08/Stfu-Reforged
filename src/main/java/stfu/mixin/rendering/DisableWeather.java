package stfu.mixin.rendering;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.StfuConfig;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;

@Mixin(LevelRenderer.class)
public abstract class DisableWeather {
    @Inject(method = "renderSnowAndRain", at = @At("HEAD"), cancellable = true)
    private void renderWeather(LightTexture manager, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo ci){
        if (!StfuConfig.get().renderWeather) ci.cancel();
    }

    @Inject(method = "tickRain", at = @At("HEAD"), cancellable = true)
    private void tickRainSplashing(Camera camera, CallbackInfo ci){
        if (!StfuConfig.get().renderWeather) ci.cancel();
    }
}