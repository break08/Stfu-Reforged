package stfu.mixin.ThreadPriorities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import stfu.config.Config;
import net.minecraft.util.Util;

@Mixin(Util.class)
public abstract class UtilMixin {
    @WrapOperation(
            method = {"/lambda\\$makeExecutor\\$\\d+/", "/lambda\\$makeIoExecutor\\$\\d+/"},
            at = @At(value = "NEW", target = "(Ljava/lang/Runnable;Ljava/lang/String;)Ljava/lang/Thread;")
    )
    private static void setName(Thread instance, String name, Operation<Void> original) {
        original.call(instance, name);
        instance.setPriority(Config.get().ioThreadPriority);
    }
}