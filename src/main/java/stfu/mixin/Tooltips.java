package stfu.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.DisableIf;
import stfu.config.StfuConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static stfu.Main.client;

@Mixin(Screen.class)
@DisableIf({"legacy", "legendarytooltips"})
public abstract class Tooltips {
    @Shadow public int width;
    @Shadow public int height;

    @ModifyVariable(method = {"renderTooltipInternal"},at = @At("HEAD"), index = 2, argsOnly = true)
    private List<ClientTooltipComponent> wrapLines(List<ClientTooltipComponent> original) {
        if (StfuConfig.get().tooltip_fix) {
            ArrayList<ClientTooltipComponent> components = new ArrayList<>();

            for (ClientTooltipComponent tooltipComponent : original) {
                if (!(tooltipComponent instanceof ClientTextTooltip component)) {
                    components.add(tooltipComponent);
                    continue;
                }
                final int length = components.size();
                client.font.getSplitter().splitLines(new FormattedText() {
                    @Override
                    public <T> Optional<T> visit(ContentConsumer<T> visitor) {
                        return visit((s, t) -> visitor.accept(t), Style.EMPTY);
                    }

                    @Override
                    public <T> Optional<T> visit(StyledContentConsumer<T> visitor, Style s) {
                        component.text.accept((index, style, codePoint) -> visitor.accept(style.applyTo(s), new String(Character.toChars(codePoint))).isEmpty());
                        return Optional.empty();
                    }
                }, width - 12, Style.EMPTY, (t, lastLineWrapped) -> components.add(ClientTooltipComponent.create(Language.getInstance().getVisualOrder(t))));
                if (components.size() == length)
                    components.add(ClientTooltipComponent.create(FormattedCharSequence.composite()));
            }

            return components;
        }
        return original;
    }

    @Inject(method = "renderTooltipInternal",
            at = @At(value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V",
                    shift = At.Shift.BEFORE),
            slice = @Slice(from = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/gui/screens/Screen;height:I", ordinal = 0)))
    private void reposition(PoseStack poseStack, List<ClientTooltipComponent> list, int mouseX, int mouseY,
                            CallbackInfo ci,
                            @Local(ordinal = 3) LocalIntRef xRef,   // n
                            @Local(ordinal = 4) LocalIntRef yRef,   // o
                            @Local(ordinal = 1) int width,          // m
                            @Local(ordinal = 2) int height) {       // p
        if (StfuConfig.get().tooltip_fix) {
            int screenWidth = this.width;
            int screenHeight = this.height;

            int x = Math.max(6, Math.min(xRef.get(), screenWidth - width - 6));
            int y = Math.max(6, Math.min(yRef.get(), screenHeight - height - 6));

            if (x == 6 && y != 6 && width + 12 <= screenWidth) {
                x = Math.min(screenWidth - width - 6, Math.max(mouseX - width / 2, 6));
                y = mouseY - height - 12;

                if (y < 6) {
                    if (screenHeight - mouseY > mouseY) y = mouseY + 12;
                    else y = 6;
                }
            }

            xRef.set(x);
            yRef.set(y);
        }
    }
}
