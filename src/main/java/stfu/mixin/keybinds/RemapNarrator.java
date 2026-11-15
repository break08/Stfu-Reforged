package stfu.mixin.keybinds;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import stfu.Main;

@Mixin(KeyboardHandler.class)
public abstract class RemapNarrator {
    @ModifyExpressionValue(
            method = "keyPress",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/KeyEvent;shortcutKey()I", ordinal = 1)
    )
    private int shutNarrator(int original, @Local(argsOnly = true, name = "event") KeyEvent event) {
        return Main.NARRATOR_KEY.matches(event) ? 98 : -2;
    }
}