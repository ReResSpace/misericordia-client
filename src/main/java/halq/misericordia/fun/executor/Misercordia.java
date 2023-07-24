package halq.misericordia.fun.executor;

import halq.misericordia.fun.gui.console.core.ConsoleAPI;
import halq.misericordia.fun.gui.igui.ClickGuiScreen;
import halq.misericordia.fun.managers.command.CommandManager;
import halq.misericordia.fun.managers.config.ConfigManager;
import halq.misericordia.fun.managers.text.TextManager;
import halq.misericordia.fun.managers.module.ModuleManager;
import halq.misericordia.fun.managers.setting.SettingManager;
import halq.misericordia.fun.utils.utils.IconsUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;

import java.io.InputStream;
import java.nio.ByteBuffer;

@Mod(modid = Misercordia.MOD_ID, name = Misercordia.MOD_NAME, version = Misercordia.VERSION)
public class Misercordia {
//
    public static final String MOD_ID = "misericordia";
    public static final String MOD_NAME = "Misericordia";
    public static final String VERSION = "0.0.3";

    public ClickGuiScreen clickGuiScreen;

    @Mod.Instance
    public static Misercordia INSTANCE;

    /**
     * Is better setup all using postInit, the loader has more time to load all.
     */

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Display.setTitle(MOD_NAME + " " + VERSION);

        SettingManager.INSTANCE = new SettingManager();
        ConfigManager.INSTANCE = new ConfigManager();
        TextManager.INSTANCE = new TextManager();
        ModuleManager.INSTANCE = new ModuleManager();
        CommandManager.INSTANCE = new CommandManager();
        ConfigManager.INSTANCE.loadConfigs();
        clickGuiScreen = new ClickGuiScreen();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.setIcon();
    }

    public static void setWindowIcon() {
        //from europa client
        try (InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/misericordia/icon16.png");
             InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/misericordia/icon32.png")) {
            ByteBuffer[] icons = new ByteBuffer[]{IconsUtil.INSTANCE.readImageToBuffer(inputStream16x), IconsUtil.INSTANCE.readImageToBuffer(inputStream32x)};
            Display.setIcon(icons);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIcon() {
        this.setWindowIcon();
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event) {
        if (ConsoleAPI.chatlog) {
            ConsoleAPI.log(event.getMessage().getUnformattedText());
        }
    }
}

