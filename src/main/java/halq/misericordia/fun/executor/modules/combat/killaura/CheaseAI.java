package halq.misericordia.fun.executor.modules.combat.killaura;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Predicate;

/**
 * @author Halq
 * @since 04/06/2023 at 19:59
 */

public class CheaseAI implements Minecraftable {

    static IBaritone baritone = BaritoneAPI.getProvider().getBaritoneForPlayer(Minecraft.getMinecraft().player);

    public static void chease(EntityPlayer player) {
        if (baritone != null) {
            Predicate<Entity> followPredicate = (entity) -> entity == player;
            baritone.getFollowProcess().follow(followPredicate);
            BaritoneAPI.getSettings().allowSprint.value = true;
            if (targetIsFast(player) && !mc.player.isSprinting()) {
                mc.player.setSprinting(true);
            }
        }
    }

    public static void stopChease() {
        if (baritone != null) {
            baritone.getFollowProcess().cancel();
        }
    }

    public static boolean targetIsFast(EntityPlayer player) {
        EntityPlayer currentUser = mc.player;

        if (currentUser == null) {
            return false;
        }

        double currentPlayerSpeed = Math.sqrt(player.motionX * player.motionX + player.motionZ * player.motionZ);
        double currentUserSpeed = Math.sqrt(currentUser.motionX * currentUser.motionX + currentUser.motionZ * currentUser.motionZ);

        return currentPlayerSpeed > currentUserSpeed;
    }
}
