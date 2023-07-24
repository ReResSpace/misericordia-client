package halq.misericordia.fun.executor.modules.combat.killaura;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

/**
 * @author Halq
 * @since 05/06/2023 at 18:06
 */

public class KillAuraRender {

    public static void renderChams1(){
        GlStateManager.pushMatrix();
        GlStateManager.color(KillAura.INSTANCE.red.getValue().floatValue() / 255.0F, KillAura.INSTANCE.green.getValue().floatValue() / 255.0F, KillAura.INSTANCE.blue.getValue().floatValue() / 255.0F, KillAura.INSTANCE.alpha.getValue().floatValue() / 255.0F);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPolygonOffset(1.0F, -1000000F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();
        GlStateManager.scale(1.001F, 1.001F, 1.001F);
        GlStateManager.translate(0F, -0.001f, 0.0F);
    }

    public static void renderChams2(){
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GlStateManager.popMatrix();
    }

    public static void renderWireframe1(){
        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(KillAura.INSTANCE.red.getValue().floatValue() / 255.0F, KillAura.INSTANCE.green.getValue().floatValue() / 255.0F, KillAura.INSTANCE.blue.getValue().floatValue() / 255.0F, KillAura.INSTANCE.alpha.getValue().floatValue() / 255.0F);
        GlStateManager.glLineWidth(1.5F);
    }
}
