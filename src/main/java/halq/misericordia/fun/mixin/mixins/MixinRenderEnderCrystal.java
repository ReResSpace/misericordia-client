package halq.misericordia.fun.mixin.mixins;

import halq.misericordia.fun.executor.modules.render.chams.crystalchams.CrystalChamsModule;
import halq.misericordia.fun.managers.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;

/**
 * @author Halq
 * @since 25/06/2023 at 18:26
 */

@Mixin(value={RenderEnderCrystal.class})
public abstract class MixinRenderEnderCrystal {
    @Shadow
    public ModelBase modelEnderCrystal;
    @Shadow
    public ModelBase modelEnderCrystalNoBase;

    @Final

    @Shadow
    public abstract void doRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9);

    @Redirect(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void render1(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        if (!ModuleManager.INSTANCE.getModule(CrystalChamsModule.class).isEnabled()) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }

    @Redirect(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal = 1))
    private void render2(ModelBase var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
        if (!ModuleManager.INSTANCE.getModule(CrystalChamsModule.class).isEnabled()) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }

    @Inject(method = {"doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V"}, at = {@At(value = "RETURN")}, cancellable = true)
    public void IdoRender(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9, CallbackInfo var10) {
        if (ModuleManager.INSTANCE.getModule(CrystalChamsModule.class).isEnabled()) {
            Minecraft mc = Minecraft.getMinecraft();
            GL11.glPushMatrix();
            GlStateManager.translate((double) var2, (double) var4, (double) var6);

            float var13 = (float) var1.innerRotation + var9;

            float var14 = MathHelper.sin((float) (var13 * 0.2f)) / 2.0f + 0.5f;

            var14 += var14 * var14;

            GL11.glScaled(CrystalChamsModule.INSTANCE.scale.getValue(), CrystalChamsModule.INSTANCE.scale.getValue(), CrystalChamsModule.INSTANCE.scale.getValue());

            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity) var1, 0.0f, var13 * CrystalChamsModule.INSTANCE.speed.getValue().floatValue(), var14 * CrystalChamsModule.INSTANCE.bounce.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
            } else {
                this.modelEnderCrystalNoBase.render((Entity) var1, 0.0f, var13 * CrystalChamsModule.INSTANCE.speed.getValue().floatValue(), var14 * CrystalChamsModule.INSTANCE.bounce.getValue().floatValue(), 0.0f, 0.0f, 0.0625f);
            }

            GL11.glPopMatrix();

        }
    }
}
