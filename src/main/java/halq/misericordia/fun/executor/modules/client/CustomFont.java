package halq.misericordia.fun.executor.modules.client;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingBoolean;

/**
 * @author accessmodifier364
 * @since 28-Nov-2021
 */

public class CustomFont extends Module {

    public SettingBoolean shadow = create("Shadow", true);

    public CustomFont() {
        super("CustomFont", Category.OTHER);
    }
}
