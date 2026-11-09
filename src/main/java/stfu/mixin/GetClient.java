package stfu.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.Main;

@Mixin(Minecraft.class)
public class GetClient {
    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;instance:Lnet/minecraft/client/Minecraft;", opcode = Opcodes.PUTSTATIC, shift = At.Shift.AFTER))
    private void stfu$setClient(GameConfig gameConfig, CallbackInfo ci) {
        Main.client = (Minecraft) (Object) this;
    }
}