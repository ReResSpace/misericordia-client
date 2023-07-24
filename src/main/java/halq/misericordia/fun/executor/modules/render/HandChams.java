package halq.misericordia.fun.executor.modules.render;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingDouble;

/**
 * @author Halq
 * @since 04/06/2023 at 20:27
 */

public class HandChams extends Module {

    public static HandChams INSTANCE;
    public HandChams() {
        super("HandChams", Category.RENDER);
        INSTANCE = this;
    }

    public SettingDouble red = create("Red", 0.0, 0, 255);
    public SettingDouble green = create("Green", 153.0, 0, 255);
    public SettingDouble blue = create("Blue", 255.0, 0, 255);
    public SettingDouble alpha = create("Alpha", 170.0, 0, 255);
}
