package halq.misericordia.fun.executor.modules.combat.crystalaura.calcs;

import halq.misericordia.fun.executor.modules.combat.crystalaura.module.CrystalAuraModule;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 10/06/2023 at 04:23
 */

public class CrystalAuraCalcs implements Minecraftable {

    public static List<BlockPos> getSphere(double radius) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        BlockPos pos = new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
        for (int x = pos.getX() - (int) radius; x <= pos.getX() + radius; ++x) {
            for (int y = pos.getY() - (int) radius; y < pos.getY() + radius; ++y) {
                for (int z = pos.getZ() - (int) radius; z <= pos.getZ() + radius; ++z) {
                    double distance = (pos.getX() - x) * (pos.getX() - x) + (pos.getZ() - z) * (pos.getZ() - z) + (pos.getY() - y) * (pos.getY() - y);
                    BlockPos position = new BlockPos(x, y, z);
                    if (distance < radius * radius && !mc.world.getBlockState(position).getBlock().equals(Blocks.AIR)) {
                        posList.add(position);
                    }
                }
            }
        }
        return posList;
    }

    public static CrystalAuraCalcPos.CalcPos calculatePositions(EntityPlayer target) {
        CrystalAuraCalcPos.CalcPos posToReturn = new CrystalAuraCalcPos.CalcPos(BlockPos.ORIGIN, 0.5f);
        for (BlockPos pos : getSphere(CrystalAuraModule.INSTANCE.range.getValue())) {
            float targetDamage = CrystalAuraCalcDamage.calculateDamage(mc.world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, target, true);
            float selfDamage = CrystalAuraCalcDamage.calculateDamage(mc.world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, mc.player, true);
            if (canPlaceCrystal(pos, true, true, CrystalAuraModule.INSTANCE.multiPlace.getValue(), false)) {
                if (mc.player.getDistance(pos.getX() + 0.5f, pos.getY() + 1.0f, pos.getZ() + 0.5f) > square(CrystalAuraModule.INSTANCE.range.getValue())) continue;
                if (selfDamage > CrystalAuraModule.INSTANCE.maxDmg.getValue()) continue;
                if (targetDamage < CrystalAuraModule.INSTANCE.minDmg.getValue()) continue;
                if (targetDamage > posToReturn.getTargetDamage()) posToReturn = new CrystalAuraCalcPos.CalcPos(pos, targetDamage);
            }
        }
        return posToReturn;
    }

    public static boolean canPlaceCrystal(BlockPos pos, boolean check, boolean entity, boolean multiPlace, boolean firePlace) {
        if(mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) || mc.world.getBlockState(pos).getBlock().equals(Blocks.OBSIDIAN)) {
            if (!mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !(firePlace && mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.FIRE))) return false;
            if (!mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) return false;
            BlockPos boost = pos.add(0, 1, 0);
            return !entity || mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(boost.getX(), boost.getY(), boost.getZ(), boost.getX() + 1, boost.getY() + (check ? 2 : 1), boost.getZ() + 1), e -> !(e instanceof EntityEnderCrystal) || multiPlace).size() == 0;
        }
        return false;
    }

    public static double square(double input) {
        return input * input;
    }
}
