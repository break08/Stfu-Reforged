package stfu.mixin.rendering;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.StfuConfig;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;

@Mixin(ClientPacketListener.class)
abstract class DisableToasts {

  @Redirect(method = "handleServerData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundServerDataPacket;enforcesSecureChat()Z", ordinal = 1))
    private boolean isSecureChatEnforced(ClientboundServerDataPacket instance) {
        return true;
    }


    /*
    @Redirect(
            method = "method_34011",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/Recipe;showNotification()Z")
    )
    private boolean disableRecipeToasts(Recipe<?> instance) {
        return Config.get().recipeToasts && instance.showNotification();
    }

     */

    @Inject(method = "handleUpdateAdvancementsPacket", at = @At("HEAD"), cancellable = true)
    private void disableAdvancementToasts(ClientboundUpdateAdvancementsPacket packet, CallbackInfo ci) {
        if (!StfuConfig.get().advancementToasts) ci.cancel();
    }
}
