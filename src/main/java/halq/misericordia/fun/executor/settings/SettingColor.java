package halq.misericordia.fun.executor.settings;

import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.core.settingcore.Setting;

import java.awt.*;

/**
 * @author Halq
 * @since 23/06/2023 at 21:44
 */

public class SettingColor extends Setting<Color> {

    private Color value;

    public SettingColor(String name, Module module, Color value, boolean visible) {
        super(name, module, visible);
        this.value = value;
    }

    @Override
    public Color getValue() {
        return value;
    }

    @Override
    public void setValue(Color value) {
        this.value = value;
    }

    public void setAlpha(int alpha) {
        this.value = new Color(this.value.getRed(), this.value.getGreen(), this.value.getBlue(), alpha);
    }

    public void setRed(int red) {
        this.value = new Color(red, this.value.getGreen(), this.value.getBlue(), this.value.getAlpha());
    }

    public void setGreen(int green) {
        this.value = new Color(this.value.getRed(), green, this.value.getBlue(), this.value.getAlpha());
    }

    public void setBlue(int blue) {
        this.value = new Color(this.value.getRed(), this.value.getGreen(), blue, this.value.getAlpha());
    }
}
