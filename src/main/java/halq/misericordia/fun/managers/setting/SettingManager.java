package halq.misericordia.fun.managers.setting;

import halq.misericordia.fun.core.settingcore.Setting;
import halq.misericordia.fun.core.modulecore.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

@SuppressWarnings("rawtypes")
public class SettingManager {

    public static SettingManager INSTANCE;
    private final List<Setting> settings;

    public SettingManager() {
        settings = new ArrayList<>();
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public List<Setting> getSettingsInModule(Module module) {
        return settings.stream().filter(setting -> setting.getModule().equals(module)).collect(Collectors.toList());
    }
}