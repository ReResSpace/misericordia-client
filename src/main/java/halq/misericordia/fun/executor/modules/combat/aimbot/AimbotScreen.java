package halq.misericordia.fun.executor.modules.combat.aimbot;

import halq.misericordia.fun.executor.modules.client.InteligentGui;
import halq.misericordia.fun.utils.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Halq
 * @since 23/06/2023 at 17:57
 */

public class AimbotScreen extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        renderEntity(mc.player, 220, 200, 50);
        RenderUtil.drawRoundedRect(150, 5, 300, 250, 5, new Color(13, 13, 23, 200));
        RenderUtil.drawRoundedRect(150, 5, 300, 10, 5, new Color(InteligentGui.INSTANCE.red.getValue().intValue(), InteligentGui.INSTANCE.green.getValue().intValue(), InteligentGui.INSTANCE.blue.getValue().intValue(), InteligentGui.INSTANCE.alpha.getValue().intValue()));

        /**                      head                     **/
        RenderUtil.drawLine(220, 100, 235, 85, 1.5f, new Color(255, 255, 255, 255).getRGB());
        RenderUtil.drawLine(235, 85, 250, 85, 1.5f, new Color(255, 255, 255, 255).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        mc.fontRenderer.drawStringWithShadow("Head", 257 * 1.4f, 83 * 1.4f, -1);
        GL11.glPopMatrix();

        RenderUtil.drawRoundedRect(270, 78, 14, 14, 5, new Color(26, 26, 46, 255).brighter());
        if(mouseX >= 270 && mouseX <= 270 + 14 && mouseY >= 78 && mouseY <= 78 + 14) {
            RenderUtil.drawRoundedRect(270, 78, 14, 14, 5, new Color(26, 26, 46, 255).brighter().brighter());
        }

        if(AimBotModule.INSTANCE.head.getValue()){
            RenderUtil.drawRoundedRect(270, 78, 14, 14, 5, new Color(InteligentGui.INSTANCE.red.getValue(), InteligentGui.INSTANCE.green.getValue(), InteligentGui.INSTANCE.blue.getValue(), 255));
        }

        /**                      chest                     **/
        RenderUtil.drawLine(220, 100, 220, 130, 1.5f, new Color(255, 255, 255, 255).getRGB());
        RenderUtil.drawLine(220, 130, 235, 145, 1.5f, new Color(255, 255, 255, 255).getRGB());
        RenderUtil.drawLine(235, 145, 250, 145, 1.5f, new Color(255, 255, 255, 255).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        mc.fontRenderer.drawStringWithShadow("Chest", 257 * 1.4f, 143 * 1.4f, -1);
        GL11.glPopMatrix();

        RenderUtil.drawRoundedRect(278, 138, 14, 14, 5, new Color(26, 26, 46, 255).brighter());
        if(mouseX >= 278 && mouseX <= 278 + 14 && mouseY >= 138 && mouseY <= 138 + 14) {
            RenderUtil.drawRoundedRect(278, 138, 14, 14, 5, new Color(26, 26, 46, 255).brighter().brighter());
        }

        if(AimBotModule.INSTANCE.chest.getValue()){
            RenderUtil.drawRoundedRect(278, 138, 14, 14, 5, new Color(InteligentGui.INSTANCE.red.getValue(), InteligentGui.INSTANCE.green.getValue(), InteligentGui.INSTANCE.blue.getValue(), 255));
        }

        if(AimBotModule.INSTANCE.head.getValue()){
            RenderUtil.drawRoundedRect(270, 78, 14, 14, 5, new Color(InteligentGui.INSTANCE.red.getValue(), InteligentGui.INSTANCE.green.getValue(), InteligentGui.INSTANCE.blue.getValue(), 255));
        }

        /**                      legs                     **/
        RenderUtil.drawLine(225, 175, 250, 175, 1.5f, new Color(255, 255, 255, 255).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        mc.fontRenderer.drawStringWithShadow("Legs", 257 * 1.4f, 174 * 1.4f, -1);
        GL11.glPopMatrix();

        RenderUtil.drawRoundedRect(270, 168, 14, 14, 5, new Color(26, 26, 46, 255).brighter());
        if(mouseX >= 270 && mouseX <= 270 + 14 && mouseY >= 168 && mouseY <= 168 + 14) {
            RenderUtil.drawRoundedRect(270, 168, 14, 14, 5, new Color(26, 26, 46, 255).brighter().brighter());
        }

        if(AimBotModule.INSTANCE.legs.getValue()){
            RenderUtil.drawRoundedRect(270, 168, 14, 14, 5, new Color(InteligentGui.INSTANCE.red.getValue(), InteligentGui.INSTANCE.green.getValue(), InteligentGui.INSTANCE.blue.getValue(), 255));
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseX >= 270 && mouseX <= 270 + 14 && mouseY >= 78 && mouseY <= 78 + 14) {
            AimBotModule.INSTANCE.chest.setValue(false);
            AimBotModule.INSTANCE.legs.setValue(false);
            AimBotModule.INSTANCE.head.setValue(!AimBotModule.INSTANCE.head.getValue());
        }

        if(mouseX >= 278 && mouseX <= 278 + 14 && mouseY >= 138 && mouseY <= 138 + 14) {
            AimBotModule.INSTANCE.head.setValue(false);
            AimBotModule.INSTANCE.legs.setValue(false);
            AimBotModule.INSTANCE.chest.setValue(!AimBotModule.INSTANCE.chest.getValue());

        }

        if(mouseX >= 270 && mouseX <= 270 + 14 && mouseY >= 168 && mouseY <= 168 + 14) {
            AimBotModule.INSTANCE.head.setValue(false);
            AimBotModule.INSTANCE.chest.setValue(false);
            AimBotModule.INSTANCE.legs.setValue(!AimBotModule.INSTANCE.legs.getValue());
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public static void renderEntity(EntityLivingBase entity, int posx, int posy, int scale) {
        GlStateManager.pushMatrix();
        GuiInventory.drawEntityOnScreen(posx, posy, scale, 0, 0, entity);
        GlStateManager.popMatrix();
    }
}
