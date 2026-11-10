package stfu.mixin.chat;

import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.StfuConfig;

@Mixin(ChatComponent.class)
public abstract class Filter {
    @Inject(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"}, at = @At("HEAD"), cancellable = true)
    private void filter(Component message, MessageSignature messageSignature, GuiMessageTag tag, CallbackInfo ci) {
        if (!(message instanceof MutableComponent mutable && mutable.getContents() instanceof TranslatableContents translatable)) return;

        if (translatable.getKey().startsWith("chat.type.advancement")) {
            if (!StfuConfig.get().announceAdvancements) ci.cancel();
        } else if (translatable.getKey().equals("chat.type.admin")) {
            StfuConfig.AdminChat adminChat = StfuConfig.get().adminChat;
            if (adminChat == StfuConfig.AdminChat.DISABLED || (adminChat == StfuConfig.AdminChat.ONLY_PLAYERS && (translatable.getArgs()[0].equals("@") || translatable.getArgs()[0].equals(Component.literal("@")))))
                ci.cancel();
        }
    }
}
