package halq.misericordia.fun.executor.modules.render.tracers;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.events.RenderEvent;
import halq.misericordia.fun.executor.settings.SettingDouble;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.util.stream.Collectors;

/**
 * @author Halq
 * @since 05/06/2023 at 16:57
 */

public class Tracers extends Module {

    SettingDouble red = create("Red", 0.0, 0.0, 255.0);
    SettingDouble green = create("Green", 0.0, 0.0, 255.0);
    SettingDouble blue = create("Blue", 0.0, 0.0, 255.0);
    SettingDouble alpha = create("Alpha", 0.0, 0.0, 255.0);
    SettingDouble width = create("Width", 0.0, 0.0, 10.0);
    SettingDouble range = create("Range", 0.0, 0.0, 100.0);
    public SettingDouble minSafeAreaX = create("MinSafeAreaX", 0.0, 0.0, 1.0);
    public SettingDouble maxSafeAreaX = create("MaxSafeAreaX", 1.0, 0.0, 1.0);
    public SettingDouble minSafeAreaY = create("MinSafeAreaY", 0.0, 0.0, 1.0);
    public SettingDouble maxSafeAreaY = create("MaxSafeAreaY", 1.0, 0.0, 1.0);


    public static Tracers INSTANCE;

    public Tracers() {
        super("Tracers", Category.RENDER);
        INSTANCE = this;
    }

    @Override
    public void onRender3D(RenderEvent event) {
        GL11.glPushMatrix();

        for (EntityPlayer player : getPlayers()) {
            double posX = player.lastTickPosX + (player.posX - player.lastTickPosX) * Minecraftable.mc.getRenderPartialTicks() - Minecraftable.mc.getRenderManager().viewerPosX;
            double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * Minecraftable.mc.getRenderPartialTicks() - Minecraftable.mc.getRenderManager().viewerPosY;
            double posZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * Minecraftable.mc.getRenderPartialTicks() - Minecraftable.mc.getRenderManager().viewerPosZ;
            double up = player.height;

            drawLine(posX, posY + player.getEyeHeight(), posZ, up, red.getValue().floatValue() / 255.0f, green.getValue().floatValue() / 255.0f, blue.getValue().floatValue() / 255.0f, alpha.getValue().floatValue() / 255.0f);
        }

        GL11.glPopMatrix();
    }

    private Iterable<EntityPlayer> getPlayers() {
        return Minecraftable.mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityPlayer && entity != Minecraftable.mc.player)
                .filter(entity -> Minecraftable.mc.player.getDistanceSq(entity) <= range.getValue() * range.getValue())
                .map(entity -> (EntityPlayer) entity)
                .collect(Collectors.toList());
    }

    private void drawLine(double posX, double posY, double posZ, double up, float red, float green, float blue, float opacity) {
        double pitch = Math.toRadians(Minecraftable.mc.player.rotationPitch);
        double yaw = Math.toRadians(Minecraftable.mc.player.rotationYaw);

        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float) pitch).rotateYaw(-(float) yaw);

        GL11.glLineWidth(width.getValue().floatValue());

        TracersRender.drawLineFromPosToPos(eyes.x, eyes.y + Minecraftable.mc.player.getEyeHeight(), eyes.z, posX, posY, posZ, up, red, green, blue, opacity, width.getValue().floatValue());
    }
}