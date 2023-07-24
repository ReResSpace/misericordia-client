package halq.misericordia.fun.core.settingcore;

import halq.misericordia.fun.core.modulecore.Module;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

abstract public class Setting<T> {

    private final String name;
    private final Module module;
    private boolean visible; // I don't know why people uses generic Predicates for this.

    public Setting(String name, Module module, boolean visible) {
        this.name = name;
        this.module = module;
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public Module getModule() {
        return module;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    abstract public T getValue();

    abstract public void setValue(T value);
}
