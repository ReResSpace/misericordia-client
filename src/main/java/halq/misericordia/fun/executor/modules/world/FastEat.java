package halq.misericordia.fun.executor.modules.world;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingBoolean;
import halq.misericordia.fun.executor.settings.SettingInteger;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * @author Halq
 * @since 02/07/2023 at 14:43
 */
    
public class FastEat extends Module {

        SettingBoolean instantEat = create("InstantEat", true);
        SettingInteger eatDelay =  create("EatDelay", 0, 0, 1000);

    public FastEat() {
        super("FastEat", Category.RENDER);
    }

    @Override
    public void onUpdate() {
        if (Minecraftable.mc.player == null) return;
        if (Minecraftable.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemFood) {
            if (instantEat.getValue()) {
                Minecraftable.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                Minecraftable.mc.playerController.processRightClick((EntityPlayerSP) Minecraftable.mc.player, Minecraftable.mc.world, EnumHand.MAIN_HAND);
            } else {
                if (Minecraftable.mc.player.getItemInUseMaxCount() >= eatDelay.getValue()) {
                    Minecraftable.mc.player.setActiveHand(EnumHand.MAIN_HAND);
                    Minecraftable.mc.playerController.processRightClick((EntityPlayerSP) Minecraftable.mc.player, Minecraftable.mc.world, EnumHand.MAIN_HAND);
                }
            }
        }
    }
}