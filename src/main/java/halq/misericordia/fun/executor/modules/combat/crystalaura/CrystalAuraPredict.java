package halq.misericordia.fun.executor.modules.combat.crystalaura;

import halq.misericordia.fun.executor.modules.combat.crystalaura.calcs.CrystalAuraCalcPos;
import halq.misericordia.fun.executor.modules.combat.crystalaura.module.CrystalAuraModule;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/**
 * @author Halq
 * @since 11/06/2023 at 19:30
 */

public class CrystalAuraPredict implements Minecraftable {

    public static Vec3d predictCrystalPosition(CrystalAuraCalcPos.CalcPos calcPos) {
        BlockPos crystalPos = calcPos.getBlockPos();
        double x = crystalPos.getX() + 0.5;
        double y = crystalPos.getY() - 0.5;
        double z = crystalPos.getZ() + 0.5;

        double xDiff = x - mc.player.posX;
        double yDiff = y - (mc.player.posY + mc.player.getEyeHeight());
        double zDiff = z - mc.player.posZ;
        double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);

        double speed = 0.5;
        double predictionTime = distance / speed;

        double motionX = mc.player.motionX;
        double motionY = mc.player.motionY;
        double motionZ = mc.player.motionZ;

        double predictedX = x + (motionX * predictionTime);
        double predictedY = y + (motionY * predictionTime);
        double predictedZ = z + (motionZ * predictionTime);

        return new Vec3d(predictedX, predictedY, predictedZ);
    }

    public static void caAttackPredict(){
        Vec3d predictedCrystalPos = predictCrystalPosition(CrystalAuraModule.crystalPosCalc);
        double x = predictedCrystalPos.x;
        double y = predictedCrystalPos.y;
        double z = predictedCrystalPos.z;

        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, new BlockPos(x, y, z), EnumFacing.UP));
        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, new BlockPos(x, y, z), EnumFacing.UP));
    }
}
