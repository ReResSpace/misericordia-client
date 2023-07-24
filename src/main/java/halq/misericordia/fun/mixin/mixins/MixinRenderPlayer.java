package halq.misericordia.fun.mixin.mixins;

import halq.misericordia.fun.managers.module.ModuleManager;
import halq.misericordia.fun.executor.modules.render.HandChams;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Halq
 * @since 04/06/2023 at 20:34
 */

@Mixin({RenderPlayer.class})
public class MixinRenderPlayer {

    public void renderArmHook(AbstractClientPlayer clientPlayer, CallbackInfo ci, boolean isRightArm) {
        if (clientPlayer != Minecraft.getMinecraft().player && ModuleManager.INSTANCE.getModule("HandChams").isEnabled()) {
            return;
        }
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1.5F);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GL11.glColor4f(HandChams.INSTANCE.red.getValue().floatValue() / 255.0F, HandChams.INSTANCE.green.getValue().floatValue() / 255.0F, HandChams.INSTANCE.blue.getValue().floatValue() / 255.0F, HandChams.INSTANCE.alpha.getValue().floatValue() / 255.0F);
    }

    public void renderArmReturn(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        if (clientPlayer != Minecraft.getMinecraft().player && ModuleManager.INSTANCE.getModule("HandChams").isEnabled()) {
            return;
        }
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL11.glPopAttrib();
    }

    @Inject(method = "renderRightArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode = 181), cancellable = true)
    public void renderRightArmHook(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        renderArmHook(clientPlayer, ci, true);
    }

    @Inject(method = "renderRightArm", at = @At("RETURN"), cancellable = true)
    public void renderRightArmReturn(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        renderArmReturn(clientPlayer, ci);
    }

    @Inject(method = "renderLeftArm", at = @At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelPlayer;swingProgress:F", opcode = 181), cancellable = true)
    public void renderLeftArmHook(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        renderArmHook(clientPlayer, ci, false);
    }

    @Inject(method = "renderLeftArm", at = @At("RETURN"), cancellable = true)
    public void renderLeftArmReturn(AbstractClientPlayer clientPlayer, CallbackInfo ci) {
        renderArmReturn(clientPlayer, ci);
    }
}
