package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

public class SettingBoolean extends Setting<Boolean> {

    private boolean value;

    public SettingBoolean(String name, Module module, Boolean value, boolean visible) {
        super(name, module, visible);
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
