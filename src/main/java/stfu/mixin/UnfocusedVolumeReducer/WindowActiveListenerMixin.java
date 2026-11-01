package stfu.mixin.UnfocusedVolumeReducer;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.Config;

import static stfu.Main.client;

@Mixin(Minecraft.class)
public class WindowActiveListenerMixin {
    @Inject(method = "setWindowActive", at = @At("TAIL"))
    private void setWindowActive(boolean bl, CallbackInfo ci) {
        client.getSoundManager().updateSourceVolume(SoundSource.MASTER, client.options.getSoundSourceVolume(SoundSource.MASTER) * (!bl? Config.get().unfocusedVolume : 1F));
    }
}