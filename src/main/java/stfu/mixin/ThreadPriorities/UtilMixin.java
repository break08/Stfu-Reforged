/*
package stfu.mixin.ThreadPriorities;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import stfu.config.StfuConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

@Mixin(Util.class)
public abstract class UtilMixin {
    @WrapOperation(
            method = {"makeExecutor", "makeIoExecutor"},
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/concurrent/Executors;newCachedThreadPool(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;"
            )
    )
    private static ExecutorService wrapCachedThreadPool(ThreadFactory factory, Operation<ExecutorService> original) {
        ThreadFactory customFactory = runnable -> {
            Thread thread = factory.newThread(runnable);
            thread.setPriority(StfuConfig.get().ioThreadPriority);
            return thread;
        };

        return original.call(customFactory);
    }
}

 */