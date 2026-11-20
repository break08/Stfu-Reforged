package stfu.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.packs.repository.PackCompatibility;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PackCompatibility.class)
public class DisablePackVersionMismatchScreen {
    @ModifyReturnValue(method = "isCompatible", at = @At(value = "RETURN"))
    private boolean isCompatible(boolean original) {
        return true;
    }
}
