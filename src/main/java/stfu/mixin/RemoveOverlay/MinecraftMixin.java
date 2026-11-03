package stfu.mixin.RemoveOverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Overlay;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    //?if <26.2 {
    @Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;overlay:Lnet/minecraft/client/gui/screens/Overlay;", opcode = Opcodes.GETFIELD))
    private Overlay overlay(Minecraft instance){
        return null;
    }
}
