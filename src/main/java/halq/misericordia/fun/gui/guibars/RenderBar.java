package halq.misericordia.fun.gui.guibars;

import halq.misericordia.fun.executor.modules.client.InteligentGui;
import halq.misericordia.fun.utils.Minecraftable;
import halq.misericordia.fun.utils.utils.RenderUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author Halq
 * @since 18/06/2023 at 21:29
 */

public class RenderBar {

    public static void render(int mouseX, int mouseY, int category){
        RenderUtil.drawRoundedRect(290, 0, 75, 9, 5, new Color(13, 13, 23, 160));

        switch (category){
            case 1:
                RenderUtil.drawRect(290, 0, 20, 9, new Color(InteligentGui.INSTANCE.red.getValue() / 255.0f, InteligentGui.INSTANCE.green.getValue() / 255.0f, InteligentGui.INSTANCE.blue.getValue() / 255.0f, InteligentGui.INSTANCE.alpha.getValue() / 255.0f).getRGB());
                if(mouseX >= 290 && mouseX <= 310 && mouseY >= 0 && mouseY <= 10){
                    RenderUtil.drawRect(290, 0, 20, 9, new Color(InteligentGui.INSTANCE.red.getValue() / 255.0f, InteligentGui.INSTANCE.green.getValue() / 255.0f, InteligentGui.INSTANCE.blue.getValue() / 255.0f, InteligentGui.INSTANCE.alpha.getValue() / 255.0f).getRGB());
                }

                if(mouseX >= 310 && mouseX <= 345 && mouseY >= 0 && mouseY <= 10){
                    RenderUtil.drawRect(310, 0, 35, 9, new Color(35, 35, 35, 255).getRGB());
                }

                if(mouseX >= 345 && mouseX <= 365 && mouseY >= 0 && mouseY <= 10){
                    RenderUtil.drawRect(345, 0, 20, 9, new Color(35, 35, 35, 255).getRGB());
                }

                break;
            case 2:
                RenderUtil.drawRect(310, 0, 35, 9, new Color(66, 7, 245, 255).getRGB());
                if(mouseX >= 290 && mouseX <= 310 && mouseY >= 0 && mouseY <= 10){
                    RenderUtil.drawRect(290, 0, 20, 9, new Color(35, 35, 35, 255).getRGB());
                }
                if(mouseX >= 310 && mouseX <= 345 && mouseY >= 0 && mouseY <= 10){
                    RenderUtil.drawRect(310, 0, 35, 9, new Color(66, 7, 245, 255).getRGB());
                }
                if(mouseX >= 345 && mouseX <= 365 && mouseY >= 0 && mouseY <= 10){
                    RenderUtil.drawRect(345, 0, 20, 9, new Color(35, 35, 35, 255).getRGB());
                }
                break;
            case 3:
                RenderUtil.drawRoundedRect(345, 0, 20, 9, 5, new Color(255, 13, 50, 255).brighter());
                break;
        }

        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraftable.mc.fontRenderer.drawStringWithShadow("GUI", 297 * 2, 3 * 2, -1);
        Minecraftable.mc.fontRenderer.drawStringWithShadow("CONSOLE", 317 * 2, 3 * 2, -1);
        Minecraftable.mc.fontRenderer.drawStringWithShadow("HUD", 351 * 2, 3 * 2, -1);
        GL11.glPopMatrix();
    }
}
