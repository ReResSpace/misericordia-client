package halq.misericordia.fun.gui.console;

import halq.misericordia.fun.gui.console.core.CommandExc;
import halq.misericordia.fun.gui.console.core.ConsoleAPI;
import halq.misericordia.fun.gui.console.core.TabComplete;
import halq.misericordia.fun.managers.text.TextManager;
import halq.misericordia.fun.utils.Minecraftable;
import halq.misericordia.fun.utils.utils.RenderUtil;
import halq.misericordia.fun.utils.utils.TimerUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

public class DrawConsole {

    public static DrawConsole instance;

    static int renderX;
    static int renderY;
    static int closedX;
    static int closedY;
    static int openX;
    static int openY;
    static int textY;
    static int x;
    static int y;
    static boolean drag;
    static int dragX;
    static int dragY;
    public static boolean isTyping = false;
    static final TimerUtil timer = new TimerUtil();
    public static boolean idling;
    public static boolean selectAll;
    public static final int maxLogs = 30;
    public static List<String> logs = new ArrayList<>();
    public static String inputString = "";
    public static List<String> lastInputString = new ArrayList<>();
    public static int lastInputStringIndex = 0;
    public DrawConsole(int x, int y){
        instance = this;
        DrawConsole.x = x;
        DrawConsole.y = y;
    }

    public static void drawConsole(int mouseX, int mouseY) {
        float dt = Minecraftable.mc.getRenderPartialTicks();

        if (drag) {
            x = dragX + mouseX;
            y = dragY + mouseY;
            ConsoleAPI.log("Dragging " + "x: " + x + " y: " + y);
        }

        renderX = (int) RenderUtil.lerp(renderX, x, dt);
        renderY = (int) RenderUtil.lerp(renderY, y, dt);

        RenderUtil.drawRoundedRect(renderX, renderY, 400, 200, 5, new Color(13, 13, 50, 160));
        RenderUtil.drawRoundedRect(renderX, renderY, 400, 15, 5, new Color(85, 18, 140, 255));
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraftable.mc.fontRenderer.drawStringWithShadow("> Misericordia - console / terminal", (renderX + 5) * 2, (renderY + 5) * 2, -1);
        GL11.glPopMatrix();

        if (mouseX > renderX + 382 && mouseX < renderX + 393 && mouseY > renderY + 2 && mouseY < renderY + 15) {
            RenderUtil.drawRect(renderX + 383, renderY + 2, 10, 10, new Color(255, 0, 0, 160).getRGB());
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            Minecraftable.mc.fontRenderer.drawStringWithShadow("X", (renderX + 385) * 2 + 3, (renderY + 5) * 2, -1);
            GL11.glPopMatrix();
        } else {
            RenderUtil.drawRect(renderX + 383, renderY + 2, 10, 10, new Color(255, 0, 0, 80).getRGB());
            GL11.glPushMatrix();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            Minecraftable.mc.fontRenderer.drawStringWithShadow("X", (renderX + 385) * 2 + 3, (renderY + 5) * 2, -1);
            GL11.glPopMatrix();
        }

        RenderUtil.drawRoundedRect(renderX + 5, renderY + 180, 390, 15, 5, new Color(32, 13, 61, 255));

        if(mouseX > renderX + 5 && mouseX < renderX + 395 && mouseY > renderY + 180 && mouseY < renderY + 195 || isTyping){
            float alpha = (float) (Math.sin(System.currentTimeMillis() / 500.0) * 0.5 + 0.5); // Varie o valor 1000.0 para ajustar a velocidade do efeito
            int alphaValue = (int) (alpha * 255);

            RenderUtil.drawLine(renderX + 5, renderY + 180, renderX + 395, renderY + 180, 1, new Color(255, 0, 240, alphaValue).getRGB());
        }
        if (isTyping) {
            Minecraftable.mc.fontRenderer.drawStringWithShadow("> " + TextFormatting.LIGHT_PURPLE + inputString + TextFormatting.WHITE +  TextManager.INSTANCE.getIdleSign(), (renderX + 10), (renderY + 183), -1);
        } else {
            Minecraftable.mc.fontRenderer.drawStringWithShadow("> "+ TextFormatting.LIGHT_PURPLE + inputString, (renderX + 10), (renderY + 183), -1);
        }

        int logCount = Math.min(maxLogs, logs.size());
        int startIndex = Math.max(0, logs.size() - logCount); // Garante que startIndex não seja negativo

        int lineHeight = 5; // Valor ajustável para controlar a diferença vertical entre as linhas

        for (int i = startIndex; i < logs.size(); i++) {
            String log = logs.get(i);
            int logY = renderY + 20 + ((i - startIndex) * lineHeight);
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.5, 0.5, 0.5);
                int maxWidth = (int) (390 * 2);
                List<String> wrappedLines = Minecraftable.mc.fontRenderer.listFormattedStringToWidth(log, maxWidth);
                int lineNumber = 0;
                for (String wrappedLine : wrappedLines) {
                    int wrappedLineY = renderY + 20 + ((i - startIndex) * lineHeight) + (lineNumber * lineHeight); // Adiciona o deslocamento do índice i
                    Minecraftable.mc.fontRenderer.drawString(wrappedLine, (renderX + 10) * 2, wrappedLineY * 2, -1);

                    lineNumber++;
                }
                GlStateManager.popMatrix();
            }

