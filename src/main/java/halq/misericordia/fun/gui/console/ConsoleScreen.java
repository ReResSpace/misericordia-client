package halq.misericordia.fun.gui.console;

import halq.misericordia.fun.executor.modules.client.Console;
import halq.misericordia.fun.gui.console.core.ConsoleAPI;
import halq.misericordia.fun.gui.guibars.ClickBar;
import halq.misericordia.fun.gui.guibars.RenderBar;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

/**
 * @author Halq
 * @since 17/06/2023 at 15:09
 */

public class ConsoleScreen extends GuiScreen {

    int x = 150;
    int y = 30;
    public static String VERSION = "b0.2";

    public ConsoleScreen(){
        DrawConsole.closedX = x;
        DrawConsole.closedY = y;
        DrawConsole.openX = x;
        DrawConsole.openY = y;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        DrawConsole.drawConsole(mouseX, mouseY);
        RenderBar.render(mouseX, mouseY, 2);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton){
        DrawConsole.mouseClicked(mouseX, mouseY, mouseButton);
        ClickBar.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state){
        DrawConsole.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode){
        DrawConsole.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui(){
        DrawConsole.x = DrawConsole.closedX;
        DrawConsole.y = DrawConsole.closedY;
        DrawConsole.renderX = -1500;
        ConsoleAPI.log("Welcome for Misericordia console, type \"help\" for a list of commands.");
        DrawConsole.isTyping = true;
        if(Console.INSTANCE.blur.getValue()){
            mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }

    @Override
    public void onGuiClosed(){
        DrawConsole.closedX = DrawConsole.x;
        DrawConsole.closedY = DrawConsole.y;
        DrawConsole.isTyping = false;
        DrawConsole.selectAll = false;

        try {
            if (mc.entityRenderer.isShaderActive()) {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        } catch (Exception ignored) {
        }
    }
}
