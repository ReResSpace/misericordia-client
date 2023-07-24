package halq.misericordia.fun.executor.modules.render;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.events.PacketEvent;
import halq.misericordia.fun.utils.utils.RenderUtil;
import halq.misericordia.fun.utils.utils.TimerUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Timer;

/**
 * @author Halq
 * @since 30/06/2023 at 21:19
 */

public class CrossHair extends Module {

    public CrossHair() {
        super("CrossHair", Category.RENDER);
    }

    TimerUtil timer = new TimerUtil();

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            event.setCanceled(true);
        }

        int screenWidth = new ScaledResolution(mc).getScaledWidth();
        int screenHeight = new ScaledResolution(mc).getScaledHeight();

        RenderUtil.drawLine((float) ((screenWidth / 2)), (float) ((screenHeight / 2) - 10), (float) ((screenWidth / 2)), (float) ((screenHeight / 2) - 2), 3, 0xffffffff);
        RenderUtil.drawLine((float) ((screenWidth / 2)), (float) ((screenHeight / 2) + 10), (float) ((screenWidth / 2)), (float) ((screenHeight / 2) + 2), 3, 0xffffffff);
        RenderUtil.drawLine((float) ((screenWidth / 2) - 10), (float) ((screenHeight / 2)), (float) ((screenWidth / 2) - 2), (float) ((screenHeight / 2)), 3, 0xffffffff);
        RenderUtil.drawLine((float) ((screenWidth / 2) + 10), (float) ((screenHeight / 2)), (float) ((screenWidth / 2) + 2), (float) ((screenHeight / 2)), 3, 0xffffffff);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK) {
            timer.reset();
        }
    }
}
