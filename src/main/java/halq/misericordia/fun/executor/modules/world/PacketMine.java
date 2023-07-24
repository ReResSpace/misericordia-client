package halq.misericordia.fun.executor.modules.world;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.events.RenderEvent;
import halq.misericordia.fun.executor.settings.SettingDouble;
import halq.misericordia.fun.executor.settings.SettingInteger;
import halq.misericordia.fun.utils.utils.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Halq
 * @since 02/07/2023 at 14:59
 */

public class PacketMine extends Module {

    private final List<BlockPos> blockList = new ArrayList<>();
    SettingInteger maxBlocksList = create("MaxBlocksList", 4, 1, 10);
    SettingDouble range = create("Range", 4.5, 2.0, 6.0);
    BlockPos breakingPos;

    public PacketMine() {
        super("PacketMine", Category.WORLD);
    }

    @Override
    public void onUpdate() {
        if (mc.playerController.isHittingBlock) {
            BlockPos blockPos = mc.objectMouseOver.getBlockPos();
            if (!blockList.contains(blockPos)) {
                blockList.add(blockPos);
            }
        }

        if (blockList.size() > maxBlocksList.getValue()) {
            blockList.remove(0);
        }

        if (blockList.isEmpty()) return;
        BlockPos pos = blockList.get(blockList.size() - 1);

        if (mc.player.getDistance(pos.getX(), pos.getY(), pos.getZ()) <= range.getValue()) {
            breakingPos = pos;
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
        }

        if(mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
            blockList.remove(pos);
        }
    }

    @Override
    public void onRender3D(RenderEvent event) {
        Block blockB = mc.world.getBlockState(breakingPos).getBlock();
        if (breakingPos != null && blockB != Blocks.AIR) {
            AxisAlignedBB bb = new AxisAlignedBB(breakingPos.getX() - mc.getRenderManager().viewerPosX, breakingPos.getY() - mc.getRenderManager().viewerPosY, breakingPos.getZ() - mc.getRenderManager().viewerPosZ, breakingPos.getX() + 1 - mc.getRenderManager().viewerPosX, breakingPos.getY() + 1 - mc.getRenderManager().viewerPosY, breakingPos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
            if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                RenderUtil.drawESP(bb, 0.0f, 255.0f, 1.0f, 140.0f);
            }
        }

        if (blockList.isEmpty()) return;
        if (blockList.size() > 1) {
            List<BlockPos> nextBlocks = blockList.subList(0, blockList.size() - 1);

            for (BlockPos pos : nextBlocks) {
                Block block = mc.world.getBlockState(pos).getBlock();
                if(block != Blocks.AIR) {
                AxisAlignedBB bb = new AxisAlignedBB(pos.getX() - mc.getRenderManager().viewerPosX, pos.getY() - mc.getRenderManager().viewerPosY, pos.getZ() - mc.getRenderManager().viewerPosZ, pos.getX() + 1 - mc.getRenderManager().viewerPosX, pos.getY() + 1 - mc.getRenderManager().viewerPosY, pos.getZ() + 1 - mc.getRenderManager().viewerPosZ);
                if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                    RenderUtil.drawESP(bb, 255.0f, 0.0f, 0.0f, 140.0f);
                }
            }
            }
        }
    }

    @Override
    public void onDisable() {
        blockList.clear();
    }
}
