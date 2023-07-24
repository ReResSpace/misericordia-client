package halq.misericordia.fun.managers.text;

import halq.misericordia.fun.core.fontcore.CustomFont;
import halq.misericordia.fun.utils.Minecraftable;
import halq.misericordia.fun.utils.utils.TimerUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author accessmodifier364
 * @since 24-Nov-2021
 */

public class TextManager implements Minecraftable {

    public static TextManager INSTANCE;
    private final TimerUtil idleTimer = new TimerUtil();
    public int scaledWidth;
    public int scaledHeight;
    public int scaleFactor;
    private CustomFont customFont = new CustomFont(new Font("Lato", Font.ITALIC, 18), true, true);
    private boolean idling;

    public void drawRainbowString(String text, float x, float y, int startColor, float factor, boolean shadow, boolean custom) {

        Color currentColor = new Color(startColor);
        float hueIncrement = 1.0f / factor;
        String[] rainbowStrings = text.split("\u00a7.");
        float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
        float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
        float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        for (int i = 0; i < text.length(); ++i) {
            char currentChar = text.charAt(i);
            char nextChar = text.charAt(MathHelper.clamp(i + 1, 0, text.length() - 1));
            final boolean equals = (String.valueOf(currentChar) + nextChar).equals("\u00a7r");
            if (equals) {
                shouldRainbow = false;
            } else if ((String.valueOf(currentChar) + nextChar).equals("\u00a7+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
                continue;
            }
            if (equals) {
                String escapeString = text.substring(i);
                this.drawString(escapeString, x + (float) currentWidth, y, Color.WHITE.getRGB(), shadow, custom);
                break;
            }
            this.drawString(String.valueOf(currentChar).equals("\u00a7") ? "" : String.valueOf(currentChar), x + (float) currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB(), shadow, custom);
            if (String.valueOf(currentChar).equals("\u00a7")) {
                shouldContinue = true;
            }
            currentWidth += this.getStringWidth(String.valueOf(currentChar));
            if (String.valueOf(currentChar).equals(" ")) continue;
            currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
            currentHue += hueIncrement;
        }

    }

    public void drawStringWithShadow(String text, float x, float y, int color, boolean custom) {
        this.drawString(text, x, y, color, true, custom);
    }

    public void drawString(String text, float x, float y, int color, boolean shadow, boolean custom) {
        if (custom) {
            if (shadow) {
                this.customFont.drawStringWithShadow(text, x, y, color);
                return;
            }
            this.customFont.drawString(text, x, y, color);
            return;
        }
        mc.fontRenderer.drawString(text, x, y, color, shadow);
    }

    public void drawStringWithSizeChanger(String text, float x, float y, int color, boolean shadow, boolean custom, int size) {
        CustomFont cf = new CustomFont(new Font("Lato", Font.ITALIC, size), true, true);

        if (custom) {
            if (shadow) {
                cf.drawStringWithShadow(text, x, y, color);
                return;
            }
            cf.drawString(text, x, y, color);
            return;
        }
        mc.fontRenderer.drawString(text, x, y, color, shadow);
    }

    public int getStringWidth(String text) {
        return mc.fontRenderer.getStringWidth(text);
    }

    public int getFontHeight() {
        return mc.fontRenderer.FONT_HEIGHT;
    }

    public void setFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        this.customFont = new CustomFont(font, antiAlias, fractionalMetrics);
    }

    public Font getCurrentFont() {
        return this.customFont.getFont();
    }

    public void updateResolution() {
        this.scaledWidth = mc.displayWidth;
        this.scaledHeight = mc.displayHeight;
        this.scaleFactor = 1;
        boolean flag = mc.isUnicode();
        int i = mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
            ++this.scaleFactor;
        }
        if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            --this.scaleFactor;
        }
        double scaledWidthD = (double) this.scaledWidth / (double) this.scaleFactor;
        double scaledHeightD = (double) this.scaledHeight / (double) this.scaleFactor;
        this.scaledWidth = MathHelper.ceil(scaledWidthD);
        this.scaledHeight = MathHelper.ceil(scaledHeightD);
    }

    public String getIdleSign() {
        if (this.idleTimer.passedMs(500L)) {
            this.idling = !this.idling;
            this.idleTimer.reset();
        }
        if (this.idling) {
            return "_";
        }
        return "";
    }

    public void drawString(String text, int x, int y, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.5, 0.5, 0.5); // Reduzir pela metade para obter um tamanho de fonte menor
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        mc.fontRenderer.drawString(text, x * 2, y * 2, color);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

}
