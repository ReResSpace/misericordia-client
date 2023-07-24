package halq.misericordia.fun.executor.modules.render.tracers;

import halq.misericordia.fun.utils.Minecraftable;
import org.lwjgl.opengl.GL11;

/**
 * @author Halq
 * @since 05/06/2023 at 18:02
 */

public class TracersRender implements Minecraftable {

    public static void drawLineFromPosToPos(double posX, double posY, double posZ, double posX2, double posY2, double posZ2, double up, float red, float green, float blue, float opacity, float width) {
        double minX = Tracers.INSTANCE.minSafeAreaX.getValue();
        double minY = Tracers.INSTANCE.minSafeAreaY.getValue();
        double maxX = Tracers.INSTANCE.maxSafeAreaX.getValue();
        double maxY = Tracers.INSTANCE.maxSafeAreaY.getValue();

        int screenWidth = mc.displayWidth;
        int screenHeight = mc.displayHeight;

        double startX = minX * screenWidth;
        double startY = minY * screenHeight;
        double endX = maxX * screenWidth;
        double endY = maxY * screenHeight;

        double clipStartX = Math.max(startX, 0);
        double clipStartY = Math.max(startY, 0);
        double clipEndX = Math.min(endX, screenWidth);
        double clipEndY = Math.min(endY, screenHeight);

        double deltaX = posX2 - posX;
        double deltaY = posY2 - posY;
        double deltaZ = posZ2 - posZ;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

        if (distance > 0) {
            double scale = Math.min(1, (clipEndX - clipStartX) / distance);
            deltaX *= scale;
            deltaY *= scale;
            deltaZ *= scale;
        }

        double lineEndX = posX + deltaX;
        double lineEndY = posY + deltaY;
        double lineEndZ = posZ + deltaZ;

        GL11.glPushAttrib(GL11.GL_SCISSOR_BIT);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) clipStartX, (int) (screenHeight - clipEndY), (int) (clipEndX - clipStartX), (int) (clipEndY - clipStartY));
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(width);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, opacity);
        GL11.glLoadIdentity();
        mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(posX, posY, posZ);
        GL11.glVertex3d(lineEndX, lineEndY, lineEndZ);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3d(1.0, 1.0, 1.0);
        GL11.glPopAttrib();
    }
}
