package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

import java.util.List;

/**
 * @author Halq
 * @since 16/06/2023 at 17:25
 */

public class SettingCategory extends Setting<String> {

    private final List<String> modes;
    private String value;

    public SettingCategory(String name, Module module, String value, List<String> modes, int isCategory) {
        super(name, module, true);
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
