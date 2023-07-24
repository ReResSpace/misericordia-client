package halq.misericordia.fun.core.modulecore;

import halq.misericordia.fun.executor.settings.*;
import halq.misericordia.fun.managers.setting.SettingManager;
import halq.misericordia.fun.events.RenderEvent;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author accessmodifier364
 * @author halqq
 * @since 24-Nov-2021
 */

abstract public class Module implements Minecraftable {

    private final String name;
    private final Category category;
    private int key;
    private boolean enabled;

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public ArrayList<String> blocks(){
        ArrayList<String> blocks = new ArrayList<>();
        blocks.add("dirt");
        return blocks;
    }

    public SettingBoolean create(String name, boolean value, boolean visible) {
        SettingBoolean setting = new SettingBoolean(name, this, value, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingScreen create(String name, boolean value, GuiScreen screen, boolean visible) {
        SettingScreen setting = new SettingScreen(name, this, value, screen,  visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingBoolean create(String name, boolean value) {
        SettingBoolean setting = new SettingBoolean(name, this, value, true);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingColor create(String name, Color color, boolean visible) {
        SettingColor setting = new SettingColor(name, this, color, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingColor create(String name, Color color) {
        SettingColor setting = new SettingColor(name, this, color, true);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingInteger create(String name, int value, int min, int max, boolean visible) {
        SettingInteger setting = new SettingInteger(name, this, value, min, max, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingDouble create(String name, double value, double min, double max, boolean visible) {
        SettingDouble setting = new SettingDouble(name, this, value, min, max, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingDouble create(String name, double value, double min, double max) {
        SettingDouble setting = new SettingDouble(name, this, value, min, max, true);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingInteger create(String name, int value, int min, int max) {
        SettingInteger setting = new SettingInteger(name, this, value, min, max, true);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingMode create(String name, String value, List<String> modes, boolean visible) {
        SettingMode setting = new SettingMode(name, this, value, modes, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingScreen create(String name, GuiScreen screen, boolean visible) {
        SettingScreen setting = new SettingScreen(name, this, true, screen, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingCategory create(String name, String value, List<String> modes, int isCategory) {
        SettingCategory setting = new SettingCategory(name, this, value, modes, isCategory);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingMode create(String name, String value, List<String> modes) {
        SettingMode setting = new SettingMode(name, this, value, modes, true);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingString create(String name, String value, boolean visible) {
        SettingString setting = new SettingString(name, this, value, visible);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public SettingString create(String name, String value) {
        SettingString setting = new SettingString(name, this, value, true);
        SettingManager.INSTANCE.getSettings().add(setting);
        return setting;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getKeyName() {
        if (key < 0) {
            return "NONE";
        }
        return Keyboard.getKeyName(key);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled() {
        if (!enabled) {
            enabled = true;
            enable();
        }
    }

    public void setDisabled() {
        if (enabled) {
            enabled = false;
            disable();
        }
    }

    public void toggle() {
        enabled = !enabled;

        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onUpdate() {
    }

    public void onSetting(){
    }

    public void onMotionUpdate() {
    }

    public void onPacketRecieve(){
    }

    public void onTick() {
    }

    public void onRender3D(RenderEvent event) {
    }

    public void onRender2D() {
    }

    public void enable() {
        MinecraftForge.EVENT_BUS.register(this);
        onEnable();
    }

    private void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        onDisable();
    }
}