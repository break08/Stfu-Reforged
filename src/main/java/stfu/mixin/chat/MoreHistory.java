package stfu.mixin.chat;

import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import stfu.config.Config;

@Mixin(ChatComponent.class)
public abstract class MoreHistory {
    @ModifyConstant(method = {"addMessageToQueue", "addMessageToDisplayQueue"},
            constant = @Constant(intValue = 100))
    private int moreHistory(int original) {
        return Config.get().maxChatHistory;
    }
}