        if(selectAll){
            float width = Minecraftable.mc.fontRenderer.getStringWidth(inputString);
            RenderUtil.drawRect(renderX + 17, renderY + 182, width + 7, 10, new Color(70, 112, 255, 160).getRGB());
        }

        if(mouseX > renderX + 375 && mouseX < renderX + 392 && mouseY > renderY + 183 && mouseY < renderY + 193){
            RenderUtil.drawRoundedRect(renderX + 375, renderY + 182, 17, 10, 5, new Color(120, 17, 225, 255).brighter());
        } else {
            RenderUtil.drawRoundedRect(renderX + 375, renderY + 182, 17, 10, 5, new Color(120, 17, 217, 255));
        }

        Minecraftable.mc.fontRenderer.drawStringWithShadow("->", renderX + 378, renderY + 183, -1);
    }

    public static void mouseClicked(int mouseX, int mouseY, int mouseButton){
        if (!(mouseX > renderX + 385 && mouseX < renderX + 395 && mouseY > renderY + 5 && mouseY < renderY + 15) && mouseX > renderX && mouseX < renderX + 400 && mouseY > renderY && mouseY < renderY + 15) {
            drag = true;
            dragX = x - mouseX;
            dragY = y - mouseY;
        } else {
            drag = false;
        }

        if (mouseX > renderX + 5 && mouseX < renderX + 373 && mouseY > renderY + 180 && mouseY < renderY + 195) {
            isTyping = true;
            logs.add("console: " + "Minecraft");
        } else {
            isTyping = false;
        }

        if (mouseX > renderX + 375 && mouseX < renderX + 392 && mouseY > renderY + 183 && mouseY < renderY + 193) {
            if(inputString.length() > 0) {
                CommandExc.excString(inputString);
            }
            isTyping = true;
        }

        if (mouseX > renderX + 385 && mouseX < renderX + 395 && mouseY > renderY + 5 && mouseY < renderY + 15) {
            isTyping = false;
            drag = false;
            Minecraftable.mc.displayGuiScreen(null);
        }

        selectAll = false;
    }

    public static void mouseReleased(int mouseX, int mouseY, int state) {
        drag = false;
    }

    public static void keyTyped(char typedChar, int keyCode) {
        if (isTyping) {
            if (keyCode == 1) {
                isTyping = false;
            } else if (keyCode == 28) {
                if(inputString.length() > 0) {
                    CommandExc.excString(inputString);
                    inputString = ""; // Limpa a string de entrada após o comando ser executado
                }
            } else if (keyCode == 14) {
                if (inputString.length() > 0) {
                    inputString = inputString.substring(0, inputString.length() - 1);
                }
            } else if (keyCode == 203) {
                if (inputString.length() > 0) {
                    inputString = inputString.substring(0, inputString.length() - 1);
                }
            } else if (keyCode == 205) {
                if (inputString.length() > 0) {
                    inputString = inputString.substring(0, inputString.length() - 1);
                }
            } else if (keyCode == 211) {
                if (inputString.length() > 0) {
                    inputString = inputString.substring(0, inputString.length() - 1);
                }
            } else if (keyCode == 47 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
                try {
                    inputString = inputString + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (keyCode == 30 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
                selectAll = true;
                ConsoleAPI.log("Selected all text");
            } else if (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29)) {
                return;

            } else  if (keyCode == 15) {
                if (!inputString.isEmpty()) {
                    TabComplete.index++;
                    TabComplete.tabComplete(inputString);
                }
            } else if(!(keyCode == 58 || keyCode == 15 || keyCode == 42 || keyCode == 54 || keyCode == 56 || keyCode == 29 || keyCode == 157 || keyCode == 184 || keyCode == 200 || keyCode == 208 || keyCode == 9)) {
                inputString += typedChar;
            }

                if(selectAll){
                    if (keyCode == 47 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
                        try {
                            inputString = "" + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        selectAll = false;
                    }

                    if (keyCode == 14) {
                        if (inputString.length() > 0) {
                            inputString = "";
                        }
                        selectAll = false;
                    }

                    if (keyCode == 46 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29))) {
                        try {
                            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(inputString), null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (!(keyCode == 30 || keyCode == 46 || keyCode == 29 && (Keyboard.isKeyDown(157) || Keyboard.isKeyDown(29)))) {
                    selectAll = false;
                }
            }

        if (keyCode == 200) {
            if (lastInputStringIndex > 0) {
                lastInputStringIndex--;
                inputString = lastInputString.get(lastInputStringIndex);
            }
        } else if (keyCode == 208) { // Tecla de seta para baixo
            if (lastInputStringIndex < lastInputString.size() - 1) {
                lastInputStringIndex++;
                inputString = lastInputString.get(lastInputStringIndex);
            } else if (lastInputStringIndex == lastInputString.size() - 1) {
                lastInputStringIndex++;
                inputString = "";
            }
        }

        if (keyCode == 1) {
            Minecraftable.mc.displayGuiScreen(null);
        }
    }
}