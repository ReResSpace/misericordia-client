package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

public class SettingDouble extends Setting<Double> {

    private final double minValue;
    private final double maxValue;
    private double value;

    public SettingDouble(String name, Module module, Double value, double minValue, double maxValue, boolean visible) {
        super(name, module, visible);
        this.value = value;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }


    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }
}
