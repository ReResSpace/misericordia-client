package halq.misericordia.fun.executor.modules.combat.killaura;

import halq.misericordia.fun.core.modulecore.Category;
import halq.misericordia.fun.core.modulecore.Module;
import halq.misericordia.fun.executor.settings.SettingBoolean;
import halq.misericordia.fun.executor.settings.SettingDouble;
import halq.misericordia.fun.executor.settings.SettingMode;
import halq.misericordia.fun.managers.shader.Flow;
import halq.misericordia.fun.managers.shader.FramebufferShader;
import halq.misericordia.fun.utils.Minecraftable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Halq
 * @since 28/05/2023 at 14:32
 */

public class KillAura extends Module {

    public static KillAura INSTANCE = new KillAura();

    public SettingDouble range = create("Range", 2.5, 1, 6.0, true);
    public SettingBoolean autoSwitch = create("AutoSwitch", true);
    public SettingBoolean attackThroughWalls = create("AttackThroughWalls", false);
    public SettingBoolean rotateHead = create("RotateHead", true);
    public SettingBoolean rotateBody = create("RotateBody", true);
    public SettingBoolean criticals = create("Criticals", true);
    public SettingBoolean angleLimit = create("AngleLimit", true);
    public SettingBoolean chaseTarget = create("ChaseTarget", true);
    public SettingBoolean render = create("Render", true);
    public SettingMode renderMode = create("Mode", "Chams", Arrays.asList("WireFrame", "Chams", "Esp", "Shader"));
    public SettingDouble red = create("Red", 255.0, 0, 255, true);
    public SettingDouble green = create("Green", 0.0, 0, 255, true);
    public SettingDouble blue = create("Blue", 255.0, 0, 255, true);
    public SettingDouble alpha = create("Alpha", 255.0, 0, 255, true);

    public EntityPlayer target;

    public KillAura() {
        super("KillAura", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (Minecraftable.mc.world != null && Minecraftable.mc.player != null) {
            double closestDistanceSq = Double.MAX_VALUE;

            for (EntityPlayer entity : Minecraftable.mc.world.playerEntities) {
                if (entity != Minecraftable.mc.player) {
                    double distanceSq = Minecraftable.mc.player.getDistanceSq(entity);

                    if (distanceSq < closestDistanceSq && isInRange(Minecraftable.mc.player, entity)) {
                        closestDistanceSq = distanceSq;
                        target = entity;
                    }
                }
            }

            if (target != null && target.isEntityAlive() && canAttack(target)) {
                if (attackThroughWalls.getValue() || isVisible(Minecraftable.mc.player, target)) {
                    if (autoSwitch.getValue()) {
                        int bestSlot = getBestWeaponSlot();
                        if (bestSlot != -1) {
                            Minecraftable.mc.player.inventory.currentItem = bestSlot;
                        }
                    }

                    if (rotateHead.getValue()) {
                        rotateHeadTowards(Minecraftable.mc.player, target);
                    }

                    if (rotateBody.getValue()) {
                        rotateBodyTowards(Minecraftable.mc.player, target);
                    }

                    if (criticals.getValue()) {
                        attackEntityCritical(Minecraftable.mc.player, target);
                    } else {
                        attackEntity(Minecraftable.mc.player, target);
                    }
                }

                if (chaseTarget.getValue() && target != null && isInRange(Minecraftable.mc.player, target)) {
                    CheaseAI.chease(target);
                } else {
                    CheaseAI.stopChease();
                }
            }
        }
    }

    public boolean canAttack(Entity entity) {
        if (angleLimit.getValue()) {
            return isInFOV(entity, 45.0F);
        }

        return true;
    }

    private void attackEntity(EntityPlayerSP player, Entity target) {
        if (player == null || target == null)
            return;

        float attackDamage = getAttackDamage(player.getHeldItemMainhand());

        CPacketUseEntity packet = new CPacketUseEntity(target);
        player.connection.sendPacket(packet);
        target.attackEntityFrom(DamageSource.causePlayerDamage(player), attackDamage);
        player.swingArm(EnumHand.MAIN_HAND);
    }

    private void attackEntityCritical(EntityPlayerSP player, Entity target) {
        if (player == null || target == null)
            return;

        float attackDamage = getAttackDamage(player.getHeldItemMainhand());

        CPacketUseEntity packet = new CPacketUseEntity(target);
        player.connection.sendPacket(packet);
        target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), attackDamage);
        player.swingArm(EnumHand.MAIN_HAND);
    }

    private float getAttackDamage(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemSword))
            return 1.0F;

        ItemSword sword = (ItemSword) stack.getItem();

        return sword.getAttackDamage() + EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
    }

    private boolean isInRange(EntityPlayerSP player, Entity target) {
        double rangeSq = range.getValue() * range.getValue();

        return player.getDistanceSq(target) <= rangeSq;
    }

    private boolean isVisible(EntityPlayerSP player, Entity target) {
        RayTraceResult result = target.world.rayTraceBlocks(player.getPositionVector().add(0.0, player.getEyeHeight(), 0.0), target.getPositionVector().add(0.0, target.getEyeHeight(), 0.0), false, true, false);

        return result == null || result.typeOfHit == RayTraceResult.Type.MISS;
    }

    private int getBestWeaponSlot() {
        int bestSlot = -1;
        float maxDamage = 0.0F;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = Minecraftable.mc.player.inventory.getStackInSlot(i);

            if (stack != null && stack.getItem() instanceof ItemSword) {
                float damage = getAttackDamage(stack);
                if (damage > maxDamage) {
                    maxDamage = damage;
                    bestSlot = i;
                }
            }
        }

        return bestSlot;
    }

    private void rotateHeadTowards(EntityPlayerSP player, Entity target) {
        double diffX = target.posX - player.posX;
        double diffY = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D - (player.posY + player.getEyeHeight());
        double diffZ = target.posZ - player.posZ;
        double distance = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(diffY, distance) * 180.0D / Math.PI);

        player.rotationYawHead = player.rotationYaw + MathHelper.wrapDegrees(yaw - player.rotationYaw);
        player.rotationPitch = MathHelper.wrapDegrees(pitch);
    }

    private void rotateBodyTowards(EntityPlayerSP player, Entity target) {
        double diffX = target.posX - player.posX;
        double diffZ = target.posZ - player.posZ;
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;

        player.rotationYaw = yaw;
    }

    private boolean isInFOV(Entity entity, float fov) {
        EntityPlayerSP player = Minecraftable.mc.player;
        Vec3d playerEyes = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d lookVec = player.getLookVec();
        Vec3d targetVec = new Vec3d(entity.posX - player.posX, entity.posY - (player.posY + player.getEyeHeight()), entity.posZ - player.posZ).normalize();

        double dotProduct = lookVec.dotProduct(targetVec);
        double fovRad = Math.toRadians(fov);
        double maxViewDistance = range.getValue() * Math.cos(fovRad / 2.0);
        double maxDotProduct = Math.cos(Math.acos(dotProduct) - fovRad / 2.0);

        return dotProduct > maxDotProduct && playerEyes.distanceTo(entity.getPositionVector()) <= maxViewDistance;
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onDisable() {
        target = null;
    }
}
