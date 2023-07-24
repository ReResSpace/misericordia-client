package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

//  SettingInteger hoter = create("cum", 50, 1, 4);

public class SettingInteger extends Setting<Integer> {

    private final int minValue;
    private final int maxValue;
    private int value;

    public SettingInteger(String name, Module module, Integer value, int minValue, int maxValue, boolean visible) {
        super(name, module, visible);
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }
}
