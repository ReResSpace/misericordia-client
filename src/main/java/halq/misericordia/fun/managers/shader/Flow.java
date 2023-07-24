package halq.misericordia.fun.managers.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL20;

public class Flow extends FramebufferShader {
    public static Flow Flow_Shader;
    public float time;
    public float timef = 0.05f;

    public Flow() {
        super("flow.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("resolution");
        this.setupUniform("time");
    }

    @Override
    public void updateUniforms() {
        GL20.glUniform2f(this.getUniform("resolution"), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), (float)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight());
        GL20.glUniform1f(this.getUniform("time"), this.time);
        time += timef * animationSpeed;
    }

    static {
        Flow.Flow_Shader = new Flow();
    }
}