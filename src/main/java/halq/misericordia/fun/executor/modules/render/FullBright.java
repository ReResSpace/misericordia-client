package halq.misericordia.fun.executor.modules.render;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingMode;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.util.Arrays;

/**
 * @author halq
 * @since 30-11-2021
 */

public class FullBright extends Module {

    SettingMode mode = create("Mode", "Setting", Arrays.asList("Setting", "Potion"));
    private float save;

    public FullBright() {
        super("FullBright", Category.RENDER);
    }

    @Override
    public void onEnable() {
        save = mc.gameSettings.gammaSetting;
    }

    @Override
    public void onUpdate() {
        if (mode.getValue().equalsIgnoreCase("Setting")) {
            mc.gameSettings.gammaSetting = 1000;
        } else if (mode.getValue().equalsIgnoreCase("Potion")) {
            mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION));
        }
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = save;
        mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
    }
}
