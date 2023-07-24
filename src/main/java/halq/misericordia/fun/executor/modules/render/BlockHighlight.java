package halq.misericordia.fun.executor.modules.render;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.events.RenderEvent;
import halq.misericordia.fun.executor.settings.SettingBoolean;
import halq.misericordia.fun.executor.settings.SettingDouble;
import halq.misericordia.fun.executor.settings.SettingInteger;
import halq.misericordia.fun.utils.utils.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.awt.*;

/**
 * @author Halq
 * @since 29/06/2023 at 21:44
 */

public class BlockHighlight extends Module {

    SettingBoolean lerp = create("Lerp", true);
    SettingInteger red = create("Red", 0, 0, 255);
    SettingInteger green = create("Green", 0, 0, 255);
    SettingInteger blue = create("Blue", 0, 0, 255);
    SettingInteger alpha = create("Alpha", 0, 0, 255);
    SettingDouble lineWidth = create("LineWidth", 1.5, 0, 10);

    private BlockPos previousPos;
    private BlockPos currentPos;
    private float renderX;
    private float renderY;
    private float renderZ;

    public BlockHighlight() {
        super("BlockHighlight", Category.RENDER);
    }

    @Override
    public void onRender3D(RenderEvent event) {
        RayTraceResult ray = mc.objectMouseOver;
        final AxisAlignedBB bb;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ray.getBlockPos();

            if (lerp.getValue()) {
                if (currentPos == null || !blockpos.equals(currentPos)) {
                    previousPos = currentPos;
                    currentPos = blockpos;
                }

                if (previousPos == null) {
                    renderX = blockpos.getX();
                    renderY = blockpos.getY();
                    renderZ = blockpos.getZ();
                } else {
                    float lerpAmount = 0.1f;
                    renderX = lerp(renderX, blockpos.getX(), lerpAmount);
                    renderY = lerp(renderY, blockpos.getY(), lerpAmount);
                    renderZ = lerp(renderZ, blockpos.getZ(), lerpAmount);
                }

                bb = new AxisAlignedBB(renderX - mc.getRenderManager().viewerPosX, renderY - mc.getRenderManager().viewerPosY, renderZ - mc.getRenderManager().viewerPosZ, renderX + 1 - mc.getRenderManager().viewerPosX, renderY + 1 - mc.getRenderManager().viewerPosY, renderZ + 1 - mc.getRenderManager().viewerPosZ);
            } else {
                bb = new AxisAlignedBB(blockpos.getX() - mc.getRenderManager().viewerPosX, blockpos.getY() - mc.getRenderManager().viewerPosY, blockpos.getZ() - mc.getRenderManager().viewerPosZ, blockpos.getX() + 1 - mc.getRenderManager().viewerPosX, blockpos.getY() + 1 - mc.getRenderManager().viewerPosY, blockpos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
            }

            if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                RenderUtil.drawEspOutline(bb, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue(), lineWidth.getValue().floatValue());
            }
        }
    }

    private static float lerp(float start, float end, float amount) {
        return start + amount * (end - start);
    }
}
