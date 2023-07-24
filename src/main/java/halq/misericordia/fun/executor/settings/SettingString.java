package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

/**
 * @author Halq
 * @since 23/06/2023 at 18:57
 */

public class SettingString extends Setting<String> {

    private String value;

    public SettingString(String name, Module module, String value, boolean visible) {
        super(name, module, visible);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
