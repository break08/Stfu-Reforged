package stfu.mixin.rendering;

import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ParticleEngine.class)
public class ParticleCulling {
    /*
    @WrapWithCondition(method = "renderParticleType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/Camera;F)V"))

    private static boolean renderParticles(Particle instance, VertexConsumer vertexConsumer, Camera camera, float v) {
        return client.levelRenderer.cullingFrustum.isVisible(instance.getBoundingBox());
    }

     */
}