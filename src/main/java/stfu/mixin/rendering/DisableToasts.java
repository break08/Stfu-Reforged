package stfu.mixin.rendering;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRecipeBookAddPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.Config;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import org.objectweb.asm.Opcodes;


@Mixin(ClientPacketListener.class)
abstract class DisableToasts {
    @Redirect(method = "handleLogin", at = @At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;seenInsecureChatWarning:Z", opcode = Opcodes.GETFIELD))
    private boolean onGameJoin(ClientPacketListener instance) {
        return true;
    }

    @Inject(method = "handleUpdateAdvancementsPacket", at = @At("HEAD"), cancellable = true)
    private void disableAdvancementToasts(ClientboundUpdateAdvancementsPacket packet, CallbackInfo ci) {
        if (!Config.get().advancementToasts) ci.cancel();
    }

    @Redirect(
            method = "handleRecipeBookAdd",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundRecipeBookAddPacket$Entry;notification()Z")
    )
    private boolean disableRecipeToasts(ClientboundRecipeBookAddPacket.Entry instance) {
        return Config.get().recipeToasts && instance.notification();
    }
}
