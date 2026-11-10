package stfu.mixin.rendering;

import net.minecraft.client.gui.components.toasts.RecipeToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.config.StfuConfig;

@Mixin(RecipeToast.class)
public class DisableRecipeToast {
    @Inject(method = "addOrUpdate", at = @At("HEAD"), cancellable = true)
    private static void disableRecipeToasts(ToastComponent p_94818_, Recipe<?> p_94819_, CallbackInfo ci) {
        if (!StfuConfig.get().recipeToasts) ci.cancel();
    }
}
