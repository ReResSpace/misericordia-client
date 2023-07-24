package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

import java.util.List;

/**
 * @author accessmodifier364
 * @apiNote This class extends same as SettingString but when you will code the gui
 * or something, ask using setting instanceof SettingMode and cast the setting to
 * this class for getting the getModes() method.
 * @since 24-Nov-2021
 */

public class SettingMode extends Setting<String> {

    private final List<String> modes;
    private String value;

    public SettingMode(String name, Module module, String value, List<String> modes, boolean visible) {
        super(name, module, visible);
        this.value = value;
        this.modes = modes;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getModes() {
        return modes;
    }
}
