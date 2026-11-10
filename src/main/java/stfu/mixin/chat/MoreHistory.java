package stfu.mixin.chat;

import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import stfu.config.StfuConfig;

@Mixin(ChatComponent.class)
public abstract class MoreHistory {
    @ModifyConstant(method = {"addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;ILnet/minecraft/client/GuiMessageTag;Z)V"},
            constant = @Constant(intValue = 100))
    private int moreHistory(int original) {
        return StfuConfig.get().maxChatHistory;
    }
}
