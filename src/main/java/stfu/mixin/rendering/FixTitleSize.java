package stfu.mixin.rendering;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public abstract class FixTitleSize {
    @Shadow public abstract Font getFont();
    @Shadow
    @Nullable
    protected Component title;
    @Shadow
    @Nullable
    protected Component subtitle;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 0))
    private void renderTitle(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        if (this.title != null) {
            int titleWidth = getFont().width(this.title);
            int maxWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth() - 16;

            if (titleWidth * 4 > maxWidth) {
                x = (float) maxWidth / titleWidth;
                y = (float) maxWidth / titleWidth;
            }
        }
        original.call(instance, x, y, z);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", ordinal = 1))
    private void renderSubTitle(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        if (this.subtitle != null) {
            int titleWidth = getFont().width(this.subtitle);
            int maxWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth() - 16;

            if (titleWidth * 2 > maxWidth) {
                x = (float) maxWidth / titleWidth;
                y = (float) maxWidth / titleWidth;
            }
        }
        original.call(instance, x, y, z);
    }
}