package halq.misericordia.fun.executor.modules.combat.surround;

import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 07/06/2023 at 03:20
 */

public class SurroundPos implements Minecraftable {

    public static List<BlockPos> getSurroundPositions() {
        List<BlockPos> positions = new ArrayList<>();

        BlockPos playerPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);

        positions.add(playerPos.add(0, -1, 0));
        positions.add(playerPos.add(1, -1, 0));
        positions.add(playerPos.add(0, -1, 1));
        positions.add(playerPos.add(-1, -1, 0));
        positions.add(playerPos.add(0, -1, -1));

        positions.add(playerPos.add(1, 0, 0));
        positions.add(playerPos.add(0, 0, 1));
        positions.add(playerPos.add(-1, 0, 0));
        positions.add(playerPos.add(0, 0, -1));

        return positions;
    }
}