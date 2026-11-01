package stfu.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import stfu.config.Config;

@Mixin(Minecraft.class)
public abstract class DisableLoadingTerrain {
    @Shadow @Nullable public ClientLevel level;

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Screen setScreen(Screen screen) {
        if (!Config.get().disableLoadingTerrain) return screen;
        if (screen instanceof ReceivingLevelScreen) {
            if (level == null) return new Screen(Component.empty()) {
            };
            else return null;
        }
        return screen;
    }

}
