package stfu.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.ServerReconfigScreen;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import stfu.config.Config;

import static stfu.Main.client;

@Mixin(Gui.class)
public abstract class DisableLoadingTerrain {
    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true, name = "screen")
    public Screen setScreen(Screen screen) {
        if (!Config.get().disableLoadingTerrain) return screen;
        if (screen instanceof ServerReconfigScreen) {
            final Connection connection = client.getConnection().getConnection();
            return new Screen(Component.empty()) {
                @Override
                public void tick() {
                    if (connection.isConnected()) connection.tick();
                    else connection.handleDisconnection();
                }
            };
        }
        return screen;
    }

}
