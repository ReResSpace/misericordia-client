package halq.misericordia.fun.executor.modules.combat.surround;

import halq.misericordia.fun.utils.Minecraftable;
import halq.misericordia.fun.utils.utils.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

/**
 * @author Halq
 * @since 10/06/2023 at 03:21
 */

public class SurroundRender implements Minecraftable {

    public static void renderSurround(boolean isPlaceable, BlockPos pos, float r, float g, float b, float a) {
        final AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mc.getRenderManager().viewerPosX, pos.getY() - mc.getRenderManager().viewerPosY, pos.getZ() - mc.getRenderManager().viewerPosZ, pos.getX() + 1 - mc.getRenderManager().viewerPosX, pos.getY() + (1) - mc.getRenderManager().viewerPosY, pos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
        if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
            if (isPlaceable) {
                Color color = new Color(r, g, b, a);
                Color color2 = new Color(r, g, b, a / 5.5F);

                RenderUtil.drawGradientFilledBox(bb, color, color2);
                RenderUtil.drawGradientBlockOutline(bb, color, color, 1.7f);
            }
        }
    }
}
