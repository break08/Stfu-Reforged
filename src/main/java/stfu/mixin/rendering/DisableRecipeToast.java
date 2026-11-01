package stfu.mixin.rendering;

import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.Config;

@Mixin(RecipeToast.class)
public class DisableRecipeToast {
    @Inject(method = "addOrUpdate", at = @At("HEAD"), cancellable = true)
    private static void disableRecipeToasts(ToastComponent toasts, RecipeHolder<?> holder, CallbackInfo ci) {
        if (!Config.get().recipeToasts) ci.cancel();
    }
}
