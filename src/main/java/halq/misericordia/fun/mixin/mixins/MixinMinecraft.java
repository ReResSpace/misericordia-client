package halq.misericordia.fun.mixin.mixins;

import halq.misericordia.fun.managers.config.ConfigManager;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

@Mixin(value = {Minecraft.class})
public class MixinMinecraft {

    @Inject(method = "crashed", at = @At("HEAD"))
    public void crashed(CrashReport crash, CallbackInfo callbackInfo) {
        ConfigManager.INSTANCE.saveConfigs();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo callbackInfo) {
        ConfigManager.INSTANCE.saveConfigs();
    }
}