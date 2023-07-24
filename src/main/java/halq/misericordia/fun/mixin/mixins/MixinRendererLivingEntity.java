package halq.misericordia.fun.mixin.mixins;

import halq.misericordia.fun.managers.module.ModuleManager;
import halq.misericordia.fun.executor.modules.combat.killaura.KillAura;
import halq.misericordia.fun.executor.modules.combat.killaura.KillAuraRender;
import halq.misericordia.fun.managers.shader.Flow;
import halq.misericordia.fun.managers.shader.FramebufferShader;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(RenderLivingBase.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected ModelBase mainModel;

    protected MixinRendererLivingEntity(RenderManager renderManager) {
        super(renderManager);
    }

    @Shadow
    protected abstract void renderModel(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor);

    @Inject(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"), cancellable = true)
    private void renderModel(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo info) {
        if (ModuleManager.INSTANCE.getModule(KillAura.class).isEnabled()) {
            entityLivingBase.hurtTime = 0;
            if (KillAura.INSTANCE.target != null && KillAura.INSTANCE.render.getValue()) {
                if (entityLivingBase == KillAura.INSTANCE.target) {
                    if (KillAura.INSTANCE.renderMode.getValue().equalsIgnoreCase("Chams")) {
                        KillAuraRender.renderChams1();
                        this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                        KillAuraRender.renderChams2();
                    } else if (KillAura.INSTANCE.renderMode.getValue().equalsIgnoreCase("Wireframe")) {
                        KillAuraRender.renderWireframe1();
                        this.mainModel.render(entityLivingBase, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                    }
                }
            }
        }
    }
}