package halq.misericordia.fun.executor.modules.render;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.events.RenderEvent;
import halq.misericordia.fun.executor.settings.SettingDouble;
import halq.misericordia.fun.utils.Minecraftable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Halq
 * @since 05/06/2023 at 16:57
 */

public class Breadcrumbs extends Module {

    SettingDouble red = create("Red", 255.0, 0, 255);
    SettingDouble green = create("Green", 0.0, 0, 255);
    SettingDouble blue = create("Blue", 107.0, 0, 255);
    SettingDouble alpha = create("Alpha", 255.0, 0, 255);
    private final List<double[]> positions;

    public Breadcrumbs() {
        super("Breadcrumbs", Category.RENDER);
        positions = new ArrayList<>();
    }

    @Override
    public void onRender3D(RenderEvent event) {
        if (!isEnabled()) return;

        GL11.glPushMatrix();
        GL11.glTranslated(-Minecraftable.mc.getRenderManager().renderPosX, -Minecraftable.mc.getRenderManager().renderPosY, -Minecraftable.mc.getRenderManager().renderPosZ);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1.5f);
        GL11.glColor4d(red.getValue() / 255.0, green.getValue() / 255.0, blue.getValue() / 255.0, alpha.getValue() / 255);

        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (double[] pos : positions) {
            GL11.glVertex3d(pos[0], pos[1], pos[2]);
        }
        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate() {
        if (!isEnabled()) return;

        double[] playerPos = {Minecraftable.mc.player.posX, Minecraftable.mc.player.posY, Minecraftable.mc.player.posZ};
        positions.add(playerPos);

        if (positions.size() > 150) {
            positions.remove(0);
        }
    }
}