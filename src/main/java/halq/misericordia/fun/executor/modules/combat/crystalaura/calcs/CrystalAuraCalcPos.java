package halq.misericordia.fun.executor.modules.combat.crystalaura.calcs;

import net.minecraft.util.math.BlockPos;

/**
 * @author Halq
 * @since 11/06/2023 at 15:32
 */

public class CrystalAuraCalcPos {
    public static class CalcPos {
        BlockPos blockPos;
        float targetDamage;

        public CalcPos(BlockPos blockPos, float targetDamage) {
            this.blockPos = blockPos;
            this.targetDamage = targetDamage;
        }

        public float getTargetDamage() {
            return targetDamage;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }
    }
}
