package halq.misericordia.fun.executor.modules.render.chams.crystalchams;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingDouble;
import halq.misericordia.fun.executor.settings.SettingInteger;
import halq.misericordia.fun.executor.settings.SettingMode;
import halq.misericordia.fun.managers.shader.Flow;
import halq.misericordia.fun.managers.shader.FramebufferShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Halq
 * @since 25/06/2023 at 18:23
 */

public class CrystalChamsModule extends Module {

    public SettingDouble scale = create("Scale", 1.0, 0.1, 3.0);
    public SettingInteger speed = create("Speed", 3, 1, 50);
    public SettingDouble bounce = create("Bounce", 1.0, 0.1, 10);
    public SettingMode mode = create("Mode", "Shader", Arrays.asList("Shader", "Solid", "Wire"));

    public static CrystalChamsModule INSTANCE;
    public CrystalChamsModule() {
        super("CrystalChams", Category.RENDER);
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onRender3D(RenderWorldLastEvent event) {
        FramebufferShader framebufferShader = Flow.Flow_Shader;
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        framebufferShader.startDraw(event.getPartialTicks());
        for (Entity entity : mc.world.loadedEntityList) {
            if ( entity == mc.getRenderViewEntity()) continue;
            if (entity instanceof EntityEnderCrystal) {
                Vec3d vector = getInterpolatedRenderPos(entity, event.getPartialTicks());
                Objects.requireNonNull(mc.getRenderManager().getEntityRenderObject(entity)).doRender(entity, vector.x, vector.y, vector.z, entity.rotationYaw, event.getPartialTicks());
            }
        }
        framebufferShader.stopDraw();
        GlStateManager.color(1f, 1f, 1f);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
    }

    public static Vec3d getInterpolatedRenderPos(final Entity entity, final float ticks) {
        return interpolateEntity(entity, ticks).subtract(Minecraft.getMinecraft().getRenderManager().viewerPosX, Minecraft.getMinecraft().getRenderManager().viewerPosY, Minecraft.getMinecraft().getRenderManager().viewerPosZ);
    }

    public static Vec3d interpolateEntity(Entity entity, float time) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time,
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
    }
}
