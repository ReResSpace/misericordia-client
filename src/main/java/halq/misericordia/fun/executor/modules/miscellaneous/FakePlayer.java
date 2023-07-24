package halq.misericordia.fun.executor.modules.miscellaneous;

import com.mojang.authlib.GameProfile;
import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingBoolean;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.UUID;

/**
 * @author Halq
 * @since 29/05/2023 at 18:49
 */

public class FakePlayer extends Module {

    SettingBoolean move = create("Move (dev)", false); //dev feature
    public FakePlayer() {
        super("FakePlayer", Category.WORLD);
    }

    private EntityOtherPlayerMP clonedPlayer;

    public void onEnable() {
        if (Minecraftable.mc.player == null || Minecraftable.mc.player.isDead) {
            onDisable();
            return;
        }

        clonedPlayer = new EntityOtherPlayerMP(Minecraftable.mc.world, new GameProfile(UUID.fromString("48efc40f-56bf-42c3-aa24-28e0c053f325"), "AuroraOnTop"));
        clonedPlayer.copyLocationAndAnglesFrom(Minecraftable.mc.player);
        clonedPlayer.rotationYawHead = Minecraftable.mc.player.rotationYawHead;
        clonedPlayer.rotationYaw = Minecraftable.mc.player.rotationYaw;
        clonedPlayer.rotationPitch = Minecraftable.mc.player.rotationPitch;
        clonedPlayer.setGameType(GameType.SURVIVAL);
        clonedPlayer.setHealth(36);
        Minecraftable.mc.world.addEntityToWorld(-12345, clonedPlayer);


        if (move.getValue()) {
            clonedPlayer = new EntityOtherPlayerMP(Minecraftable.mc.world, new GameProfile(UUID.fromString("48efc40f-56bf-42c3-aa24-28e0c053f325"), "AuroraOnTop"));
            clonedPlayer.copyLocationAndAnglesFrom(Minecraftable.mc.player);
            clonedPlayer.rotationYawHead = Minecraftable.mc.player.rotationYawHead;
            clonedPlayer.rotationYaw = Minecraftable.mc.player.rotationYaw;
            clonedPlayer.rotationPitch = Minecraftable.mc.player.rotationPitch;
            clonedPlayer.setGameType(GameType.SURVIVAL);
            clonedPlayer.setHealth(36);
            Minecraftable.mc.world.addEntityToWorld(-12345, clonedPlayer);

            new Thread(() -> {
                while (true) {
                    double speed = 0.1;

                    double directionX = -Math.sin(Math.toRadians(clonedPlayer.rotationYaw));
                    double directionZ = Math.cos(Math.toRadians(clonedPlayer.rotationYaw));

                    clonedPlayer.setPosition(clonedPlayer.posX + directionX * speed, clonedPlayer.posY, clonedPlayer.posZ + directionZ * speed);

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void onDisable() {
        if (Minecraftable.mc.world != null) {
            Minecraftable.mc.world.removeEntityFromWorld(-12345);
        }
    }

    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (isEnabled()){
            onDisable();
        }
    }


}