package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author Halq
 * @since 23/06/2023 at 18:53
 */

public class SettingScreen extends Setting<Boolean> {

    private boolean value;
    private final GuiScreen screen;

    public SettingScreen(String name, Module module, Boolean value, GuiScreen screen, boolean visible) {
        super(name, module, visible);
        this.value = value;
        this.screen = screen;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }

    public GuiScreen getScreen() {
        return screen;
    }
}

