package halq.misericordia.fun.executor.modules.render;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Halq
 * @since 10/06/2023 at 15:55
 */

public class NoRender extends Module {

    public static NoRender INSTANCE;

    public NoRender() {
        super("NoRender", Category.RENDER);
        INSTANCE = this;
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Minecraftable.mc.effectRenderer.clearEffects(Minecraftable.mc.world);
    }
}
